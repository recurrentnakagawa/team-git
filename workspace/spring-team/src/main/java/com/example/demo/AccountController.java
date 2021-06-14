package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	/**
	 * ログイン画面を表示
	 */

	@RequestMapping("/login")
	public String logins() {
		// セッション情報はクリアする
		session.invalidate();
		return "login";
	}

	/**
	 * ログインを実行
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView doLogin(@RequestParam("email") String email, @RequestParam("password") String password,
			ModelAndView mv) {
		if (email.length() == 0 && password.length() == 0) {
			mv.addObject("message_email", "メールアドレスが未入力です");
			mv.addObject("message_password", "パスワードが未入力です");
			mv.setViewName("login");
			return mv;
		}
		if (email.length() == 0) {
			mv.addObject("message_email", "メールアドレスが未入力です");
			mv.setViewName("login");
			return mv;
		}
		if (password.length() == 0) {
			mv.addObject("message_password", "パスワードが未入力です");
			mv.setViewName("login");
			return mv;
		}
		Client user = clientRepository.findByClientEmailAndClientPassword(email, password);
		if (user == null) {
			mv.addObject("message_email", "メールアドレスとパスワードが一致しません");
			mv.setViewName("login");
			return mv;
		}
		mv.setViewName("InnFrame");
		return mv;
	}

	@RequestMapping("/registUser")
	public ModelAndView registUser(ModelAndView mv) {
		mv.setViewName("regist");
		return mv;
	}

//	@RequestMapping(value = "/registUser", method = RequestMethod.POST)
//	public ModelAndView registUsers(@RequestParam("name") String name, @RequestParam("email") String email,
//			@RequestParam("password") String password, @RequestParam("questionCode") String questionCode,
//			@RequestParam("answer") String answer, ModelAndView mv) {
//		if (name.length() == 0) {
//			mv.addObject("message_name", "名前が入力されていません");
//			mv.setViewName("regist");
//		}
//		if (email.length() == 0) {
//			mv.addObject("message_email", "メールアドレスが入力されていません");
//			mv.setViewName("regist");
//		}
//		if (password.length() == 0) {
//			mv.addObject("message_password", "パスワードが入力されていません");
//			mv.setViewName("regist");
//		}
//		if (answer.length() == 0) {
//			mv.addObject("message_answer", "質問の答えが入力されていません");
//			mv.setViewName("regist");
//		}
//		if (name.length() == 0 || email.length() == 0 || password.length() == 0 || answer.length() == 0) {
//			return mv;
//		}
//		Client user = clientRepository.findByEmail(email);
//		if (user != null) {
//			mv.addObject("message_email", "すでに存在しているメールアドレスです");
//			mv.setViewName("regist");
//			return mv;
//		}
//		int questionCodes = Integer.parseInt(questionCode);
//		Client users = new Client(name, email, password, questionCodes, answer);
//		clientRepository.saveAndFlush(users);
//		mv.setViewName("login");
//		return mv;
//	}

	@RequestMapping("/emailChecker")
	public ModelAndView emailChecker(ModelAndView mv) {
		mv.setViewName("conUser");
		return mv;
	}

	@RequestMapping(value = "/emailChecker", method = RequestMethod.POST)
	public ModelAndView emailCheckers(@RequestParam("email") String email, ModelAndView mv) {
		if (email.length() == 0) {
			mv.addObject("message_email", "メールアドレスが未入力です");
			mv.setViewName("conUser");
			return mv;
		}
		Client user = clientRepository.findByClientEmail(email);
		if (user == null) {
			mv.addObject("message_email", "存在しないメールアドレスです");
			mv.setViewName("conUser");
			return mv;
		}
		int code = user.getQuestionCode();
		Question question = questionRepository.findByQuestionCode(code);
		mv.addObject("question", question);
		mv.setViewName("ansSecret");
		return mv;
	}

	@RequestMapping(value = "/changePass", method = RequestMethod.POST)
	public ModelAndView emailCheckers(@RequestParam("answer") String answer, @RequestParam("question") String question,
			@RequestParam("password") String password, @RequestParam("rePassword") String rePassword,
			@RequestParam("questioCode") String questioCode, @RequestParam("code") String code, ModelAndView mv) {
		int questioCodes = Integer.parseInt(questioCode);
		Question questions = questionRepository.findByQuestionCode(questioCodes);
		mv.addObject("question", questions);
		if (answer.length() == 0) {
			mv.addObject("message_answer", "質問の答えが未入力です");
			mv.setViewName("ansSecret");
		}
		if (password.length() == 0) {
			mv.addObject("message_password", "パスワードが未入力です");
			mv.setViewName("ansSecret");
		}
		if (rePassword.length() == 0) {
			mv.addObject("message_rePassword", "パスワード再入力が未入力です");
			mv.setViewName("ansSecret");
		}
		if (answer.length() == 0 || password.length() == 0 || rePassword.length() == 0) {
			return mv;
		}
		if (!rePassword.equals(password)) {
			mv.addObject("message", "パスワードとパスワード再入力違います");
			mv.setViewName("ansSecret");
			return mv;
		}
		int codes = Integer.parseInt(code);
		Client user = clientRepository.findByClientCode(codes);
		if (!user.getClientAnswer().equals(answer)) {
			mv.addObject("message_answer", "質問の答えが違います");
			mv.setViewName("ansSecret");
			return mv;
		}
		if (passcheck(password) == false) {
			mv.addObject("message", "半角の大文字小文字英字と半角数字を含む8文字以上20文字以下ののパスワードにしてください");
			mv.setViewName("ansSecret");
		}
		user.setClientPassword(password);
		clientRepository.saveAndFlush(user);
		mv.setViewName("login");
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
