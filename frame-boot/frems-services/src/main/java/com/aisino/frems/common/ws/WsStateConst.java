package com.aisino.frems.common.ws;

/**
 * 常用的接口返回状态.
 */
public class WsStateConst {

	// == SUCCESS: 是否成功.
	/** [成功] {@value}: 假. */
	public static final int SUCCESS_FALSE = 0;
	/** [成功] {@value}: 真. */
	public static final int SUCCESS_TRUE = 1;

	// == CRJ0xxx: 成功性状态.
	/** [状态] {@value}: 处理成功. */
	public static final String STATE_SUCCESS = "CRJ0000";

	// == CRJ1xxx: 一般错误.
	// -- 权限性错误.
	/** [状态] {@value}: 用户名/密码错误. */
	public static final String STATE_ERROR_PASSWORD = "CRJ1001";
	/** [状态] {@value}: 会话已失效，请重新登录. */
	public static final String STATE_ERROR_SESSION = "CRJ1002";
	/** [状态] {@value}: 用户权限不够，不能执行此操作. */
	public static final String STATE_ERROR_PERMISSION = "CRJ1003";
	/** [状态] {@value}: 功能代码无效. */
	public static final String STATE_ERROR_FUNCCODE = "CRJ1004";
	/** [状态] {@value}: 校验码无效或已过时. */
	public static final String STATE_ERROR_CHECKCODE = "CRJ1005";
	/** [状态] {@value}: 时段限制. */
	public static final String STATE_ERROR_TIMELIMIT = "CRJ1006";

	// -- 一般性错误.
	/** [状态] {@value}: 未知错误. */
	public static final String STATE_ERROR_UNKNOWN = "CRJ1101";
	/** [状态] {@value}: 不支持. */
	public static final String STATE_ERROR_NO_SUPPORT = "CRJ1102";
	/** [状态] {@value}: 操作失败. */
	public static final String STATE_ERROR_OPERATION_FAIL = "CRJ1103";
	/** [状态] {@value}: 条件不满足. */
	public static final String STATE_ERROR_DISAGREE = "CRJ1104";
	/** [状态] {@value}: 返回值解析失败. */
	public static final String STATE_ERROR_RESULT_PARSE = "CRJ1105";
	/** [状态] {@value}: JSON解析失败. */
	public static final String STATE_ERROR_JSON_PARSE = "CRJ1106";

	// -- 参数错误. 即调用者所传参数（args）里的错误.
	/** [状态] {@value}: 参数错误. */
	public static final String STATE_ERROR_ARGUMENT = "CRJ1201";
	/** [状态] {@value}: 参数为空. */
	public static final String STATE_ERROR_ARGUMENT_NULL = "CRJ1202";
	/** [状态] {@value}: 参数重复. */
	public static final String STATE_ERROR_ARGUMENT_DUPLICATION = "CRJ1203";
	/** [状态] {@value}: 参数超过范围. */
	public static final String STATE_ERROR_ARGUMENT_RANGE = "CRJ1204";
	/** [状态] {@value}: 参数格式错误. */
	public static final String STATE_ERROR_ARGUMENT_FORMAT = "CRJ1205";

	// -- 值错误. 即内部数据里的错误.
	/** [状态] {@value}: 值错误. */
	public static final String STATE_ERROR_VALUE = "CRJ1301";
	/** [状态] {@value}: 值为空. */
	public static final String STATE_ERROR_VALUE_NULL = "CRJ1302";
	/** [状态] {@value}: 值重复. */
	public static final String STATE_ERROR_VALUE_DUPLICATION = "CRJ1303";
	/** [状态] {@value}: 值超过范围. */
	public static final String STATE_ERROR_VALUE_RANGE = "CRJ1304";
	/** [状态] {@value}: 值格式错误. */
	public static final String STATE_ERROR_VALUE_FORMAT = "CRJ1305";

	// -- 数据库错误.
	/** [状态] {@value}: 数据库错误. */
	public static final String STATE_ERROR_DB = "CRJ1401";
	/** [状态] {@value}: 数据库连接失败. */
	public static final String STATE_ERROR_DB_CONNECT = "CRJ1402";
	/** [状态] {@value}: 调用存储过程失败. */
	public static final String STATE_ERROR_DB_PROCEDURE = "CRJ1403";
	/** [状态] {@value}: 数据库查询失败. */
	public static final String STATE_ERROR_DB_SELECT = "CRJ1404";
	/** [状态] {@value}: 数据库入库失败. */
	public static final String STATE_ERROR_DB_INSERT = "CRJ1405";
	/** [状态] {@value}: 数据库更新失败. */
	public static final String STATE_ERROR_DB_UPDATE = "CRJ1406";
	/** [状态] {@value}: 数据库删除失败. */
	public static final String STATE_ERROR_DB_DELETE = "CRJ1407";

	// -- WebService错误.
	/** [状态] {@value}: WebService错误. */
	public static final String STATE_ERROR_WS = "CRJ1501";
	/** [状态] {@value}: WebService连接失败. */
	public static final String STATE_ERROR_WS_CONNECT = "CRJ1502";
	/** [状态] {@value}: WebService方法调用失败. */
	public static final String STATE_ERROR_WS_METHOD = "CRJ1503";

	// -- 网络错误.
	/** [状态] {@value}: 网络错误. */
	public static final String STATE_ERROR_NET = "CRJ1601";
	/** [状态] {@value}: 网络连接失败. */
	public static final String STATE_ERROR_NET_CONNECT = "CRJ1602";
	/** [状态] {@value}: 网络连接关闭失败. */
	public static final String STATE_ERROR_NET_CLOSE = "CRJ1603";
	/** [状态] {@value}: 网络读取失败. */
	public static final String STATE_ERROR_NET_READ = "CRJ1604";
	/** [状态] {@value}: 网络写入失败. */
	public static final String STATE_ERROR_NET_WRITE = "CRJ1605";
	/** [状态] {@value}: 网络操作超时. */
	public static final String STATE_ERROR_NET_TIMEOUT = "CRJ1606";

	// == CRJ2xxx: 业务错误.
	// -- 找不到数据.
	/** [状态] {@value}: 找不到数据. */
	public static final String STATE_ERROR_NO_DATA = "CRJ2101";
	/** [状态] {@value}: 用户信息不存在. */
	public static final String STATE_ERROR_NO_USER = "CRJ2102";
	/** [状态] {@value}: 人员信息不存在. */
	public static final String STATE_ERROR_NO_PERSON = "CRJ2103";
	/** [状态] {@value}: 申请信息不存在. */
	public static final String STATE_ERROR_NO_APPLY = "CRJ2104";
	/** [状态] {@value}: 证件信息不存在. */
	public static final String STATE_ERROR_NO_PAPLE = "CRJ2105";
	/** [状态] {@value}: 居留许可不存在. */
	public static final String STATE_ERROR_NO_RESIDE_PERMIT = "CRJ2106";
	/** [状态] {@value}: 单位不存在. */
	public static final String STATE_ERROR_NO_DANWEI = "CRJ2107";
}
