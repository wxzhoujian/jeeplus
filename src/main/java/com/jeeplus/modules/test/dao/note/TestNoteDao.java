/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.dao.note;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.test.entity.note.TestNote;

/**
 * 富文本测试DAO接口
 * @author liugf
 * @version 2016-10-04
 */
@MyBatisDao
public interface TestNoteDao extends CrudDao<TestNote> {

	
}