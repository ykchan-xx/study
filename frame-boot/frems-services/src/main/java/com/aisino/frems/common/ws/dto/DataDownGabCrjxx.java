package com.aisino.frems.common.ws.dto;

import java.util.List;

public class DataDownGabCrjxx {
    private List<ModelDownGabFIIS_CRJ> FIIS_CRJ;// 出入境人员的信息.
    private String returnCode;// 查询错误码信息.
    private String returnCodeDes;// 查询返回码描述.
    private int arraySize;// 口岸出入境记录信息结果集的大小.

    public List<ModelDownGabFIIS_CRJ> getFIIS_CRJ() {
        return FIIS_CRJ;
    }

    public void setFIIS_CRJ(List<ModelDownGabFIIS_CRJ> fIIS_CRJ) {
        FIIS_CRJ = fIIS_CRJ;
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
