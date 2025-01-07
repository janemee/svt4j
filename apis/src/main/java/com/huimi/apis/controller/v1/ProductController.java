package com.huimi.apis.controller.v1;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.PageResult;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.bs.BsProductItemPo;
import com.huimi.core.po.bs.BsProductPo;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.bs.BsProductItemService;
import com.huimi.core.service.bs.BsProductService;
import com.huimi.core.task.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController

@RequestMapping("/v1/products")
@Api(tags = "产品模块")
public class ProductController extends WebGenericController<Integer, Task> {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private BsProductService bsProductService;
    @Autowired
    private BsProductItemService bsProductItemService;

    /**
     * 产品列表(分页)
     *
     * @param page     页数
     * @param pageSize 数据条数
     * @param title    标题
     * @return
     */
    @ApiOperation(value = "产品列表")
    @RequestMapping("/productList")
    public ResultEntity<Object> productList(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "20") Integer pageSize,
                                            @RequestParam(defaultValue = "") String title) {
        try {
            DtGrid dtGrid = new DtGrid();
            dtGrid.setNowPage(page);
            dtGrid.setPageSize(pageSize);
            StringBuilder whereSql = new StringBuilder();
            if (StringUtils.isNotBlank(title)) {
                whereSql.append(" and product_title like '%").append(title).append("%'");
            }
            whereSql.append(" and del_flag = ").append(Admin.DELFLAG.NO.code).append(" ");
            whereSql.append(" and status = ").append(EnumConstants.ApplicationStatusEunm.YES.value).append(" ");
            dtGrid.setWhereSql(whereSql.toString());
            dtGrid.setSortSql("order by update_time DESC");
            dtGrid = bsProductService.getDtGridList(dtGrid);

            List<Object> poList = dtGrid.getExhibitDatas();
            PageResult result = new PageResult();
            result.setList(poList);
            result.setTotalCount(dtGrid.getPageCount());
            return ok(result, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }


    /**
     * 产品列表(分页)
     *
     * @param productId 产品id
     * @return
     */
    @ApiOperation(value = "产品详情")
    @RequestMapping("/productInfo")
    public ResultEntity<Object> productInfo(@RequestParam Integer productId) {
        try {
            if(Objects.isNull(productId)){
                return fail("参数有误");
            }
            BsProductPo bsProductPo  = bsProductService.selectByPrimaryKey(productId);
            if(Objects.isNull(bsProductPo)){
                return  fail("产品不存在,请检查后重试");
            }
            if(EnumConstants.ApplicationStatusEunm.YES.value != bsProductPo.getStatus()){
             return fail("该产品未发布,请检查后重试");
            }
            List<BsProductItemPo> bsProductItemPos = bsProductItemService.findListByProductId(productId,EnumConstants.ApplicationStatusEunm.YES.value);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("productInfo",bsProductPo);
            //根据类型进行分组
            Map<String,List<BsProductItemPo>> listMap = bsProductItemPos.stream().collect(Collectors.groupingBy(BsProductItemPo::getTableTypeDesc));
            dataMap.putAll(listMap);
            return ok(dataMap, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }
}
