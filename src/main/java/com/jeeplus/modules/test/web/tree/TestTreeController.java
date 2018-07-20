/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.web.tree;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.entity.tree.TestTree;
import com.jeeplus.modules.test.service.tree.TestTreeService;

/**
 * 组织机构Controller
 * @author liugf
 * @version 2016-10-04
 */
@Controller
@RequestMapping(value = "${adminPath}/test/tree/testTree")
public class TestTreeController extends BaseController {

	@Autowired
	private TestTreeService testTreeService;
	
	@ModelAttribute
	public TestTree get(@RequestParam(required=false) String id) {
		TestTree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testTreeService.get(id);
		}
		if (entity == null){
			entity = new TestTree();
		}
		return entity;
	}
	
	/**
	 * 机构列表页面
	 */
	@RequiresPermissions("test:tree:testTree:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestTree testTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<TestTree> list = testTreeService.findList(testTree); 
		model.addAttribute("list", list);
		return "modules/test/tree/testTreeList";
	}

	/**
	 * 查看，增加，编辑机构表单页面
	 */
	@RequiresPermissions(value={"test:tree:testTree:view","test:tree:testTree:add","test:tree:testTree:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TestTree testTree, Model model) {
		if (testTree.getParent()!=null && StringUtils.isNotBlank(testTree.getParent().getId())){
			testTree.setParent(testTreeService.get(testTree.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(testTree.getId())){
				TestTree testTreeChild = new TestTree();
				testTreeChild.setParent(new TestTree(testTree.getParent().getId()));
				List<TestTree> list = testTreeService.findList(testTree); 
				if (list.size() > 0){
					testTree.setSort(list.get(list.size()-1).getSort());
					if (testTree.getSort() != null){
						testTree.setSort(testTree.getSort() + 30);
					}
				}
			}
		}
		if (testTree.getSort() == null){
			testTree.setSort(30);
		}
		model.addAttribute("testTree", testTree);
		return "modules/test/tree/testTreeForm";
	}

	/**
	 * 保存机构
	 */
	@RequiresPermissions(value={"test:tree:testTree:add","test:tree:testTree:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TestTree testTree, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, testTree)){
			return form(testTree, model);
		}
		if(!testTree.getIsNewRecord()){//编辑表单保存
			TestTree t = testTreeService.get(testTree.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(testTree, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			testTreeService.save(t);//保存
		}else{//新增表单保存
			testTreeService.save(testTree);//保存
		}
		addMessage(redirectAttributes, "保存机构成功");
		return "redirect:"+Global.getAdminPath()+"/test/tree/testTree/?repage";
	}
	
	/**
	 * 删除机构
	 */
	@RequiresPermissions("test:tree:testTree:del")
	@RequestMapping(value = "delete")
	public String delete(TestTree testTree, RedirectAttributes redirectAttributes) {
		testTreeService.delete(testTree);
		addMessage(redirectAttributes, "删除机构成功");
		return "redirect:"+Global.getAdminPath()+"/test/tree/testTree/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TestTree> list = testTreeService.findList(new TestTree());
		for (int i=0; i<list.size(); i++){
			TestTree e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}