package com.huimi.apis.controller.v1;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.common.entity.PageResult;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.bs.BsApplicationAreaPo;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.bs.BsApplicationAreaService;
import com.huimi.core.task.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;


@RestController

@RequestMapping("/v1/applicationArea")
@Api(tags = "产品模块")
public class ApplicationAreaController extends WebGenericController<Integer, Task> {
    private static final Logger log = LoggerFactory.getLogger(ApplicationAreaController.class);

    @Autowired
    private BsApplicationAreaService bsApplicationAreaService;


    /**
     * 应用领域列表(分页)
     *
     * @param page     页数
     * @param pageSize 数据条数
     * @param title    标题
     * @return
     */
    @ApiOperation(value = "产品列表")
    @RequestMapping("/listPage")
    public ResultEntity<Object> listPage(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "20") Integer pageSize,
                                            @RequestParam(defaultValue = "") String title) {
        try {
            DtGrid dtGrid = new DtGrid();
            dtGrid.setNowPage(page);
            dtGrid.setPageSize(pageSize);
            StringBuilder whereSql = new StringBuilder();
            if (StringUtils.isNotBlank(title)) {
                whereSql.append(" and application_title like '%").append(title).append("%'");
            }
            whereSql.append(" and del_flag = ").append(Admin.DELFLAG.NO.code).append(" ");
            whereSql.append(" and status = ").append(EnumConstants.ApplicationStatusEunm.YES.value).append(" ");
            dtGrid.setWhereSql(whereSql.toString());
            dtGrid.setSortSql("order by update_time DESC");
            dtGrid = bsApplicationAreaService.getDtGridList(dtGrid);

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
     * 应用领域详情(分页)
     *
     * @param id 应用领域id
     * @return
     */
    @ApiOperation(value = "应用领域详情")
    @RequestMapping("/info")
    public ResultEntity<Object> info(@RequestParam Integer id) {
        try {
            if(Objects.isNull(id)){
                return fail("参数有误");
            }
            BsApplicationAreaPo bsApplicationAreaPo  = bsApplicationAreaService.selectByPrimaryKey(id);
            if(Objects.isNull(bsApplicationAreaPo)){
                return  fail("应用信息不存在不存在,请检查后重试");
            }
            if(EnumConstants.ApplicationStatusEunm.YES.value != bsApplicationAreaPo.getStatus()){
             return fail("该信息未发布,请检查后重试");
            }
            return ok(bsApplicationAreaPo, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }
}
