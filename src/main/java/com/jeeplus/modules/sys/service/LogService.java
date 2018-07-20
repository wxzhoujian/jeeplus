/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.sys.dao.LogDao;
import com.jeeplus.modules.sys.entity.Log;

/**
 * 日志Service
 * @author jeeplus
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {

	@Autowired
	private LogDao logDao;
	
	public Page<Log> findPage(Page<Log> page, Log log) {
		
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		
		return super.findPage(page, log);
		
	}
	
	/**
	 * 删除全部数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void empty(){
		
		logDao.empty();
	}
	
}
