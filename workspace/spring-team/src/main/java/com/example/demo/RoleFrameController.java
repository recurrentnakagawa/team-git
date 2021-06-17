package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RoleFrameController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	PrefecturesRepository prefecturesRepository;
	
	@RequestMapping("/aaa")
	public ModelAndView test(ModelAndView mv) {
		mv.setViewName("roleFrame");
		return mv;
	}
	
	@RequestMapping("/roleMenu")
	public ModelAndView menu(ModelAndView mv) {
		mv.setViewName("roleMenu");
		return mv;
	}
	
	@RequestMapping("/roleSearch")
	public ModelAndView search(ModelAndView mv) { 
		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		RoleSearchForm roleBean = new RoleSearchForm();
		mv.addObject("roleBean",roleBean);
		mv.addObject("prefecturesList",prefecturesList);
		mv.setViewName("roleSearch");
		return mv;
	}
	
	@RequestMapping("/roleTop")
	public ModelAndView main(ModelAndView mv) {
		mv.setViewName("roleTop");
		return mv;
	}
}
