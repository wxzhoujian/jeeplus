package com.jeeplus.modules.iim.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友列表
 * 
 * @author Swhite
 * 
 */
public class FriendGroup {
	private String groupname;// 好友分组名
	private int id; // 分组ID
	private int online; // 在线数量
	private List<Friend> list = new ArrayList<Friend>(); // 分组下的好友列表

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public List<Friend> getList() {
		return list;
	}

	public void setList(List<Friend> list) {
		this.list = list;
	}

}
