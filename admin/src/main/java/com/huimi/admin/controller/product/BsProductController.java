package com.huimi.admin.controller.product;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.constant.EnumDTO;
import com.huimi.core.po.bs.BsApplicationAreaItemPo;
import com.huimi.core.po.bs.BsProductItemPo;
import com.huimi.core.po.bs.BsProductPo;
import com.huimi.core.service.bs.BsProductItemService;
import com.huimi.core.service.bs.BsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品跳转页面控制类
 */
@Controller
@RequestMapping(BaseController.BASE_URI + "/bsProduct")
public class BsProductController extends BaseController {
    @Autowired
    private BsProductService bsProductService;
    @Autowired
    private BsProductItemService bsProductItemService;


    @RequestMapping("/list")
    public String list() {
        return getAdminTemplate("bsProduct/list");
    }


    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Integer> typeMap = EnumConstants.ProductTypeEunm.getEnumMap();
        modelAndView.addObject("tableTypeValues", typeMap);
        modelAndView.setViewName("bsProduct/add");
        return modelAndView;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ModelAndView update(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BsProductPo bsProductPo = bsProductService.selectByPrimaryKey(ids);
        Map<String, Integer> typeMap = EnumConstants.ProductTypeEunm.getEnumMap();
        modelAndView.addObject("typeMap", typeMap);
        modelAndView.addObject("model", bsProductPo);
        modelAndView.setViewName("bsProduct/update");
        return modelAndView;
    }


    /**
     * 跳转查看二维码图片页面
     *
     * @param ids
     * @return
     */
    @RequestMapping("/lookQeCodeImg")
    public ModelAndView lookQeCodeImg(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BsProductPo bizApkHistory = bsProductService.selectByPrimaryKey(ids);
        modelAndView.addObject("model", bizApkHistory);
        modelAndView.setViewName("bsProduct/qrCodeImg");
        return modelAndView;
    }

    @RequestMapping("/itemList")
    public ModelAndView list(Long ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product_id", ids);
        modelAndView.setViewName("bsProduct/item_list");
        return modelAndView;
    }

    @RequestMapping("/addItem")
    public ModelAndView addItem(Integer product_id) {
        ModelAndView modelAndView = new ModelAndView();
        BsProductItemPo itemPo = new BsProductItemPo();
        itemPo.setProductId(product_id);
        itemPo.setTableType(EnumConstants.ProductTypeEunm.JINGMI.value);
        itemPo.setStatus(EnumConstants.ApplicationStatusEunm.NO.value);
        modelAndView.addObject("model", itemPo);
        List<EnumDTO> enumDTOList = EnumConstants.ProductTypeEunm.getList();
        modelAndView.addObject("tableTypeList", enumDTOList);
        modelAndView.setViewName("bsProduct/add_item");
        return modelAndView;
    }


    @RequestMapping("/updateItem")
    public ModelAndView updateItem(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BsProductItemPo itemPo = bsProductItemService.selectByPrimaryKey(ids);
        modelAndView.addObject("model", itemPo);
        List<EnumDTO> enumDTOList = EnumConstants.ProductTypeEunm.getList();
        modelAndView.addObject("tableTypeList", enumDTOList);
        modelAndView.setViewName("bsProduct/update_item");
        return modelAndView;
    }
}
