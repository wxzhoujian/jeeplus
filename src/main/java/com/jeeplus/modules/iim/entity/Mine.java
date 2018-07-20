package com.jeeplus.modules.iim.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * layim我的信息
 * @author Swhite
 *
 */
public class Mine {
	private String username;//用户名
	private String id; //id
	private String status; //状态
	private String remark; //个性签名
	private String avatar; //头像
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
