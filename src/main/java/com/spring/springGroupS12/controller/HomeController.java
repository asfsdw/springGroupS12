package com.spring.springGroupS12.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.spring.springGroupS12.common.Pagination;
import com.spring.springGroupS12.service.BoardService;
import com.spring.springGroupS12.service.ShopService;
import com.spring.springGroupS12.vo.BoardVO;
import com.spring.springGroupS12.vo.ShopVO;

@Controller
public class HomeController {
	@Autowired
	Pagination pagination;
	@Autowired
	ShopService shopService;
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = {"/","/h","/index","main"}, method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		if(session.getAttribute("sEmailKey") != null) session.removeAttribute("sEmailKey");
		List<ShopVO> sVOS = shopService.getProductListHome();
		List<BoardVO> bVOS = boardService.getBoardListHome();
		
		model.addAttribute("sVOS", sVOS);
		model.addAttribute("bVOS", bVOS);
		return "home";
	}
	
	// 이미지 업로드.
	@PostMapping("/ImageUpload")
	public void imageUploadPost(MultipartFile upload, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
		String oFileName = upload.getOriginalFilename();
		
		String regExt = "(jpg|jpeg|gif|png|mp4|webm|ogv)";
		String ext = oFileName.substring(oFileName.lastIndexOf(".")+1);
		if(!ext.matches(regExt)) return;
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		oFileName = sdf.format(date)+"_"+oFileName;
		
		FileOutputStream fos = new FileOutputStream(new File(realPath, oFileName));
		byte[] bytes = upload.getBytes();
		fos.write(bytes);
		
		// ckeditor에 결과 보내주기.
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath()+"/data/ckeditor/"+oFileName;
		out.println("{\"originalFilename\":\""+oFileName+"\",\"uploaded\":\"1\",\"url\":\""+fileUrl+"\"}");
		
		fos.close();
	}
	
	//파일 통합(zip) 다운로드.
	@GetMapping("/FileDownAction")
	public void fileDownActionGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getParameter("path");
		String file = request.getParameter("file");
		
		if(path.equals("pds")) path += "/temp/";
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+path);
		File downFile = new File(realPath+file);
		
		if(downFile.exists()) {
			String downLoadName = "";
			if(request.getHeader("user-agent").indexOf("MSIE") == -1) downLoadName = new String(file.getBytes("UTF-8"), "8859_1");
			else downLoadName = new String(file.getBytes("EUC-KR"), "8859_1");
			
			response.setHeader("Content-Disposition", "attachment;filename="+downLoadName);
			
			FileInputStream fis = new FileInputStream(downFile);
			ServletOutputStream sos = response.getOutputStream();
			
			byte[] bytes = new byte[2048];
			int size = 0;
			while((size = fis.read(bytes, 0, bytes.length)) != -1) {
				sos.write(bytes, 0, size);
			}
			sos.flush();
			sos.close();
			fis.close();
			
			downFile.delete();
		}
	}
}
