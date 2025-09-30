package com.spring.springGroupS12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	public List<BoardVO> getBoardList() {
		return boardDAO.getBoardList();
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
		int res = 0;
		String oFileNames = "";
		String sFileNames = "";
		String fileSize = "";
		
		try {
			List<MultipartFile> fileList = mFile.getFiles("file");
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				String sFileName = projectProvide.saveFileName(oFileName);
				projectProvide.writeFile(file, sFileName, "board");
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSize += file.getSize() + "/";
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
			fileSize = fileSize.substring(0, fileSize.length()-1);
			
			res = boardDAO.setBoardInput(vo);
			
			fVO.setPart("board");
			fVO.setParentIdx(boardDAO.getParentIdx());
			fVO.setOFileName(oFileNames);
			fVO.setSFileName(sFileNames);
			fVO.setFileSize(fileSize);
			res = fileDAO.setFile(fVO);
		} catch (Exception e) {
			e.printStackTrace();
		} return res;
	}

}
