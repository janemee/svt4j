package com.huimi.apis.controller.common;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.utils.OSSClientUtils;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.service.cache.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * API文件上传控制器
 * 提供H5和iOS端的文件上传接口，所有文件上传到阿里云OSS
 */
@RestController
@RequestMapping("/api/web/upload")
@Api(tags = "文件上传")
public class UploadController extends WebController {

    @Autowired
    private RedisService redisService;

    /** 用户文件上传基础路径（未使用） */
    private static final String UPLOAD_PATH = "/userFile";

    /**
     * 文件上传类型枚举
     * 定义不同类型文件的存储路径和后缀
     */
    @AllArgsConstructor
    public enum Type {
        /** 身份证图片 - 正面 */
        T1(1, "member/id_card_1", ".jpg"),
        /** 身份证图片 - 背面 */
        T2(2, "member/id_card_2", ".jpg"),
        /** 头像上传 */
        T3(3, "public/avatar_pic", ".jpg"),
        /** 银行卡图片 */
        T4(4, "member/bank_card_pic", ".jpg"),
        /** 聊天图片 */
        T5(5, "member/msg_pic", ".jpg"),
        /** 评价图片 */
        T6(6, "member/comment_pic", ".jpg"),
        ;

        /** 类型值 */
        @Getter
        final int val;
        /** OSS存储路径 */
        @Getter
        final String path;
        /** 文件后缀 */
        @Getter
        final String suffix;

        /**
         * 根据类型值获取枚举
         * @param val 类型值
         * @return 对应的Type枚举，未找到返回null
         */
        public static Type getEnum(int val) {
            for (Type type : Type.values()) {
                if (type.val == val) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * H5文件上传接口
     * 接收base64编码的图片文件上传到OSS
     *
     * @param file base64编码的图片字符串
     * @param type 文件类型：1身份证正面，2身份证背面，3头像，4银行卡，5聊天，6评论
     * @return 包含文件访问路径的结果
     */
    @ApiOperation(value = "文件上传-H5", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "base64转码图片", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型:1身份证正面，2身份证背面，3头像，4银行卡，5聊天，6评论", required = true, dataType = "Integer"),
    })
    @PostMapping("/doUploadH5")
    public ResultEntity uploadForH5(String file, Integer type) {
        Integer loginUserId = getLoginUserId();
        if (null == loginUserId) {
            return fail("请先登录");
        }
        // 如果base64字符串包含逗号前缀，只取逗号后面的部分
        if (file.indexOf(",") > 0) {
            file = file.substring(file.indexOf(",") + 1);
        }
        Type fileType = Type.getEnum(type);
        byte[] b = Base64.decode(file);

        // 生成随机文件名并上传到OSS
        String fileName = RandomUtil.randomUUID() + fileType.getSuffix();
        String filePath = "/" + fileType.getPath() + "/" + fileName;
        String url = OSSClientUtils.uploadFile(b, filePath);

        // 拼接图片服务器地址返回给前端
        Map<String, Object> map = new HashMap<>();
        map.put("path", redisService.get(ConfigNID.IMAGE_SEVER_URL) + "/" + url);
        return ok(map);
    }

    /**
     * iOS文件上传接口
     * 接收MultipartFile文件流上传到OSS
     *
     * @param file 图片文件流
     * @param type 文件类型：1身份证正面，2身份证背面，3头像，4银行卡，5聊天，6评论
     * @return 包含文件访问路径的结果
     */
    @ApiOperation(value = "文件上传-IOS", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片流", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "type", value = "类型:1身份证正面，2身份证背面，3头像，4银行卡，5聊天，6评论", required = true, dataType = "Integer"),
    })
    @PostMapping("/doUploadIOS")
    public ResultEntity uploadForIOS(MultipartFile file, Integer type) {
        Integer loginUserId = getLoginUserId();
        if (null == loginUserId) {
            return fail("请先登录");
        }
        // 判断图片是否为空
        if (file.isEmpty()) {
            return ResultEntity.fail("图片为空");
        }
        // 获取文件扩展名
        String originalFileName = file.getOriginalFilename();
        String extName = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = RandomUtil.randomUUID() + "." + extName;
        Type fileType = Type.getEnum(type);

        try {
            // 上传到OSS
            String filePath = "/" + fileType.getPath() + "/" + fileName;
            String url = OSSClientUtils.uploadFile(file.getBytes(), filePath);

            // 拼接图片服务器地址返回给前端
            Map<String, Object> map = new HashMap<>();
            map.put("path", redisService.get(ConfigNID.IMAGE_SEVER_URL) + "/" + url);

            return ok(map);
        } catch (IOException e) {
            e.printStackTrace();
            return fail("文件上传失败");
        }
    }
}
