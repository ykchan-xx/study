package com.aisino.frems.common.ws.dto;

import org.apache.commons.lang.StringUtils;

import java.util.Comparator;

/**
 * 公安部临住信息.
 */
public class ModelDownGabTempRegInfo implements Comparator<ModelDownGabTempRegInfo> {
	private String businessNO; // 业务编号, 各省编号
	private String nameCH; // 中文姓名
	private String surnameEN; // 英文（拼音）名
	private String firstnameEN; // 英文（拼音）姓，外国人为必填项
	private String dataSource; // 数据来源，临时住宿登记数据来源代码
	private String gender; // 性别. 字典901
	private String birthday; // 出生日期. 格式yyyyMMdd
	private String nationality; // 国籍/地区
	private String paperType; // 所持证件种类，证件种类代码. 字典903
	private String paperNO; // 所持证件号码
	private String visaType; // 签证种类，签证种类代码，台湾居民来往大陆通行证签注种类代码
	private String visaNO; // 签证（注）号码
	private String localizePeriod; // 签证（注）停留有效期(日期格式yyyMMdd)
	private String assignmentOrg; // 签发单位，审批签发机关代码
	private String resideTime; // 住宿日期(日期格式yyyyMMdd)
	private String resideAddress; // 散居住址或留宿单位地址
	private String leaveTime; // 离开日期(日期格式yyyyMMdd)
	private String receiveUnit; // 接待单位
	private String tmpResideRegOrgArea; // 临时住宿登记单位行政区划. 字典702
	private String tmpResideRegOrgCode; // 临时住宿登记单位代码
	private String tmpResideRegOrgName; // 临时住宿登记单位名称
	private String remarks; // 备注
	private String opTime; // 记录操作时间(日期格式yyyyMMddHHmmss)
	private String opType; // 操作类型，I－新增记录；U－修改记录；D－删除记录
	private String province; // 省份，上报用户所在的省级单位；用于数据监控
	private String origFileName; // 原始文件名

	public String getBusinessNO() {
		return businessNO;
	}

	public void setBusinessNO(String businessNO) {
		this.businessNO = businessNO;
	}

	public String getNameCH() {
		return nameCH;
	}

	public void setNameCH(String nameCH) {
		this.nameCH = nameCH;
	}

	public String getSurnameEN() {
		return surnameEN;
	}

	public void setSurnameEN(String surnameEN) {
		this.surnameEN = surnameEN;
	}

	public String getFirstnameEN() {
		return firstnameEN;
	}

	public void setFirstnameEN(String firstnameEN) {
		this.firstnameEN = firstnameEN;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getPaperNO() {
		return paperNO;
	}

	public void setPaperNO(String paperNO) {
		this.paperNO = paperNO;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getVisaNO() {
		return visaNO;
	}

	public void setVisaNO(String visaNO) {
		this.visaNO = visaNO;
	}

	public String getLocalizePeriod() {
		return localizePeriod;
	}

	public void setLocalizePeriod(String localizePeriod) {
		this.localizePeriod = localizePeriod;
	}

	public String getAssignmentOrg() {
		return assignmentOrg;
	}

	public void setAssignmentOrg(String assignmentOrg) {
		this.assignmentOrg = assignmentOrg;
	}

	public String getResideTime() {
		return resideTime;
	}

	public void setResideTime(String resideTime) {
		this.resideTime = resideTime;
	}

	public String getResideAddress() {
		return resideAddress;
	}

	public void setResideAddress(String resideAddress) {
		this.resideAddress = resideAddress;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getReceiveUnit() {
		return receiveUnit;
	}

	public void setReceiveUnit(String receiveUnit) {
		this.receiveUnit = receiveUnit;
	}

	public String getTmpResideRegOrgArea() {
		return tmpResideRegOrgArea;
	}

	public void setTmpResideRegOrgArea(String tmpResideRegOrgArea) {
		this.tmpResideRegOrgArea = tmpResideRegOrgArea;
	}

	public String getTmpResideRegOrgCode() {
		return tmpResideRegOrgCode;
	}

	public void setTmpResideRegOrgCode(String tmpResideRegOrgCode) {
		this.tmpResideRegOrgCode = tmpResideRegOrgCode;
	}

	public String getTmpResideRegOrgName() {
		return tmpResideRegOrgName;
	}

	public void setTmpResideRegOrgName(String tmpResideRegOrgName) {
		this.tmpResideRegOrgName = tmpResideRegOrgName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOpTime() {
		return opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getOrigFileName() {
		return origFileName;
	}

	public void setOrigFileName(String origFileName) {
		this.origFileName = origFileName;
	}

	@Override
	public int compare(ModelDownGabTempRegInfo o1, ModelDownGabTempRegInfo o2) {
		if (null!=o1 && null!=o2&&StringUtils.isNotEmpty(o1.getResideTime())&&StringUtils.isNotEmpty(o2.getResideTime())){
			long o1Long = Long.parseLong(o1.getResideTime());
			long o2Long = Long.parseLong(o2.getResideTime());
			return Long.compare(o2Long,o1Long);
		}
		return 0;
	}
}
