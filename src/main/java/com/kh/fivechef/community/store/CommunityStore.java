package com.kh.fivechef.community.store;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.kh.fivechef.community.domain.CReply;
import com.kh.fivechef.community.domain.Community;

public interface CommunityStore {

	int insertCommunity(SqlSessionTemplate session, Community community);

	int selectTotalFCount(SqlSessionTemplate session, String searchCondition, String searchValue);

	List<Community> selectAllCommunity(SqlSessionTemplate session, int currentPage, int communityLimit);

	Community selectOneByNo(SqlSessionTemplate session, Integer communityNo);

	int updateCommunityCount(SqlSessionTemplate session, int communityNo);

	int deleteCommunity(SqlSessionTemplate session, int communityNo);

	int updateCommunity(SqlSessionTemplate session, Community community);

	List<Community> selectAllByValue(SqlSessionTemplate session, String searchCondition, String searchValue,
			int currentPage, int communityLimit);

	int insertReply(SqlSessionTemplate session, CReply cReply);

	int updateReply(SqlSessionTemplate session, CReply cReply);

	int deleteReply(SqlSessionTemplate session, Integer replyNo);

	List<CReply> selectAllReply(SqlSessionTemplate session, Integer refCommunityNo);

	List<Community> selectAllSaleBoard(SqlSessionTemplate session, int currentPage, int communityLimit);

	int selectTotalSCount(SqlSessionTemplate session, String searchCondition, String searchValue);

	List<Community> selectSaleBoardByValue(SqlSessionTemplate session, String searchCondition, String searchValue,
			int currentPage, int communityLimit);

	List<Community> selectAllMyCommunity(SqlSessionTemplate session, String communityWriter, int currentPage, int communityLimit);

	int selectTotalCount(SqlSessionTemplate session, String searchCondition, String searchValue);

}
