package com.aisino.frems.modules.system.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.aisino.frems.modules.system.entity.SysDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
    @Select("SELECT * FROM SYS_DICT_ITEM WHERE DICT_ID = #{mainId}")
    List<SysDictItem> selectItemsByMainId(String mainId);

    /**
     * 根据text查询value
     *@param dictCode
     * @param itemText
     * @return
     */
    SysDictItem  queryDictKeyByText (@Param("dictCode")String dictCode, @Param("itemText") String itemText);

    /**
     * 根据item_text 查询对应的code
     * @param dictCode
     * @param itemText
     * @return:
     */
    @Select("select a.item_value from sys_dict_item a where a.dict_id = (select b.id from sys_dict b where b.dict_code =#{dictCode}) and a.item_text =#{itemText}")
    String queryItemCodeByItemText(String dictCode, String itemText);
}
