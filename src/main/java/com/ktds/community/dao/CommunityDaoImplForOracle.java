package com.ktds.community.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.community.vo.CommunityVO;

public class CommunityDaoImplForOracle extends SqlSessionDaoSupport implements CommunityDao {

	/*
		SqlSessionDaoSupport
		sqlSessionTemplate Bean 객체를 가지고 있음.
	 */

	@Override
	public List<CommunityVO> selectAll() {
		return getSqlSession().selectList("CommunityDao.selectAll");  // 커뮤니티.셀렉올 은 무조건 복붙한다 타이핑x
	}

	@Override
	public int insertCommunity(CommunityVO communityVO) {
			
		return getSqlSession().insert("CommunityDao.insertCommunity", communityVO);
	}

	@Override
	public CommunityVO selectOne(int id) {
		return getSqlSession().selectOne("CommunityDao.selectOne", id);
	}

	
	
	
	@Override
	public int incrementViewCount(int id) {
		
		return getSqlSession().update("CommunityDao.incrementViewCount", id);
	}

	@Override
	public int incrementRCount(int id) {
		return getSqlSession().update("CommunityDao.incrementRCount", id);
	}



	@Override
	public int deleteMyCommunities(int userId) {
		return getSqlSession().delete("CommunityDao.deleteMyCommunities", userId);
	}

	@Override
	public int updateCommunity(CommunityVO communityVO) {
		return getSqlSession().update("CommunityDao.updateCommunity", communityVO);
	}

	@Override
	public int selectMyCommunitiesCount(int userId) {
		
		return getSqlSession().selectOne("CommunityDao.selectMyCommunitiesCount", userId);
	}

	@Override
	public List<CommunityVO> selectMyCommunities(int userId) {
		
		return getSqlSession().selectList("CommunityDao.selectMyCommunities", userId);
	}

	@Override
	public int deleteCommunity(int id) {
		
		return getSqlSession().delete("CommunityDao.deleteCommunity", id);
	}
	  @Override
	   public int deleteCommunities(List<Integer> ids, int userId) {
	      Map<String, Object> params = new HashMap<String, Object>();
	      params.put("ids", ids);
	      params.put("userId", userId);
	      return getSqlSession().delete("CommunityDao.deleteCommunities", params);
	   }
	

}
