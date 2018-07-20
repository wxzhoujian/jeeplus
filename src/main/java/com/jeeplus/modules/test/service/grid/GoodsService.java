/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.service.grid;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.test.entity.grid.Category;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.test.entity.grid.Goods;
import com.jeeplus.modules.test.dao.grid.GoodsDao;

/**
 * 商品Service
 * @author liugf
 * @version 2016-10-04
 */
@Service
@Transactional(readOnly = true)
public class GoodsService extends CrudService<GoodsDao, Goods> {

	public Goods get(String id) {
		return super.get(id);
	}
	
	public List<Goods> findList(Goods goods) {
		return super.findList(goods);
	}
	
	public Page<Goods> findPage(Page<Goods> page, Goods goods) {
		return super.findPage(page, goods);
	}
	
	@Transactional(readOnly = false)
	public void save(Goods goods) {
		super.save(goods);
	}
	
	@Transactional(readOnly = false)
	public void delete(Goods goods) {
		super.delete(goods);
	}
	
	public Page<Category> findPageBycategory(Page<Category> page, Category category) {
		category.setPage(page);
		page.setList(dao.findListBycategory(category));
		return page;
	}
	
	
	
}