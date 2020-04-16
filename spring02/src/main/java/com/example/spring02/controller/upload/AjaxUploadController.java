package com.example.spring02.controller.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring02.service.board.BoardService;

import util.MediaUtils;
import util.UploadFileUtils;


@Controller
public class AjaxUploadController {
	private static final Logger logger = LoggerFactory.getLogger(AjaxUploadController.class);
	
	@Inject
	BoardService boardService;
	
	//servlet-context.xml에 선언된 두값  ,업로드 디렉토리 servlet-context.xml에 설정되어 있음
	@Resource(name="uploadPath")
	String uploadPath;
	
	//파일첨부 페이지로 이동
	@RequestMapping(value="/upload/uploadAjax", method=RequestMethod.GET)
	public void uploadAjax() {  // void 이므로 /upload/uploadAjax 그대로 이동
		//view/upload/uploadAjax.jsp 로 이동 
	}
	// 업로드한 파일은 MultipartFile 변수에 저장됨
	@ResponseBody // json 형식으로 리턴 , jsp로 넘어가는 것이 아닌 데이터를 돌려준다
	@RequestMapping(value = "/upload/uploadAjax", 
	method = RequestMethod.POST, produces = "text/plain;charset=utf-8")   // produces는 여기서 만들어지는 데이터는 텍스트이다 라고 선언
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {  //ResponseEntity<String>는 메시지와 에러코드를 같이 넘겨주는 코드
		//파일 정보를 로그에 출력
		logger.info("파일이름  : " +file.getOriginalFilename());
		logger.info("파일크기  : " +file.getSize());
		logger.info("파일종류 : " +file.getContentType());
		//new ResponseEntity(데이터, 상태코드)
		//new ResponseEntity(업로드된 파일이름, 상태코드)
		// 업로드한 파일 정보와 Http 상태 코드를 함께 리턴
		return new ResponseEntity<String>(
				//업로드가 이루어짐
				UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.OK);
	}

	// 이미지 표시 기능
	@ResponseBody // view가 아닌 data 리턴
	@RequestMapping("/upload/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) 
			throws Exception {
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		System.out.println(fileName+"zzzzzzzzzzzz");
		try {
			// 확장자 검사
			String formatName = fileName.substring(
					fileName.lastIndexOf(".") + 1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			// 헤더 구성 객체
			HttpHeaders headers = new HttpHeaders();
			// InputStream 생성
			in = new FileInputStream(uploadPath + fileName);
			if (mType != null) { // 이미지 파일이면
				headers.setContentType(mType);
			} else {  // 이미지가 아니면
				fileName = fileName.substring(  //uuid를 제외한 파일 이름 
						fileName.indexOf("_") + 1); 
				// 다운로드용 컨텐트 타입
				headers.setContentType(   //컨텐트타입
						MediaType.APPLICATION_OCTET_STREAM);  //APPLICATION_OCTET_STREAM은 범용적인 컨텐트타입이다 이미지 파일이아니면 다양한 확장자를 가지고 있기때문에
				// 파일이름에 한글이 들어간경우
				// 스트링.getBytes("언어셋") 스트링을 바이트배열로 변환
				// new.String( 바이트배열 , "언어셋") 문자열의 인코딩 변경
				// new String(fileName.getBytes("utf-8"),"iso-8859-1")    --> utf-8로 읽어서 서유럽 iso로 바꾼다
				headers.add("Content-Disposition",	 //첨부파일의 정보 
						"attachment; filename=\"" 
								+ new String(
fileName.getBytes("utf-8"), "iso-8859-1") + "\""); 
				// headers.add("Content-Disposition"
				// ,"attachment; filename='"+fileName+"'");
			}
			// 바이트배열, 헤더
			entity = new ResponseEntity<byte[]>(
					IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(
					HttpStatus.BAD_REQUEST);
		} finally {
			if (in != null)
				in.close(); // 스트림 닫기
		}
		return entity;
	}
	
	@ResponseBody //뷰가 아닌 데이터를 리턴
	@RequestMapping(value="/upload/deleteFile"
		,method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName){
		logger.info("fileName:"+fileName); 
		//확장자 검사
		//fileName에는 이미지 파일의 경우 썸네일 파일 이름이 넘어옴
		String formatName=fileName.substring(
				fileName.lastIndexOf(".")+1);
		MediaType mType=MediaUtils.getMediaType(formatName);
		if(mType != null) { //이미지 파일이면 원본이미지 삭제
			String front=fileName.substring(0, 12);  //연도 
			String end=fileName.substring(14); //  _s 이후 
// 		File.separatorChar : 유닉스 / 윈도우즈\	
			new File(uploadPath+(front+end).replace(
					'/',File.separatorChar)).delete(); //썸네일 삭제
		}
		//원본 파일 삭제(이미지이면 썸네일 삭제)
		new File(uploadPath+fileName.replace( 
				'/',File.separatorChar)).delete();  //파일삭제  
		//db의 레코드 삭제
		boardService.deleteFile(fileName);
		return new ResponseEntity<String>("deleted"
	 			,HttpStatus.OK);
	}
}