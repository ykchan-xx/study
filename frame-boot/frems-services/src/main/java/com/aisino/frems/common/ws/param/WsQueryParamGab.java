package com.aisino.frems.common.ws.param;


import com.aisino.frems.common.util.SpringContextUtils;
import com.aisino.frems.modules.system.service.ISysConfigService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 公安部接口查询条件.
 */
public class WsQueryParamGab {

//	@Resource
	private ISysConfigService sysConfigService;
	private String client_ip;// String(40) 客户端IP.
	private String client_zsbh;// String(40) 客户端证书编号
	private String client_dwbh;// String(12) 客户端单位编号.
	private String client_dwmc;// String(100) 客户端单位名称.
	private String client_xm;// String(100) 客户端姓名.
	private String client_sfzh;// String(18) 客户端身份证号.

	// --公安部临住信息查询条件
	private String paperType;// String(1) 证件种类
	private String paperNO;// String(16) 证件号码
	private String nationality;// String(3) 国籍. 【注】实测发现证件号码查询时该参数无效，会返回其他国家的数据.
	private String birthday;// String(8) 出生日期(yyyyMMdd格式).
	private String nameEN;// String(70) 英文姓名
	private String nameCH;// String(44) 中文姓名

	// --公安部出入境记录查询条件
	private String personType;// String(2) 人员类别:1港澳居民,2非港澳居民,9未说明.
	private String startDate;// String(8) 开始出入境日期. 格式yyyyMMdd
	private String endDate;// String(8) 结束出入境日期. 格式yyyyMMdd
	private String returnRecNum;// int 返回记录数:默认为0.
	private String name;// String(50) 人员姓名.
	private String birth;// String(8) 出生日期,格式yyyyMMdd.
	private String gender;// String(1) 性别:1男性;2女性.
	private String certificateNO;// String(20) 证件号码,不超过20位.

	// --公安部涉案人员信息文本查询
	// private String paperNO ;// String(16) 证件号码
	// private String nationality ;// String(3) 国籍/地区
	// private String birthday ;// String(8) 出生日期(yyyyMMdd)
	// private String name ;// String(70) 姓名(中英文都可以)

	// --公安部全国案事件信息精确核查
	private String caseNO;// String 案事件编号
	private String province;// String 上报省份代码

	// --公安部全国案事件信息精确核查
	//涉案人员编号,查询qg_aj_queryCasePeopleExact 时使用
	private String casePersonNO;

	// -- 公安部常住信息
	private String businessNO;
	private String personNO;
	private String reportedProvince;

	// --公安部外国人证件签发信息查询
	private String id;// String(15) 业务编号
	private String personnelId;// String(23) 人员编号
	// private String paperNO ;// String(16) 签证/居留许可证/绿卡 号码
	// private String paperType ;// String(1) 签证类型
	// private String nameEN ;// String(70) 英文姓名
	// private String birthday ;// String(8) 出生日期. 格式yyyyMMdd
	// private String nationality ;// String(3) 国籍/地区
	private String passportNO;// String(16) 所持护照号码
	private String returnID;// String(16) 所持护照号码

	// --公安部港澳居民回乡证签发信息查询
	//private String paperNO;// String(16) 通行证号码，只可以是7位或9位，香港居民以"H"开头,澳门居民以"M"开头.
	private String versionNO;// String(16) 版本号,2位.
	private String areaCode;// String(16) 地区代码,"HKG"香港,"MAC"澳门.
	//private String returnID;// String(16) 返回标识 1返回文本+照片;2返回照片

	// --公安部台胞证签发信息查询
	private String txzh; //8位台胞通行证号（一次证为7位）.
	private String sfzh; //台湾身份证号码.

	private String zwxm; // 中文姓名（不在GBK标准范围内的应转为两个半角问号“??”）.
	private String ywxm; //英文姓名.
	private String csrq; //出生日期. 格式yyyyMMdd.
	private String tbzh; //台胞证号.  qg_tw_queryTbzzp接口查询照片使用

	// --公安部不准出境人员信息查询
	private String idNo; //身份证号码,15位或18位.

