/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.web.grid;

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
import com.jeeplus.modules.test.entity.grid.Category;
import com.jeeplus.modules.test.service.grid.CategoryService;

/**
 * 商品分类Controller
 * @author liugf
 * @version 2016-10-04
 */
@Controller
@RequestMapping(value = "${adminPath}/test/grid/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public Category get(@RequestParam(required=false) String id) {
		Category entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = categoryService.get(id);
		}
		if (entity == null){
			entity = new Category();
		}
		return entity;
	}
	
	/**
	 * 商品分类列表页面
	 */
	@RequiresPermissions("test:grid:category:list")
	@RequestMapping(value = {"list", ""})
	public String list(Category category, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Category> page = categoryService.findPage(new Page<Category>(request, response), category); 
		model.addAttribute("page", page);
		return "modules/test/grid/categoryList";
	}

	/**
	 * 查看，增加，编辑商品分类表单页面
	 */
	@RequiresPermissions(value={"test:grid:category:view","test:grid:category:add","test:grid:category:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Category category, Model model) {
		model.addAttribute("category", category);
		return "modules/test/grid/categoryForm";
	}

	/**
	 * 保存商品分类
	 */
	@RequiresPermissions(value={"test:grid:category:add","test:grid:category:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Category category, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, category)){
			return form(category, model);
		}
		if(!category.getIsNewRecord()){//编辑表单保存
			Category t = categoryService.get(category.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(category, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			categoryService.save(t);//保存
		}else{//新增表单保存
			categoryService.save(category);//保存
		}
		addMessage(redirectAttributes, "保存商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/test/grid/category/?repage";
	}
	
	/**
	 * 删除商品分类
	 */
	@RequiresPermissions("test:grid:category:del")
	@RequestMapping(value = "delete")
	public String delete(Category category, RedirectAttributes redirectAttributes) {
		categoryService.delete(category);
		addMessage(redirectAttributes, "删除商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/test/grid/category/?repage";
	}
	
	/**
	 * 批量删除商品分类
	 */
	@RequiresPermissions("test:grid:category:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			categoryService.delete(categoryService.get(id));
		}
		addMessage(redirectAttributes, "删除商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/test/grid/category/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("test:grid:category:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Category category, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品分类"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Category> page = categoryService.findPage(new Page<Category>(request, response, -1), category);
    		new ExportExcel("商品分类", Category.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品分类记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/grid/category/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("test:grid:category:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Category> list = ei.getDataList(Category.class);
			for (Category category : list){
				try{
					categoryService.save(category);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品分类记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品分类记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品分类失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/grid/category/?repage";
    }
	
	/**
	 * 下载导入商品分类数据模板
	 */
	@RequiresPermissions("test:grid:category:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品分类数据导入模板.xlsx";
    		List<Category> list = Lists.newArrayList(); 
    		new ExportExcel("商品分类数据", Category.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/test/grid/category/?repage";
    }
	
	
	

}