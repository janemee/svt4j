package com.huimi.apis.controller.v1;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.PageResult;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.bs.BsMediaCenterPo;
import com.huimi.core.po.bs.BsProductItemPo;
import com.huimi.core.po.bs.BsProductPo;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.bs.BsMediaCenterService;
import com.huimi.core.service.bs.BsMessageBoardService;
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


/**
 * 媒体中心模块
 * @author Jiazngxiaobai
 */
@RestController

@RequestMapping("/v1/mediaCenter")
@Api(tags = "媒体中心模块")
public class MediaCenterController extends WebGenericController<Integer, BsMediaCenterPo> {
    private static final Logger log = LoggerFactory.getLogger(MediaCenterController.class);

    @Autowired
    private BsMediaCenterService bsMediaCenterService;


    /**
     * 产品列表(分页)
     *
     * @param page     页数
     * @param pageSize 数据条数
     * @param title    标题
     * @return
     */
    @ApiOperation(value = "媒体中心列表")
    @RequestMapping("/mediaCenterList")
    public ResultEntity<Object> productList(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "20") Integer pageSize,
                                            @RequestParam(defaultValue = "") String title,
                                            @RequestParam(defaultValue = "1") Integer type) {
        try {
            DtGrid dtGrid = new DtGrid();
            dtGrid.setNowPage(page);
            dtGrid.setPageSize(pageSize);
            StringBuilder whereSql = new StringBuilder();
            if (StringUtils.isNotBlank(title)) {
                whereSql.append(" and title like '%").append(title).append("%'");
            }
            whereSql.append(" and del_flag = ").append(Admin.DELFLAG.NO.code).append(" ");
            whereSql.append(" and status = ").append(EnumConstants.ApplicationStatusEunm.YES.value).append(" ");
            whereSql.append(" and type = ").append(type).append(" ");
            dtGrid.setWhereSql(whereSql.toString());
            dtGrid.setSortSql("order by update_time DESC");
            dtGrid = bsMediaCenterService.getDtGridList(dtGrid);

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


    @ApiOperation(value = "媒体详情")
    @RequestMapping("/info")
    public ResultEntity<Object> productInfo(@RequestParam Integer id) {
        try {
            if(Objects.isNull(id)){
                return fail("参数有误");
            }
            BsMediaCenterPo bsMediaCenterPo  = bsMediaCenterService.selectByPrimaryKey(id);
            if(Objects.isNull(bsMediaCenterPo) || EnumConstants.ApplicationStatusEunm.YES.value != bsMediaCenterPo.getStatus()){
                return  fail("产品不存在,请检查后重试");
            }
            return ok(bsMediaCenterPo, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }
}
