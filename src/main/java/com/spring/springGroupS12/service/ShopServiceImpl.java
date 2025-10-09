package com.spring.springGroupS12.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.springGroupS12.common.ProjectProvide;
import com.spring.springGroupS12.dao.FileDAO;
import com.spring.springGroupS12.dao.ShopDAO;
import com.spring.springGroupS12.vo.FileVO;
import com.spring.springGroupS12.vo.ShopVO;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	ShopDAO shopDAO;
	@Autowired
	ProjectProvide projectProvide;
	@Autowired
	FileDAO fileDAO;

	@Override
	public int getTotRecCnt(String flag, String search, String searchStr) {
		return shopDAO.getTotRecCnt(flag, search, searchStr);
	}

	@Override
	public int setProductImage(MultipartFile fName, ShopVO vo, FileVO fVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		int res = 0;
		
		String oFileNames = "";
		String sFileNames = "";
		String fileSize = "";
		String content = "";
		int position = 35;
		boolean sw = true;
		
		if(vo.getContent().contains("src=\"/")) content = vo.getContent().substring(vo.getContent().indexOf("src=\"/")+position);
		
		try {
			if(!content.equals("")) {
				while(sw) {
					String imgFile = content.substring(0, content.indexOf("\""));
					String oFilePath = realPath+"ckeditor/"+imgFile;
					String copyFilePath = realPath+"shop/"+imgFile;
					
					fileCopyCheck(oFilePath, copyFilePath);
					
					String oFileName = content.substring(content.indexOf("_")+1, content.indexOf("\""));
					String sFileName = content.substring(0, content.indexOf("\""));
					oFileNames += oFileName+"/";
					sFileNames += sFileName+"/";
					// 업로드한 파일과 배열 수를 맞추기 위해 파일 크기에 0추가.
					fileSize += "0/";
					
					if(content.indexOf("src=\"/") == -1) sw = false;
					else content = content.substring(content.indexOf("src=\"/")+position);
				}
			}
			
			String oFileName = fName.getOriginalFilename();
			// 첨부파일이 있을 때.
			if(!oFileName.equals("")) {
				String sFileName = projectProvide.saveFileName(oFileName);
				projectProvide.writeFile(fName, sFileName, "shop");
				
				oFileNames += oFileName+"/";
				sFileNames += sFileName+"/";
				fileSize += fName.getSize()+"/";
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
			fileSize = fileSize.substring(0, fileSize.length()-1);
			
			vo.setContent(vo.getContent().replace("ckeditor", "shop"));
			res = shopDAO.setProductInput(vo);
			
			fVO.setPart("shop");
			fVO.setParentIdx(fileDAO.getParentIdx("shop"));
			fVO.setOFileName(oFileNames);
			fVO.setSFileName(sFileNames);
			fVO.setFileSize(fileSize);
			res = fileDAO.setFile(fVO);
			
			// 파일 처리 끝나면 임시폴더(ckeditor)에 있는 파일 삭제.
			String deleteFileNames[] = sFileNames.split("/");
			for(int i=0; i<deleteFileNames.length; i++) {
				projectProvide.fileDelete(deleteFileNames[i], "ckeditor");
			}
		} catch (Exception e) {e.printStackTrace();}
		
		projectProvide.fileDelete(sFileNames, "ckeditor");
		return res;
	}
	//파일 복사.
	private void fileCopyCheck(String oFilePath, String copyFilePath) {
		try {
			FileInputStream fis = new FileInputStream(new File(oFilePath));
			FileOutputStream fos = new FileOutputStream(new File(copyFilePath));
			
			byte[] bytes = new byte[2048];
			int cnt = 0;
			while((cnt=fis.read(bytes)) != -1) {
				fos.write(bytes, 0, cnt);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
