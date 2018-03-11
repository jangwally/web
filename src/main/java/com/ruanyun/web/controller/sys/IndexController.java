package com.ruanyun.web.controller.sys;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.utils.EmptyUtils;

import com.ruanyun.web.model.sys.TUser;

import com.ruanyun.web.util.HttpSessionUtils;

@Controller
public class IndexController extends BaseController {
	

	@RequestMapping("/index")
	public String index(Model model,HttpSession session){
		
		return "pc/index";
	}
	
	
	

}
