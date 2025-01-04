package com.huimi.admin.controller.mediaCenter;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.bs.BsMediaCenterPo;
import com.huimi.core.service.bs.BsMediaCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;

/**
 * 客户留言跳转页面控制类
 */
@Controller
@RequestMapping(BaseController.BASE_URI + "/bsMediaCenter")
public class BsMediaCenterController extends BaseController {
    @Autowired
    private BsMediaCenterService bsMediaCenterService;


    @RequestMapping("/list")
    public String list() {
        return getAdminTemplate("bsMediaCenter/list");
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("typeList", EnumConstants.MediaCenterTypeEunm.getList());
        modelAndView.setViewName("bsMediaCenter/add");
        return modelAndView;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ModelAndView update(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BsMediaCenterPo bsMediaCenterPo = bsMediaCenterService.selectByPrimaryKey(ids);
        Map<String, Integer> typeMap = EnumConstants.MediaCenterTypeEunm.getEnumMap();
        modelAndView.addObject("typeList", EnumConstants.MediaCenterTypeEunm.getList());
        modelAndView.addObject("model", bsMediaCenterPo);
        modelAndView.setViewName("bsMediaCenter/update");
        return modelAndView;
    }


    @RequestMapping("/getById")
    public ModelAndView getById(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        if(Objects.isNull(ids)){
            modelAndView.setViewName("bsMediaCenter/list");
            return modelAndView;
        }
        BsMediaCenterPo bsMediaCenterPo = bsMediaCenterService.selectByPrimaryKey(ids);
        modelAndView.addObject("typeList", EnumConstants.MediaCenterTypeEunm.getList());
        modelAndView.addObject("model",bsMediaCenterPo);
        modelAndView.setViewName("bsMediaCenter/getById");
        return modelAndView;
    }


    @RequestMapping("/lookQeCodeImg")
    public ModelAndView lookQeCodeImg(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BsMediaCenterPo bsMediaCenterPo = bsMediaCenterService.selectByPrimaryKey(ids);
        modelAndView.addObject("typeList", EnumConstants.MediaCenterTypeEunm.getList());
        modelAndView.addObject("model",bsMediaCenterPo);
        modelAndView.setViewName("bsMediaCenter/qrCodeImg");
        return modelAndView;
    }
}
