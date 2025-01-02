package com.huimi.core.mapper.bs;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.bs.BsMessageBoardPo;
import com.huimi.core.po.bs.BsNoticePo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 业务公告实体
 *
 * @author Jiazngxiaobai
 */
@Repository
public interface BsMessageBoardMapper extends GenericMapper<BsMessageBoardPo, Integer> {


    @Select("select * from  bs_message_board where name  = #{name} and del_flag = 1 ")
    List<BsMessageBoardPo> findByTitleList(@Param("name") String name);


    @Update("update bs_message_board set del_flag = 0 , update_time = now() where id = #{id}")
    void updateDelFlagById(@Param("id") Long id);

    @Update("update bs_message_board set status = #{status}, update_time = now()  where id = #{id} ")
    void updateStatusById(@Param("id") int id,@Param("status") int status );
}
