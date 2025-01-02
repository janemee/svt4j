package com.huimi.admin.controller.messageBoard;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.po.bs.BsMessageBoardPo;
import com.huimi.core.po.bs.BsNoticePo;
import com.huimi.core.service.bs.BsMessageBoardService;
import com.huimi.core.service.bs.BsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 客户留言跳转页面控制类
 */
@Controller
@RequestMapping(BaseController.BASE_URI + "/bsMessageBoard")
public class BsMessageBoardController extends BaseController {
    @Autowired
    private BsMessageBoardService bsMessageBoardService;


    @RequestMapping("/list")
    public String list() {
        return getAdminTemplate("bsMessageBoard/list");
    }

    @RequestMapping("/getById")
    public ModelAndView getById(Integer ids) {
        ModelAndView modelAndView = new ModelAndView();
        BsMessageBoardPo bsMessageBoardPo = bsMessageBoardService.selectByPrimaryKey(ids);
        modelAndView.addObject("model",bsMessageBoardPo);
        modelAndView.setViewName("bsMessageBoard/getById");
        return modelAndView;
    }
}
