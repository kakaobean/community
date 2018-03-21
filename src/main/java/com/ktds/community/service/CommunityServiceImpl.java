package com.ktds.community.service;

import java.util.ArrayList;
import java.util.List;

import com.ktds.community.dao.CommunityDao;
import com.ktds.community.vo.CommunityVO;

public class CommunityServiceImpl implements CommunityService {
	private CommunityDao communityDao;
	
	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}
	
	@Override
	public List<CommunityVO> getAll() {
		return communityDao.selectAll();
	}
	
	@Override
	public boolean createCommunity(CommunityVO communityVO) {
		String body = communityVO.getBody();
		// \n --> <br>
		body = body.replace("\n", "<br/>");
		body = filter(body);
		communityVO.setBody(body);

		int insertCount = communityDao.insertCommunity(communityVO);

		return insertCount > 0;
	}

	@Override
	public CommunityVO getOne(int id) {

		// 업데이트를 잘 수행했다면
		// if( communityDao.incrementViewCount(id) >0) {
		return communityDao.selectOne(id);
		// }

		// return null;
	}

	@Override
	public int readMyCommunitiesCount(int userId) {
		return communityDao.selectMyCommunitiesCount(userId);
	}
	
	@Override
	public void increaseR(int id) {
		communityDao.incrementRCount(id);
	}

	@Override
	public boolean increaseV(int id) {
		return communityDao.incrementViewCount(id) > 0;
	}
	

	
	@Override
	public boolean updateCommunity(CommunityVO communityVO) {
		return communityDao.updateCommunity(communityVO) > 0;
	}
	
	
	
	public String filter(String str) {
		List<String> blackList = new ArrayList<String>();
		blackList.add("욕");
		blackList.add("씨");
		blackList.add("발");
		blackList.add("1식");
		blackList.add("종간나세끼");

		for (String blackString : blackList) {
			str = str.replace(blackString, "뿅뿅");

		}

		return str;
	}

	@Override
	public boolean removePage(int id) {
		return communityDao.deleteCommunity(id) > 0;
		
	}

	@Override
	public List<CommunityVO> readMyCommunities(int userId) {
		return communityDao.selectMyCommunities(userId);
	}
	
	@Override
	public boolean deleteCommunities(List<Integer> ids, int userId) {
		return communityDao.deleteCommunities(ids, userId) >0;
	}

}
