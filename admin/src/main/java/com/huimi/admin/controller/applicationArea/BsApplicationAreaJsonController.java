package com.huimi.admin.controller.applicationArea;

import com.huimi.admin.controller.BaseController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.bs.BsApplicationAreaItemPo;
import com.huimi.core.po.bs.BsApplicationAreaPo;
import com.huimi.core.service.bs.BsApplicationAreaItemService;
import com.huimi.core.service.bs.BsApplicationAreaService;
import com.huimi.core.service.cache.RedisService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static com.huimi.common.entity.ResultEntity.fail;

/**
 * create by lja on 2020/7/30 16:45
 */
@RestController
@RequestMapping(BaseController.BASE_URI + "/bsApplicationArea/json")
public class BsApplicationAreaJsonController extends BaseController {
    @Resource
    private BsApplicationAreaService bsApplicationAreaService;
    @Resource
    private BsApplicationAreaItemService bsApplicationAreaItemService;
    @Resource
    private RedisService redisService;

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
            whereSql.append(" and product_title like '%").append(name).append("%'");
        }
        whereSql.append(" and del_flag = 0");
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        dtGrid = bsApplicationAreaService.getDtGridList(dtGrid);
        return dtGrid;
    }

    /**
     * 添加文件上传历史
     */
    @ResponseBody
    @RequestMapping("/add")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(BsApplicationAreaPo bsApplicationAreaPo) {
        try {
            bsApplicationAreaPo.setStatus(EnumConstants.ApplicationStatusEunm.NO.value);
            checked(EnumConstants.FunctionTypeEunm.SAVE.code, bsApplicationAreaPo);
            bsApplicationAreaPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if (bsApplicationAreaService.insert(bsApplicationAreaPo) == 0) {
                return fail("添加失败，请检查后重试");
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 更新文件上传历史
     */
    @ResponseBody
    @RequestMapping("/update")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity updateJson(BsApplicationAreaPo bsApplicationAreaPo) {
        try {
            checked("update", bsApplicationAreaPo);
            bsApplicationAreaPo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            if (bsApplicationAreaService.updateByPrimaryKeySelective(bsApplicationAreaPo) == 0) {
                return fail("更新失败，请检查后重试");
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 删除数据
     */
    @ResponseBody
    @RequestMapping("/del")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity updateJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要删除的数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsApplicationAreaService.updateDelFlagByIds(Long.parseLong(id));
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
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
                bsApplicationAreaService.updateStatus(Integer.parseInt(id),EnumConstants.ApplicationStatusEunm.YES.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 发布
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
                bsApplicationAreaService.updateStatus(Integer.parseInt(id),EnumConstants.ApplicationStatusEunm.NO.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 校验
     */
    public void checked(String type, BsApplicationAreaPo bsApplicationAreaPo) {
        //检查是否存在已启用的文件
        List<BsApplicationAreaPo> list = bsApplicationAreaService.findByStateList(EnumConstants.HistoryState.YES.value);

        //检查文件名是否重复
        List<BsApplicationAreaPo> nameList = bsApplicationAreaService.findByNameList(bsApplicationAreaPo.getApplicationTitle());
        if (EnumConstants.FunctionTypeEunm.SAVE.code.equals(type)) {
            //文件名是否存在
            if (CollectionUtils.isNotEmpty(nameList)) {
                throw new BussinessException("文件名称已存在");
            }
        } else {
            if (EnumConstants.HistoryState.YES.value == bsApplicationAreaPo.getStatus().intValue()) {
                //检查是否存在启用的文件 并且不是当前文件
                if (CollectionUtils.isNotEmpty(list) && !bsApplicationAreaPo.getId().equals(list.get(0).getId())) {
                    throw new BussinessException("存在启用的文件，请先禁用");
                }
            }
            //文件名是否存在 并且不是当前文件
            if (CollectionUtils.isNotEmpty(nameList) && !bsApplicationAreaPo.getId().equals(nameList.get(0).getId())) {
                throw new BussinessException("文件名称已存在");
            }
        }
    }


    @RequestMapping("/itemList")
    @ResponseBody
    public DtGrid itemListJson(HttpServletRequest request, int rows, int page) throws Exception {
        Integer pageSize = rows == 0 ? 1 : rows;
        Integer pageNumber = page == 0 ? 1 : page;
        DtGrid dtGrid = new DtGrid();
        dtGrid.setNowPage(pageNumber);
        dtGrid.setPageSize(pageSize);
        StringBuilder whereSql = new StringBuilder();
        String name = request.getParameter("search_val");
        String applicationAreaId = request.getParameter("application_area_id");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and name like '%").append(name).append("%'");
        }
        whereSql.append(" and del_flag = 0");
        whereSql.append(" and application_area_id = ").append(applicationAreaId).append(" ");
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql(" order by create_time DESC");
        dtGrid = bsApplicationAreaItemService.getDtGridList(dtGrid);
        return dtGrid;
    }


    /**
     * 更新应用领域详情子项
     *
     */
    @ResponseBody
    @RequestMapping("/updateItem")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity updateItemJson(BsApplicationAreaItemPo bsApplicationAreaItemPo) {
        try {
            checked(EnumConstants.FunctionTypeEunm.EDIT.code, bsApplicationAreaItemPo);
            bsApplicationAreaItemPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if (bsApplicationAreaItemService.updateByIdAndVersionSelective(bsApplicationAreaItemPo) == 0) {
                return fail("更新失败，请检查后重试");
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }



    /**
     * 添加应用领域详情子项
     *
     */
    @ResponseBody
    @RequestMapping("/addItem")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addItemJson(BsApplicationAreaItemPo bsApplicationAreaItemPo) {
        try {
            checked(EnumConstants.FunctionTypeEunm.SAVE.code, bsApplicationAreaItemPo);
            bsApplicationAreaItemPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if (bsApplicationAreaItemService.insert(bsApplicationAreaItemPo) == 0) {
                return fail("添加失败，请检查后重试");
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 删除数据
     */
    @ResponseBody
    @RequestMapping("/delItem")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity delItemJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要删除的数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsApplicationAreaItemService.updateDelFlagByIds(Integer.parseInt(id));
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 校验子项参数
     */
    public void checked(String type, BsApplicationAreaItemPo bsApplicationAreaItemPo) {
        //检查文件名是否重复
        List<BsApplicationAreaPo> list = bsApplicationAreaItemService.findByStateList(
                bsApplicationAreaItemPo.getApplicationAreaId(),
                bsApplicationAreaItemPo.getApplicationTitle());
        if (EnumConstants.FunctionTypeEunm.SAVE.code.equals(type)) {
            if (CollectionUtils.isNotEmpty(list)) {
                throw new BussinessException("存在相同标题子项，请检查后重试");
            }
        } else {
        }
    }

}

