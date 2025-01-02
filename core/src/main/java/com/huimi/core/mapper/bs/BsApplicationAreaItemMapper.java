package com.huimi.core.mapper.bs;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.bs.BsApplicationAreaItemPo;
import com.huimi.core.po.bs.BsApplicationAreaPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Jiazngxiaobai
 */
@Repository
public interface BsApplicationAreaItemMapper extends GenericMapper<BsApplicationAreaItemPo, Integer> {


    @Select(" select * from  bs_application_area_item  where del_flag = 1  and application_area_id = #{applicationAreaId} order by id desc ")
    List<BsApplicationAreaItemPo> findApplicationItemsByApplicationAreaId(@Param("applicationAreaId") Integer applicationAreaId);


    @Update("update bs_application_area_item  set del_flag = 0 , update_time = now() where id = #{id}")
    void updateDelFlagById(@Param("id") Integer id);

    @Select(" select * from bs_application_area_item where del_flag = 1  and application_area_id = #{applicationAreaId} and application_title = #{applicationTitle}")
    List<BsApplicationAreaPo> findByStateList(@Param("applicationAreaId") Integer applicationAreaId, @Param("applicationTitle") String applicationTitle);
}
