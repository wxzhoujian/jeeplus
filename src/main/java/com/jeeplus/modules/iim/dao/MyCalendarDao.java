/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.iim.entity.MyCalendar;


/**
 * 日历DAO接口
 * @author liugf
 * @version 2016-04-19
 */
@MyBatisDao
public interface MyCalendarDao extends CrudDao<MyCalendar> {
	
}