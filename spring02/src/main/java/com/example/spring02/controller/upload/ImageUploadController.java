package com.example.spring02.controller.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller //현재 클래스를 컨트롤러 빈으로 등록
public class ImageUploadController {
	private static final Logger logger =LoggerFactory.getLogger(ImageUploadController.class);
	
	@ResponseBody
	@RequestMapping("imageUpload.do")
	public void imageUpload(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam MultipartFile upload) throws Exception {  //ckeditor에서 upload라는 이름으로 집어 넣는다.
		//http header 설정  , 브라우저에 인코딩 지정
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//http body
		//업로드한 파일이름
		String fileName = upload.getOriginalFilename();
		//바이트 배열로 변환
		byte[] bytes = upload.getBytes();
		//이미지르 업로드할 디렉토리(배포 경로로 설정)
		//개발디렉토리 
		//String uploadPath = "C:\\Users\\hookcu\\Documents\\workspace-spring-tool-suite-4-4.3.1.RELEASE\\spring02\\src\\main\\webapp\\WEB-INF\\views\\images";
		//배포디렉토리
		String uploadPath = "C:\\Users\\hookcu\\Documents\\workspace-spring-tool-suite-4-4.3.1.RELEASE\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";
		
		
		
		OutputStream out = new FileOutputStream(new File(uploadPath+ fileName));//java.io
		//서버에 저장됨
		out.write(bytes);
		String callback = request.getParameter("CKEditorFuncNum");  // CKEditorFuncNum 이것은 ck내부에서 쓰는것이니깐 그대로 써야한다
		PrintWriter printWriter =response.getWriter();
		String fileUrl = request.getContextPath()+"/images/"+fileName;
		System.out.println("fileUrl:" + fileUrl);
		printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction("  
				+callback+",'"+fileUrl+"',' 이미지가 업로드되었습니다.')" +"</script>"); //이부분도 그대로 그냥써라
		//스크림 닫기
		printWriter.flush();
		
	}

}
