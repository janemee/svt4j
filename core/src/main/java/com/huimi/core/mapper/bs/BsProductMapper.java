package com.huimi.core.mapper.bs;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.bs.BsProductPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BsProductMapper extends GenericMapper<BsProductPo, Integer> {


    @Select("select * from  bs_product where product_title  = #{productTitle} and del_flag = 1 ")
    List<BsProductPo> findByNameList(@Param("productTitle") String productTitle);


    @Update("update bs_product set del_flag = 0 , update_time = now() where id = #{id}")
    void updateDelFlagById(@Param("id") Long id);

    @Update("update bs_product set status = #{status}, update_time = now()  where id = #{id} ")
    void updateStatusById(@Param("status") int status, @Param("id") int id);

}
