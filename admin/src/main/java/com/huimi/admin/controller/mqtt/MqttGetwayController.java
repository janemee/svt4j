package com.huimi.admin.controller.mqtt;

import com.huimi.admin.controller.BaseController;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

/**
 * mqtt 网关接口模块
 */
@Log4j
@Controller
@RequestMapping(BaseController.BASE_URI)
public class MqttGetwayController extends BaseController {
//
//
//    /**
//     * 发送mqtt消息
//     */
//    @RequestMapping(value = "/mqtt/send", method = RequestMethod.GET)
//    public ModelAndView mqttSend(HttpServletResponse response) {
//        ModelAndView modelAndView = new ModelAndView();
//        //如果已经登录跳转到首页
//        if (SecurityUtils.getSubject().isAuthenticated()) {
//            modelAndView.setView(new RedirectView("/s/s/main", true, false));
//            return modelAndView;
//        }
//        response.addHeader("ADMIN_LOGIN_STATE", "0");
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }


}
