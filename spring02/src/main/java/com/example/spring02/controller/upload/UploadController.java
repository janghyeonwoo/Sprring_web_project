package com.example.spring02.controller.upload;

import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	//xml에 설정된 리소스 참조
	//bean의 id가 uploadPath인 태그 를참조
	@Resource(name="uploadPath")
	String uploadPath;   // String uploadPath= d:/upload 이렇게 써도 무관하다. 하지만 공통으로 쓸때는 ...
	@RequestMapping(value="/upload/uploadForm",method=RequestMethod.GET) //get방식
	public void upload() {
		//upload/uploadForm.jsp로 포워딩
	}
	//업로드 버튼 => 임시 디렉티로에 업로드
	// ==> 파일 정보가 file에 저장 
	// ==> 지정된 디렉토리에 저장 ==>
	@RequestMapping(value="/upload/uploadForm",method=RequestMethod.POST) //post방식
	public ModelAndView uploadForm(MultipartFile file, ModelAndView mav) throws Exception{//파일이 오면 file에 다쌓이게된다.
		logger.info("파일이름" + file.getOriginalFilename());
		String savedName = file.getOriginalFilename();
		logger.info("파일크기" + file.getSize());
		logger.info("컨텐트 타입" + file.getContentType());
		savedName = uploadFile(savedName,file.getBytes());
		mav.setViewName("upload/uploadResult");
		mav.addObject("savedName" , savedName);
		return mav; //uploadResult.jsp로 포워딩
		
	}
	//파일 이름이 중복되지 않도록 처리 
	private String uploadFile(String originalName, byte[] fileData) throws Exception{
		//uuid 생성(Universal Unique IDentifier, 범용 고유 식별자) ,랜덤으로 코드를 만들어낸다 거의 중복되지 않는다.
		UUID uid =UUID.randomUUID();
		String savedName = uid.toString() +"_" + originalName;
		File target =new File(uploadPath, savedName);  //파일을 실제로 저장하는곳
		//임시 디렉토리에 저장된 업르드된 파일을
		//지정된 디렉토리로 복사
		//FileCopyUtils.copy(바이트배열,파일객체);
		FileCopyUtils.copy(fileData,target);  //파일을 복사
		return savedName; //복사한 파일이름을 되돌려준다.
	
	}

}
