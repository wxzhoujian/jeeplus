/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.entity.grid;

import com.jeeplus.modules.test.entity.grid.Category;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品Entity
 * @author liugf
 * @version 2016-10-04
 */
public class Goods extends DataEntity<Goods> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商品名称
	private Category category;		// 所属类型
	private String price;		// 价格
	
	public Goods() {
		super();
	}

	public Goods(String id){
		super(id);
	}

	@ExcelField(title="商品名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="所属类型", align=2, sort=2)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@ExcelField(title="价格", align=2, sort=3)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}