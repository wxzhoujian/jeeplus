/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.entity.validation;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 测试校验功能Entity
 * @author lgf
 * @version 2016-10-05
 */
public class TestValidation extends DataEntity<TestValidation> {
	
	private static final long serialVersionUID = 1L;
	private Double num;		// 浮点数字
	private Integer num2;		// 整数
	private String str;		// 字符串
	private String email;		// 邮件
	private String url;		// 网址
	private Date newDate;		// 日期
	
	public TestValidation() {
		super();
	}

	public TestValidation(String id){
		super(id);
	}

	@Min(value=(long)20.1,message="浮点数字的最小值不能小于20.1")
	@Max(value=(long)69.3,message="浮点数字的最大值不能超过69.3")
	@ExcelField(title="浮点数字", align=2, sort=1)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@NotNull(message="整数不能为空")
	@Min(value=10,message="整数的最小值不能小于10")
	@Max(value=30,message="整数的最大值不能超过30")
	@ExcelField(title="整数", align=2, sort=2)
	public Integer getNum2() {
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}
	
	@Length(min=5, max=65, message="字符串长度必须介于 5 和 65 之间")
	@ExcelField(title="字符串", align=2, sort=3)
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
	@Email(message="邮件必须为合法邮箱")
	@ExcelField(title="邮件", align=2, sort=4)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@URL(message="网址必须为合法网址")
	@ExcelField(title="网址", align=2, sort=5)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="日期", align=2, sort=6)
	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}
	
}