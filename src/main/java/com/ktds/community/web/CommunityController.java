package com.ktds.community.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.service.CommunityService;
import com.ktds.community.vo.CommunityVO;
import com.ktds.member.constants.Member;
import com.ktds.member.util.DownloadUtil;
import com.ktds.member.vo.MemberVO;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

@Controller
public class CommunityController {

	private CommunityService communityService;

	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	@RequestMapping("/")
	public ModelAndView viewListPage() {
		
		ModelAndView view = new ModelAndView();

		// /WEB-INF/view/community/list.jsp
		view.setViewName("community/list");
		
		List<CommunityVO> communityList = communityService.getAll();
		view.addObject("communityList", communityList);
		
		return view;
	}
 
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	// @GetMapping("/write")
	public String viewWritePage() {
		
		
		return "community/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	// @PostMapping("/write")
	ModelAndView doWrite(@ModelAttribute("writeForm") 
	@Valid CommunityVO communityVO, Errors errors, HttpServletRequest request) {
		
	
		
		/*		
		/////////////////////////////////////////////////////////////////////////////////////
		if(communityVO.getTitle() == null || communityVO.getTitle().length() == 0) {
			session.setAttribute("status", "emptyTitle");
			return new ModelAndView("redirect:/write");
		}
		if(communityVO.getBody() ==null || communityVO.getBody().length() ==0) {
			session.setAttribute("status", "emptyBody");
			return "redirect:/write";
		}
		
		if(communityVO.getWriteDate() == null || communityVO.getWriteDate().length()==0) {
			session.setAttribute("status", "emptyDate");
			return "redirect:/write";
		}
		
		//////////////////////////////////////////////////////////////////////////////////////
		
		*/
		if(errors.hasErrors()) {
			ModelAndView view = new ModelAndView();
			view.setViewName("community/write");
			view.addObject("communityVO", communityVO);
			return view;
		}
		// ip보여주기
		String requestorIp = request.getRemoteAddr();
		communityVO.setRequestIp(requestorIp);
		
		communityVO.save();
		
		boolean isSuccess = communityService.createCommunity(communityVO);

		// 만약에 글쓰기 등록이 완료 되었다면~ 리스트로 정보 보내고 다시 가겠다.
		
		if (isSuccess) {
			return new ModelAndView("redirect:/");
		}

		return new ModelAndView("redirect:/write");
	}

	
	@RequestMapping("/view/{id}")
	public ModelAndView viewViewPage(@PathVariable int id) {
		
		
		
		ModelAndView view = new ModelAndView();
		view.setViewName("community/view");
		
	
		CommunityVO community = communityService.getOne(id);
		
		view.addObject("community", community);
		
		return view;
	}
	
	@RequestMapping("/read/{id}")
	public String viewCount(@PathVariable int id) {
		if(communityService.increaseV(id)) { 
			return "redirect:/view/" + id;
		}
		return "redirect:/"; 
	}
	
	@RequestMapping("/recommend/{id}")
	public String rCount(@PathVariable int id) {
		communityService.increaseR(id);
		return "redirect:/view/" + id; 
	}
	
	
	@RequestMapping("/delete/{id}")
	public String deletePage(@PathVariable int id, HttpSession session) {
	
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);   // 명시적
		
		CommunityVO community = communityService.getOne(id);
		
		boolean isMyCommunity = member.getId() == community.getUserId();
		
		if ( isMyCommunity && communityService.removePage(id)) {    // 가장 기본적인 보안 방식
			
			return "redirect:/";
		}
		return "/WEB-INF/view/error/404";
	}
	
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public ModelAndView viewModifyPage(@PathVariable int id, HttpSession session) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER); //캐스팅
		CommunityVO community = communityService.getOne(id);
		
		int userId = member.getId();
		
		if( userId != community.getUserId()) {
			return new ModelAndView("error/404");
		}
				
		ModelAndView view = new ModelAndView();
		view.setViewName("community/write");
		view.addObject("communityVO", community);
		view.addObject("mode", "modify");
		
		return view;
	}
	
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	public String doModifyAction(@PathVariable int id, 
			HttpSession session, HttpServletRequest request, 
			@ModelAttribute("writeForm") 
			@Valid CommunityVO communityVO, 
			Errors errors) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO originalVO = communityService.getOne(id);
		
		if( member.getId() != originalVO.getUserId()) {
			return "error/404";
		}
		if (errors.hasErrors()) {
			return "redirect:/modify/" + id;
		}
		
		CommunityVO newCommunity = new CommunityVO();
		newCommunity.setId(originalVO.getId());
		newCommunity.setUserId(member.getId());
		
		boolean isModify = false;
		
		//1. IP 변경확인
		String ip = request.getRemoteAddr();
		
		if ( !ip.equals(originalVO.getRequestIp())) {
			newCommunity.setRequestIp(ip);
			isModify = true;
		}
		
		// 2. 제목 변경확인
		if(!originalVO.getTitle().equals(communityVO.getTitle())) {
			newCommunity.setTitle(communityVO.getTitle());
			isModify = true;
		}
		 
		// 3. 내용 변경확인
		if(!originalVO.getBody().equals(communityVO.getBody())) {
			newCommunity.setBody(communityVO.getBody());
			isModify = true;
		}
		
		// 4. 파일 변경확인
		// 랭스==  데이터가 있다는 것
		if(communityVO.getDisplayFilename().length() > 0) {
			
			File file = new File("d:/uploadFiles/" + communityVO.getDisplayFilename());
			file.delete();
			communityVO.setDisplayFilename("");
		}
		else {
			communityVO.setDisplayFilename(originalVO.getDisplayFilename());
			
		}
		communityVO.save();
		if(!originalVO.getDisplayFilename().equals(communityVO.getDisplayFilename())) {
			newCommunity.setDisplayFilename(communityVO.getDisplayFilename());
			isModify = true;
		}
		// 5. 변경이 없는지 확인
		if(isModify) {
			// 6. UPDATE 하는 Service code 호출
			communityService.updateCommunity(newCommunity);
			
		}
		
		
		return "redirect:/view/" +id;
	}
	
	
	@RequestMapping("/get/{id}")
	public void download(@PathVariable int id,
							HttpServletRequest request, HttpServletResponse response) {
		CommunityVO community = communityService.getOne(id);
		String filename = community.getDisplayFilename();
		
		DownloadUtil download = new DownloadUtil("d:/uploadfiles/" + filename);
		
		try {
			download.download(request, response, filename);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);   //이렇게 하면 실무가면 감탄
		}
		
		
	}
	
	
}
