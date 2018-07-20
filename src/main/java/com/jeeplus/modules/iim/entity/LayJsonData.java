package com.jeeplus.modules.iim.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * LayJson--->Data对象
 * 
 * @author Swhite
 * 
 */
public class LayJsonData {
	
	private Object mine; // 我的信息

	private List<FriendGroup> friend = new ArrayList(); // 好友列表
	
	private List<LayGroup> group = new ArrayList();//群组列表

	public Object getMine() {
		return mine;
	}

	public void setMine(Object mine) {
		this.mine = mine;
	}

	public List<FriendGroup> getFriend() {
		return friend;
	}

	public void setFriend(List<FriendGroup> friend) {
		this.friend = friend;
	}

	public void setGroup(List<LayGroup> group) {
		this.group = group;
	}

	public List<LayGroup> getGroup() {
		return group;
	}


}
