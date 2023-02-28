package com.huimi.admin.controller.mqtt;

import com.huimi.admin.controller.BaseController;
import com.huimi.core.service.mqtt.MQTTServer;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * mqtt 网关接口模块
 */
@Log4j
@Controller
@RequestMapping(BaseController.BASE_URI)
public class MqttGetwayJsonController extends BaseController {

    @Autowired
    private MQTTServer mqttServer;

    /**
     * 发送mqtt消息
     */
    @RequestMapping(value = "/json/mqtt/send", method = RequestMethod.GET)
    public String mqttSend(@Param("topic") String topic, String msg, Integer qos) {
        mqttServer.sendMQTTMessage(topic, msg, qos);
        String data = "发送了一条主题是‘" + topic + "’，内容是:" + msg + "，消息级别 " + qos;
        return data;

    }

    /**
     * 订阅主题
     *
     * @param topic 主题
     * @param qos   消息级别
     * @return
     */
    @RequestMapping(value = "/json/mqtt/testSubsribe")
    public String testSubsribe(String topic, int qos) {
        mqttServer.init(topic, qos);
        return "订阅主题'" + topic + "'成功";
    }

}
