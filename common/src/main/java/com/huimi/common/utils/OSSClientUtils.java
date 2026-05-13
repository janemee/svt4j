package com.huimi.common.utils;

import com.aliyun.oss.OSSClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.function.Consumer;

/**
 * 阿里云OSS上传工具类
 * 用于文件上传到阿里云OSS存储服务
 *
 * 注意：OSS配置硬编码在此类中，如需替换OSS配置，请修改以下常量：
 * ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET, BUCKETNAME, BASE_PATH, ACCESS_BASE_PATH
 */
public class OSSClientUtils {
    /** OSS服务Endpoint地址 */
    private static final String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    /** OSS访问密钥ID */
    private static final String ACCESSKEYID = "LTAI4GJLH13DGbtJVeeyukX5";
    /** OSS访问密钥Secret */
    private static final String ACCESSKEYSECRET = "35CLga2w76Uid3iIwLrPhsPg50huGZ";
    /** OSS存储桶名称 */
    private static final String BUCKETNAME =  "douguanjia";
    /** 基础路径前缀 */
    public static final String BASE_PATH = "apk/";
    /** 文件访问前缀URL，用于拼接返回给前端的访问地址 */
    public static final String ACCESS_BASE_PATH = "http://douguanjia.oss-cn-hangzhou.aliyuncs.com";

    /**
     * 获取OSSClient实例
     * @return OSSClient实例
     */
    public static OSSClient getInstence(){
        OSSClient ossClient = new OSSClient(ENDPOINT,ACCESSKEYID,ACCESSKEYSECRET);
        ossClient.createBucket(BUCKETNAME);
        return ossClient;
    }

    /**
     * 通过File对象上传文件到OSS
     * @param imgFile 文件对象
     * @param filePath OSS上的文件存储路径
     */
    public static void doUpload(File imgFile, String filePath) throws Exception {
        OSSClient ossClient = getInstence();
        ossClient.putObject(BUCKETNAME,filePath,imgFile);
        ossClient.shutdown();
    }

    /**
     * 通过字节数组上传文件到OSS
     * 这是项目中主要使用的上传方法
     *
     * @param file_buff 文件字节数组
     * @param filePath OSS上的文件存储路径（不包含BASE_PATH）
     * @return 完整的OSS文件路径（包含BASE_PATH）
     */
    public static String uploadFile(final byte[] file_buff, final String filePath) {
        final String path = BASE_PATH + filePath;
        ossClient(o -> o.putObject(BUCKETNAME, path, new ByteArrayInputStream(file_buff)));
        return path;
    }

    /**
     * OSS客户端操作封装方法
     * 使用函数式编程接口，自动处理OSSClient的创建和关闭
     *
     * @param consumer 接收OSSClient实例并执行操作的函数
     */
    private static void ossClient(Consumer<OSSClient> consumer) {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        try {
            consumer.accept(ossClient);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
        } finally {
            // 关闭client，释放资源
            ossClient.shutdown();
        }
    }
}

