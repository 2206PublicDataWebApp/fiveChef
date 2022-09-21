package com.kh.fivechef.community.store.logic;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.fivechef.community.domain.Community;
import com.kh.fivechef.community.store.CommunityStore;


@Repository
public class CommunityStoreLogic implements CommunityStore{

	@Override
	public int insertCommunity(SqlSessionTemplate session, Community community) {
		int result = session.insert("CommunityMapper.insertCommunity", community);
		return result;
	}

	@Override
	public int selectTotalCount(SqlSessionTemplate session, String searchCondition, String searchValue) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("searchCondition", searchCondition);
		paramMap.put("searchValud", searchValue);
		int totalCount = session.selectOne("CommunityMapper.selectTotalCount", paramMap);
		return totalCount;
	}

	@Override
	public List<Community> selectAllCommunity(SqlSessionTemplate session, int currentPage, int communityLimit) {
		int offset = (currentPage - 1) * communityLimit;
		RowBounds rowBounds = new RowBounds(offset, communityLimit);
		List<Community> cList = session.selectList("CommunityMapper.selectAllCommunity", null, rowBounds);
		return cList;
	}

	@Override
	public Community selectOneByNo(SqlSessionTemplate session, Integer communityNo) {
		Community community = session.selectOne("CommunityMapper.selectOne", communityNo);
		return community;
	}

	@Override
	public int updateCommunityCount(SqlSessionTemplate session, int communityNo) {
		int result = session.update("CommunityMapper.updateCount", communityNo);
		return result;
	}

	@Override
	public int deleteCommunity(SqlSessionTemplate session, int communityNo) {
		int result = session.delete("CommunityMapper.deleteCommunity", communityNo);
		return result;
	}

	@Override
	public int updateCommunity(SqlSessionTemplate session, Community community) {
		int result = session.update("CommunityMapper.updateCommunity", community);
		return result;
	}

	@Override
	public List<Community> selectAllByValue(SqlSessionTemplate session, String searchCondition, String searchValue,
			int currentPage, int communityLimit) {
		int offset = (currentPage - 1) * communityLimit;
		RowBounds rowBounds = new RowBounds(offset, communityLimit);
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("searchCondition", searchCondition);
		paramMap.put("searchValue", searchValue);
		List<Community> cList = session.selectList("CommunityMapper.selectAllByValue", paramMap, rowBounds);
		return cList;
	}
	
}
