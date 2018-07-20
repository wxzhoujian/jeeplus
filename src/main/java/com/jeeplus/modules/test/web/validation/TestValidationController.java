/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.web.validation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.test.entity.validation.TestValidation;
import com.jeeplus.modules.test.service.validation.TestValidationService;

/**
 * 测试校验功能Controller
 * @author lgf
 * @version 2016-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/test/validation/testValidation")
public class TestValidationController extends BaseController {

	@Autowired
	private TestValidationService testValidationService;
	
	@ModelAttribute
	public TestValidation get(@RequestParam(required=false) String id) {
		TestValidation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testValidationService.get(id);
		}
		if (entity == null){
			entity = new TestValidation();
		}
		return entity;
	}
	
	/**
	 * 测试校验列表页面
	 */
	@RequiresPermissions("test:validation:testValidation:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestValidation testValidation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestValidation> page = testValidationService.findPage(new Page<TestValidation>(request, response), testValidation); 
		model.addAttribute("page", page);
		return "modules/test/validation/testValidationList";
	}

	/**
	 * 查看，增加，编辑测试校验表单页面
	 */
	@RequiresPermissions(value={"test:validation:testValidation:view","test:validation:testValidation:add","test:validation:testValidation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TestValidation testValidation, Model model) {
		model.addAttribute("testValidation", testValidation);
		return "modules/test/validation/testValidationForm";
	}

	/**
	 * 保存测试校验
	 */
	@RequiresPermissions(value={"test:validation:testValidation:add","test:validation:testValidation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TestValidation testValidation, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, testValidation)){
			return form(testValidation, model);
		}
		if(!testValidation.getIsNewRecord()){//编辑表单保存
			TestValidation t = testValidationService.get(testValidation.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(testValidation, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			testValidationService.save(t);//保存
		}else{//新增表单保存
			testValidationService.save(testValidation);//保存
		}
		addMessage(redirectAttributes, "保存测试校验成功");
		return "redirect:"+Global.getAdminPath()+"/test/validation/testValidation/?repage";
	}
	
	/**
	 * 删除测试校验
	 */
	@RequiresPermissions("test:validation:testValidation:del")
	@RequestMapping(value = "delete")
	public String delete(TestValidation testValidation, RedirectAttributes redirectAttributes) {
		testValidationService.delete(testValidation);
		addMessage(redirectAttributes, "删除测试校验成功");
		return "redirect:"+Global.getAdminPath()+"/test/validation/testValidation/?repage";
	}
	
	/**
	 * 批量删除测试校验
	 */
	@RequiresPermissions("test:validation:testValidation:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testValidationService.delete(testValidationService.get(id));
		}
		addMessage(redirectAttributes, "删除测试校验成功");
		return "redirect:"+Global.getAdminPath()+"/test/validation/testValidation/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("test:validation:testValidation:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(TestValidation testValidation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "测试校验"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestValidation> page = testValidationService.findPage(new Page<TestValidation>(request, response, -1), testValidation);
    		new ExportExcel("测试校验", TestValidation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出测试校验记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/validation/testValidation/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("test:validation:testValidation:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestValidation> list = ei.getDataList(TestValidation.class);
			for (TestValidation testValidation : list){
				try{
					testValidationService.save(testValidation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条测试校验记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条测试校验记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入测试校验失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/validation/testValidation/?repage";
    }
	
	/**
	 * 下载导入测试校验数据模板
	 */
	@RequiresPermissions("test:validation:testValidation:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "测试校验数据导入模板.xlsx";
    		List<TestValidation> list = Lists.newArrayList(); 
    		new ExportExcel("测试校验数据", TestValidation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/validation/testValidation/?repage";
    }
	
	
	

}