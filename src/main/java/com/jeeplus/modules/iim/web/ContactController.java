/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.iim.entity.Friend;
import com.jeeplus.modules.iim.entity.FriendGroup;
import com.jeeplus.modules.iim.entity.LayFileJsonData;
import com.jeeplus.modules.iim.entity.LayGroup;
import com.jeeplus.modules.iim.entity.LayGroupJsonData;
import com.jeeplus.modules.iim.entity.LayGroupUser;
import com.jeeplus.modules.iim.entity.LayJson;
import com.jeeplus.modules.iim.entity.LayJsonData;
import com.jeeplus.modules.iim.entity.Mine;
import com.jeeplus.modules.iim.service.LayGroupService;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 联系人Controller
 * 
 * @author jeeplus(seven修改)
 * @version 2016-6-23
 */
@Controller
@RequestMapping(value = "${adminPath}/iim/contact")
public class ContactController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private LayGroupService layGroupService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private OfficeService officeService;

	/**
	 * 打开通讯录
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "index", "" })
	public String index(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<User> list = systemService.findUser(user);
		model.addAttribute("list", list);
		return "modules/iim/contacts";
	}

	/**
	 * 查找
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "searchUsers")
	public String searchUsers(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<User> friends = userDao.searchUsers(user);
		model.addAttribute("list", friends);

		return "modules/iim/search_user";
	}

	/**
	 * 添加好友--->常用联系人
	 */
	@RequestMapping(value = "addFriend")
	public String addFriend(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] = ids.split(",");
		User currentUser = UserUtils.getUser();
		for (String id : idArray) {
			if (userDao.findFriend(currentUser.getId(), id) == null) {

				userDao.insertFriend(IdGen.uuid(), currentUser.getId(), id);// 添加对方为好友
				// userDao.insertFriend(IdGen.uuid(), id,
				// currentUser.getId());//同时把自己添加为对方的好友
			}
		}
		addMessage(redirectAttributes, "添加好友成功");
		return "redirect:" + Global.getAdminPath() + "/iim/contact/myFriends/?repage";
	}

	/**
	 * 删除好友
	 */
	@RequestMapping(value = "delFriend")
	public String delFriend(String id, RedirectAttributes redirectAttributes) {
		User friend = UserUtils.get(id);
		User currentUser = UserUtils.getUser();
		if (friend != null && userDao.findFriend(currentUser.getId(), friend.getId()) != null) {
			userDao.deleteFriend(currentUser.getId(), friend.getId());// 删除好友
			// userDao.deleteFriend(friend.getId(),
			// currentUser.getId());//同时把自己从对方的好友列表删除
		}

		return "redirect:" + Global.getAdminPath() + "/iim/contact/myFriends/?repage";
	}

	/**
	 * 打开我的好友列表--->常用联系人列表
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "myFriends")
	public String myFriends(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		List<User> friends = userDao.findFriends(currentUser);
		model.addAttribute("list", friends);
		return "modules/iim/myFriends";
	}

	/**
	 * 打开聊天窗口
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "layerIM")
	public String layerIM(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<User> list = systemService.findUser(user);
		model.addAttribute("list", list);
		return "modules/iim/layerIM";
	}

	/**
	 * layim初始化信息
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "friend")
	@ResponseBody
	public LayJson getFriend(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		LayJsonData data = new LayJsonData();

		data.getFriend().clear();
		LayJson j = new LayJson();
		j.setCode(0);
		// 我的个人信息
		Mine m = new Mine();
		m.setId(UserUtils.getUser().getLoginName());
		m.setUsername(UserUtils.getUser().getName());
		m.setStatus("online");
		m.setRemark(UserUtils.getUser().getSign());
		m.setAvatar(UserUtils.getUser().getPhoto());
		data.setMine(m);

		// 我的好友--->常用联系人
		User currentUser = UserUtils.getUser();
		List<User> friends = userDao.findFriends(currentUser);
		FriendGroup friendGroup = new FriendGroup();
		friendGroup.setGroupname("我的好友");
		friendGroup.setId(1);
		friendGroup.setOnline(friends.size());
		for (User u : friends) {
			Friend friend = new Friend();
			friend.setId(u.getLoginName());
			friend.setUsername(u.getName());
			friend.setAvatar(u.getPhoto());
			friend.setSign(u.getSign());
			friendGroup.getList().add(friend);
		}
		data.getFriend().add(friendGroup);

		// 按部门显示联系人
		List<Office> officeList = officeService.findList(true);
		int index = 1;
		for (Office office : officeList) {
			user.setOffice(office);
			List<User> users = userDao.findListByOffice(user);
			FriendGroup fgroup = new FriendGroup();
			fgroup.setGroupname(office.getName());
			fgroup.setOnline(users.size());
			fgroup.setId(++index);
			for (User u : users) {
				Friend friend = new Friend();
				friend.setId(u.getLoginName());
				friend.setUsername(u.getName());
				friend.setAvatar(u.getPhoto());
				friend.setSign(u.getSign());
				fgroup.getList().add(friend);
			}
			data.getFriend().add(fgroup);
		}

		// 不属于任何部门的联系人
		user.setOffice(null);
		List<User> users = userDao.findListByOffice(user);
		FriendGroup group = new FriendGroup();
		group.setGroupname("未分组");
		group.setOnline(users.size());
		group.setId(++index);
		for (User u : users) {
			Friend friend = new Friend();
			friend.setId(u.getLoginName());
			friend.setUsername(u.getName());
			friend.setAvatar(u.getPhoto());
			friend.setSign(u.getSign());
			group.getList().add(friend);
		}
		data.getFriend().add(group);

		// 用户自己创建的群组和属于的群组
		List<LayGroup> layGroupList = new ArrayList<LayGroup>();
		LayGroup layGroup = new LayGroup();
		// 查找我自己创建的群组
		layGroup.setCreateBy(UserUtils.getUser());
		List<LayGroup> ownerLayGroupList = layGroupService.findList(layGroup);

		// 查找我属于的群组
		List<LayGroup> memberLayGroupList = layGroupService.findGroupList(UserUtils.getUser());

		layGroupList.addAll(ownerLayGroupList);
		layGroupList.addAll(memberLayGroupList);

		for (LayGroup g : layGroupList) {
			data.getGroup().add(g);
		}

		j.setData(data);
		return j;
	}

	/**
	 * 群组成员接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "getMembers")
	@ResponseBody
	public LayJson getMembers(LayGroup group) {
		LayGroupJsonData data = new LayGroupJsonData();
		group = layGroupService.get(group.getId());
		// 设置群主
		data.getOwner().put("username", group.getCreateBy().getName());
		data.getOwner().put("id", group.getCreateBy().getId());
		data.getOwner().put("avatar", group.getCreateBy().getPhoto());
		data.getOwner().put("sign", group.getCreateBy().getSign());
		// 将群主添加到群聊第一个
		Friend ower = new Friend();
		ower.setUsername(group.getCreateBy().getName());
		ower.setId(group.getCreateBy().getId());
		ower.setAvatar(group.getCreateBy().getPhoto());
		ower.setSign(group.getCreateBy().getSign());
		data.getList().add(ower);

		List<LayGroupUser> zlist = group.getLayGroupUserList();
		for (LayGroupUser lgUser : zlist) {
			Friend friend = new Friend();
			friend.setUsername(lgUser.getUser().getName());
			friend.setId(lgUser.getUser().getId());
			friend.setAvatar(lgUser.getUser().getPhoto());
			friend.setSign(lgUser.getUser().getSign());
			data.getList().add(friend);
		}

		LayJson j = new LayJson();
		j.setData(data);
		return j;
	}

	@ResponseBody
	@RequestMapping(value = { "uploadImage", "uploadFile" })
	public String uploadImage(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws IllegalStateException, IOException {
		String filepath = "";
		String content = null;
		LayFileJsonData data = new LayFileJsonData();
		// 判断文件是否为空
		if (!file.isEmpty()) {
			// 文件保存路径
			String realPath = Global.USERFILES_BASE_URL + UserUtils.getPrincipal() + "/images/";
			// 转存文件
			FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
			file.transferTo(new File(Global.getUserfilesBaseDir() + realPath + file.getOriginalFilename()));
			filepath = request.getContextPath() + realPath + file.getOriginalFilename();
			data.setName(file.getName());
			data.setSrc(filepath);
		}
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", "0");
		map.put("data", data);
		content = mapper.writeValueAsString(map);
 		return content;
	}
}