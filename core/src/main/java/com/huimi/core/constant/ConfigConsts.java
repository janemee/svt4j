package com.huimi.core.constant;

/**
 * 系统配置常量接口
 * 定义了系统配置项的key值，这些配置通常存储在数据库或Redis中
 * 注意：当前OSS配置实际上是硬编码在OSSClientUtils中的，并未使用这些常量
 *
 * @author Vector
 * @create 2017-06-12 15:06
 */
public interface ConfigConsts {

    String WEB_NAME = "web_name";
    String VERIFY_CODE_TIME = "verify_code_time";
    String SUPER_validate_open = "super_validate_open";
    String STATIC_SERVER_URL = "static_server_url";
    String SMS_BUSINSYS_NO = "sms_businsys_no";
    String SMS_URL = "sms_url";
    String SMS_CAMPAIGN_ID = "sms_campaign_id";
    String WEB_SIGN = "web_sign";
    String SMS_OPEN = "sms_open";
    String SMS_APIKEY = "sms_apikey";
    String PC_SERVER_URL = "pc_server_url";
    /** 阿里云OSS Endpoint（未使用，配置硬编码在OSSClientUtils中） */
    String OSS_ENDPOINT = "oss_endpoint";
    /** 阿里云OSS Bucket名称（未使用，配置硬编码在OSSClientUtils中） */
    String OSS_BUCKET_NAME = "oss_bucket_name";
    /** 阿里云OSS AccessKey Secret（未使用，配置硬编码在OSSClientUtils中） */
    String OSS_ACCESSKEY_SECRET = "oss_accesskey_secret";
    /** 阿里云OSS AccessKey ID（未使用，配置硬编码在OSSClientUtils中） */
    String OSS_ACCESSKEY_ID = "oss_accesskey_id";
    /** 图片服务器地址 */
    String IMAGE_SERVER_URL = "image_server_url";
    String COPY_RIGHT = "copy_right";
    String APP_SERVER_URL = "app_server_url";
    /**
     * api接口服务器缓存标志
     */
    String API_web_SERVER_URL = "api_web_server_url";
    String API_quote_SERVER_URL = "api_quote_server_url";
    String ADMIN_SERVER_URL = "admin_server_url";
    String MAX_PWD_ERROR_COUNT = "max_pwd_error_count";

    /**
     * RSA加密公钥
     */
    String PUBLIC_KEY  ="PUBLIC_KEY";

    String SUPER_VALIDATE_CODE = "9999";

    /**
     * 充值提现-手续费-按笔数count
     */
    String FEE_COUNT="fee_count";
    /**
     * 充值提现-手续费-按比例ratio
     */
    String FEE_RATIO="fee_ratio";
    /**
     * 提现-手续费-收取方式 ratio按比例,count按笔数
     */
    String CASH_FEE_TYPE ="cash_fee_type";
    /**
     * 充值-手续费-收取方式 ratio按比例,count按笔数
     */
    String RECHARGE_FEE_TYPE = "recharge_fee_type";
    /**
     * 用户推广分享内容
     */
    String SHARE_CONTENT="share_content";
    /**
     * 用户推广分享图片
     */
    String SHARE_PIC="share_pic";
    //提现收取比例
    String WITHDRAW_FEE_SCALE = "withdraw_fee_scale";
    //提现最低收取
    String WITHDRAW_FEE_LOWEST = "withdraw_fee_lowest";
}
