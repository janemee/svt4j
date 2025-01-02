package com.huimi.admin.controller.notice;

import com.huimi.admin.controller.BaseController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.bs.BsNoticePo;
import com.huimi.core.po.system.Admin;
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
@RequestMapping(BaseController.BASE_URI + "/bsNotice/json")
public class BsNoticeJsonController extends BaseController {
    @Resource
    private BsNoticeService bsNoticeService;

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
        dtGrid = bsNoticeService.getDtGridList(dtGrid);
        return dtGrid;
    }

    /**
     * 添加产品信息
     */
    @ResponseBody
    @RequestMapping("/add")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(BsNoticePo bsNoticePo) {
        try {
            checked(EnumConstants.FunctionTypeEunm.SAVE.code, bsNoticePo);
            bsNoticePo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if (bsNoticeService.insert(bsNoticePo) == 0) {
                return fail("添加失败，请检查后重试");
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 更新产品信息
     */
    @ResponseBody
    @RequestMapping("/update")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity updateJson(BsNoticePo bsNoticePo) {
        try {
            checked("update", bsNoticePo);
            bsNoticePo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            if (bsNoticeService.updateByPrimaryKeySelective(bsNoticePo) == 0) {
                return fail("更新失败，请检查后重试");
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 删除产品（逻辑删除）
     */
    @ResponseBody
    @RequestMapping("/del")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity updateJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要删除的产品数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsNoticeService.updateDelFlagByIds(Long.parseLong(id));
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 校验产品参数
     */
    public void checked(String type, BsNoticePo bsNoticePo) {
        //检查文件名是否重复
        List<BsNoticePo> nameList = bsNoticeService.findByTitleList(bsNoticePo.getTitle());
        if (EnumConstants.FunctionTypeEunm.SAVE.code.equals(type)) {
            //文件名是否存在
            if (CollectionUtils.isNotEmpty(nameList)) {
                throw new BussinessException("文件名称已存在");
            }
        } else {
        }
    }


    /**
     * 发布
     */
    @ResponseBody
    @RequestMapping("/startUp")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity startUpJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要发布的数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsNoticeService.updateStatus(Integer.parseInt(id),EnumConstants.ApplicationStatusEunm.YES.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 撤回
     */
    @ResponseBody
    @RequestMapping("/disable")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity disableJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要撤回的数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsNoticeService.updateStatus(Integer.parseInt(id),EnumConstants.ApplicationStatusEunm.NO.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }
}

