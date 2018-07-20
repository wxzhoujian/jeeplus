/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.iim.entity.MailBox;

/**
 * 发件箱DAO接口
 * @author jeeplus
 * @version 2015-11-15
 */
@MyBatisDao
public interface MailBoxDao extends CrudDao<MailBox> {
	
	public int getCount(MailBox entity);
	
}