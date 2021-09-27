package com.aisino.frems.common.ws.dto;

import java.util.List;

public class DataDownGabRyLzxx {

	private List<ModelDownGabTempRegInfo> info;// 临时住宿登记信息结果集
	private String returnCode;// 查询错误码信息
	private String returnCodeDes;// 查询返回码描述
	private int arraySize;// 结果集的大小

	public List<ModelDownGabTempRegInfo> getInfo() {
		return info;
	}

	public void setInfo(List<ModelDownGabTempRegInfo> info) {
		this.info = info;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnCodeDes() {
		return returnCodeDes;
	}

	public void setReturnCodeDes(String returnCodeDes) {
		this.returnCodeDes = returnCodeDes;
	}

	public int getArraySize() {
		return arraySize;
	}

	public void setArraySize(int arraySize) {
		this.arraySize = arraySize;
	}

}
