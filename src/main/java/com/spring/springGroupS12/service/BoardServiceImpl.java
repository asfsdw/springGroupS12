package com.spring.springGroupS12.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.springGroupS12.common.ProjectProvide;
import com.spring.springGroupS12.dao.BoardDAO;
import com.spring.springGroupS12.dao.FileDAO;
import com.spring.springGroupS12.vo.BoardVO;
import com.spring.springGroupS12.vo.FileVO;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardDAO boardDAO;
	@Autowired
	FileDAO fileDAO;
	@Autowired
	ProjectProvide projectProvide;

	@Override
	public int getTotRecCnt(String flag, String search, String searchStr) {
		return boardDAO.getTotRecCnt(flag, search, searchStr);
	}
	
	@Override
	public List<BoardVO> getBoardList(int startIndexNo, int pageSize, String search, String searchStr) {
		return boardDAO.getBoardList(startIndexNo, pageSize, search, searchStr);
	}

	@Override
	public BoardVO getBoard(int idx) {
		return boardDAO.getBoard(idx);
	}

	@Override
	public int setBoardInput(BoardVO vo) {
		return boardDAO.setBoardInput(vo);
	}

	@Transactional
	@Override
	public int uploadBoardInput(MultipartHttpServletRequest mFile, BoardVO vo, FileVO fVO) {
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
		// 					 1				 2				 3
		// 0123456789012345678901234567890123456789
		// src="/springGroupS12/data/ckeditor/251001124341_07.jpg" style="height:199px; width:300px" /></p>
		try {
			// ckeditor로 파일을 업로드했을 때.
			if(!content.equals("")) {
				while(sw) {
					String imgFile = content.substring(0, content.indexOf("\""));
					String oFilePath = realPath+"ckeditor/"+imgFile;
					String copyFilePath = realPath+"board/"+imgFile;
					
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
			List<MultipartFile> fileList = mFile.getFiles("file");
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				// 첨부파일이 있을 때.
				if(!oFileName.equals("")) {
					String sFileName = projectProvide.saveFileName(oFileName);
					projectProvide.writeFile(file, sFileName, "board");
					
					oFileNames += oFileName+"/";
					sFileNames += sFileName+"/";
					fileSize += file.getSize()+"/";
				}
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
			fileSize = fileSize.substring(0, fileSize.length()-1);
			
			vo.setContent(vo.getContent().replace("ckeditor", "board"));
			res = boardDAO.setBoardInput(vo);
			
			fVO.setPart("board");
			fVO.setParentIdx(boardDAO.getParentIdx());
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

	@Override
	public void setGood(int idx, int goodCnt) {
		boardDAO.setGood(idx, goodCnt);
	}

	@Override
	public void contentView(int idx) {
		boardDAO.contentView(idx);
	}

	@Override
	public List<BoardVO> getBoardBest(int startIndexNo, int pageSize, String search, String searchStr) {
		return boardDAO.getBoardBest(startIndexNo, pageSize, search, searchStr);
	}

}
