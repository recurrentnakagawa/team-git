package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class AccountFrame {
	
	@RequestMapping("/userMenu")
	public ModelAndView userMenu(ModelAndView mv) {
		mv.setViewName("userMenu");
		return mv;
	}
	@RequestMapping("/userRegistFrame")
	public ModelAndView userRegistFrame(ModelAndView mv) {
		mv.setViewName("userRegistFrame");
		return mv;
	}
	@RequestMapping("/userLoginFrame")
	public ModelAndView userLoginFrame(ModelAndView mv) {
		mv.setViewName("userLoginFrame");
		return mv;
	}
}
