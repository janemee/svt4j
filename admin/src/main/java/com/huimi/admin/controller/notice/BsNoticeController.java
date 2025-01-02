package com.huimi.admin.controller.notice;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.po.bs.BsNoticePo;
import com.huimi.core.service.bs.BsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 产品跳转页面控制类
 */
@Controller
@RequestMapping(BaseController.BASE_URI + "/bsNotice")
public class BsNoticeController extends BaseController {
    @Autowired
    private BsNoticeService bsNoticeService;


    @RequestMapping("/list")
    public String list() {
        return getAdminTemplate("bsNotice/list");
    }


    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bsNotice/add");
        return modelAndView;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ModelAndView update(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BsNoticePo bsNoticePo = bsNoticeService.selectByPrimaryKey(ids);
        modelAndView.addObject("model", bsNoticePo);
        modelAndView.setViewName("bsNotice/update");
        return modelAndView;
    }


    /**
     * 查看图片
     *
     * @param ids 图片地址
     * @return
     */
    @RequestMapping("/lookQeCodeImg")
    public ModelAndView lookQeCodeImg(String ids) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("imgUrl", ids);
        modelAndView.setViewName("bsNotice/qrCodeImg");
        return modelAndView;
    }
}
