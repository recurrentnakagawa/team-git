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
	public ModelAndView innAddComp(@ModelAttribute("conInnInfoForm")conInnInfoForm conInInfoForm,ModelAndView mv) throws DAOException {
		Inn innBean = new Inn(conInInfoForm.getInnName(),conInInfoForm.getPrefectureCode(),conInInfoForm.getInnAddress(),conInInfoForm.getInnAccess(),conInInfoForm.getCheckinTime(),conInInfoForm.getCheckoutTime(),conInInfoForm.getInnAmenity(),"0",null);
		
		conInnInfoForm bean = new conInnInfoForm();
		bean.setPrefecture(innBean.getPrefecturesCode());
		
		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		List<String> timeList = time();
		
		InnDAO dao = new InnDAO();
		String pref = dao.findPrefectureName(conInInfoForm.getPrefectureCode());
		
		mv.addObject("pref",pref);
		mv.addObject("prefecturesList",prefecturesList);
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
			ModelAndView mv) throws DAOException {
		
		Inn innBean = new Inn(conInInfoForm.getInnName(),conInInfoForm.getPrefectureCode(),conInInfoForm.getInnAddress(),conInInfoForm.getInnAccess(),conInInfoForm.getCheckinTime(),conInInfoForm.getCheckoutTime(),conInInfoForm.getInnAmenity(),conInInfoForm.getInnInvalid(),null);
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
			mv.setViewName("innInfo");
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
		InnDAO dao = new InnDAO();
		String pref = dao.findPrefectureName(conInInfoForm.getPrefectureCode());
		mv.addObject("pref",pref);
		
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
			Inn innBean = new Inn(innCode,conInInfoForm.getInnName(),conInInfoForm.getPrefectureCode(),conInInfoForm.getInnAddress(),conInInfoForm.getInnAccess(),conInInfoForm.getCheckinTime(),conInInfoForm.getCheckoutTime(),conInInfoForm.getInnAmenity(),conInInfoForm.getInnInvalid(),null);
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
		mv.addObject("innCode",innCode);
		mv.setViewName("roleShowRoom");
		return mv;
	}
	
	//部屋追加画面に遷移
	@RequestMapping(value="/addRoom",method=RequestMethod.POST)
	public ModelAndView addRoom(@RequestParam("innCode")String innCodeStr,ModelAndView mv) {	
		
		int innCode = Integer.parseInt(innCodeStr);
		conRoomInfoForm bean = new conRoomInfoForm();
		
		mv.addObject("bean", bean);
		mv.addObject("innCode", innCode);
		mv.addObject("addFlg","1");
		mv.setViewName("roomInfo");
		return mv;
	}
	
	//部屋更新画面に遷移
	@RequestMapping(value="/updRoom/{roomCode}")
	public ModelAndView updRoom(
			@PathVariable(name = "roomCode") int roomCode,
			ModelAndView mv
	) {	
		Room roomBean = roomRepository.findByRoomCode(roomCode);
				
		mv.addObject("bean", roomBean);
		mv.addObject("updFlg","1");
		mv.setViewName("roomInfo");
		return mv;
	}
	
	//管理者で部屋追加確認画面へ遷移
	@RequestMapping(value="/roomAdd",method=RequestMethod.POST)
	public ModelAndView roomAddComp(
			@ModelAttribute("conRoomInfoForm")conRoomInfoForm conRoomInfoForm,
			@RequestParam("innCode")int innCode,
			ModelAndView mv) {
		
		int flg = 0;
		//部屋名の未入力チェック
		if(conRoomInfoForm.getRoomName().length()==0) {
			mv.addObject("roomNameMessage", "部屋名を入力してください。");
			flg = 1;
		}
		//部屋の詳細の未入力チェック
		if(conRoomInfoForm.getRoomDetail().length()==0) {
			mv.addObject("roomDetailMessage", "部屋の詳細を入力してください。");
			flg = 1;
		}
		//部屋の料金の未入力チェック
		if(conRoomInfoForm.getRoomPrice().length()==0) {
			mv.addObject("roomPriceMessage", "宿の料金を入力してください。");
			flg = 1;
		}
		//部屋の数の未入力チェック
		if(conRoomInfoForm.getRoomTotal().length()==0) {
			mv.addObject("roomTotalMessage", "部屋の数を入力してください。");
			flg = 1;
		}
		//部屋の最大人数の未入力チェック
		if(conRoomInfoForm.getRoomMax().length()==0) {
			mv.addObject("roomMaxMessage", "部屋の最大人数を入力してください。");
			flg = 1;
		}
		//料金、部屋の数、最大人数の数値チェック
		if(numcheck(conRoomInfoForm.getRoomPrice()) == false) {
			mv.addObject("roomPriceMessage", "部屋の料金は数値で入力してください。");
			flg = 1;
		}
		if(numcheck(conRoomInfoForm.getRoomTotal()) == false) {
			mv.addObject("roomTotalMessage", "部屋の数は数値で入力してください。");
			flg = 1;
		}
		if(numcheck(conRoomInfoForm.getRoomMax()) == false) {
			mv.addObject("roomMaxMessage", "部屋の最大人数は数値で入力してください。");
			flg = 1;
		}
		if(conRoomInfoForm.getRoomPrice().length()>=8) {
			mv.addObject("roomPriceMessage", "部屋の料金は8桁までの数値で入力してください");
			flg = 1;
		}
		if(conRoomInfoForm.getRoomTotal().length()>=4) {
			mv.addObject("roomTotalMessage", "部屋の数は4桁までの数値で入力してください");
			flg = 1;
		}
		if(conRoomInfoForm.getRoomMax().length()>=3) {
			mv.addObject("roomMaxMessage", "部屋の最大人数は3桁までの数値で入力してください");
			flg = 1;
		}
		//一つでもエラー項目があれば再度入力画面へ遷移
		if(flg ==1) {
			mv.addObject("addFlg", "1");
			mv.addObject("bean", conRoomInfoForm);
			mv.addObject("innCode", innCode);
			mv.setViewName("roomInfo");
			return mv;
		}
		mv.addObject("addFlg", "1");
		mv.addObject("bean", conRoomInfoForm);
		mv.addObject("innCode", innCode);
		mv.setViewName("conRoomInfo");
		return mv;
	}
	
	//管理者で部屋更新確認画面へ遷移
	@RequestMapping(value="/roomUpd",method=RequestMethod.POST)
	public ModelAndView roomUpdComp(@ModelAttribute("conRoomInfoForm")conRoomInfoForm conRoomInfoForm,ModelAndView mv) {
		
		int flg = 0;
		//部屋名の未入力チェック
		if(conRoomInfoForm.getRoomName().length()==0) {
			mv.addObject("roomNameMessage", "部屋名を入力してください。");
			flg = 1;
		}
		//部屋の詳細の未入力チェック
		if(conRoomInfoForm.getRoomDetail().length()==0) {
			mv.addObject("roomDetailMessage", "部屋の詳細を入力してください。");
			flg = 1;
		}
		//部屋の料金の未入力チェック
		if(conRoomInfoForm.getRoomPrice().length()==0) {
			mv.addObject("roomPriceMessage", "宿の料金を入力してください。");
			flg = 1;
		}
		//部屋の数の未入力チェック
		if(conRoomInfoForm.getRoomTotal().length()==0) {
			mv.addObject("roomTotalMessage", "部屋の数を入力してください。");
			flg = 1;
		}
		//部屋の最大人数の未入力チェック
		if(conRoomInfoForm.getRoomMax().length()==0) {
			mv.addObject("roomMaxMessage", "部屋の最大人数を入力してください。");
			flg = 1;
		}
		//料金、部屋の数、最大人数の数値チェック
		if(numcheck(conRoomInfoForm.getRoomPrice()) == false) {
			mv.addObject("roomPriceMessage", "部屋の料金料金は数値で入力してください。");
			flg = 1;
		}
		if(numcheck(conRoomInfoForm.getRoomTotal()) == false) {
			mv.addObject("roomTotalMessage", "部屋の数は数値で入力してください。");
			flg = 1;
		}
		if(numcheck(conRoomInfoForm.getRoomMax()) == false) {
			mv.addObject("roomMaxMessage", "部屋の最大人数は数値で入力してください。");
			flg = 1;
		}
		if(conRoomInfoForm.getRoomPrice().length()>=8) {
			mv.addObject("roomPriceMessage", "部屋の料金は8桁までの数値で入力してください");
			flg = 1;
		}
		if(conRoomInfoForm.getRoomTotal().length()>=4) {
			mv.addObject("roomTotalMessage", "部屋の数は4桁までの数値で入力してください");
			flg = 1;
		}
		if(conRoomInfoForm.getRoomMax().length()>=3) {
			mv.addObject("roomMaxMessage", "部屋の最大人数は3桁までの数値で入力してください");
			flg = 1;
		}
		//一つでもエラー項目があれば再度入力画面へ遷移
		if(flg ==1) {
			mv.addObject("updFlg", "1");
			mv.addObject("bean", conRoomInfoForm);
			mv.setViewName("roomInfo");
			return mv;
		}
		mv.addObject("updFlg", "1");
		mv.addObject("bean", conRoomInfoForm);
		mv.setViewName("conRoomInfo");
		return mv;
	}
	
	//管理者で部屋削除確認画面へ遷移
	@RequestMapping(value="/delRoom/{roomCode}")
	public ModelAndView roomDelComp(@PathVariable(name="roomCode") String roomCodeStr,ModelAndView mv) {
		
		int roomCode = Integer.parseInt(roomCodeStr);
		
		Room roomBean = roomRepository.findByRoomCode(roomCode);
		String priceStr = String.valueOf(roomBean.getRoomPrice());
		String totalStr = String.valueOf(roomBean.getRoomTotal());
		String maxStr = String.valueOf(roomBean.getRoomMax());
		
		conRoomInfoForm bean = new conRoomInfoForm(roomBean.getRoomCode(),roomBean.getInnCode(),roomBean.getRoomName(),roomBean.getRoomDetail(),priceStr,totalStr,maxStr);
		
		mv.addObject("bean",bean);
		mv.addObject("delFlg","1");
		mv.setViewName("conRoomInfo");
		return mv;
	}

	//部屋の追加・更新・削除処理と、TOP画面への遷移
	@RequestMapping(value="/conRoomInfo",method=RequestMethod.POST)
	public ModelAndView roomAddComplete(
			@ModelAttribute("conRoomInfoForm")conRoomInfoForm conRoomInfoForm,
			@RequestParam("innCode")String innCodeStr,
			@RequestParam("flg")String flg,
			ModelAndView mv) {
			
		int innCode = Integer.parseInt(innCodeStr);
		
		int price = Integer.parseInt(conRoomInfoForm.getRoomPrice());
		int total = Integer.parseInt(conRoomInfoForm.getRoomTotal());
		int max = Integer.parseInt(conRoomInfoForm.getRoomMax());
		
		if(flg.equals("1")) {
			Room roomBean = new Room(innCode,conRoomInfoForm.getRoomName(),conRoomInfoForm.getRoomDetail(),price,total,max);
			roomRepository.saveAndFlush(roomBean);
			mv.addObject("message", "部屋が１件追加されました。");
		}
		if(flg.equals("2")) {
			Room roomBean = new Room(conRoomInfoForm.getRoomCode(),innCode,conRoomInfoForm.getRoomName(),conRoomInfoForm.getRoomDetail(),price,total,max);
			roomRepository.saveAndFlush(roomBean);
			mv.addObject("message", "部屋が１件更新されました。");
		}
		if(flg.equals("3")) {
			roomRepository.deleteById(conRoomInfoForm.getRoomCode());
			mv.addObject("message", "部屋が１件削除されました。");
		}
		mv.setViewName("roleTop");
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
	
	// 数値チェック
	public boolean numcheck(String suu) {
		boolean isNumeric = suu.matches("[+-]?\\d*(\\.\\d+)?");
		return isNumeric;
	}
	
	
}