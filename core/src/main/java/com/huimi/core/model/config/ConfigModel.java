package com.huimi.core.model.config;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

/**
 * 基础配置信息类
 * @author Jiazngxiaobai
 */
@Data
@Serialization
public class ConfigModel {
    private String name;
    private String description;
    private String value;


    public ConfigModel(String name, String description, String value){
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public ConfigModel(){

    }
}
