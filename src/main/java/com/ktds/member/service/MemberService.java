package com.ktds.member.service;

import com.ktds.member.vo.MemberVO;

public interface MemberService {
	
	public boolean createMember(MemberVO memberVO);
	public MemberVO readMember(MemberVO member);
	public boolean removeMember(int id, String deleteFlag);
	
}
