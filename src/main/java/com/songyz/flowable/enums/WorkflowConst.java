package com.songyz.flowable.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class WorkflowConst {

    private WorkflowConst() {

    }

    public static enum EntityType {

        TICKET(1), // 工单

        AUDIT_EXPENSE(2), // 费用申请
        AUDIT_STOPSLA(3), // 停表申请
        AUDIT_PARTS(4), // 备件申请
        AUDIT_PAYMENT(5),// 结费申请

        ;

        private Integer code;

        private EntityType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static EntityType get(Integer code) {
            for (EntityType entityType : EntityType.values()) {
                if (Objects.equals(entityType.getCode(), code)) {
                    return entityType;
                }
            }
            return null;
        }
    }

    public static enum IsOrNo {

        NO(0), // 否
        IS(1);// 是

        private Integer code;

        private IsOrNo(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static IsOrNo get(Integer code) {
            for (IsOrNo isOrNo : IsOrNo.values()) {
                if (Objects.equals(isOrNo.getCode(), code)) {
                    return isOrNo;
                }
            }
            return null;
        }
    }

    public static enum WorkingPlatform {

        APP(1), // 移动端
        PC(2), // PC端
        BOTH(3),// 移动端和PC端都可以用
        ;

        private Integer code;

        private WorkingPlatform(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static WorkingPlatform get(Integer code) {
            for (WorkingPlatform platform : WorkingPlatform.values()) {
                if (Objects.equals(platform.getCode(), code)) {
                    return platform;
                }
            }
            return null;
        }
    }

    public static enum Status {

        DRAFT(1), // 草稿
        ENABLED(2), // 可用
        DISABLED(3),// 不可用
        ;

        private Integer code;

        private Status(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static Status get(Integer code) {
            for (Status status : Status.values()) {
                if (Objects.equals(status.getCode(), code)) {
                    return status;
                }
            }
            return null;
        }
    }

    public static enum StepType {

        EXECUTE(1), // 动作
        PAGE(2), // 页面
        ;

        private Integer code;

        private StepType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static StepType get(Integer code) {
            for (StepType stepType : StepType.values()) {
                if (Objects.equals(stepType.getCode(), code)) {
                    return stepType;
                }
            }
            return null;
        }
    }

    public static enum PageType {

        APP(1), // 移动端
        WEB(2); // PC端

        private Integer code;

        private PageType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static PageType get(Integer code) {
            for (PageType pageType : PageType.values()) {
                if (Objects.equals(pageType.getCode(), code)) {
                    return pageType;
                }
            }
            return null;
        }
    }

    public static enum PageFormType {

        EXECUTE(1), // 流程使用
        MORE(2); // 更多使用

        private Integer code;

        private PageFormType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static PageFormType get(Integer code) {
            for (PageFormType pageFormType : PageFormType.values()) {
                if (Objects.equals(pageFormType.getCode(), code)) {
                    return pageFormType;
                }
            }
            return null;
        }
    }

    public static enum InstanceStatus {

        STARTED(1), // 启动
        RUNNING(2), // 运行
        STOPED(3), // 完成
        CANCELLED(4),// 撤销
        ;

        private Integer code;

        private InstanceStatus(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static InstanceStatus get(Integer code) {
            for (InstanceStatus instanceStatus : InstanceStatus.values()) {
                if (Objects.equals(instanceStatus.getCode(), code)) {
                    return instanceStatus;
                }
            }
            return null;
        }
    }

    public static enum JumpType {

        TO(1), // 跳至
        OVER(2), // 跳过
        ;

        private Integer code;

        private JumpType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static JumpType get(Integer code) {
            for (JumpType jumpType : JumpType.values()) {
                if (Objects.equals(jumpType.getCode(), code)) {
                    return jumpType;
                }
            }
            return null;
        }
    }

    public static enum SlaTag {

        APPOINT("sla_appointed"), //
        ADDRIVE("sla_addrived"), //
        COMPLETE("sla_completed"),//
        ;
        private String code;

        SlaTag(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }

        public static SlaTag get(String code) {
            for (SlaTag slaTag : SlaTag.values()) {
                if (Objects.equals(slaTag.getCode(), code)) {
                    return slaTag;
                }
            }
            return null;
        }
    }

    public static enum PayType {

        APP(1), // App支付
        SCAN(2);// 扫码付款

        private Integer code;

        private PayType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public static PayType get(Integer code) {
            for (PayType payType : PayType.values()) {
                if (Objects.equals(payType.getCode(), code)) {
                    return payType;
                }
            }
            return null;
        }
    }

    public static enum FlowDataType {
        TEXT(1), // 文本
        IMAGE(2), // 图片
        AUDIO(3), // 音频
        VIDEO(4), // 视频
        EVALUATE(5), // 评价
        JSON(6), // Json
        GPS(7), // GPS信息
        BOOLEAN(8), // 布尔
        TIMESTAMP(9), // 时间戳
        BASE_AMOUNT(10), // 基础收款金额类
        COMBINATION_AMOUNT(11), // 组合收款金额类
        INDEX(12), // 索引类
        COMBINATION_PARAM(13), // 组合收款折扣参数
        BASE64_IMAGE(14), // 地图签到页面截图（Base64数据）
        ;
        private int code;

        FlowDataType(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public static FlowDataType get(Integer code) {
            for (FlowDataType dataType : FlowDataType.values()) {
                if (Objects.equals(dataType.getCode(), code)) {
                    return dataType;
                }
            }
            return null;
        }

        // 是否是Base64格式的附件
        public static boolean isBase64(Integer code) {
            if (Objects.equals(code, BASE64_IMAGE.getCode())) {
                return true;
            }
            return false;
        }

        // 是否不是Base64格式的附件
        public static boolean nonBase64(Integer code) {
            return isBase64(code) == false;
        }

        // 文本类型
        public static List<Integer> textTypes = Arrays.asList(TEXT.getCode(), EVALUATE.getCode(), GPS.getCode(),
                BOOLEAN.getCode(), TIMESTAMP.getCode(), BASE_AMOUNT.getCode(), COMBINATION_AMOUNT.getCode(),
                INDEX.getCode(), COMBINATION_PARAM.getCode());

        // 附件类型
        public static List<Integer> fileTypes = Arrays.asList(IMAGE.getCode(), AUDIO.getCode(), VIDEO.getCode(),
                BASE64_IMAGE.getCode());
    }

    public static enum ItemType {
        INPUT_SIMPLE_TEXT(1), // 文本输入
        TEXTAREA_RECORD(2), // 块输入录音
        TEXTAREA_VOICE(3), // 块输入语音
        INPUT_DATETIME(4), // 日期时间
        INPUT_PHONE(5), // 联系电话
        INPUT_ADDRESS(6), // 地址录入
        SCAN_QR_CODE(7), // 条码录入
        LIST(8), // 列表单选
        RADIO(9), // 单选框
        INPUT_GPS(10), // GPS组件
        EVALUATE_MODULE(11), // 评价模块
        EVALUATE_ITEM(12), // 评价项
        INTERVAL(13), // 间隔块
        PARAGRAPH(14), // 段落块
        ENGINEER_INFO(15), // 工程师信息
        INPUT_IMAGE(16), // 图片录入
        INPUT_VIDEO(17), // 视频录入
        INPUT_SIGN(18), // 签名录入
        INPUT_PART_UP(19), // 换上件录入
        INPUT_PART_DOWN(20), // 换下件录入
        SIMPLE_CONTAINER(21), // 基础容器
        INPUT_NUMBER(22), // 数字输入
        MAP_SIGN(23), // 地图签到

        SIMPLE_SCAN_PAY(24), // 基础收款---扫码收款

        SCAN_PICTURE(25), // 扫码拍照
        CONDITION_CONTAINER(26), // 条件容器

        COMBINATION_SCAN_PAY(27), // 组合收款---扫码收款

        // 附属组件
        CONDITION_ITEM(28), // 条件项
        PAY_ITEM(29), // 收款项

        SIMPLE_APP_PAY(30), // 基础付款---App付款
        COMBINATION_APP_PAY(31), // 组合付款---App收款

        IMAGE_OCR(32), // 图像识别组件
        HTTP_LINK(33), // 文章链接组件
        EXPRESS(34),// 物流信息组件

        ;

        private ItemType(int code) {
            this.code = code;
        }

        private int code;

        public int getCode() {
            return code;
        }

        public static ItemType get(Integer code) {
            for (ItemType itemType : ItemType.values()) {
                if (Objects.equals(itemType.getCode(), code)) {
                    return itemType;
                }
            }
            return null;
        }

    }

    // 页面生成常量
    public static class PageConst {
        // 工程师信息组件
        public static final String ENGINEER_NAME = "engineerName";
        public static final String ENGINEER_PHOTO = "engineerPhoto";
        public static final String ENGINEER_PHONE = "engineerPhone";

        // 地图签到组件
        public static final String MAP_SIGN_LAT = "mapSignLat";
        public static final String MAP_SIGN_LONG = "mapSignLong";

        public static final String TEXT_INPUT_PREFIX = "text_";
        public static final String FILE_INPUT_PREFIX = "file_";
    }

    // 步骤扩展属性
    public static class FlowStepExt {

        public static final String CANDIDATE_GROUPS = "candidate_groups";// 操作角色
        public static final String CANDIDATE_USERS = "candidate_users";// 操作人

        public static final String NEXT_STEPS = "next_steps";// 临近的下一级元素
        public static final String STEP_TIP = "step_tip";// 步骤提示
        public static final String STEP_SLA_CODE = "step_sla_code";// 步骤sla标签code
        public static final String STEP_ICON_PATH = "step_icon_path";// 步骤图标路径

    }

    // 组件扩展属性
    public static class FlowItemConfigExt {

        public static final String NAME = "name";
        public static final String REQUIRED = "required";
        public static final String DEFAULT_VALUE = "default_value";
        public static final String TABLE_VALUE = "table_value";
        public static final String MIN_LENGTH = "min_length";
        public static final String MAX_LENGTH = "max_length";
        public static final String PLACEHOLDER = "placeholder";
        public static final String MIN_VAL = "min_val";
        public static final String MAX_VAL = "max_val";
        public static final String ACCURACY = "accuracy";
        public static final String TIME_TEMPLET = "time_templet";
        public static final String LIST_VALUE = "list_value";
        public static final String ATTACHMENTS = "attachments";
        public static final String DESCRIPTION = "description";

        public static final String DEFAULT_CONDITION = "defaultCondition";
        public static final String CONDITION_LIST = "conditionList";
        public static final String CONDITION_INDEX = "conditionIndex";

        public static final String AMOUNT = "amount";
        public static final String PRICE = "price";
        public static final String UNIT = "unit";
        public static final String TRADE_PLATFORM = "tradePlatform";
        public static final String DISCOUNT_FLAG = "discountFlag";

        public static final String LINK_TYPE = "linkType";
        public static final String KNOWLEDGE_PARAM = "knowledgeParam";

    }

    // 页面扩展属性
    public static class FlowPageConfigExt extends FlowItemConfigExt {

    }

}
