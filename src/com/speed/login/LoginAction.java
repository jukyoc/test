package com.speed.login;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.speed.ocss.man.vo.AgentInfoVObj;
@Controller
@RequestMapping("/login")
public class LoginAction {
	@RequestMapping("/login.action")
	public void login(HttpSession session) {
		AgentInfoVObj agent = new AgentInfoVObj();
		agent.setCsId("1000");
		agent.setCsName("admin");
		session.setAttribute("loginAgent", agent);
		System.out.println("login");
	}
}
