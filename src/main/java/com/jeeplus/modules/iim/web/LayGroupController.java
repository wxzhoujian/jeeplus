/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.iim.dao.LayGroupUserDao;
import com.jeeplus.modules.iim.entity.LayGroup;
import com.jeeplus.modules.iim.entity.LayGroupUser;
import com.jeeplus.modules.iim.service.LayGroupService;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 群组Controller
 * @author lgf
 * @version 2016-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/iim/layGroup")
public class LayGroupController extends BaseController {

	@Autowired
	private LayGroupService layGroupService;
	
	@Autowired
	private LayGroupUserDao layGroupUserDao;
	
	@Autowired
	private UserDao userDao;
	
	@ModelAttribute
	public LayGroup get(@RequestParam(required=false) String id) {
		LayGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = layGroupService.get(id);
		}
		if (entity == null){
			entity = new LayGroup();
		}
		return entity;
	}
	
	/**
	 * 群组列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(LayGroup layGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		//用户自己创建的群组和属于的群组
		List<LayGroup> layGroupList = new ArrayList<LayGroup>();
		//查找我自己创建的群组
		layGroup .setCreateBy(UserUtils.getUser());
		List<LayGroup> ownerLayGroupList = layGroupService.findList(layGroup);
		
		//查找我属于的群组
		List<LayGroup> memberLayGroupList = layGroupService.findGroupList(UserUtils.getUser());
		
		layGroupList.addAll(ownerLayGroupList);
		layGroupList.addAll(memberLayGroupList);
		model.addAttribute("layGrouplist", layGroupList);
		return "modules/iim/layGroupList";
	}

	/**
	 * 查看，增加，编辑群组表单页面
	 */
	@RequestMapping(value = "form")
	public String form(LayGroup layGroup, Model model) {
		model.addAttribute("layGroup", layGroup);
		return "modules/iim/layGroupForm";
	}
	
	/**
	 * 添加群组成员--->常用联系人
	 */
	@RequestMapping(value = "addUser")
	public String addUser(String ids, String groupid, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			User user = userDao.get(id);
			LayGroup group =layGroupService.get(groupid);
			LayGroupUser lgUser = new LayGroupUser();
			lgUser.setGroup(group);
			lgUser.setUser(user);
			
			 if(layGroupUserDao.findList(lgUser).size() == 0 && !user.getId().equals(group.getCreateBy().getId())){
				 lgUser.setId(IdGen.uuid());
				 layGroupUserDao.insert(lgUser);
			 }
		}
		addMessage(redirectAttributes, "添加组员成功");
		return "redirect:"+Global.getAdminPath()+"/iim/layGroup/?repage";
	}
	/**
	 * 保存群组
	 */
	@RequestMapping(value = "save")
	public String save(LayGroup layGroup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, layGroup)){
			return form(layGroup, model);
		}
		if(!layGroup.getIsNewRecord()){//编辑表单保存
			LayGroup t = layGroupService.get(layGroup.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(layGroup, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			layGroupService.save(t);//保存
		}else{//新增表单保存
			layGroupService.save(layGroup);//保存
		}
		addMessage(redirectAttributes, "保存群组成功");
		return "redirect:"+Global.getAdminPath()+"/iim/layGroup/?repage";
	}
	
	/**
	 * 删除群组
	 */
	@RequestMapping(value = "delete")
	public String delete(LayGroup layGroup, RedirectAttributes redirectAttributes) {
		layGroupService.delete(layGroup);
		addMessage(redirectAttributes, "解散群组成功");
		return "redirect:"+Global.getAdminPath()+"/iim/layGroup/?repage";
	}
	
	/**
	 * 退出群组
	 */
	@RequestMapping(value = "logout")
	public String logout(LayGroupUser layGroupUser, RedirectAttributes redirectAttributes) {
		
		layGroupUserDao.delete(layGroupUserDao.findList(layGroupUser).get(0));
		addMessage(redirectAttributes, "退出群组成功");
		return "redirect:"+Global.getAdminPath()+"/iim/layGroup/?repage";
	}
	
	/**
	 * 批量删除群组
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			layGroupService.delete(layGroupService.get(id));
		}
		addMessage(redirectAttributes, "删除群组成功");
		return "redirect:"+Global.getAdminPath()+"/iim/layGroup/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LayGroup layGroup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "群组"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LayGroup> page = layGroupService.findPage(new Page<LayGroup>(request, response, -1), layGroup);
    		new ExportExcel("群组", LayGroup.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出群组记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/iim/layGroup/?repage";
    }

	/**
	 * 导入Excel数据

	 */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LayGroup> list = ei.getDataList(LayGroup.class);
			for (LayGroup layGroup : list){
				try{
					layGroupService.save(layGroup);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条群组记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条群组记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入群组失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/iim/layGroup/?repage";
    }
	
	/**
	 * 下载导入群组数据模板
	 */
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "群组数据导入模板.xlsx";
    		List<LayGroup> list = Lists.newArrayList(); 
    		new ExportExcel("群组数据", LayGroup.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/iim/layGroup/?repage";
    }
	
	
	

}