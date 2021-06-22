package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	PointRepository pointRepository;

	/**
	 * ログイン画面を表示
	 */

	@RequestMapping("/login")
	public ModelAndView logins(ModelAndView mv) {
		// セッション情報はクリアする
		session.invalidate();
		// 初期Formの作成
		LoginForm loginBean = new LoginForm();
		mv.addObject("bean", loginBean);
		mv.setViewName("login");
		return mv;
	}

	/**
	 * ログインを実行
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView doLogin(@ModelAttribute("bean") LoginForm bean, ModelAndView mv) {
		if (bean.getEmail().length() == 0 && bean.getPassword().length() == 0) {
			mv.addObject("message_email", "メールアドレスが未入力です");
			mv.addObject("message_password", "パスワードが未入力です");
			mv.setViewName("login");
			return mv;
		}
		if (bean.getEmail().length() == 0) {
			mv.addObject("message_email", "メールアドレスが未入力です");
			mv.setViewName("login");
			return mv;
		}
		if (bean.getPassword().length() == 0) {
			mv.addObject("message_password", "パスワードが未入力です");
			mv.setViewName("login");
			return mv;
		}
		//データベースにメールアドレスとパスワードが一致しているアカウントが存在するかを判断
		Client user = clientRepository.findByClientEmailAndClientPassword(bean.getEmail(), bean.getPassword());
		if (user == null) {
			mv.addObject("message_email", "メールアドレスとパスワードが一致しません");
			mv.setViewName("login");
			return mv;
		}
		session.setAttribute("loginUser", user);
		session.setAttribute("login", 1);
		Point point = pointRepository.findByPointCode(user.getPointCode());
		session.setAttribute("point", point.getPointTotal());
		mv.addObject("reloadFlg", 1);
		//電話番号を3つに分ける
		int index1 = user.getClientTel().indexOf("-");
		String telStr1 = user.getClientTel().substring(0, index1);
		int index2 = user.getClientTel().lastIndexOf("-");
		String telStr2 = user.getClientTel().substring(index1+1, index2);
		String telStr3 = user.getClientTel().substring(index2+1, user.getClientTel().length());
		session.setAttribute("tel1", telStr1);
		session.setAttribute("tel2", telStr2);
		session.setAttribute("tel3", telStr3);
		//管理者判断
		if(user.getRoleCode()==1) {
			session.setAttribute("kanri", "1");
		}else {
			session.setAttribute("kanri", "0");
		}
		mv.setViewName("login");
		return mv;
	}
	@RequestMapping("/loginFlg")
	public ModelAndView loginflg(ModelAndView mv) {
		//管理者判断
		if(session.getAttribute("kanri").equals("1")) {
			mv.setViewName("roleFrame");
		}else {
			mv.setViewName("innFrame");
		}
		return mv;
	}

	@RequestMapping("/emailChecker")
	public ModelAndView emailChecker(ModelAndView mv) {
		mv.setViewName("conUser");
		// 初期Formの作成
		conUserForm conUserBean = new conUserForm();
		mv.addObject("conUserBean", conUserBean);
		return mv;
	}

	@RequestMapping(value = "/emailChecker", method = RequestMethod.POST)
	public ModelAndView emailCheckers(@ModelAttribute("bean") conUserForm bean, ModelAndView mv) {
		conUserForm conUserBean = new conUserForm();
		if (bean.getEmail().length() == 0) {
			mv.addObject("message_email", "メールアドレスが未入力です");
			mv.setViewName("conUser");
			mv.addObject("conUserBean", conUserBean);
			return mv;
		}
		Client user = clientRepository.findByClientEmail(bean.getEmail());
		if (user == null) {
			mv.addObject("message_email", "存在しないメールアドレスです");
			mv.setViewName("conUser");
			mv.addObject("conUserBean", conUserBean);
			return mv;
		}
		int code = user.getQuestionCode();
		Question question = questionRepository.findByQuestionCode(code);
		mv.addObject("question", question);
		mv.addObject("code", code);
		session.setAttribute("clientCode", user.getClientCode());
		mv.setViewName("ansSecret");
		// 初期Formの作成
		AnsSecretForm ansSecretBean = new AnsSecretForm();
		mv.addObject("ansSecretBean", ansSecretBean);
		return mv;
	}

	@RequestMapping(value = "/changePass", method = RequestMethod.POST)
	public ModelAndView emailCheckers1(@ModelAttribute("bean") AnsSecretForm bean,
			@RequestParam("question") String question, @RequestParam("code") String code, ModelAndView mv) {
		AnsSecretForm ansSecretBean = new AnsSecretForm();
		int codes = Integer.parseInt(code);
		Question questions = questionRepository.findByQuestionCode(codes);
		mv.addObject("question", questions);
		mv.addObject("code", code);
		if (bean.getAnswer().length() == 0) {
			mv.addObject("message_answer", "質問の答えが未入力です");
			mv.setViewName("ansSecret");
		}
		if (bean.getPassword().length() == 0) {
			mv.addObject("message_password", "パスワードが未入力です");
			mv.setViewName("ansSecret");
		}
		if (bean.getRePassword().length() == 0) {
			mv.addObject("message_rePassword", "パスワード再入力が未入力です");
			mv.setViewName("ansSecret");
		}
		if (bean.getAnswer().length() == 0 || bean.getPassword().length() == 0 || bean.getRePassword().length() == 0) {
			mv.addObject("ansSecretBean", ansSecretBean);
			return mv;
		}
		if (!bean.getRePassword().equals(bean.getPassword())) {
			mv.addObject("message", "パスワードとパスワード再入力の値が違います");
			mv.setViewName("ansSecret");
			mv.addObject("ansSecretBean", ansSecretBean);
			return mv;
		}
		int clientCode=(int) session.getAttribute("clientCode");
		Client user = clientRepository.findByClientCode(clientCode);
		if (!user.getClientAnswer().equals(bean.getAnswer())) {
			mv.addObject("message_answer", "質問の答えが違います");
			mv.setViewName("ansSecret");
			mv.addObject("ansSecretBean", ansSecretBean);
			return mv;
		}
		if (passcheck(bean.getPassword()) == false) {
			mv.addObject("message", "半角の大文字小文字英数字を含む8文字以上20文字以下のパスワードにしてください");
			mv.setViewName("ansSecret");
			mv.addObject("ansSecretBean", ansSecretBean);
			return mv;
		}
		mv.addObject("ansSecretBean", ansSecretBean);
		mv.addObject("reloadFlg", 1);
		user.setClientPassword(bean.getPassword());
		clientRepository.saveAndFlush(user);
		mv.setViewName("ansSecret");
		mv.addObject("message", "パスワードを変更しました");
		mv.addObject("bean", bean);
		return mv;
	}
	@RequestMapping("/changePassFlg")
	public ModelAndView changePassFlg(ModelAndView mv) {
		mv.setViewName("userLoginFrame");
		return mv;
	}

	public static boolean passcheck(String pass) {
		boolean result = true;
		String check = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z\\-]{8,20}$";
		Pattern pattern = Pattern.compile(check);
		Matcher m1 = pattern.matcher(pass); // パターンと検査対象文字列の照合
		result = m1.matches(); // 照合結果をtrueかfalseで取得
		return result;
	}
}