	// --公安部不准入境人员信息查询
	private String gjdq;
	private String zjzl;
	private String zjhm;
	private String xb;

	public WsQueryParamGab() {
		ISysConfigService sysConfigService = (ISysConfigService) SpringContextUtils.getBean("sysConfigService");
		this.sysConfigService = sysConfigService;
		String client_msg = this.sysConfigService.getParaValueByCode("gk.client_msgs");
		String[] client_msgs = client_msg.split(",");
		client_ip = client_msgs[0];
		client_zsbh = client_msgs[1];
		client_dwbh = client_msgs[2];
		client_dwmc = client_msgs[3];
		client_xm = client_msgs[4];
		client_sfzh = client_msgs[5];
	}



	public String getCasePersonNO() {
		return casePersonNO;
	}

	public void setCasePersonNO(String casePersonNO) {
		this.casePersonNO = casePersonNO;
	}

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	public String getClient_zsbh() {
		return client_zsbh;
	}

	public void setClient_zsbh(String client_zsbh) {
		this.client_zsbh = client_zsbh;
	}

	public String getClient_dwbh() {
		return client_dwbh;
	}

	public void setClient_dwbh(String client_dwbh) {
		this.client_dwbh = client_dwbh;
	}

	public String getClient_dwmc() {
		return client_dwmc;
	}

	public void setClient_dwmc(String client_dwmc) {
		this.client_dwmc = client_dwmc;
	}

	public String getClient_xm() {
		return client_xm;
	}

	public void setClient_xm(String client_xm) {
		this.client_xm = client_xm;
	}

	public String getClient_sfzh() {
		return client_sfzh;
	}

	public void setClient_sfzh(String client_sfzh) {
		this.client_sfzh = client_sfzh;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNameEN() {
		return nameEN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	public String getNameCH() {
		return nameCH;
	}

	public void setNameCH(String nameCH) {
		this.nameCH = nameCH;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReturnRecNum() {
		return returnRecNum;
	}

	public void setReturnRecNum(String returnRecNum) {
		this.returnRecNum = returnRecNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCertificateNO() {
		return certificateNO;
	}

	public void setCertificateNO(String certificateNO) {
		this.certificateNO = certificateNO;
	}

	public String getCaseNO() {
		return caseNO;
	}

	public void setCaseNO(String caseNO) {
		this.caseNO = caseNO;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(String personnelId) {
		this.personnelId = personnelId;
	}

	public String getPassportNO() {
		return passportNO;
	}

	public void setPassportNO(String passportNO) {
		this.passportNO = passportNO;
	}

	public String getReturnID() {
		return returnID;
	}

	public void setReturnID(String returnID) {
		this.returnID = returnID;
	}

	public String getBusinessNO() {
		return businessNO;
	}

	public void setBusinessNO(String businessNO) {
		this.businessNO = businessNO;
	}

	public String getPersonNO() {
		return personNO;
	}

	public void setPersonNO(String personNO) {
		this.personNO = personNO;
	}

	public String getReportedProvince() {
		return reportedProvince;
	}

	public void setReportedProvince(String reportedProvince) {
		this.reportedProvince = reportedProvince;
	}

	public String getVersionNO() {
		return versionNO;
	}

	public void setVersionNO(String versionNO) {
		this.versionNO = versionNO;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getTxzh() {
		return txzh;
	}

	public void setTxzh(String txzh) {
		this.txzh = txzh;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getZwxm() {
		return zwxm;
	}

	public void setZwxm(String zwxm) {
		this.zwxm = zwxm;
	}

	public String getYwxm() {
		return ywxm;
	}

	public void setYwxm(String ywxm) {
		this.ywxm = ywxm;
	}

	public String getCsrq() {
		return csrq;
	}

	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	public String getTbzh() {
		return tbzh;
	}

	public void setTbzh(String tbzh) {
		this.tbzh = tbzh;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getGjdq() {
		return gjdq;
	}

	public void setGjdq(String gjdq) {
		this.gjdq = gjdq;
	}

	public String getZjzl() {
		return zjzl;
	}

	public void setZjzl(String zjzl) {
		this.zjzl = zjzl;
	}

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}
}
