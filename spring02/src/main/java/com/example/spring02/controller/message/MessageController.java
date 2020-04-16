package com.example.spring02.controller.message;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring02.model.message.dto.MessageDTO;
import com.example.spring02.service.message.MessageService;

// https://www.youtube.com/watch?v=7zbDZ0IM9LA&list=PLY9pe3iUjRrRiJeg0jw22yW1G5yzAdiqC&index=14 (28:00분 부터 참고 38분부터 post로 json보내는방식설명)
//@Controller
@RestController
@RequestMapping("/messages/*")
public class MessageController {
	@Inject
	MessageService service;
	
	//@ResponseBody
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> addMessage(
			@RequestBody MessageDTO dto){  //@RequestBody는 입력되는 데이터가 제이슨 형식일때 사용  , json은 스트링이다 이런 스트링을 dto에 담을라면 변환과정을 거쳐야하는데 그것이 RequestBody이다 
		ResponseEntity<String> entity=null; //ResponseEntity는  코드를 실행하고 성공했는지 실패했는지의 메시지 하고 간단 한 에러코드를 함계 리턴할수있따.
		try {
			service.addMessage(dto);
			entity=new ResponseEntity<>("success",HttpStatus.OK); // 성공했을때 success 메시지를 보내고 HttpStatus.OK 코드를 보낸다 .
		}catch (Exception e) {
			e.printStackTrace();
			entity= new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST); //실패했을때 에러메시지와 에러코드를 보낸다 .
		}
		return entity;
		
	}

}
