package com.jeeplus.modules.iim.entity;


/**
 * layim的初始化数据
 * @author Swhite
 *
 */
public class LayJson {
	
	private int code = 0;  //0表示成功，其它表示失败
	private String msg;  //失败信息
	private Object data; //数据集合
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
