/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.dao.grid;

import com.jeeplus.modules.test.entity.grid.Category;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.test.entity.grid.Goods;

/**
 * 商品DAO接口
 * @author liugf
 * @version 2016-10-04
 */
@MyBatisDao
public interface GoodsDao extends CrudDao<Goods> {

	public List<Category> findListBycategory(Category category);
	
}