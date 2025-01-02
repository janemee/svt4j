package com.huimi.admin.controller.messageBoard;

import com.huimi.admin.controller.BaseController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.bs.BsMessageBoardPo;
import com.huimi.core.po.bs.BsNoticePo;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.bs.BsMessageBoardService;
import com.huimi.core.service.bs.BsNoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

import static com.huimi.common.entity.ResultEntity.fail;

/**
 * 公告控制类
 */
@RestController
@RequestMapping(BaseController.BASE_URI + "/bsMessageBoard/json")
public class BsMessageBoardJsonController extends BaseController {
    @Resource
    private BsMessageBoardService bsMessageBoardService;

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
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        whereSql.append(" and del_flag = ").append(Admin.DELFLAG.NO.code).append(" ");
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        dtGrid = bsMessageBoardService.getDtGridList(dtGrid);
        return dtGrid;
    }

    /**
     *无效
     */
    @ResponseBody
    @RequestMapping("/isAside")
    public ResultEntity isAsideJson(String ids) {
        try {
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsMessageBoardService.updateStatus(Integer.parseInt(id), EnumConstants.MessageBoardStatusEunm.IN_ASIDE.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     *暂不处理
     */
    @ResponseBody
    @RequestMapping("/leaveAside")
    public ResultEntity leaveAsideJson(String ids) {
        try {
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsMessageBoardService.updateStatus(Integer.parseInt(id), EnumConstants.MessageBoardStatusEunm.LEAVE_ASIDE.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     *跟进中
     */
    @ResponseBody
    @RequestMapping("/processing")
    public ResultEntity processingJson(String ids) {
        try {
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsMessageBoardService.updateStatus(Integer.parseInt(id), EnumConstants.MessageBoardStatusEunm.PROCESSING.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     *已完成
     */
    @ResponseBody
    @RequestMapping("/pass")
    public ResultEntity passJson(String ids) {
        try {
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsMessageBoardService.updateStatus(Integer.parseInt(id), EnumConstants.MessageBoardStatusEunm.PASS.value);
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
                return fail("请选择需要删除的产品数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsMessageBoardService.updateDelFlagByIds(Long.parseLong(id));
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }
}

