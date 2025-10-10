package com.spring.springGroupS12.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.springGroupS12.dao.FileDAO;
import com.spring.springGroupS12.vo.FileVO;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	FileDAO fileDAO;

	@Override
	public FileVO getFile(String part, int parentIdx) {
		return fileDAO.getFile(part, parentIdx);
	}

	@Override
	public List<FileVO> getFileList(String part) {
		return fileDAO.getFileList(part);
	}

	// 게시글 수정.
	@Override
	public void imgBackup(String content) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 32;
		boolean sw = true;
		String nextImage = content.substring(content.indexOf("src=\"/")+position);
		
		while(sw) {
			String imgFile = nextImage.substring(0, nextImage.indexOf("\""));
			String oFilePath = realPath+"board/"+imgFile;
			String copyFilePath = realPath+"ckeditor/"+imgFile;
			
			fileCopyCheck(oFilePath, copyFilePath);
			
			if(nextImage.indexOf("src=\"/") == -1) sw = false;
			else nextImage = nextImage.substring(nextImage.indexOf("src=\"/")+position);
		}
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

	// 파일 삭제.
	@Override
	public void imgDelete(String part, String content) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 32;
		String nextImage = content.substring(content.indexOf("src=\"/")+position);
		boolean sw = true;
		
		while(sw) {
		String imgFile = nextImage.substring(0, nextImage.indexOf("\""));
		String oFilePath = realPath+part+imgFile;
		
		fileDelete(oFilePath);
		
		if(nextImage.indexOf("src=\"/") == -1) sw = false;
		else nextImage = nextImage.substring(nextImage.indexOf("src=\"/")+position);
		}
	}
	// 첨부파일 삭제.
	@Override
	public void fileRemove(String part, FileVO fVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		String fileNames[] = fVO.getSFileName().split("/");
		String fileSizes[] = fVO.getFileSize().split("/");
		
		for(int i=0; i<fileNames.length; i++) {
			if(!fileSizes[i].equals("0")) {
				String oFilePath = realPath+part+"/"+fileNames[i];
				fileDelete(oFilePath);
			}
		}
	}
	// 파일 삭제.
	private void fileDelete(String oFilePath) {
		File delFile = new File(oFilePath);
		if(delFile.exists()) delFile.delete();
	}
	
}
