package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.DAOException;
import com.example.dao.HistoryDAO;


@Controller
public class mypageControlle {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ViewHistoryRepository viewHistoryRepository;
	
	@RequestMapping("/mypageView")
	public ModelAndView mypageView(ModelAndView mv) throws DAOException {
		//ログインユーザー情報
		Client client = (Client)session.getAttribute("loginUser");
		HistoryDAO dao = new HistoryDAO();
		List <ViewBean> viewList = dao.history(client.getClientCode());
		mv.addObject("viewList", viewList);
		mv.setViewName("mypageView");
		return mv;
	}
	
	@RequestMapping("/deleteView")
	public ModelAndView deleteView(@RequestParam("code") String code,ModelAndView mv) throws DAOException {
		int viewCode = Integer.parseInt(code);
		viewHistoryRepository.deleteById(viewCode);
		viewHistoryRepository.flush();
		Client client = (Client)session.getAttribute("loginUser");
		HistoryDAO dao = new HistoryDAO();
		List <ViewBean> viewList = dao.history(client.getClientCode());
		mv.addObject("viewList", viewList);
		mv.setViewName("mypageView");
		return mv;
	}
}
