package com.aisino.frems.common.base.controller;

import org.asframework.base.model.InvokeResult;
import org.asframework.web.common.util.I18nMessage;

/**
 * 基础Controller类
 *
 * @author wenzhaoming
 */
public class BaseController {

	/**
	 * 查询成功返回结果
	 *
	 * @param data
	 * @return
	 */
	protected InvokeResult querySuccess(Object data) {
		String message = I18nMessage.getMessage("system.query.success");
		return InvokeResult.success(message, data);
	}
}
