package com.jeeplus.modules.iim.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * LayJson--->Data对象
 * 
 * @author Swhite
 * 
 */
public class LayGroupJsonData {
	
	private HashMap owner = new HashMap(); // 我的信息

	
	private List<Friend> list = new ArrayList(); // 群组中的成员列表


	public void setOwner(HashMap owner) {
		this.owner = owner;
	}


	public HashMap getOwner() {
		return owner;
	}


	public void setList(List<Friend> list) {
		this.list = list;
	}


	public List<Friend> getList() {
		return list;
	}


}
