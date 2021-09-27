package com.aisino.frems.common.constant;

/**
 * 项目信息相关常量
 * @author hxq
 */
public class ProjectConstant {
    /** 业务类型: 出入境业务*/
    public static String YWLX_CRJ = "A02A01";
    /** 业务类型: 创新业务*/
    public static String YWLX_CX = "A02A02";
    /** 业务类型: 研发项目*/
    public static String YWLX_YF = "A02A03";

    /** 合同种类: 正式合同*/
    public static String HTZL_ZS = "03";
    /** 合同种类: 意向合同*/
    public static String HTZL_YX = "02";

    /** 业务类型字典代码*/
    public static String DICT_YWLX = "business_type";
    /** 省份字典代码*/
    public static String DICT_SF = "province";
    /** 项目类型字典代码*/
    public static String DICT_XMLX = "project_type";
    /** 合同种类字典代码*/
    public static String DICT_HTZL = "contract_type";
    /** 合同状态字典代码*/
    public static String DICT_HTZT = "contract_status";
    /** 审计类型字典代码*/
    public static String DICT_SJLX = "audit_type";
    /** 项目状态字典代码*/
    public static String DICT_XMZT = "project_status";
    /** 项目状态（实施）字典代码*/
    public static String DICT_XMZTSS = "project_status_operation";
    /** 有效状态字典代码 */
    public static String DICT_YXZT = "valid_status";
    /** 招标方式字典代码 */
    public static String DICT_ZBFS = "tender_way";
    /** 中标情况字典代码 */
    public static String DICT_ZBQK = "bid_status";
    /** 合同签订情况字典代码 */
    public static String DICT_HTQDQK = "qd_sign_status";
    /** 是否字典代码 */
    public static String DICT_YN = "yn";
    /** 项目类别字典代码 */
    public static String DICT_XMLB = "item_type";
    /** 回款情况字典代码 */
    public static String DICT_HKQK = "return_status";
    /** 售前状态字典代码 */
    public static String DICT_SQZT = "presale_status";
    /** 采购类别字典代码 */
    public static String DICT_CGLB = "purchase_type";
    /** 内部供应商字典代码 */
    public static String DICT_NBGYS = "isSupplier";
    /** 客户等级字典代码 */
    public static String DICT_KHDJ = "custom_grade";
    /** 采购用途 */
    public static String DICT_CGTY = "buy_use";
    /** 合同执行情况 */
    public static String DICT_HTZXQK = "contract_execute_status";
    /** 销售经理 */
    public static String DICT_XSJL = "sales_manager";
    /** 项目经理 */
    public static String DICT_XMJL = "project_manager";
    /** 跟踪类型 */
    public static String DICT_JGLX = "trace_type";
    /** 是否已转项目管理 */
    public static String DICT_SFYZXMGL = "is_change_xmgl";
    /** 收入项目进度 */
    public static String DICT_SRXMJD = "sr_xmjd";
    /** 开票申请状态 */
    public static String DICT_KPSQZT = "apply_status";
    /** 开票状态 */
    public static String DICT_KPZT = "invoice_status";
    /** 开票名称 */
    public static String DICT_KPMC = "kpmc";
    /** 商务条款-收款类型 */
    public static String DICT_SWTK_SKLX = "swtk_sklx";
    /** 档案类别 */
    public static String DICT_DALB = "files_type";


    /** 项目状态:0-项目启动 */
    public static String XMZT_0_QD = "0";
    /** 项目状态:1-设计完成 */
    public static String XMZT_1_SJ = "1";
    /** 项目状态:2-测试完成 */
    public static String XMZT_2_CS = "2";
    /** 项目状态:2-项目上线 */
    public static String XMZT_2_SX = "3";
    /** 项目状态:2-项目初验 */
    public static String XMZT_2_CY = "4";
    /** 项目状态:2-项目终验 */
    public static String XMZT_3_ZY = "5";
    /** 项目状态:4-质保结束 */
    public static String XMZT_4_ZBJS = "6";
    /** 项目状态:5-启动 */
    public static String XMZT_5_QD = "7";
    /** 项目状态:6-结束 */
    public static String XMZT_6_JS = "8";

    /** 项目类型:服务 */
    public static String XMLX_FW = "01";
    /** 项目类型:运维 */
    public static String XMLX_YW = "02";
    /** 项目类型:分销 */
    public static String XMLX_FX = "03";
    /** 项目类型:开发 */
    public static String XMLX_KF = "04";
    /** 项目类型:集成 */
    public static String XMLX_JC = "05";
    /** 项目类型:其他 */
    public static String XMLX_OTHER = "99";

    /** 定时器用户 */
    public static String USER_QUARTZ = "quartz";

    /** 收入项目阶段:设计 */
    public static String SR_XMJD_SJ = "1";
    /** 收入项目阶段:上线 */
    public static String SR_XMJD_SX = "2";
    /** 收入项目阶段:验收 */
    public static String SR_XMJD_YS = "3";
    /** 收入项目阶段:运维 */
    public static String SR_XMJD_WH = "4";

    /** 收款类型-合同签订款 */
    public static String SKLX_HTQDK = "1";
    /** 收款类型-上线款 */
    public static String SKLX_SXK = "2";
    /** 收款类型-初验款 */
    public static String SKLX_CYK = "3";
    /** 收款类型-终验款 */
    public static String SKLX_ZYK = "4";
    /** 收款类型-尾款 */
    public static String SKLX_WK = "5";

}
