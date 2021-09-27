package com.aisino.frems.common.ws.dto;

/**
 * 全国系统返回结果数据对象.
 */
public class WsResultBase<T> {

	private int success; // 是否成功。(0-失败，1-成功)。不可为空

	private String state; // 状态。状态代码用于区分错误类型。不可为空

	private String message; // 消息。可为空

	private String resultStr; // 原始的结果字符串

	private T data;

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{success=").append(success);
		sb.append(", state='").append(state);
		sb.append("', message='").append(message);
		sb.append("', data=").append(data);
		sb.append("}");
		return sb.toString();
	}
}
