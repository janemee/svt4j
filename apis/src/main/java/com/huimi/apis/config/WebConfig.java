package com.huimi.apis.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.huimi.common.mask.fastjson.DataMaskEmailSerializer;
import com.huimi.common.tools.StringUtil;
import com.huimi.core.constant.Constants;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.common.I18nService;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

/**
 * @author Jiazngxiaobai
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    static {
        //全局配置关闭 Fastjson 循环引用
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
    }

    private RedisService redisService;

    public WebConfig(RedisService redisService) {
        this.redisService = redisService;
    }

//    @Bean
//    public HttpMessageConverter valueFilter() {
//        // 1.定义一个converters转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        // 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(PrettyFormat, WriteMapNullValue);
//        //添加自己写的拦截器
//        fastJsonConfig.setSerializeFilters(new ValueMaskFilter(redisService));
//        // 3.在converter中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
////        fastJsonConfig.getSerializeConfig().put(Date.class,ForceDateCodec.INSTANCE);
//        // 4.将converter赋值给HttpMessageConverter
//        HttpMessageConverter<?> converter = fastConverter;
//        // 5.返回HttpMessageConverters对象
//        return converter;
//    }

//    @Bean
//    public HttpMessageConverter valueFilter2() {
//        // 1.定义一个converters转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        // 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(PrettyFormat, WriteMapNullValue);
//        //添加自己写的拦截器
//        fastJsonConfig.setSerializeFilters(new ValueMaskFilter(redisService));
//        // 3.在converter中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
////        fastJsonConfig.getSerializeConfig().put(Date.class,ForceDateCodec.INSTANCE);
//        // 4.将converter赋值给HttpMessageConverter
//        HttpMessageConverter<?> converter = fastConverter;
//        // 5.返回HttpMessageConverters对象
//        return converter;
//    }


    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        final List<MediaType> supplyMediaTypes = Arrays.stream(
                new MediaType[]{MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM, MediaType.TEXT_PLAIN,
                        MediaType.ALL, MediaType.valueOf("application/vnd.git-lfs+json")}).collect(Collectors.toList());
        FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4 = new FastJsonHttpMessageConverter4();
        fastJsonHttpMessageConverter4.setDefaultCharset(defaultCharset);
        fastJsonHttpMessageConverter4.setSupportedMediaTypes(supplyMediaTypes);
        final FastJsonConfig config = new FastJsonConfig();
        config.setCharset(defaultCharset);
        config.setSerializerFeatures(Constants.SERIALIZER_FEATURES);
        config.getSerializeConfig().put(Object.class, DataMaskEmailSerializer.DATA_MASK_EMAIL_SERIALIZER);
        config.getSerializeConfig().put(Date.class, ForceDateCodec.INSTANCE);
//        config.setSerializeFilters(new ValueMaskFilter());
        System.out.println(config.getSerializeConfig().get(String.class));
        fastJsonHttpMessageConverter4.setFastJsonConfig(config);
        converters.add(fastJsonHttpMessageConverter4);
        super.configureMessageConverters(converters);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }


    private Charset defaultCharset = Charset.forName("UTF-8");
}
