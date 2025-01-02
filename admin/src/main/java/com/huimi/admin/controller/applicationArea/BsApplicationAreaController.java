package com.huimi.admin.controller.applicationArea;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.po.bs.BsApplicationAreaItemPo;
import com.huimi.core.po.bs.BsApplicationAreaPo;
import com.huimi.core.service.bs.BsApplicationAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 文件上传历史
 */
@Controller
@RequestMapping(BaseController.BASE_URI + "/bsApplicationArea")
public class BsApplicationAreaController extends BaseController {
    @Autowired
    private BsApplicationAreaService bsApplicationAreaService;


    @RequestMapping("/list")
    public String list() {
        return getAdminTemplate("bsApplicationArea/list");
    }


    @RequestMapping("/add")
    public String add() {
        return getAdminTemplate("bsApplicationArea/add");
    }

    @RequestMapping("/update")
    @ResponseBody
    public ModelAndView update(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BsApplicationAreaPo bsApplicationAreaPo = bsApplicationAreaService.selectByPrimaryKey(ids);
        modelAndView.addObject("model", bsApplicationAreaPo);
        modelAndView.setViewName("bsApplicationArea/update");
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
        BsApplicationAreaPo bizApkHistory = bsApplicationAreaService.selectByPrimaryKey(ids);
        modelAndView.addObject("model", bizApkHistory);
        modelAndView.setViewName("bsApplicationArea/qrCodeImg");
        return modelAndView;
    }

    @RequestMapping("/itemList")
    public ModelAndView list(Long ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("application_area_id", ids);
        modelAndView.setViewName("bsApplicationArea/item_list");
        return modelAndView;
    }

    @RequestMapping("/addItem")
    public ModelAndView addItem(Integer application_area_id) {
        ModelAndView modelAndView = new ModelAndView();
        BsApplicationAreaItemPo itemPo = new BsApplicationAreaItemPo();
        itemPo.setApplicationAreaId(application_area_id);
        modelAndView.addObject("model", itemPo);
        modelAndView.setViewName("bsApplicationArea/add_item");
        return modelAndView;
    }


    @RequestMapping("/updateItem")
    public ModelAndView updateItem(Integer application_area_id) {
        ModelAndView modelAndView = new ModelAndView();
        BsApplicationAreaItemPo itemPo = new BsApplicationAreaItemPo();
        itemPo.setApplicationAreaId(application_area_id);
        modelAndView.addObject("model", itemPo);
        modelAndView.setViewName("bsApplicationArea/update_item");
        return modelAndView;
    }
}
