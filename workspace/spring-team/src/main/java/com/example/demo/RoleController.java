package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.DAOException;
import com.example.dao.InnDAO;

@Controller
public class RoleController {

	
	String PREFECTURE = "13";
	
	@Autowired
	HttpSession session;

	@Autowired
	InnRepository innRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	PrefecturesRepository prefecturesRepository;

	//管理者で宿検索
	@RequestMapping(value="/roleSearch", method=RequestMethod.POST)
	public ModelAndView roleSearch(@ModelAttribute("roleSearchForm")RoleSearchForm roleSearchForm,ModelAndView mv) throws DAOException {
		
		InnDAO dao = new InnDAO();
		
		//地域名で検索
		if(roleSearchForm.getInn_name().length() <= 0) {
			List<Inn> innList =  innRepository.findByPrefecturesCode(roleSearchForm.getPref_id());
			mv.addObject("innList", innList);
		}
		//地域名と宿名で検索
		else {
			List <Inn> innList = dao.findByInn(roleSearchForm.getInn_name(), roleSearchForm.getPref_id());
			
			mv.addObject("innList", innList);
		}
		mv.setViewName("roleShowInn");
		return mv;
	}
	
	//宿情報更新画面に遷移
	@RequestMapping(value = "/conInnUpd/{innCode}")
	public ModelAndView innUpd(@PathVariable(name = "innCode") int innCode,ModelAndView mv) {
		
		List<Prefectures> prefecturesList =  prefecturesRepository.findAll();
		List<String> timeList = time();
		
		Inn innBean = innRepository.findByInnCode(innCode);
		
		mv.addObject("innCode",innCode);
		mv.addObject("prefecturesList",prefecturesList);
		mv.addObject("timeList",timeList);
		mv.addObject("bean", innBean);
		mv.addObject("updFlg",1);
		mv.setViewName("innInfo");
		return mv;
	}
	
	//管理者で宿追加画面に遷移
	@RequestMapping("/innAdd")
	public ModelAndView innAdd(ModelAndView mv) {
		conInnInfoForm bean = new conInnInfoForm();
		bean.setPrefecture(PREFECTURE);
		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		List<String> timeList = time();
		
		mv.addObject("prefecturesList",prefecturesList);
		mv.addObject("timeList",timeList);
		mv.addObject("bean",bean);
		mv.setViewName("innInfo");
		return mv;
	}
	
	//管理者で宿追加確認画面へ遷移
	@RequestMapping(value="/innAdd",method=RequestMethod.POST)
	public ModelAndView innAddComp(@ModelAttribute("conInnInfoForm")conInnInfoForm conInInfoForm,ModelAndView mv) {
		Inn innBean = new Inn(conInInfoForm.getInnName(),conInInfoForm.getPrefectureCode(),conInInfoForm.getInnAddress(),conInInfoForm.getInnAccess(),conInInfoForm.getCheckinTime(),conInInfoForm.getCheckoutTime(),conInInfoForm.getInnAmenity(),"0",null);
		
		conInnInfoForm bean = new conInnInfoForm();
		bean.setPrefecture(innBean.getPrefecturesCode());
		
		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		List<String> timeList = time();
		
		mv.addObject(prefecturesList);
		mv.addObject("timeList",timeList);
		
		int flg = 0;
		//宿名の未入力チェック
		if(innBean.getInnName().length()==0) {
			mv.addObject("innNameMessage", "宿名を入力してください。");
			flg = 1;
		}
		//住所の未入力チェック
		if(innBean.getInnAddress().length()==0) {
			mv.addObject("innAddressMessage", "住所を入力してください。");
			flg = 1;
		}
		//最寄駅からのアクセスの未入力チェック
		if(innBean.getInnAccess().length()==0) {
			mv.addObject("innAccessMessage", "最寄駅からのアクセスを入力してください。");
			flg = 1;
		}
		//アメニティ詳細の未入力チェック
		if(innBean.getInnAmenity().length()==0) {
			mv.addObject("innAmenityMessage", "アメニティ詳細を入力してください。");
			flg = 1;
		}
		//一つでも未入力項目があれば再度入力画面へ遷移
		if(flg ==1) {
			mv.addObject("bean", conInInfoForm);
			mv.setViewName("innInfo");
			return mv;
		}
		//宿名と住所の重複チェック
		Inn checkInnBean = innRepository.findByInnNameAndInnAddress(innBean.getInnName(),innBean.getInnAddress());
		if(checkInnBean!=null) {
			mv.addObject("innNameAndInnAddressMessage", "その宿名と住所の組み合わせは既に使われています。");
			mv.addObject("bean", conInInfoForm);
			mv.setViewName("innInfo");
			return mv;
		}
		mv.addObject("addFlg", "1");
		mv.addObject("bean", conInInfoForm);
		mv.setViewName("conInnInfo");
		return mv;
	}
	
	
	//管理者で宿更新確認画面へ遷移
	@RequestMapping(value="/innUpd",method=RequestMethod.POST)
	public ModelAndView innUpdComp(
			@ModelAttribute("conInnInfoForm")conInnInfoForm conInInfoForm,
			@RequestParam("innCode") String innCodeStr,
			ModelAndView mv) {
		
		Inn innBean = new Inn(conInInfoForm.getInnName(),conInInfoForm.getPrefectureCode(),conInInfoForm.getInnAddress(),conInInfoForm.getInnAccess(),conInInfoForm.getCheckinTime(),conInInfoForm.getCheckoutTime(),conInInfoForm.getInnAmenity(),"0",null);
		int innCode = Integer.parseInt(innCodeStr);
		innBean.setInnCode(innCode);
		int flg = 0;
		//宿名の未入力チェック
		if(innBean.getInnName().length()==0) {
			mv.addObject("innNameMessage", "宿名を入力してください。");
			flg = 1;
		}
		//住所の未入力チェック
		if(innBean.getInnAddress().length()==0) {
			mv.addObject("innAddressMessage", "住所を入力してください。");
			flg = 1;
		}
		//最寄駅からのアクセスの未入力チェック
		if(innBean.getInnAccess().length()==0) {
			mv.addObject("innAccessMessage", "最寄駅からのアクセスを入力してください。");
			flg = 1;
		}
		//アメニティ詳細の未入力チェック
		if(innBean.getInnAmenity().length()==0) {
			mv.addObject("innAmenityMessage", "アメニティ詳細を入力してください。");
			flg = 1;
		}
		//一つでも未入力項目があれば再度入力画面へ遷移
		if(flg ==1) {
			mv.addObject("bean", conInInfoForm);
			mv.setViewName("conInnInfo");
			return mv;
		}
		//宿名と住所の重複チェック
		Inn checkInnBean = innRepository.findByInnNameAndInnAddress(innBean.getInnName(),innBean.getInnAddress());
		if(checkInnBean!=null) {
			if(!(checkInnBean.getInnName().equals(innBean.getInnName()) && checkInnBean.getInnAddress().equals(innBean.getInnAddress()))) {
				mv.addObject("innNameAndInnAddressMessage", "その宿名と住所の組み合わせは既に使われています。");
				mv.addObject("bean", conInInfoForm);
				mv.setViewName("conInnInfo");
				return mv;
			}
		}
		conInInfoForm.setInnCode(innCode);
		mv.addObject("bean", conInInfoForm);
		mv.addObject("updFlg","2");
		mv.setViewName("conInnInfo");
		return mv;
	}
	
