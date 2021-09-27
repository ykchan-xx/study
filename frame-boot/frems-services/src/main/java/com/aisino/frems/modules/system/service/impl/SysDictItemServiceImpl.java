package com.aisino.frems.modules.system.service.impl;

import com.aisino.frems.modules.system.entity.SysDictItem;
import com.aisino.frems.modules.system.dao.SysDictItemMapper;
import com.aisino.frems.modules.system.service.ISysDictItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements ISysDictItemService {

    @Autowired
    private SysDictItemMapper sysDictItemMapper;

    @Override
    public List<SysDictItem> selectItemsByMainId(String mainId) {
        return sysDictItemMapper.selectItemsByMainId(mainId);
    }

    @Override
    public SysDictItem queryDictKeyByText(String dictName, String itemText) {
        return sysDictItemMapper.queryDictKeyByText(dictName,itemText);
    }
}
