package com.example.demo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FrameController {
	
	@Autowired
	HttpSession session;
	
	@RequestMapping("/")
	public String login() {
		// セッション情報はクリアする
		session.invalidate();
		return "innFrame";
	}
	
	@RequestMapping("/menu")
	public ModelAndView menu(ModelAndView mv) {
		mv.setViewName("menu");
		return mv;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(ModelAndView mv) {
		mv.setViewName("search");
		return mv;
	}
	
	@RequestMapping("/top")
	public ModelAndView main(ModelAndView mv) {
		mv.setViewName("top");
		return mv;
	}
}
