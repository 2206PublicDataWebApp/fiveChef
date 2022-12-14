package com.kh.fivechef.recipe.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.fivechef.fridge.domain.LargeCategory;
import com.kh.fivechef.fridge.domain.SmallCategory;
import com.kh.fivechef.fridge.domain.Storage;
import com.kh.fivechef.fridge.service.StorageService;
import com.kh.fivechef.recipe.domain.ComPhoto;
import com.kh.fivechef.recipe.domain.Ingradient;
import com.kh.fivechef.recipe.domain.Like;
import com.kh.fivechef.recipe.domain.Order;
import com.kh.fivechef.recipe.domain.Recipe;
import com.kh.fivechef.recipe.domain.WhatRecipe;
import com.kh.fivechef.recipe.service.RecipeService;
import com.kh.fivechef.user.domain.User;

//hms2dd
@Controller
public class RecipeController {
	@Autowired
	private RecipeService rService;
	@Autowired
	private StorageService sService;

	@RequestMapping(value = "/recipe/writeView.kh", method = RequestMethod.GET)
	public ModelAndView showRecipeWrite(ModelAndView mv) {
		List<LargeCategory> lList = sService.printLargeCat();
		List<SmallCategory> sList = rService.printSmallCat();
		List<WhatRecipe> wList = rService.printWhat();

		System.out.println(sList);
		mv.addObject("wList", wList);
		mv.addObject("lList", lList);
		mv.addObject("sList", sList);
		mv.setViewName("recipe/recipeWriteFormcopy");
		return mv;
	}
//
	// ????????? ??????
	@RequestMapping(value = "/recipe/recipeRegister.kh", method = RequestMethod.POST)
	public ModelAndView recipeRegist(ModelAndView mv, @ModelAttribute Recipe recipe, @ModelAttribute Ingradient ing,
			@ModelAttribute Order order, @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			@RequestParam(value = "ouploadFile", required = false) List<MultipartFile> ouploadFile,
			@RequestParam(value = "cuploadFile", required = false) List<MultipartFile> cuploadFile,
			HttpServletRequest request) {
		System.out.println(recipe.getUserId());

		// ???????????? ?????? ?????????
		// ??????????????? ????????? ????????? ???????????? ???????????????
		try {
			String thumbnailName = uploadFile.getOriginalFilename();
			/////////////////////////// ??????,???????????? ??????
			if (!thumbnailName.equals("")) {
				String root = request.getSession().getServletContext().getRealPath("resources");
				String savePath = root + "\\ruploadFiles";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhss");
				String thumbnailRename = sdf.format(new Date(System.currentTimeMillis())) + "." // ??????
						+ thumbnailName.substring(thumbnailName.lastIndexOf(".") + 1); // ????????????
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				uploadFile.transferTo(new File(savePath + "\\" + thumbnailRename));// ??????????????? rename?????? ?????? ??????????????? ??????
				String thumbnailpath = savePath + "\\" + thumbnailRename;
				// ????????? ruploadFile????????? ???????????? ?????????
				recipe.setThumbnailName(thumbnailName);
				recipe.setThumbnailRename(thumbnailRename);
				recipe.setThumbnailpath(thumbnailpath);
			}

			// ????????? ??????
			int result = rService.registerRecipe(recipe);

			// ???????????? INGRADIENT_TBL
			// list??? stream?????? ???????????? ?????? ?????? ?????? ??? ??????
			List<Ingradient> iList = new ArrayList<Ingradient>();
			for (int i = 0; i < ing.getIngAmount().split(",").length; i++) {
				Ingradient ingredient = new Ingradient();
				ingredient.setIngBundleName(ing.getIngBundleName());
				ingredient.setIngAmount(ing.getIngAmount().split(",")[i]);
				ingredient.setLargeCatId(ing.getLargeCatId().split(",")[i]);
				ingredient.setSmallCatId(ing.getSmallCatId().split(",")[i]);
				iList.add(ingredient);
			}
			System.out.println(iList);
			for (int i = 0; i < iList.size(); i++) {
				int result2 = rService.registerIngradient(iList.get(i));
			}

			// order?????? ORDER_TBL
			List<Order> oList = new ArrayList<Order>();
			for (int i = 0; i < order.getRecipeContents().split(",").length; i++) {
				Order ord = new Order();
				ord.setRecipeContents(order.getRecipeContents().split(",")[i]);
				String orderPhotoName = ouploadFile.get(i).getOriginalFilename();
				if (!orderPhotoName.equals("")) {
					String root = request.getSession().getServletContext().getRealPath("resources");
					String savePath = root + "\\ruploadFiles";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhss");
					String orderPhotoRename = sdf.format(new Date(System.currentTimeMillis())) + "o" + i + "." // ??????
							+ orderPhotoName.substring(orderPhotoName.lastIndexOf(".") + 1); // ????????????
					File file = new File(savePath);
					if (!file.exists()) {
						file.mkdir();
					}
					ouploadFile.get(i).transferTo(new File(savePath + "\\" + orderPhotoRename));// ??????????????? rename?????? ??????
																								// ??????????????? ??????
					String orderPhotopath = savePath + "\\" + orderPhotoRename;
					// ????????? ruploadFile????????? ???????????? ?????????
					ord.setOrderPhotoName(orderPhotoName);
					ord.setOrderPhotoRename(orderPhotoRename);
					ord.setOrderPhotopath(orderPhotopath);
				}
				oList.add(ord);
			}
			for (int i = 0; i < oList.size(); i++) {
				int result3 = rService.registerOrder(oList.get(i));
			}

			// ?????????????????? COM_PHOTO_TBL
			List<ComPhoto> cList = new ArrayList<ComPhoto>();
//			System.out.println(cuploadFile.size());
			for (int i = 0; i < cuploadFile.size(); i++) {
				ComPhoto cPhoto = new ComPhoto();
				String comPhotoName = cuploadFile.get(i).getOriginalFilename();
//				System.out.println(comPhotoName == "");
				if (comPhotoName != "" && !comPhotoName.equals("")) {
					String root = request.getSession().getServletContext().getRealPath("resources");
					String savePath = root + "\\ruploadFiles";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhss");
					String comPhotoRename = sdf.format(new Date(System.currentTimeMillis())) + "c" + i + "." // ??????
							+ comPhotoName.substring(comPhotoName.lastIndexOf(".") + 1); // ????????????
					File file = new File(savePath);
					if (!file.exists()) {
						file.mkdir();
					}
					cuploadFile.get(i).transferTo(new File(savePath + "\\" + comPhotoRename));// ??????????????? rename?????? ?????? ???????????????
																								// ??????
					String comPhotopath = savePath + "\\" + comPhotoRename;
					// ????????? ruploadFile????????? ???????????? ?????????
					cPhoto.setComPhotoName(comPhotoName);
					cPhoto.setComPhotoRename(comPhotoRename);
					cPhoto.setComPhotopath(comPhotopath);
				}
				cList.add(cPhoto);
			}
			for (int i = 0; i < cList.size(); i++) {
				int result4 = rService.registerComPhoto(cList.get(i));
			}

			request.setAttribute("msg", "????????? ????????? ?????????????????????.");
			// ????????? ?????????????????? ??????????????? ??????
			request.setAttribute("url", "/recipe/recipeList.kh");
			mv.setViewName("common/alert");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mv.addObject("msg", "??????????????? ??????").setViewName("common/errorPage");
		}

		return mv;
	}

