/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.entity.onetomany;

import com.jeeplus.modules.sys.entity.Area;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 票务代理Entity
 * @author liugf
 * @version 2016-10-06
 */
public class TestDataChild extends DataEntity<TestDataChild> {
	
	private static final long serialVersionUID = 1L;
	private Area startArea;		// 出发地
	private Area endArea;		// 目的地
	private Date starttime;		// 出发时间
	private Double price;		// 代理价格
	private TestDataMain testDataMain;		// 业务主表ID 父类
	
	public TestDataChild() {
		super();
	}

	public TestDataChild(String id){
		super(id);
	}

	public TestDataChild(TestDataMain testDataMain){
		this.testDataMain = testDataMain;
	}

	@NotNull(message="出发地不能为空")
	@ExcelField(title="出发地", fieldType=Area.class, value="startArea.name", align=2, sort=1)
	public Area getStartArea() {
		return startArea;
	}

	public void setStartArea(Area startArea) {
		this.startArea = startArea;
	}
	
	@NotNull(message="目的地不能为空")
	@ExcelField(title="目的地", fieldType=Area.class, value="endArea.name", align=2, sort=2)
	public Area getEndArea() {
		return endArea;
	}

	public void setEndArea(Area endArea) {
		this.endArea = endArea;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="出发时间不能为空")
	@ExcelField(title="出发时间", align=2, sort=3)
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	
	@NotNull(message="代理价格不能为空")
	@ExcelField(title="代理价格", align=2, sort=4)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public TestDataMain getTestDataMain() {
		return testDataMain;
	}

	public void setTestDataMain(TestDataMain testDataMain) {
		this.testDataMain = testDataMain;
	}
	
}