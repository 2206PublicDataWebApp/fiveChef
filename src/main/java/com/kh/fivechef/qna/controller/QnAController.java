package com.kh.fivechef.qna.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.fivechef.qna.domain.QnA;
import com.kh.fivechef.qna.service.QnAService;
import com.kh.fivechef.user.domain.User;

@Controller
public class QnAController {

	@Autowired
	private QnAService qService;
	
	@RequestMapping(value="/qna/qnaWriteView.kh", method=RequestMethod.GET) //QnA 문의글 페이지 이동
	public String showQnAWrite() {
		return "qna/qnaWriteForm";
	}
	
	@RequestMapping(value="/qna/qnaRegist.kh", method=RequestMethod.POST) //QnA 문의글 등록
	public ModelAndView RegistQnA(
				ModelAndView mv
//				,@RequestParam("questionTitle") String questionTitle
//				,@RequestParam("questionWriter") String questionWriter
//				,@RequestParam("questionContents") String questionContents
				,@ModelAttribute QnA qna
				,@RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
				,HttpServletRequest request) {
		try {
			String qFileName = uploadFile.getOriginalFilename();
			if(!uploadFile.getOriginalFilename().equals("")) {
				String root = request.getSession().getServletContext().getRealPath("resources");
				String savePath = root + "\\quploadFiles";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss");
				String qFileReName = sdf.format(new Date(System.currentTimeMillis())) + "." + qFileName.substring(qFileName.lastIndexOf(".")+1);
				File file = new File(savePath);
				if(!file.exists()) {
					file.mkdir();
				}
				uploadFile.transferTo(new File(savePath + "\\" + qFileReName));
				String qFilePath = savePath + "\\" + qFileReName;
				qna.setqFileName(qFileName);
				qna.setqFileReName(qFileReName);
				qna.setqFilePath(qFilePath);
			}
			int result = qService.registQnA(qna);
			request.setAttribute("msg", "등록이 완료되었습니다.");
			request.setAttribute("url", "/qna/list.kh");
			mv.setViewName("common/alert");
		} catch(Exception e) {
			mv.addObject("msg", e.toString()).setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/qna/list.kh" , method=RequestMethod.GET) //사용자가 작성한 문의글 보기
	public ModelAndView myQnAListView (ModelAndView mv, @RequestParam(value="page", required=false) Integer page, HttpSession session, QnA qna) {
		User user =(User)session.getAttribute("loginUser");
		String questionWriter = user.getUserId();
		//session.setAttribute("questionWriter", qna.getQuestionWriter());
		int currentPage = (page != null) ? page : 1;
		int totalCount = qService.getTotalCount("", "");
		int qnaLimit = 10;
		int naviLimit = 5;
		int maxPage;
		int startNavi;
		int endNavi;
		maxPage = (int)((double)totalCount/qnaLimit + 0.9);
		startNavi = ((int)((double)currentPage/naviLimit + 0.9)-1) * naviLimit + 1;
		endNavi = startNavi + naviLimit - 1;
		if(maxPage < endNavi) {
			endNavi = maxPage;
		}
		List<QnA> qList = qService.printMyQnA(questionWriter, currentPage, qnaLimit);
		if(!qList.isEmpty()) {
			mv.addObject("questionWriter", questionWriter);
			mv.addObject("urlVal", "list");
			mv.addObject("currentPage", currentPage);
			mv.addObject("maxPage", maxPage);  // listView.jsp에서 사용하기위해 작성 ([이전], [다음]을 위해 작성)
			mv.addObject("startNavi", startNavi);
			mv.addObject("endNavi", endNavi);
			mv.addObject("qList", qList);
		}
		mv.setViewName("qna/list");
		return mv;
	}
	
	@RequestMapping(value="/qna/detail.kh", method=RequestMethod.GET) 
	public ModelAndView qnaDetailView(ModelAndView mv, @RequestParam("questionNo") Integer questionNo, @RequestParam("page")Integer page, HttpSession session) {
		try {
			QnA qna = qService.printOneByNo(questionNo);
			session.setAttribute("questionNo", qna.getQuestionNo());
			mv.addObject("qna", qna);
			mv.addObject("page", page);
			mv.setViewName("qna/detail");		
		} catch (Exception e) {
			mv.addObject("msg", e.toString());
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
}
