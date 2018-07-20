/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.echarts.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.echarts.entity.ChinaWeatherDataBean;

/**
 * 城市气温DAO接口
 * @author lgf
 * @version 2016-06-02
 */
@MyBatisDao
public interface ChinaWeatherDataBeanDao extends CrudDao<ChinaWeatherDataBean> {

	
}