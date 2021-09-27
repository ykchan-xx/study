package com.aisino.frems.common.ws.dto;

public class ModelDownGabFIIS_CRJ {
    private KeyMapping personType;// KeyMapping(2|20) 人员类别.
    private String name;// String(50) 人员姓名.
    private KeyMapping nationality;// KeyMapping(3|30) 国籍,国家/地区代码.
    private KeyMapping gender;// KeyMapping(1|6) 性别,性别代码.
    private String birth;// String(8) 出生日期. 格式yyyyMMdd
    private KeyMapping certificateType;// KeyMapping(2|50) 证件类型,证件类别代码.
    private String certificateNO;// String(20) 证件号码.
    private KeyMapping profCode;// KeyMapping(1|20) 职业代码（无内容）.
    private KeyMapping visaType;// KeyMapping(1|20) 签证类型,外国人签证种类代码.
    private String ioDate;// String(8) 出入境日期. 格式yyyyMMdd
    private String ioTime;// String(6) 出入境时间.
    private KeyMapping ioPort;// KeyMapping(7|20) 出入口岸.
    private String inspectorID;// String(6) 检查员号.
    private KeyMapping trafficMode;// KeyMapping(1|10) 交通方式,交通方式代码.
    private String trafficTool;// String(30) 交通工具.
    private String destination;// String(3) 前往地.
    private KeyMapping grantCertOrg;// KeyMapping(4|40) 发证机关,审批签发机关代码.
    private String grantCertDate;// String(8) 发证日期. 格式yyyyMMdd
    private KeyMapping outReason;// KeyMapping(1|10) 出境理由,口岸入出境事由代码.
    private String channelNO;// String(4) 通道号.
    private String tourGroupNO;// String(20) 旅游团号.
    private String inTime;// String(18) 入库时间(出境登记卡号).
    private String id;// String(2) 标识.
    private String namePY;// String(8) 人员姓名拼音.
    private String queryStr;// String(500) 拼接的查询条件,格式为字段名1:值1,字段名2:值2...
    private String constructID;// String(20) 构建对象ID.

    public KeyMapping getPersonType() {
        return personType;
    }

    public void setPersonType(KeyMapping personType) {
        this.personType = personType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KeyMapping getNationality() {
        return nationality;
    }

    public void setNationality(KeyMapping nationality) {
        this.nationality = nationality;
    }

    public KeyMapping getGender() {
        return gender;
    }

    public void setGender(KeyMapping gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public KeyMapping getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(KeyMapping certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNO() {
        return certificateNO;
    }

    public void setCertificateNO(String certificateNO) {
        this.certificateNO = certificateNO;
    }

    public KeyMapping getProfCode() {
        return profCode;
    }

    public void setProfCode(KeyMapping profCode) {
        this.profCode = profCode;
    }

    public KeyMapping getVisaType() {
        return visaType;
    }

    public void setVisaType(KeyMapping visaType) {
        this.visaType = visaType;
    }

    public String getIoDate() {
        return ioDate;
    }

    public void setIoDate(String ioDate) {
        this.ioDate = ioDate;
    }

    public String getIoTime() {
        return ioTime;
    }

    public void setIoTime(String ioTime) {
        this.ioTime = ioTime;
    }

    public KeyMapping getIoPort() {
        return ioPort;
    }

    public void setIoPort(KeyMapping ioPort) {
        this.ioPort = ioPort;
    }

    public String getInspectorID() {
        return inspectorID;
    }

    public void setInspectorID(String inspectorID) {
        this.inspectorID = inspectorID;
    }

    public KeyMapping getTrafficMode() {
        return trafficMode;
    }

    public void setTrafficMode(KeyMapping trafficMode) {
        this.trafficMode = trafficMode;
    }

    public String getTrafficTool() {
        return trafficTool;
    }

    public void setTrafficTool(String trafficTool) {
        this.trafficTool = trafficTool;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public KeyMapping getGrantCertOrg() {
        return grantCertOrg;
    }

    public void setGrantCertOrg(KeyMapping grantCertOrg) {
        this.grantCertOrg = grantCertOrg;
    }

    public String getGrantCertDate() {
        return grantCertDate;
    }

    public void setGrantCertDate(String grantCertDate) {
        this.grantCertDate = grantCertDate;
    }

    public KeyMapping getOutReason() {
        return outReason;
    }

    public void setOutReason(KeyMapping outReason) {
        this.outReason = outReason;
    }

    public String getChannelNO() {
        return channelNO;
    }

    public void setChannelNO(String channelNO) {
        this.channelNO = channelNO;
    }

    public String getTourGroupNO() {
        return tourGroupNO;
    }

    public void setTourGroupNO(String tourGroupNO) {
        this.tourGroupNO = tourGroupNO;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamePY() {
        return namePY;
    }

    public void setNamePY(String namePY) {
        this.namePY = namePY;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public String getConstructID() {
        return constructID;
    }

    public void setConstructID(String constructID) {
        this.constructID = constructID;
    }
}
