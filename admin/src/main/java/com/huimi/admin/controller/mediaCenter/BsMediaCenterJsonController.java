package com.huimi.admin.controller.mediaCenter;

import com.huimi.admin.controller.BaseController;
import com.huimi.admin.utils.AdminSessionHelper;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.bs.BsMediaCenterPo;
import com.huimi.core.po.system.Admin;
import com.huimi.core.po.user.UserIdentify;
import com.huimi.core.service.bs.BsMediaCenterService;
import com.huimi.core.service.bs.BsMessageBoardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static com.huimi.common.entity.ResultEntity.fail;

/**
 * 公告控制类
 */
@RestController
@RequestMapping(BaseController.BASE_URI + "/bsMediaCenter/json")
public class BsMediaCenterJsonController extends BaseController {
    @Resource
    private BsMediaCenterService bsMediaCenterService;

    @RequestMapping("/list")
    @ResponseBody
    public DtGrid listJson(HttpServletRequest request, int rows, int page) throws Exception {
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        DtGrid dtGrid = new DtGrid();
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String name = request.getParameter("search_val");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and title like '%").append(name).append("%'");
        }
        whereSql.append(" and del_flag = ").append(Admin.DELFLAG.NO.code).append(" ");
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        dtGrid = bsMediaCenterService.getDtGridList(dtGrid);
        return dtGrid;
    }

    /**
     *添加
     */
    @ResponseBody
    @RequestMapping("/add")
    public ResultEntity isAsideJson(BsMediaCenterPo request) {
        try {
            request.setCreateTime(new Date());
            request.setCreator(AdminSessionHelper.getAdminName());
            bsMediaCenterService.insert(request);
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     *更新
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResultEntity leaveAsideJson(BsMediaCenterPo request) {
        try {
            request.setUpdateTime(new Date());
            request.setUpdator(AdminSessionHelper.getAdminName());
            bsMediaCenterService.updateByPrimaryKeySelective(request);
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     *更新状态
     */
    @ResponseBody
    @RequestMapping("/startUp")
    public ResultEntity startUpJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要操作的数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsMediaCenterService.updateStatus(Integer.parseInt(id), EnumConstants.ApplicationStatusEunm.YES.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     *更新状态
     */
    @ResponseBody
    @RequestMapping("/disable")
    public ResultEntity disableJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要操作的数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsMediaCenterService.updateStatus(Integer.parseInt(id), EnumConstants.ApplicationStatusEunm.NO.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 删除客户留言（逻辑删除）
     */
    @ResponseBody
    @RequestMapping("/del")
    public ResultEntity updateJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要删除的数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsMediaCenterService.updateDelFlagByIds(Long.parseLong(id));
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }
}

