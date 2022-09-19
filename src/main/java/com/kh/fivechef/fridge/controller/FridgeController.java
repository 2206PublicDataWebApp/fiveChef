package com.kh.fivechef.fridge.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.fivechef.fridge.domain.Fridge;
import com.kh.fivechef.fridge.service.FridgeService;


@Controller
public class FridgeController {
	@Autowired
	private FridgeService fService;
	
//	@RequestMapping(value="/fridge/myFridge.kh", method=RequestMethod.GET)
//	public String showMyFridge() {
//		return "fridge/myFridge";
//	}
	
	// 냉장고 등록
	@PostMapping("/fridge/register.kh")
	public ModelAndView registFridge(ModelAndView mv
			, @ModelAttribute Fridge fridge
			, @RequestParam(value="uploadProfile", required=false) MultipartFile uploadFile
			, HttpServletRequest request) {
		try {
			String fridgeFilename = uploadFile.getOriginalFilename();
			if(!fridgeFilename.equals("")) {
				//////////////////////////////////////////////////////////////////////경로, 파일이름 설정
				String root = request.getSession().getServletContext().getRealPath("resources");
				String savePath = root + "\\fuploadFiles";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String fridgeFileRename = sdf.format(new Date(System.currentTimeMillis()))+ "." // 시간
						+ fridgeFilename.substring(fridgeFilename.lastIndexOf(".")+1); // 확장자명
				// 1.png, img.png
				File file = new File(savePath);
				if(!file.exists()) {
					file.mkdir();
				}
				//////////////////////////////////////////////////////////////////////
				uploadFile.transferTo(new File(savePath+"\\"+fridgeFileRename)); // 저장할때는 Rename으로 저장
				// 파일을 buploadFiles 경로에 저장하는 메소드
				String fridgeFilepath = savePath + "\\" + fridgeFileRename;
				fridge.setFridgeFilename(fridgeFilename);
				fridge.setFridgeFileRename(fridgeFileRename);
				fridge.setFridgeFilepath(fridgeFilepath);
			}
			int result = fService.registerFridge(fridge);
			mv.setViewName("redirect:/");
		} catch (Exception e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	// 냉장고 조회
	@GetMapping("/fridge/myFridge.kh")
	public ModelAndView fridgeListView(ModelAndView mv) {
		List<Fridge> fList = fService.printAllFridge();
		System.out.println(fList);
		if(!fList.isEmpty()) {
			mv.addObject("fList", fList);
		}
		mv.setViewName("redirect:/");
		return mv;
	}
	// 냉장고 수정
	// 냉장고 삭제
}