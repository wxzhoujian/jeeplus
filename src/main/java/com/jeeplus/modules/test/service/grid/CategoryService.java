/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.service.grid;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.test.entity.grid.Category;
import com.jeeplus.modules.test.dao.grid.CategoryDao;

/**
 * 商品分类Service
 * @author liugf
 * @version 2016-10-04
 */
@Service
@Transactional(readOnly = true)
public class CategoryService extends CrudService<CategoryDao, Category> {

	public Category get(String id) {
		return super.get(id);
	}
	
	public List<Category> findList(Category category) {
		return super.findList(category);
	}
	
	public Page<Category> findPage(Page<Category> page, Category category) {
		return super.findPage(page, category);
	}
	
	@Transactional(readOnly = false)
	public void save(Category category) {
		super.save(category);
	}
	
	@Transactional(readOnly = false)
	public void delete(Category category) {
		super.delete(category);
	}
	
	
	
	
}