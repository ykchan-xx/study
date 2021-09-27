package com.aisino.frems.modules.system.service;

import com.aisino.frems.modules.system.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
public interface ISysDictItemService extends IService<SysDictItem> {
    public List<SysDictItem> selectItemsByMainId(String mainId);

    /**
     * 根据text查询value
     *@param dictCode
     * @param itemText
     * @return
     */
    SysDictItem  queryDictKeyByText (String dictCode,String itemText);
}
