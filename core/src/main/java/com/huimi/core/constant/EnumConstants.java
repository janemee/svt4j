package com.huimi.core.constant;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举常量类
 */
public class EnumConstants {


    /**
     * redis 缓存过期时间
     */
    public enum ExpireTime {
        zero(0, "zero", "零秒"),
        five(5, "five", "五秒"),
        ten(10, "ten", "十秒");


        public final String code;
        public final int value;
        public final String desc;
        private static Map<String, Integer> map = new HashMap<>();

        ExpireTime(int value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (ExpireTime status : ExpireTime.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {

            for (ExpireTime status : ExpireTime.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (ExpireTime status : ExpireTime.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }


    /**
     * redis 缓存过期时间
     */
    public enum EquipmentUpgradeType {
        VERSION(1, "version", "版本更新"),
        ALL(2, "all", "全量更新");

        public final String code;
        public final int value;
        public final String desc;
        private static Map<String, Integer> map = new HashMap<>();

        EquipmentUpgradeType(int value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (EquipmentUpgradeType status : EquipmentUpgradeType.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {

            for (EquipmentUpgradeType status : EquipmentUpgradeType.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (EquipmentUpgradeType status : EquipmentUpgradeType.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 结束任务的方式
     */
    public enum overType {
        MAINTASK(1, "mainTask", "停止主任务"),
        SUBSIDIARY(2, "subsidiary", "停止主任务下疯狂点屏");

        public final String code;
        public final int value;
        public final String desc;
        private static Map<String, Integer> map = new HashMap<>();

        overType(int value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (EquipmentUpgradeType status : EquipmentUpgradeType.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {

            for (EquipmentUpgradeType status : EquipmentUpgradeType.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (EquipmentUpgradeType status : EquipmentUpgradeType.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 任务类型
     */
    public enum TaskType {
        //private letter
        //参数值  枚举代码  描述  类型（1 包含话术 ， 2  只包含设备   3 设备 + 其他信息) 任务类型 （1 直播任务 2 抖音任务）） 是否属于实时任务(1.属于 0 不属于)  描述
        INSANE_CLICK("insane_click", "insane_click", "疯狂点屏", 3, 1, 1, "<span class='label label-danger'>疯狂点屏</span>"),
        LIVE_CHAT("live_chat", "live_chat", "实时互动", 1, 1, 1, "<span class='label label-danger'>实时互动</span>"),
        JOIN_FAN_GROUP("join_fan_group", "join_fan_group", "加入粉丝团", 3, 1, 1, "<span class='label label-danger'>加入粉丝团</span>"),
        LIVE_HOT("live_hot", "live_hot", "超级热度", 1, 1, 0, "<span class='label label-danger'>超级热度</span>"),
        FOLLOW_MAKER_A_LIST("follow_maker_a_list", "follow_maker_a_list", "关注打榜", 3, 1, 1, "<span class='label label-danger'>关注打榜</span>"),
        GRAB_A_RED_ENVELOPE("grab_a_red_envelope", "grab_a_red_envelope", "抢红包", 3, 1, 1, "<span class='label label-danger'>抢红包</span>"),
        LOOK_SHOPPING("look_shopping", "look_shopping", "查看商店", 2, 1, 1, "<span class='label label-danger'>查看商店</span>"),
        GIVE_GIFT("give_gift", "give_gift", "赠送礼物", 3, 1, 1, "<span class='label label-danger'>赠送礼物</span>"),
        FANS_GROUP("fans_group", "fans_group", "粉丝团", 3, 1, 1, "<span class='label label-danger'>粉丝团</span>"),
        FOLLOW_PK_OPPONENT("follow_pk_opponent", "follow_pk_opponent", "关注PK对手", 2, 1, 1, "<span class='label label-danger'>关注PK对手</span>"),
        AUTO("auto", "auto", "自动养号", 1, 2, 0, "<span class='label label-danger'>自动养号</span>"),
        PRIVATE_LETTER("private_letter", "private_letter", "私信", 1, 2, 0, "<span class='label label-danger'>私信</span>"),
        ONE_KEY_EXPLOSIVE_POWDER("one_key_explosive_powder", "one_key_explosive_powder", "一键爆粉", 1, 2, 0, "<span class='label label-danger'>一键爆粉</span>"),
        MATRIX_PUSH_FLOW("matrix_push_flow", "matrix_push_flow", "矩阵推流", 1, 2, 0, "<span class='label label-danger'>矩阵推流</span>"),
        SAME_CITY_DRAINAGE("same_city_drainage", "same_city_drainage", "同城引流", 1, 2, 0, "<span class='label label-danger'>同城引流</span>"),
        EQUIPMENT_UPGRADE("equipment_upgrade", "equipment_upgrade", "设备升级", 2, 2, 0, "<span class='label label-danger'>设备升级</span>"),
        OVER("over", "over", "停止任务", 2, 2, 0, "<span class='label label-danger'>停止任务</span>"),
        FOLLOW("follow", "follow", "关注", 2, 1, 1, "<span class='label label-danger'>关注</span>"),
        GRAB_FU_BAG("grab_fu_bag", "grab_fu_bag", "抢福袋", 2, 1, 1, "<span class='label label-danger'>抢福袋</span>");


        public final String code;
        public final String value;
        public final String desc;
        public final int type;
        public final int taskType;
        public final int immediately;
        public final String htmlStr;
        private static Map<String, Object> map = new HashMap<>();

        TaskType(String value, String code, String desc, int type, int taskType, int immediately, String htmlStr) {
            this.code = code;
            this.value = value;
            this.desc = desc;
            this.type = type;
            this.immediately = immediately;
            this.htmlStr = htmlStr;
            this.taskType = taskType;
        }

        public static String getValue(String code) {
            if (null == code) {
                return null;
            }
            for (TaskType status : TaskType.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return null;
        }

        public static String getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (TaskType status : TaskType.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }


        public static TaskType getTaskType(String code) {
            if (null == code || "".equals(code)) {
                return null;
            }
            for (TaskType status : TaskType.values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }

        public static Map<String, Object> getEnumMap() {
            if (map.size() == 0) {
                for (TaskType status : TaskType.values()) {
                    map.put(status.code, status);
                }
            }
            return map;
        }
    }


    /**
     * 任务执行状态
     */
    public enum taskStatus {
        RUN("1", "running", "运行中"),
        DONE("2", "done", "执行完成"),
        WAIT("0", "wait", "等待执行"),
        ACTIVE_OVER("3", "active_over", "主动停止");


        public final String code;
        public final String value;
        public final String desc;
        private static Map<String, String> map = new HashMap<>();

        taskStatus(String value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static String getValue(String code) {
            if (null == code) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return null;
        }

        public static String getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getDesc(String value) {
            if (null == value) {
                return "";
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.value.equals(value)) {
                    return status.desc;
                }
            }
            return "";
        }


        public static Map<String, String> getEnumMap() {
            for (taskStatus status : taskStatus.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }


    /**
     * 设备状态
     */
    public enum equipmentType {
        全部("0", "全部", "全部"),
        R版本("1", "R版本", "R版本"),
        V版本("1", "V版本", "V版本"),
        E版本("1", "E版本", "E版本");


        public final String code;
        public final String value;
        public final String desc;
        private static Map<String, String> map = new HashMap<>();

        equipmentType(String value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static String getValue(String code) {
            if (null == code) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return null;
        }

        public static String getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static Map<String, String> getEnumMap() {
            if (map.size() == 0) {
                for (taskStatus status : taskStatus.values()) {
                    map.put(status.code, status.value);
                }
            }
            return map;
        }
    }


    /**
     * 赠送灯牌
     */
    public enum GiveLightPlateFlag {
        YES("1", "yes", "赠送"),
        NO("0", "no", "不赠送");


        public final String code;
        public final String value;
        public final String desc;
        private static Map<String, String> map = new HashMap<>();

        GiveLightPlateFlag(String value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static String getValue(String code) {
            if (null == code) {
                return null;
            }
            for (GiveLightPlateFlag status : GiveLightPlateFlag.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return null;
        }

        public static String getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (GiveLightPlateFlag status : GiveLightPlateFlag.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static Map<String, String> getEnumMap() {
            for (GiveLightPlateFlag status : GiveLightPlateFlag.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 选择设备方式
     */
    public enum DevicesOrGroupsId {
        EQUIPEMENT(1, "equipment", "选择设备"),
        GROUPING(2, "grouping", "选择分组");


        public final String code;
        public final int value;
        public final String desc;
        private static Map<String, Integer> map = new HashMap<>();

        DevicesOrGroupsId(int value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (DoLoginFlag status : DoLoginFlag.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {

            for (DoLoginFlag status : DoLoginFlag.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (DoLoginFlag status : DoLoginFlag.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 上线下线
     */
    public enum DoLoginFlag {
        YES(1, "yes", "上线"),
        NO(0, "no", "下线");


        public final String code;
        public final int value;
        public final String desc;
        private static Map<String, Integer> map = new HashMap<>();

        DoLoginFlag(int value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (DoLoginFlag status : DoLoginFlag.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {

            for (DoLoginFlag status : DoLoginFlag.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (DoLoginFlag status : DoLoginFlag.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 任务执行方式
     */
    public enum TaskRunCode {
        NOW(1, "now", "立即执行", "<span class=\"label label-primary\">立刻执行</span>"),
        RANDOM(2, "random", "随机执行", "<span class=\"label label-primary\">随机执行</span>"),
        DELAY(0, "delay", "定时执行", "<span class=\"label label-info\">定时任务</span>");


        public final String code;
        public final int value;
        public final String desc;
        public final String htmlStr;
        private static Map<String, Integer> map = new HashMap<>();

        TaskRunCode(int value, String code, String desc, String htmlStr) {
            this.code = code;
            this.value = value;
            this.desc = desc;
            this.htmlStr = htmlStr;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (TaskRunCode status : TaskRunCode.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {
            for (TaskRunCode status : TaskRunCode.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getHtmlStr(String code) {
            if (null == code || "".equals(code)) {
                return "";
            }
            for (TaskRunCode status : TaskRunCode.values()) {
                if (status.code.equals(code)) {
                    return status.htmlStr;
                }
            }
            return "";
        }


        public static TaskRunCode getTaskType(String code) {
            if (null == code || "".equals(code)) {
                return null;
            }
            for (TaskRunCode status : TaskRunCode.values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (TaskRunCode status : TaskRunCode.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 任务执行方式
     */
    public enum HistoryState {
        YES(1, "yes", "启用", "<span class=\"label label-primary\">启用</span>"),
        NO(0, "no", "未启用", "<span class=\"label label-info\">未启用</span>");


        public final String code;
        public final int value;
        public final String desc;
        public final String htmlStr;
        private static Map<String, Integer> map = new HashMap<>();

        HistoryState(int value, String code, String desc, String htmlStr) {
            this.code = code;
            this.value = value;
            this.desc = desc;
            this.htmlStr = htmlStr;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (HistoryState status : HistoryState.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {
            for (HistoryState status : HistoryState.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getHtmlStr(String code) {
            if (null == code || "".equals(code)) {
                return "";
            }
            for (HistoryState status : HistoryState.values()) {
                if (status.code.equals(code)) {
                    return status.htmlStr;
                }
            }
            return "";
        }

        public static String getHtmlStr(Integer value) {
            if (null == value) {
                return "";
            }
            for (HistoryState status : HistoryState.values()) {
                if (status.value == value.intValue()) {
                    return status.htmlStr;
                }
            }
            return "";
        }


        public static HistoryState getTaskType(String code) {
            if (null == code || "".equals(code)) {
                return null;
            }
            for (HistoryState status : HistoryState.values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (HistoryState status : HistoryState.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }


    /**
     * 任务内容类型
     */
    public enum TaskContentType {
        LOOK(0, "切换视频", "切换视频"),
        CLICK(1, "点赞", "点赞"),
        FOLLOW(2, "关注", "关注"),
        COMMENT(3, "评论", "评论"),
        FORWARD(4, "转发", "转发"),
        LOOK_INDEX(5, "查看首页", "查看首页"),
        PRIVATE_LETTER(6, "私信", "私信");


        public final String code;
        public final int value;
        public final String desc;
        private static Map<String, Integer> map = new HashMap<>();

        TaskContentType(int value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (TaskContentType status : TaskContentType.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {
            for (TaskContentType status : TaskContentType.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static TaskContentType getTaskType(String code) {
            if (null == code || "".equals(code)) {
                return null;
            }
            for (TaskContentType status : TaskContentType.values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (TaskContentType status : TaskContentType.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 管理员的名称
     */
    public enum roleName {
        ADMIN("0", "系统管理员", "系统管理员"),
        ONEAGENT("1", "一级代理商", "一级代理商"),
        TWOAGENT("15", "二级代理商", "二级代理商");


        public final String code;
        public final String value;
        public final String desc;
        private static Map<String, String> map = new HashMap<>();

        roleName(String value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static String getValue(String code) {
            if (null == code) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return null;
        }

        public static String getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getDesc(String value) {
            if (null == value) {
                return "";
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.value.equals(value)) {
                    return status.desc;
                }
            }
            return "";
        }


        public static Map<String, String> getEnumMap() {
            for (taskStatus status : taskStatus.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 平台邀请码
     */
    public enum invitationCode {
        INVITATION_CODE("1", "platform_invite_code", "平台邀请码");


        public final String code;
        public final String value;
        public final String desc;
        private static Map<String, String> map = new HashMap<>();

        invitationCode(String value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static String getValue(String code) {
            if (null == code) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return null;
        }

        public static String getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getDesc(String value) {
            if (null == value) {
                return "";
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.value.equals(value)) {
                    return status.desc;
                }
            }
            return "";
        }


        public static Map<String, String> getEnumMap() {
            for (taskStatus status : taskStatus.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }


    /**
     * 平台类型
     */
    public enum PLAT_FROM_TYPE {
        TIKTOK("1", "TikTok", "抖音任务"),
        KUAISHOU("2", "kwaiFu", "快手任务");


        public final String code;
        public final String value;
        public final String desc;
        private static Map<String, String> map = new HashMap<>();

        PLAT_FROM_TYPE(String value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static PLAT_FROM_TYPE getEnumCodeOrValue(String codeOrValue) {
            if (null == codeOrValue) {
                return null;
            }
            for (PLAT_FROM_TYPE status : PLAT_FROM_TYPE.values()) {
                if (status.code.equals(codeOrValue) || status.value.equals(codeOrValue)) {
                    return status;
                }
            }
            return null;
        }

        public static String getValue(String code) {
            if (null == code) {
                return null;
            }
            for (PLAT_FROM_TYPE status : PLAT_FROM_TYPE.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return null;
        }

        public static String getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (PLAT_FROM_TYPE status : PLAT_FROM_TYPE.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getDesc(String value) {
            if (null == value) {
                return "";
            }
            for (PLAT_FROM_TYPE status : PLAT_FROM_TYPE.values()) {
                if (status.value.equals(value)) {
                    return status.desc;
                }
            }
            return "";
        }


        public static Map<String, String> getEnumMap() {
            for (PLAT_FROM_TYPE status : PLAT_FROM_TYPE.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 查询的方式
     */
    public enum selectType {
        EQUIPMENTANAME("1", "equipmentName", "设备名称"),
        EQUIPMENTGROUPNAME("2", "equipmentGroupName", "分组名称"),
        AGENTNAME("3", "agentName", "代理商名称");


        public final String code;
        public final String value;
        public final String desc;
        private static Map<String, String> map = new HashMap<>();

        selectType(String value, String code, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public static String getValue(String code) {
            if (null == code) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return null;
        }

        public static String getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getDesc(String value) {
            if (null == value) {
                return "";
            }
            for (taskStatus status : taskStatus.values()) {
                if (status.value.equals(value)) {
                    return status.desc;
                }
            }
            return "";
        }


        public static Map<String, String> getEnumMap() {
            for (taskStatus status : taskStatus.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }


    /**
     * 应用领域状态
     */
    public enum ApplicationStatusEunm {
        YES(0, "yes", "发布", "<span class=\"label label-primary\">发布</span>"),
        NO(1, "no", "未发布", "<span class=\"label label-info\">未发布</span>");


        public final String code;
        public final int value;
        public final String desc;
        public final String htmlStr;
        private static Map<String, Integer> map = new HashMap<>();

        ApplicationStatusEunm(int value, String code, String desc, String htmlStr) {
            this.code = code;
            this.value = value;
            this.desc = desc;
            this.htmlStr = htmlStr;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (ApplicationStatusEunm status : ApplicationStatusEunm.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {
            for (ApplicationStatusEunm status : ApplicationStatusEunm.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getHtmlStr(String code) {
            if (null == code || "".equals(code)) {
                return "";
            }
            for (ApplicationStatusEunm status : ApplicationStatusEunm.values()) {
                if (status.code.equals(code)) {
                    return status.htmlStr;
                }
            }
            return "";
        }

        public static String getHtmlStr(Integer value) {
            if (null == value) {
                return "";
            }
            for (ApplicationStatusEunm status : ApplicationStatusEunm.values()) {
                if (status.value == value.intValue()) {
                    return status.htmlStr;
                }
            }
            return "";
        }


        public static ApplicationStatusEunm getTaskType(String code) {
            if (null == code || "".equals(code)) {
                return null;
            }
            for (ApplicationStatusEunm status : ApplicationStatusEunm.values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (ApplicationStatusEunm status : ApplicationStatusEunm.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 应用领域类型
     */
    public enum ApplicationTypeEunm {
        JINGMI(1, "JM", "精密结构", "<span class=\"label label-primary\">精密结构</span>"),
        JIAZHI(2, "JZ", "价值优势", "<span class=\"label label-info\">价值优势</span>"),
        APPLICATION(3, "APP", "应用领域", "<span class=\"label label-info\">应用领域</span>");


        public final String code;
        public final int value;
        public final String desc;
        public final String htmlStr;
        private static Map<String, Integer> map = new HashMap<>();

        ApplicationTypeEunm(int value, String code, String desc, String htmlStr) {
            this.code = code;
            this.value = value;
            this.desc = desc;
            this.htmlStr = htmlStr;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (ApplicationTypeEunm status : ApplicationTypeEunm.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {
            for (ApplicationTypeEunm status : ApplicationTypeEunm.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getHtmlStr(String code) {
            if (null == code || "".equals(code)) {
                return "";
            }
            for (ApplicationTypeEunm status : ApplicationTypeEunm.values()) {
                if (status.code.equals(code)) {
                    return status.htmlStr;
                }
            }
            return "";
        }

        public static String getHtmlStr(Integer value) {
            if (null == value) {
                return "";
            }
            for (ApplicationTypeEunm status : ApplicationTypeEunm.values()) {
                if (status.value == value.intValue()) {
                    return status.htmlStr;
                }
            }
            return "";
        }


        public static ApplicationTypeEunm getTaskType(String code) {
            if (null == code || "".equals(code)) {
                return null;
            }
            for (ApplicationTypeEunm status : ApplicationTypeEunm.values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (ApplicationTypeEunm status : ApplicationTypeEunm.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }

    /**
     * 产品详情类型
     */
    public enum ProductTypeEunm {
        JINGMI(1, "JM", "精密结构", "<span class=\"label label-primary\">精密结构</span>"),
        JIAZHI(2, "JZ", "价值优势", "<span class=\"label label-info\">价值优势</span>"),
        APPLICATION(3, "APP", "应用领域", "<span class=\"label label-info\">应用领域</span>");


        public final String code;
        public final int value;
        public final String desc;
        public final String htmlStr;
        private static Map<String, Integer> map = new HashMap<>();

        ProductTypeEunm(int value, String code, String desc, String htmlStr) {
            this.code = code;
            this.value = value;
            this.desc = desc;
            this.htmlStr = htmlStr;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (ProductTypeEunm status : ProductTypeEunm.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {
            for (ProductTypeEunm status : ProductTypeEunm.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getHtmlStr(String code) {
            if (null == code || "".equals(code)) {
                return "";
            }
            for (ProductTypeEunm status : ProductTypeEunm.values()) {
                if (status.code.equals(code)) {
                    return status.htmlStr;
                }
            }
            return "";
        }

        public static String getHtmlStr(Integer value) {
            if (null == value) {
                return "";
            }
            for (ProductTypeEunm status : ProductTypeEunm.values()) {
                if (status.value == value.intValue()) {
                    return status.htmlStr;
                }
            }
            return "";
        }


        public static ProductTypeEunm getTaskType(String code) {
            if (null == code || "".equals(code)) {
                return null;
            }
            for (ProductTypeEunm status : ProductTypeEunm.values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (ProductTypeEunm status : ProductTypeEunm.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }

        public static List<EnumDTO> getList(){
            List<EnumDTO> list = new ArrayList<>();
            for (ProductTypeEunm status : ProductTypeEunm.values()){
                list.add(new EnumDTO(status.htmlStr, status.value));
            }
            return list;
        }
    }


    /**
     * 操作类型
     */
    public enum FunctionTypeEunm {
        SAVE(1, "save", "添加", "<span class=\"label label-primary\">精密结构</span>"),
        EDIT(2, "edit", "更新", "<span class=\"label label-info\">价值优势</span>"),
        DEL(3, "del", "删除", "<span class=\"label label-info\">应用领域</span>");


        public final String code;
        public final int value;
        public final String desc;
        public final String htmlStr;
        private static Map<String, Integer> map = new HashMap<>();

        FunctionTypeEunm(int value, String code, String desc, String htmlStr) {
            this.code = code;
            this.value = value;
            this.desc = desc;
            this.htmlStr = htmlStr;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (FunctionTypeEunm status : FunctionTypeEunm.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {
            for (FunctionTypeEunm status : FunctionTypeEunm.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getHtmlStr(String code) {
            if (null == code || "".equals(code)) {
                return "";
            }
            for (FunctionTypeEunm status : FunctionTypeEunm.values()) {
                if (status.code.equals(code)) {
                    return status.htmlStr;
                }
            }
            return "";
        }

        public static String getHtmlStr(Integer value) {
            if (null == value) {
                return "";
            }
            for (FunctionTypeEunm status : FunctionTypeEunm.values()) {
                if (status.value == value.intValue()) {
                    return status.htmlStr;
                }
            }
            return "";
        }


        public static FunctionTypeEunm getTaskType(String code) {
            if (null == code || "".equals(code)) {
                return null;
            }
            for (FunctionTypeEunm status : FunctionTypeEunm.values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (FunctionTypeEunm status : FunctionTypeEunm.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }
    }



    /**
     * 客户留言处理状态
     * 状态 0：待处理 1：跟进中，2.暂不处理，3.无效
     */
    public enum MessageBoardStatusEunm {
        PROCESSED(0, "PROCESSED", "待处理", "<span class=\"label label-primary\">待处理</span>"),
        PROCESSING(1, "PROCESSING", "跟进中", "<span class=\"label label-info\">跟进中</span>"),
        LEAVE_ASIDE(2, "LEAVE_ASIDE", "暂不处理", "<span class=\"label label-info\">暂不处理</span>"),
        IN_ASIDE(3, "IN_ASIDE", "无效", "<span class=\"label label-info\">无效</span>"),
        PASS(4, "PASS", "已完成", "<span class=\"label label-info\">已完成</span>");


        public final String code;
        public final int value;
        public final String desc;
        public final String htmlStr;
        private static Map<String, Integer> map = new HashMap<>();

        MessageBoardStatusEunm(int value, String code, String desc, String htmlStr) {
            this.code = code;
            this.value = value;
            this.desc = desc;
            this.htmlStr = htmlStr;
        }

        public static int getValue(String code) {
            if (null == code) {
                return -1;
            }
            for (MessageBoardStatusEunm status : MessageBoardStatusEunm.values()) {
                if (status.code.equals(code)) {
                    return status.value;
                }
            }
            return -1;
        }

        public static String getCode(int value) {
            for (MessageBoardStatusEunm status : MessageBoardStatusEunm.values()) {
                if (status.value == (value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static String getHtmlStr(String code) {
            if (null == code || "".equals(code)) {
                return "";
            }
            for (MessageBoardStatusEunm status : MessageBoardStatusEunm.values()) {
                if (status.code.equals(code)) {
                    return status.htmlStr;
                }
            }
            return "";
        }

        public static String getHtmlStr(Integer value) {
            if (null == value) {
                return "";
            }
            for (MessageBoardStatusEunm status : MessageBoardStatusEunm.values()) {
                if (status.value == value.intValue()) {
                    return status.htmlStr;
                }
            }
            return "";
        }


        public static MessageBoardStatusEunm getTaskType(String code) {
            if (null == code || "".equals(code)) {
                return null;
            }
            for (MessageBoardStatusEunm status : MessageBoardStatusEunm.values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            return null;
        }

        public static Map<String, Integer> getEnumMap() {
            for (MessageBoardStatusEunm status : MessageBoardStatusEunm.values()) {
                map.put(status.code, status.value);
            }
            return map;
        }

        public static List<EnumDTO> getList(){
            List<EnumDTO> list = new ArrayList<>();
            for (MessageBoardStatusEunm status : MessageBoardStatusEunm.values()){
                list.add(new EnumDTO(status.htmlStr, status.value));
            }
            return list;
        }
    }


    public static void main(String[] args) {
//        String ss = "#在抖音，记录美好生活#【俊姐 情感主播】正在直播，来和我一起支持TA吧。复制下方链接，打开【抖音短视频】，直接观看直播！ https://v.douyin.com/J617CGY/";
//        //获取直播间地址
//        String detailUrl = StringUtil.subTextToWebUrlStr(ss);
//        System.out.println(detailUrl);
//        //获取直播间详情地址
//        String dataStr = HttpUtils.POST_FORM(detailUrl, null);
//        System.out.println(dataStr);
//        //请求直播间详情地址
//        String sss = HttpUtils.sendGet(dataStr, "");
//        System.out.println("---->>" + sss);
//        //清楚tag标签的str
//        String cleanHtmlTag = HtmlUtil.cleanHtmlTag(sss);
//        //获取第一个short_id的位置
//        int start = cleanHtmlTag.indexOf("short_id\":");
//        //计算结束截取的位置
//        int end = start + 20;
//        String shortId = cleanHtmlTag.substring(start + 10, end);
//        System.out.println("shortId : " + shortId);
        System.out.println(PLAT_FROM_TYPE.getEnumCodeOrValue("2"));
    }

}
