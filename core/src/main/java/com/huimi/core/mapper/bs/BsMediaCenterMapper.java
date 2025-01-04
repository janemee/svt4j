package com.huimi.core.mapper.bs;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.bs.BsMediaCenterPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BsMediaCenterMapper extends GenericMapper<BsMediaCenterPo, Integer> {


    @Select("select * from  bs_media_center where title  = #{title} and del_flag = 1 ")
    List<BsMediaCenterPo> findByNameList(@Param("title") String title);


    @Update("update bs_media_center set del_flag = 0 , update_time = now() where id = #{id}")
    void updateDelFlagById(@Param("id") Long id);

    @Update("update bs_media_center set status = #{status}, update_time = now()  where id = #{id} ")
    void updateStatusById(@Param("status") int status, @Param("id") int id);

}
