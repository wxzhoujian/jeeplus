/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.entity.tree;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.jeeplus.common.persistence.TreeEntity;

/**
 * 组织机构Entity
 * @author liugf
 * @version 2016-10-04
 */
public class TestTree extends TreeEntity<TestTree> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private Integer sort;		// 排序
	private TestTree parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	
	public TestTree() {
		super();
	}

	public TestTree(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="排序不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public TestTree getParent() {
		return parent;
	}

	public void setParent(TestTree parent) {
		this.parent = parent;
	}
	
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}