package com.spring.springGroupS12.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectProvide {
	@Autowired
	JavaMailSender mailSender;
	
	public String mailSend(String toMail, String title, String mailFlag) throws MessagingException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 메일 전송을 위한 객체: MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		String content = "";
		// 메시지 보관함에 저장되는 content 변수 안에 발신자가 보여주고 싶은 정보를 추가한다.
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>springGroup에서 보냅니다.</h3><hr><br>";
		content += "<font color='red' size='6'><b>"+mailFlag+"</b></font><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p><a href='http://192.168.50.53:9090/springGroupS12/' class='btn btn-info'>방문하기</a></p>";
		content += "<hr>";
		
		messageHelper.setTo(toMail);
		messageHelper.setSubject(title);
		// true: 예약처리 안 하고 바로 보내겠다.
		messageHelper.setText(content, true);
		
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		// 이미지파일 이름, 경로
		messageHelper.addInline("main.jpg", file);
		
		// 메일 전송.
		mailSender.send(message);
		
		return "1";
	}

	// 파일 업로드 후, 서버에 저장된 이름 반환.
	public String fileUpload(MultipartFile fName, String mid, String part) {
		String oFileName = fName.getOriginalFilename();
		String sFileName = mid+"_"+(UUID.randomUUID().toString().substring(0, 4))+"_"+oFileName;
		try {
			writeFile(fName, sFileName, part);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sFileName;
	}
	// 파일 업로드.
	public void writeFile(MultipartFile fName, String sFileName, String part) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+part+"/");
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		if(fName.getBytes().length != -1) fos.write(fName.getBytes());
		fos.flush();
		fos.close();
	}
	// 파일 삭제.
	public void fileDelete(String fileName, String part) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+part+"/");
		File file = new File(realPath+fileName);
		
		// 파일이 존재하면, 삭제.
		if(file.exists()) file.delete();
	}
	// 파일 이름 변경.
	public String saveFileName(String oFileName) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		return sdf.format(date)+"_"+oFileName;
	}
	
}
