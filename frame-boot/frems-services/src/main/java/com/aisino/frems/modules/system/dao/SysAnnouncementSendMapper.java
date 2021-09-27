package com.aisino.frems.modules.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.aisino.frems.modules.system.entity.SysAnnouncementSend;
import com.aisino.frems.modules.system.model.AnnouncementSendModel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 用户通告阅读标记表
 * @Author: jeecg-boot
 * @Date:  2019-02-21
 * @Version: V1.0
 */
public interface SysAnnouncementSendMapper extends BaseMapper<SysAnnouncementSend> {

	List<String> queryByUserId(@Param("userId" ) String userId);

	/**
	 * @功能：获取我的消息
	 * @param announcementSendModel
	 * @param announcementSendModel
	 * @return
	 */
	List<AnnouncementSendModel> getMyAnnouncementSendList(Page<AnnouncementSendModel> page, @Param("announcementSendModel" ) AnnouncementSendModel announcementSendModel);

}
