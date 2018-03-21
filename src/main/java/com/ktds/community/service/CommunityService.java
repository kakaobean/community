package com.ktds.community.service;

import java.util.List;

import com.ktds.community.vo.CommunityVO;

public interface CommunityService {
	public List<CommunityVO> getAll();
	
	public CommunityVO getOne(int id);

	public int readMyCommunitiesCount(int userId);
	public List<CommunityVO>readMyCommunities(int userId);
	
	public boolean createCommunity(CommunityVO communityVO);
	
	public void increaseR(int id);
	
	public boolean increaseV(int id);
	
	public boolean removePage(int id);
	
	public boolean deleteCommunities(List<Integer>ids, int userId);
	
	public boolean updateCommunity(CommunityVO communityVO);
}
