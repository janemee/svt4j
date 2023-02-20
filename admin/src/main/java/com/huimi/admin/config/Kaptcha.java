package com.huimi.admin.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码插件
 */
@Configuration
public class Kaptcha {

    @Value("${com.mi.kaptcha.border}")
    private String border;

    @Value("${com.mi.kaptcha.border.color}")
    private String borderColor;

    @Value("${com.mi.kaptcha.textproducer.font.color}")
    private String fontColor;

    @Value("${com.mi.kaptcha.image.width}")
    private String imageWidth;

    @Value("${com.mi.kaptcha.image.height}")
    private String imageHeight;

    @Value("${com.mi.kaptcha.textproducer.font.size}")
    private String fontSize;

    @Value("${com.mi.kaptcha.session.key}")
    private String sessionKey;

    @Value("${com.mi.kaptcha.background.clear.from}")
    private String clearFrom;

    @Value("${com.mi.kaptcha.background.clear.to}")
    private String clearTo;

    @Value("${com.mi.kaptcha.textproducer.char.length}")
    private String charLength;

    @Value("${com.mi.kaptcha.textproducer.font.names}")
    private String fontNames;

    @Value("${com.mi.kaptcha.textproducer.char.string}")
    private String charString;

    @Value("${com.mi.kaptcha.noise.impl}")
    private String noiseImpl;

    @Value("${com.mi.kaptcha.obscurificator.impl}")
    private String obscurificatorImpl;

    @Bean
    public DefaultKaptcha captchaProducer() {
        Properties p = new Properties();
        p.setProperty("kaptcha.border", border);
        p.setProperty("kaptcha.border.color", borderColor);
        p.setProperty("kaptcha.textproducer.font.color", fontColor);
        p.setProperty("kaptcha.image.width", imageWidth);
        p.setProperty("kaptcha.image.height", imageHeight);
        p.setProperty("kaptcha.textproducer.font.size", fontSize);
        p.setProperty("kaptcha.session.key", sessionKey);
        p.setProperty("kaptcha.background.clear.from", clearFrom);
        p.setProperty("kaptcha.background.clear.to", clearTo);
        p.setProperty("kaptcha.textproducer.char.length", charLength);
        p.setProperty("kaptcha.textproducer.font.names", fontNames);
        p.setProperty("kaptcha.textproducer.char.string", charString);
        p.setProperty("kaptcha.noise.impl", noiseImpl);
        p.setProperty("kaptcha.obscurificator.impl", obscurificatorImpl);
        Config config = new Config(p);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
