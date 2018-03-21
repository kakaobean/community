package com.ktds.member.dao;

import com.ktds.member.vo.MemberVO;

public interface MemberDao {
	
	public int insertMember(MemberVO memberVO);
	
	public MemberVO selectMember(MemberVO memberVO); // CRUD R빼고 다 int
	
	public int removeMember(int id);
	
	
}
