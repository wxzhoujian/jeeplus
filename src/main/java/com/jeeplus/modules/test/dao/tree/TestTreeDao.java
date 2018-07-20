/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.dao.tree;

import com.jeeplus.common.persistence.TreeDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.test.entity.tree.TestTree;

/**
 * 组织机构DAO接口
 * @author liugf
 * @version 2016-10-04
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}