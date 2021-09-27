package com.aisino.frems.modules.system.service.impl;

import com.aisino.frems.modules.system.entity.SysDataLog;
import com.aisino.frems.modules.system.dao.SysDataLogMapper;
import com.aisino.frems.modules.system.service.ISysDataLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

@Service
public class SysDataLogServiceImpl extends ServiceImpl<SysDataLogMapper,SysDataLog> implements ISysDataLogService {
	@Resource
	private SysDataLogMapper logMapper;

	/**
	 * 添加数据日志
	 */
	@Override
	public void addDataLog(String tableName, String dataId, String dataContent) {
		Integer versionNumber = 0;
		String dataVersion = logMapper.queryMaxDataVer(tableName, dataId);
		if(dataVersion != null ) {
			versionNumber = Integer.parseInt(dataVersion)+1;
		}
		SysDataLog log = new SysDataLog();
		log.setDataTable(tableName);
		log.setDataId(dataId);
		log.setDataContent(dataContent);
		log.setDataVersion(versionNumber);
		this.save(log);
	}

}
