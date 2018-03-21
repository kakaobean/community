package com.ktds.member.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.member.vo.MemberVO;

public class MemberDaoImplForOracle extends SqlSessionDaoSupport implements MemberDao{
	// 인터페이스  , 현재 매소드명 , 파라미터
	@Override
	public int insertMember(MemberVO memberVO) {
		return getSqlSession().insert("MemberDao.insertMember", memberVO); 
	}

	@Override
	public MemberVO selectMember(MemberVO memberVO) {
		
		return getSqlSession().selectOne("MemberDao.selectMember", memberVO); 
	}

	@Override
	public int removeMember(int id) {
		return getSqlSession().delete("MemberDao.removeMember", id);
	}
	
}