	//宿の追加・更新処理と、TOP画面への遷移
	@RequestMapping(value="/conInnInfo",method=RequestMethod.POST)
	public ModelAndView innAddComplete(
			@ModelAttribute("conInnInfoForm")conInnInfoForm conInInfoForm,
			@RequestParam(name = "innCode",defaultValue = "")String innCodeStr,
			@RequestParam("flg")String flg,
			ModelAndView mv) {
				
		if(flg.equals("1")) {
			
			Inn innBean = new Inn(conInInfoForm.getInnName(),conInInfoForm.getPrefectureCode(),conInInfoForm.getInnAddress(),conInInfoForm.getInnAccess(),conInInfoForm.getCheckinTime(),conInInfoForm.getCheckoutTime(),conInInfoForm.getInnAmenity(),"0",null);
			innRepository.saveAndFlush(innBean);
			mv.addObject("message", "宿が１件追加されました。");
		}
		if(flg.equals("2")) {
			int innCode = Integer.parseInt(innCodeStr);
			Inn innBean = new Inn(innCode,conInInfoForm.getInnName(),conInInfoForm.getPrefectureCode(),conInInfoForm.getInnAddress(),conInInfoForm.getInnAccess(),conInInfoForm.getCheckinTime(),conInInfoForm.getCheckoutTime(),conInInfoForm.getInnAmenity(),"0",null);
			innRepository.saveAndFlush(innBean);
			mv.addObject("message", "宿が１件更新されました。");
		}
		mv.setViewName("roleTop");
		return mv;
	}
	
	//部屋一覧を表示する
	@RequestMapping(value="/conShowRoom/{innCode}")
	public ModelAndView showRoom(@PathVariable(name = "innCode") String innCodeStr,ModelAndView mv) {	
		
		int innCode = Integer.parseInt(innCodeStr);
		List<Room> roomList = roomRepository.findByInnCode(innCode);
		
		mv.addObject("roomList", roomList);
		mv.setViewName("roleShowRoom");
		return mv;
	}
	
	@RequestMapping(value="/addRoom/",method=RequestMethod.POST)
	public ModelAndView addRoom(@RequestParam("innCode")String innCodeStr,ModelAndView mv) {	
		int innCode = Integer.parseInt(innCodeStr);
		List<Room> roomList = roomRepository.findByInnCode(innCode);
		
		mv.addObject("roomList", roomList);
		mv.setViewName("roleShowRoom");
		return mv;
	}
	
	public List<String> time(){
		List<String> timeList = new ArrayList<String>() ;
		for(int i = 1 ; i <= 24; i++) {
			String iStr = Integer.toString(i);
			if(i <= 9) {
				iStr = "0" + iStr;
			}
			iStr = iStr + "00";
			timeList.add(iStr);
		}
		return timeList;
	}
	
	
}