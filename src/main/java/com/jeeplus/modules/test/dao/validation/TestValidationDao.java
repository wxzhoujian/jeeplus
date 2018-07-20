/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.dao.validation;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.test.entity.validation.TestValidation;

/**
 * 测试校验功能DAO接口
 * @author lgf
 * @version 2016-10-05
 */
@MyBatisDao
public interface TestValidationDao extends CrudDao<TestValidation> {

	
}