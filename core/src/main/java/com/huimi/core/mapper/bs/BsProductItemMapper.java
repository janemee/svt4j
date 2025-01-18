package com.huimi.core.mapper.bs;

import com.huimi.common.baseMapper.GenericMapper;
import com.huimi.core.po.bs.BsProductItemPo;
import com.huimi.core.po.bs.BsProductPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BsProductItemMapper extends GenericMapper<BsProductItemPo, Integer> {


    @Select("select * from  bs_product_item where product_title  = #{productTitle} and del_flag = 1  and product_id = #{productId} ")
    List<BsProductPo> findByNameList(@Param("productId") Integer productId, @Param("productTitle") String productTitle);


    @Update("update bs_product_item set del_flag = 0 , update_time = now() where id = #{id}")
    void updateDelFlagById(@Param("id") Integer id);

    @Update(" update bs_product_item set status = #{status} , update_time = now() where id = #{id}")
    void updateStatusById(@Param("status") int status, @Param("id") int id);

    @Select("select * from bs_product_item where product_id = #{productId} and del_flag = 0 and status = #{status} order by update_time desc")
    List<BsProductItemPo> findListByProductId(@Param("productId") Integer productId, @Param("status") Integer status);
}
