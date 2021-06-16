package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class AccountFrame {
	
	@RequestMapping("/userMenu")
	public ModelAndView userMenu(ModelAndView mv) {
		mv.setViewName("userMenu");
		return mv;
	}
}
