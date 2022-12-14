package com.kh.fivechef.community.service;

import java.util.List;

import com.kh.fivechef.community.domain.CReply;
import com.kh.fivechef.community.domain.Community;

public interface CommunityService {

	int registCommunity(Community community);

	int getTotalFCount(String searchCondition, String searchValue);

	List<Community> printAllCommunity(int currentPage, int communityLimit);

	Community printOneByNo(Integer communityNo);

	int removeCommunity(int communityNo);

	int modifyCommunity(Community community);

	List<Community> printAllByValue(String searchCondition, String searchValue, int currentPage, int communityLimit);

	int registReply(CReply cReply);

	int modifyReply(CReply cReply);

	int removeReply(Integer replyNo);

	List<CReply> printAllReply(Integer refCommunityNo);

	List<Community> printAllSaleBoard(int currentPage, int communityLimit);

	int getTotalSCount(String searchCondition, String searchValue);

	List<Community> printSaleBoardByValue(String searchCondition, String searchValue, int currentPage,
			int communityLimit);

	List<Community> printAllMyCommunity(String communityWriger, int currentPage, int communityLimit);

	int getTotalCount(String searchCondition, String searchValue);

}
