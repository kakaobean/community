package com.ktds.member.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.service.CommunityService;
import com.ktds.member.constants.Member;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MemberController {

	private MemberService memberService;
	private CommunityService communityService;
	
	
	
	public CommunityService getCommunityService() {
		return communityService;
	}

	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	 

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLoginPage(HttpSession session) {

		if (session.getAttribute(Member.USER) != null) {
			return "redirect:/";
		}

		return "member/login";
	}
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String viewRegistPage() {
		return "member/regist";
		
		
	}
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ModelAndView doRegistAction(@ModelAttribute("registForm")
										@Valid MemberVO memberVO, Errors errors) {
		if( errors.hasErrors()) {
			//ModelAndView
			
			return new ModelAndView("member/regist");
			
		}
		
		if (memberService.createMember(memberVO)) {
			
			return new ModelAndView("redirect:/login") ;   // 성공했을 떄 
		}
		return new ModelAndView("member/regist");
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLoginAction(@ModelAttribute("loginForm") @Valid MemberVO memberVO, Errors errors, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		// FIXME DB에 계정이 존재하지 않을 경우로 변경
		MemberVO loginMember = memberService.readMember(memberVO);
		
		if( loginMember != null) {
			session.setAttribute(Member.USER, loginMember);
			return "redirect:/";
			
		}
		
		return "redirect:/login";   // 로그인 실패
	}

	@RequestMapping("/logout")
	public String doLogoutAction(HttpSession session) {

		// 세션 소멸
		session.invalidate();

		return "redirect:/login";
	}
	
	@RequestMapping("/delete/process1")
	public String viewVerifyPage() {
		return "member/delete/process1";
	}
	
	@RequestMapping("/delete/process2")
	public ModelAndView viewDeleteMyCommunitiesPage(
			@RequestParam(required = false , defaultValue = "") String password, HttpSession session){
		if(password.length() == 0) {
			return new ModelAndView("error/404");  // 잘못 적었다면 404
		}

		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		member.setPassword(password);
		
		MemberVO verifyMember = memberService.readMember(member);
		if(verifyMember == null) {
			return new ModelAndView("redirect:/delete/process1");  // 비번이 아니라면 돌아가라 
		}
		
		// TODO 내가 작성한 게시글의 갯수 가져오기 
		int myCommunitiesCount = communityService.readMyCommunitiesCount(verifyMember.getId());
		
		
		ModelAndView view = new ModelAndView();
		view.setViewName("member/delete/process2");
		view.addObject("myCommunitiesCount", myCommunitiesCount);
		
		String uuid = UUID.randomUUID().toString();
		session.setAttribute("__TOKEN__", uuid);
		view.addObject("token", uuid);  // 나노세컨으로 난수를 생성
		
		
		return view;
	}
	
	
	@RequestMapping("/account/delete/{deleteFlag}")
	public String removeAction(HttpSession session,
			@RequestParam(required=false, defaultValue="") String token,
			@PathVariable String deleteFlag) {
		
		String sessionToken = (String) session.getAttribute("__TOKEN__");
		if(sessionToken == null || !sessionToken.equals(token)) {
			return "error/404";
		}
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		if( member == null) {
			return "redirect:/login";
		}
		int id= member.getId();
		
		if(memberService.removeMember(id, deleteFlag)){
			session.invalidate();
		}
		
		
		
		return "member/delete/delete";
	}
}