	// ????????? ?????????
	@RequestMapping(value = "/recipe/recipeList.kh", method = RequestMethod.GET)
	public ModelAndView recipeAllListView(ModelAndView mv,
			@RequestParam(value = "category", required = false) String listValue,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "whatRecipe", required = false) String whatRecipe, HttpSession session) {

		try {
			if (session.getAttribute("postingid") != null && (listValue == "" || listValue == null) && page == null) {
				session.removeAttribute("postingid");
				System.out.println("????????????");
			}
			String[] searching = null;
			if (session.getAttribute("postingid") != null) {
				searching = ((String) session.getAttribute("postingid")).split(",");
				;
				System.out.println(searching);
			}
			int currentPage = (page != null) ? page : 1;
			int totalCount = rService.countAllRecipe(searching, whatRecipe, listValue);
			int recipeLimit = 18;
			int naviLimit = 5;
			int maxPage;
			int startNavi;
			int endNavi;
			maxPage = (int) ((double) totalCount / recipeLimit + 0.9);
			startNavi = ((int) ((double) currentPage / naviLimit + 0.9) - 1) * naviLimit + 1;
			endNavi = startNavi + naviLimit - 1;
			if (maxPage < endNavi) {
				endNavi = maxPage;
			}
			// store?????? hashmap???????????? ????????? ?????? ?????????
//			System.out.println(totalCount);
			// ?????????????????????????????????????????????
			List<WhatRecipe> wList = rService.printWhat();
			// ?????? ???????????? ??????
			List<LargeCategory> lList = sService.printLargeCat();
			List<SmallCategory> sList = rService.printSmallCat();
			List<Recipe> rList = rService.printAllRecipe(searching, whatRecipe, listValue, currentPage, recipeLimit);
			// whatNo ??????
//			System.out.println(whatRecipe);
			if (!rList.isEmpty()) {
				mv.addObject("rList", rList);
			}

			mv.addObject("searching", searching);
			mv.addObject("whatRecipe", whatRecipe);
			mv.addObject("lList", lList);
			mv.addObject("sList", sList);
			mv.addObject("wList", wList);
			mv.addObject("startNavi", startNavi);
			mv.addObject("endNavi", endNavi);
			mv.addObject("maxPage", maxPage);
			mv.addObject("currentPage", currentPage);
			mv.addObject("urlVal", "recipeList");
			mv.addObject("listValue", listValue);
			mv.addObject("totalCount", totalCount);
			mv.setViewName("recipe/listView");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "?????? ?????? ??????").setViewName("common/errorPage");
		}

		return mv;
	}

	@RequestMapping(value = "/recipe/searchIng.kh", method = RequestMethod.POST)
	public ModelAndView searchIng(ModelAndView mv,
			@RequestParam(value = "postingid", required = false) String postingid,
			@RequestParam(value = "category", required = false) String listValue,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "whatRecipe", required = false) String whatRecipe, HttpServletRequest request,
			HttpSession session) {
		try {

			// ????????? ????????? ?????? ??????

			// ???????????? null ??????
//				System.out.println(postingid);
//				System.out.println(1);
			if (postingid == "") {
				request.setAttribute("msg", "???????????? ????????????.");
				request.setAttribute("url", "/recipe/recipeList.kh");
				mv.setViewName("common/alert");
				return mv;
			}

//				System.out.println(2);
			String[] searching = postingid.split(",");
//				System.out.println(3);
			session.setAttribute("postingid", postingid);
//				if(session.getAttribute("postingid") == null) {
//					System.out.println(4);
//				}
			int currentPage = (page != null) ? page : 1;
			int totalCount = rService.countAllRecipe(searching, whatRecipe, listValue);
			int recipeLimit = 18;
			int naviLimit = 5;
			int maxPage;
			int startNavi;
			int endNavi;
			maxPage = (int) ((double) totalCount / recipeLimit + 0.9);
			startNavi = ((int) ((double) currentPage / naviLimit + 0.9) - 1) * naviLimit + 1;
			endNavi = startNavi + naviLimit - 1;
			if (maxPage < endNavi) {
				endNavi = maxPage;
			}
			// ?????????????????????????????????????????????
			List<WhatRecipe> wList = rService.printWhat();
			// ?????? ???????????? ??????
			List<LargeCategory> lList = sService.printLargeCat();
			List<SmallCategory> sList = rService.printSmallCat();
			List<Recipe> rList = rService.printAllRecipe(searching, whatRecipe, listValue, currentPage, recipeLimit);

			if (!rList.isEmpty()) {
				mv.addObject("rList", rList);
			}
			mv.addObject("searching", searching);
			mv.addObject("whatRecipe", whatRecipe);
			mv.addObject("lList", lList);
			mv.addObject("sList", sList);
			mv.addObject("wList", wList);
			mv.addObject("startNavi", startNavi);
			mv.addObject("endNavi", endNavi);
			mv.addObject("maxPage", maxPage);
			mv.addObject("currentPage", currentPage);
			mv.addObject("urlVal", "recipeList");
			mv.addObject("listValue", listValue);
			mv.addObject("totalCount", totalCount);
			mv.setViewName("recipe/listView");

		} catch (Exception e) {
			// TODO: handle exception
		}

		return mv;
	}

	// ????????? ??????
	@RequestMapping(value = "/recipe/recipeDetailView.kh", method = RequestMethod.GET)
	public ModelAndView recipeDetailView(ModelAndView mv,
			@RequestParam(value = "category", required = false) String listValue,
			@RequestParam(value = "page", required = false) Integer page, @RequestParam("recipeNo") Integer recipeNo,
			HttpSession session) {
		try {
			User user = (User) session.getAttribute("loginUser");
			System.out.println("????????? ???????????? ?????? ?????????: " + recipeNo);
			if (user != null) {
				String likeUser = user.getUserId();
//					System.out.println(likeUser);
				Like like = new Like();
				like.setUserId(likeUser);
				like.setRecipeNo(recipeNo);
//				System.out.println(recipeid.getUserId());
				// ????????? ????????? ??????
				int result = rService.checkLikeId(like);
				mv.addObject("result", result);
				mv.addObject("like", like);
			}
			// ??????????????? ??????
			Recipe recipe = rService.printOneByNo(recipeNo);
			// ?????????????????? ???????????? ??????
			session.setAttribute("recipeNo", recipe.getRecipeNo());
			// ????????????
			List<Ingradient> iList = rService.printAllIng(recipeNo);
//				System.out.println(iList);
			String bundle = "????????????";
			// ????????? ???????????? ????????? ?????? ???????????? ???????????????
			if (!iList.isEmpty()) {
				bundle = iList.get(0).getIngBundleName();
			}
			// ???????????? ??????
			List<Order> oList = rService.printAllOrder(recipeNo);

			// ???????????? ??????
			List<ComPhoto> cList = rService.printAllComPhoto(recipeNo);
			// ?????????????????????????????????????????????
			List<WhatRecipe> wList = rService.printWhat();

			mv.addObject("wList", wList);
			mv.addObject("urlVal", "recipeList");
			mv.addObject("listValue", listValue);
			mv.addObject("page", page);
			mv.addObject("cList", cList);
			mv.addObject("oList", oList);
			mv.addObject("iList", iList);
			mv.addObject("bundle", bundle);
			mv.addObject("recipe", recipe);
			mv.setViewName("recipe/recipeDetailView");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "??????????????? ??????").setViewName("common/errorPage");
		}

		return mv;
	}

	//
	@RequestMapping(value = "/recipe/recipeModifyView.kh", method = RequestMethod.GET)
	public ModelAndView recipeModifyView(ModelAndView mv
//			,@RequestParam(value="recipeNo",required=false) Integer recipeNo
			, @RequestParam("page") Integer page, @RequestParam(value = "loginId", required = false) String loginId,
			HttpSession session, HttpServletRequest request) {

		try {
			// ??????????????? ?????? ??????
			User user = (User) session.getAttribute("loginUser");
			if (user == null) {
				System.out.println("????????? ??????????????? ??????");
				request.setAttribute("msg", "???????????? ????????? ????????????.");
				request.setAttribute("url", "/");
				mv.setViewName("common/alert");
				return mv;
			}
			int recipeNo = (Integer) session.getAttribute("recipeNo");
			System.out.println("??????????????? ?????? ?????????: " + recipeNo);
			Recipe recipe = rService.printRecipeByRNo(recipeNo);
//			System.out.println(rList.get);
			List<Ingradient> iList = rService.printIngByRNo(recipeNo);
			List<Order> oList = rService.printOrderByRNo(recipeNo);
			List<ComPhoto> cList = rService.printComByRNo(recipeNo);
			List<LargeCategory> lList = sService.printLargeCat();
			List<SmallCategory> sList = rService.printSmallCat();
			List<WhatRecipe> wList = rService.printWhat();
//			???????????????..?
//			String uc = user.getUserId();
			String rc = recipe.getUserId();
//			System.out.println(user.getUserId());
//			System.out.println(recipe.getUserId());
			System.out.println(loginId);
			System.out.println(loginId == rc);
//			if (user.getUserId() != recipe.getUserId()) {
//				System.out.println("????????? ????????? ???????????? ??????");
//				request.setAttribute("msg", "???????????? ????????? ??? ????????????..");
//				request.setAttribute("url", "/recipe/recipeDetailView.kh?recipeNo=" + recipeNo);
//				mv.setViewName("common/alert");
//				return mv;
//			}

			String bundle = "????????????";
			// ????????? ???????????? ????????? ?????? ???????????? ???????????????
			if (!iList.isEmpty()) {
				bundle = iList.get(0).getIngBundleName();
			}
//			System.out.println(iList);
			System.out.println(oList);
			mv.addObject("bundle", bundle);
			mv.addObject("wList", wList);
			mv.addObject("lList", lList);
			mv.addObject("sList", sList);
			mv.addObject("recipeNo", recipeNo);
			mv.addObject("recipe", recipe);
			mv.addObject("iList", iList);
			mv.addObject("oList", oList);
			mv.addObject("cList", cList);
			mv.setViewName("/recipe/recipeModify");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "???????????????????????? ?????? ??????").setViewName("common/errorPage");
		}
		return mv;
	}

	// ???????????????
	@RequestMapping(value = "/recipe/recipeModify.kh", method = RequestMethod.POST)
	public ModelAndView recipeModify(ModelAndView mv,
			@RequestParam(value = "recipeNo", required = false) Integer recipeNo,
			@RequestParam(value = "reuploadFile", required = false) MultipartFile reuploadFile,
			@RequestParam(value = "reouploadFile", required = false) List<MultipartFile> reouploadFile,
			@RequestParam(value = "recuploadFile", required = false) List<MultipartFile> recuploadFile,
			@RequestParam("page") Integer page, @ModelAttribute Recipe recipe, @ModelAttribute Order order,
			@ModelAttribute ComPhoto comPhoto, @ModelAttribute Ingradient ing, @ModelAttribute WhatRecipe what,
			HttpServletRequest request) {
		try {
			// ?????? ??????
			String thumbnailName = reuploadFile.getOriginalFilename();
			if (reuploadFile != null && !thumbnailName.equals("")) {
				String root = request.getSession().getServletContext().getRealPath("resources");
				String savedPath = root + "\\ruploadFiles";
				File file = new File(savedPath + "\\" + recipe.getThumbnailRename());
				if (file.exists()) {
					file.delete();
				}
				// ??????????????????
				SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
				String thumbnailRename = sdf.format(new Date(System.currentTimeMillis())) + "."
						+ thumbnailName.substring(thumbnailName.lastIndexOf(".") + 1);
				String thumbnailpath = savedPath + "\\" + thumbnailRename;
				reuploadFile.transferTo(new File(thumbnailpath));
				recipe.setThumbnailName(thumbnailName);
				recipe.setThumbnailRename(thumbnailRename);
				recipe.setThumbnailpath(thumbnailpath);
			}
			// ???????????????
			int result = rService.modifyRecipe(recipe);
			// ????????????
			List<Ingradient> iList = new ArrayList<Ingradient>();
			for (int i = 0; i < ing.getIngAmount().split(",").length; i++) {
				Ingradient ingredient = new Ingradient();
				ingredient.setRecipeNo(ing.getRecipeNo());
				ingredient.setIngNo(ing.getIngNo().split(",")[i]);
				ingredient.setIngBundleName(ing.getIngBundleName());
				ingredient.setIngAmount(ing.getIngAmount().split(",")[i]);
				ingredient.setLargeCatId(ing.getLargeCatId().split(",")[i]);
				ingredient.setSmallCatId(ing.getSmallCatId().split(",")[i]);
				iList.add(ingredient);
			}
			// ????????????
			List<Order> oList = new ArrayList<Order>();
			for (int i = 0; i < order.getRecipeContents().split(",").length; i++) {
				Order ord = new Order();
				ord.setRecipeNo(order.getRecipeNo());
				ord.setOrederNo(order.getOrederNo().split(",")[i]);
				ord.setRecipeContents(order.getRecipeContents().split(",")[i]);
				// ?????????????????????
				String orderPhotoName = reouploadFile.get(i).getOriginalFilename();
				if (reouploadFile.get(i) != null && !orderPhotoName.equals("") && !orderPhotoName.equals(" ")) {
					System.out.println(1);
					String root = request.getSession().getServletContext().getRealPath("resources");
					String savedPath = root + "\\ruploadFiles";
//					System.out.println(order.getOrderPhotoRename().split(",")[i]);
//					File file = new File(savedPath + "\\" + order.getOrderPhotoRename().split(",")[i]);
//					if(file.exists()) {
//						file.delete();
//					}
					// ?????? ????????????
					SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
					String orderPhotoRename = sdf.format(new Date(System.currentTimeMillis())) + "ro" + i + "."
							+ orderPhotoName.substring(thumbnailName.lastIndexOf(".") + 1);
					String orderPhotopath = savedPath + "\\" + orderPhotoRename;
					reouploadFile.get(i).transferTo(new File(orderPhotopath));
					ord.setOrderPhotoName(orderPhotoName);
					ord.setOrderPhotoRename(orderPhotoRename);
					ord.setOrderPhotopath(orderPhotopath);
				}
				oList.add(ord);
			}
			// ??????????????????
			List<ComPhoto> cList = new ArrayList<ComPhoto>();
			for (int i = 0; i < comPhoto.getComPhotoNo().split(",").length; i++) {
				ComPhoto com = new ComPhoto();
				com.setRecipeNo(comPhoto.getRecipeNo());
				com.setComPhotoNo(comPhoto.getComPhotoNo().split(",")[i]);
				String comPhotoName = recuploadFile.get(i).getOriginalFilename();
				if (recuploadFile.get(i) != null && !comPhotoName.equals("")) {
					String root = request.getSession().getServletContext().getRealPath("resources");
					String savedPath = root + "\\ruploadFiles";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
					String comPhotoRename = sdf.format(new Date(System.currentTimeMillis())) + "rc" + i + "."
							+ comPhotoName.substring(comPhotoName.lastIndexOf(".") + 1);
					String comPhotopath = savedPath + "\\" + comPhotoRename;
					recuploadFile.get(i).transferTo(new File(comPhotopath));
					com.setComPhotoName(comPhotoName);
					com.setComPhotoRename(comPhotoRename);
					com.setComPhotopath(comPhotopath);
				}
				cList.add(com);
			}
			for (int i = 0; i < cList.size(); i++) {
				int cresult = rService.modifyCom(cList.get(i));
			}

//			System.out.println(oList);
			// ????????????
			for (int i = 0; i < iList.size(); i++) {
				int iresult = rService.modifyIng(iList.get(i));
			}
			// ???????????? ??????
			for (int i = 0; i < oList.size(); i++) {
				int oresult = rService.modifyOrder(oList.get(i));
			}
			mv.setViewName("redirect:/recipe/recipeDetailView.kh?recipeNo=" + recipeNo);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "????????? ?????? ??????").setViewName("common/errorPage");
		}

		return mv;
	}

	@RequestMapping(value = "/recipe/recipeRemove.kh", method = RequestMethod.GET)
	public ModelAndView recipeRemove(ModelAndView mv,
			@RequestParam(value = "recipeNo", required = false) Integer recipeNo, HttpSession session,
			HttpServletRequest request) {

		try {
			String user = String.valueOf(session.getAttribute("loginUser"));
			if (user == null) {
				System.out.println("????????? ??????????????? ??????");
				request.setAttribute("msg", "???????????? ????????? ??? ????????????.");
				request.setAttribute("url", "/recipe/recipeDetailView.kh?recipeNo=" + recipeNo);
				mv.setViewName("common/alert");
				return mv;
			}
//			Recipe recipe = rService.printRecipeByRNo(recipeNo);
//			if (user != recipe.getUserId()) {
//				System.out.println("????????? ????????? ???????????? ??????");
//				request.setAttribute("msg", "???????????? ????????? ??? ????????????.");
//				request.setAttribute("url", "/recipe/recipeDetailView.kh?recipeNo=" + recipeNo);
//				mv.setViewName("common/alert");
//				return mv;
//			}
			mv.addObject("recipeNo", recipeNo);
			int result = rService.removeRecipe(recipeNo);
			request.setAttribute("msg", "???????????? ?????? ???????????????.");
			request.setAttribute("url", "/recipe/recipeList.kh");
			mv.setViewName("common/alert");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "????????? ?????? ??????").setViewName("common/errorPage");
		}

		// ???????????????
		return mv;
	}

	// ????????? ??????
	@RequestMapping(value = "/recipe/recipeLike.kh", method = RequestMethod.POST)
	public String recipeLikeUpdate(ModelAndView mv, @RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "recipeNo", required = false) Integer recipeNo,
			@RequestParam(value = "category", required = false) String listValue,
			@RequestParam(value = "page", required = false) Integer page, HttpServletRequest request) {
		try {
			Like like = new Like();
			like.setUserId(userId);
			like.setRecipeNo(recipeNo);
			int result = rService.likeUp(like);
			return "redirect:/recipe/recipeDetailView.kh?recipeNo=" + recipeNo + "&page=" + page + "&category="
					+ listValue + "&userId=" + userId;
		} catch (Exception e) {
			// TODO: handle exception
			request.setAttribute("msg", "???????????? ????????????");
			request.setAttribute("url", "/recipe/recipeDetailView.kh?recipeNo=" + recipeNo + "&page=" + page
					+ "&category=" + listValue + "&userId=" + userId);
			return "common/alert";
		}
	}

	// ????????? ??????
	@RequestMapping(value = "/recipe/recipeLikeDel.kh", method = RequestMethod.POST)
	public String recipeLikeRemove(ModelAndView mv, @RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "recipeNo", required = false) Integer recipeNo,
			@RequestParam(value = "category", required = false) String listValue,
			@RequestParam(value = "page", required = false) Integer page, HttpServletRequest request) {
		try {
			Like like = new Like();
			like.setUserId(userId);
			like.setRecipeNo(recipeNo);
			int result = rService.likeDown(like);
			return "redirect:/recipe/recipeDetailView.kh?recipeNo=" + recipeNo + "&page=" + page + "&category="
					+ listValue + "&userId=" + userId;
		} catch (Exception e) {
			request.setAttribute("msg", "???????????? ????????????");
			request.setAttribute("url", "/recipe/recipeDetailView.kh?recipeNo=" + recipeNo + "&page=" + page
					+ "&category=" + listValue + "&userId=" + userId);
			return "common/alert";
		}
	}

	@RequestMapping(value = "/recipe/addRecipe.kh", method = RequestMethod.POST)
	public ModelAndView addRecipeReply(ModelAndView mv, @ModelAttribute Recipe recipe, @ModelAttribute Order order,
			@ModelAttribute ComPhoto comPhoto, @ModelAttribute Ingradient ing) {

		return mv;
	}
}
