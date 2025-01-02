package com.huimi.admin.controller.product;

import com.huimi.admin.controller.BaseController;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.entity.dtgrid.DtGrid;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.po.bs.BsProductItemPo;
import com.huimi.core.po.bs.BsProductPo;
import com.huimi.core.po.system.Admin;
import com.huimi.core.service.bs.BsProductItemService;
import com.huimi.core.service.bs.BsProductService;
import com.huimi.core.service.cache.RedisService;
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
 * 产品具体实现控制类
 *
 */
@RestController
@RequestMapping(BaseController.BASE_URI + "/bsProduct/json")
public class BsProductJsonController extends BaseController {
    @Resource
    private BsProductService bsProductService;
    @Resource
    private BsProductItemService bsProductItemService;
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
        whereSql.append(" and del_flag = ").append(Admin.DELFLAG.NO.code).append(" ");
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql("order by create_time DESC");
        dtGrid = bsProductService.getDtGridList(dtGrid);
        return dtGrid;
    }

    /**
     * 添加产品信息
     */
    @ResponseBody
    @RequestMapping("/add")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addJson(BsProductPo bsProductPo) {
        try {
            checked(EnumConstants.FunctionTypeEunm.SAVE.code, bsProductPo);
            bsProductPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if (bsProductService.insert(bsProductPo) == 0) {
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
    public ResultEntity updateJson(BsProductPo bsProductPo) {
        try {
            checked("update", bsProductPo);
            bsProductPo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            if (bsProductService.updateByPrimaryKeySelective(bsProductPo) == 0) {
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
                bsProductService.updateDelFlagByIds(Long.parseLong(id));
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 校验产品参数
     */
    public void checked(String type, BsProductPo bsProductPo) {
        //检查文件名是否重复
        List<BsProductPo> nameList = bsProductService.findByNameList(bsProductPo.getProductTitle());
        if (EnumConstants.FunctionTypeEunm.SAVE.code.equals(type)) {
            //文件名是否存在
            if (CollectionUtils.isNotEmpty(nameList)) {
                throw new BussinessException("文件名称已存在");
            }
        } else {
        }
    }

    /**
     * 产品详情子类列表
     *
     * @param request
     * @param rows
     * @param page
     * @return
     * @throws Exception
     */
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
        String productId = request.getParameter("product_id");
        if (StringUtils.isNotBlank(name)) {
            whereSql.append(" and product_title like '%").append(name).append("%'");
        }
        whereSql.append(" and del_flag = 0");
        whereSql.append(" and product_id = ").append(productId).append(" ");
        dtGrid.setWhereSql(whereSql.toString());
        dtGrid.setSortSql(" order by create_time DESC");
        dtGrid = bsProductItemService.getDtGridList(dtGrid);
        return dtGrid;
    }


    /**
     * 更新应用领域详情子项
     */
    @ResponseBody
    @RequestMapping("/updateItem")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity updateItemJson(BsProductItemPo bsProductItemPo) {
        try {
            checked(EnumConstants.FunctionTypeEunm.EDIT.code, bsProductItemPo);
            bsProductItemPo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            if (bsProductItemService.updateByIdAndVersionSelective(bsProductItemPo) == 0) {
                return fail("更新失败，请检查后重试");
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 添加应用领域详情子项
     */
    @ResponseBody
    @RequestMapping("/addItem")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity addItemJson(BsProductItemPo bsProductItemPo) {
        try {
            checked(EnumConstants.FunctionTypeEunm.SAVE.code, bsProductItemPo);
            bsProductItemPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if (bsProductItemService.insert(bsProductItemPo) == 0) {
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
                bsProductItemService.updateDelFlagByIds(Integer.parseInt(id));
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
                bsProductService.updateStatus(Integer.parseInt(id),EnumConstants.ApplicationStatusEunm.YES.value);
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
                bsProductService.updateStatus(Integer.parseInt(id),EnumConstants.ApplicationStatusEunm.NO.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 发布子项
     */
    @ResponseBody
    @RequestMapping("/startUpItem")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity startUpItemJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要发布的数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsProductItemService.updateStatus(Integer.parseInt(id),EnumConstants.ApplicationStatusEunm.YES.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 撤回产品详情子项
     */
    @ResponseBody
    @RequestMapping("/disableItem")
//    @RequiresPermissions("sys:config:save")
    public ResultEntity disableItemJson(String ids) {
        try {
            if (StringUtil.isBlank(ids)) {
                return fail("请选择需要撤回的数据");
            }
            String[] strings = ids.split(",");
            for (String id : strings) {
                bsProductItemService.updateStatus(Integer.parseInt(id),EnumConstants.ApplicationStatusEunm.NO.value);
            }
            return ResultEntity.success();
        } catch (BussinessException e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 校验子项参数
     */
    public void checked(String type, BsProductItemPo bsProductItemPo) {
        //检查文件名是否重复
        List<BsProductPo> list = bsProductItemService.findByNameList(
                bsProductItemPo.getProductId(),
                bsProductItemPo.getProductTitle());
        if (EnumConstants.FunctionTypeEunm.SAVE.code.equals(type)) {
            if (CollectionUtils.isNotEmpty(list)) {
                throw new BussinessException("存在相同标题子项，请检查后重试");
            }
        } else {
        }
    }

}

