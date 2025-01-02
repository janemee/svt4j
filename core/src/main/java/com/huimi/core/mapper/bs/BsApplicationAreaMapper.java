package com.huimi.core.mapper.bs;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.bizApkHistory.BizApkHistory;
import com.huimi.core.po.bizApkHistory.BizApkHistoryModel;
import com.huimi.core.po.bs.BsApplicationAreaPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BsApplicationAreaMapper extends GenericMapper<BsApplicationAreaPo, Integer> {

    @Select(" select * from  bs_application_area_item  where del_flag = 1  and application_area_id = #{applicationAreaId} order by id desc ")
    List<BsApplicationAreaPo> findApplicationItemsByApplicationAreaId(@Param("applicationAreaId") Long applicationAreaId);


    @Select("select * from  bs_application_area where status  = #{status}  and del_flag = 1  ")
    List<BsApplicationAreaPo> findByStateList(@Param("status") int status);


    @Select("select * from  bs_application_area where application_title  = #{applicationTitle} and del_flag = 1  ")
    List<BsApplicationAreaPo> findByNameList(@Param("applicationTitle") String applicationTitle);

    @Select("select * from  bs_application_area where del_flag = 1 and  status  = #{status} order by id desc  limit 1")
    BsApplicationAreaPo findByStateOne(@Param("status") int value);

    @Update("update bs_application_area set del_flag = 0 , update_time = now() where id = #{id}")
    void updateDelFlagById(@Param("id") Long id);

    @Update("update bs_application_area set status = #{status} , update_time = now() where id = #{id}")
    void updateStatusById(@Param("id") Integer id, @Param("status") Integer status);
}
