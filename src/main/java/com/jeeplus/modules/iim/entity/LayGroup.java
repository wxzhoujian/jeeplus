/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 群组Entity
 * @author lgf
 * @version 2016-08-07
 */
public class LayGroup extends DataEntity<LayGroup> {
	
	private static final long serialVersionUID = 1L;
	private String groupname;		// 群组名
	private String avatar;		// 群头像
	private List<LayGroupUser> layGroupUserList = Lists.newArrayList();		// 子表列表
	
	public LayGroup() {
		super();
	}

	public LayGroup(String id){
		super(id);
	}

	@Length(min=0, max=64, message="群组名长度必须介于 0 和 64 之间")
	@ExcelField(title="群组名", align=2, sort=1)
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	@Length(min=0, max=256, message="群头像长度必须介于 0 和 256 之间")
	@ExcelField(title="群头像", align=2, sort=2)
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public List<LayGroupUser> getLayGroupUserList() {
		return layGroupUserList;
	}

	public void setLayGroupUserList(List<LayGroupUser> layGroupUserList) {
		this.layGroupUserList = layGroupUserList;
	}
}