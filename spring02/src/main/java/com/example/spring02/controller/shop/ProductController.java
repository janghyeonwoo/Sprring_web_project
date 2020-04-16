package com.example.spring02.controller.shop;

import java.io.File;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.shop.dto.ProductDTO;
import com.example.spring02.service.shop.ProductService;

@Controller  //현재 클래스를 스프링에서 관리하는 Controller Bean으로 설정
@RequestMapping("/shop/product/*") //공통적인 url
public class ProductController {
	@Inject  //의존관계 주입(DI)  , 개발자가 객체생성해서 의존관계를 설정하는것이아니라 스프링에서 알아서 만들어서 링크시켜준다.
	ProductService productService; //스프링에서 만든 서비스 객체를 연결시킴
	
	@RequestMapping("list.do")//세부적인 url mapping
	public ModelAndView list(ModelAndView mav) {
		mav.setViewName("/shop/product_list");  //이동할 페이지이름
		mav.addObject("list",productService.listProduct()); //데이터저장
		return mav; //페이지 이동
		
	}
	@RequestMapping("/detail/{product_id}")  //url에 포함된 변수
	public ModelAndView detail(@PathVariable("product_id") int product_id, ModelAndView mav) {
		mav.setViewName("/shop/product_detail");
		mav.addObject("dto", productService.detailProduct(product_id));
		return mav;
		
	}
	@RequestMapping("write.do")
	public String write() {
		return "shop/product_write";
	}
	@RequestMapping("insert.do")
	public String insert(ProductDTO dto) { //form에서 보낸 파일이 dto의 multPartFile에 저장된다
		String filename="-";
		if(!dto.getFile1().isEmpty()) {  //첨부파일이 존재하면
			filename=dto.getFile1().getOriginalFilename();
			//이미자폴더 속성보기 하면 경로 나옴 , 밑에있는 것은 개발디렉토리 이다 이렇게 파일을 등록을 하면 새로고침을 해줘야한다 아직 배포디렉토리에 올라가지 않았기때문이다.
			//String path="C:\\Users\\hookcu\\Documents\\workspace-spring-tool-suite-4-4.3.1.RELEASE\\spring02\\src\\main\\webapp\\WEB-INF\\views\\images\\";
			
			//밑에는 배포디렉토리에 넣어주는 방법으로 개발디렉토리에는 파일이 추가되지않고 배포디렉토리에 바로 들어가기떄문에 새로고침할필요없다, 배포디렉토리 경로에 개발디렉토리의 spring02이후를 붙여주면된다.. , 경로를 아는 방법은 home.jsp의  application.getRealPath("/") 를통해서 알수있다.  
			//String path="C:\\Users\\hookcu\\Documents\\workspace-spring-tool-suite-4-4.3.1.RELEASE\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";
			String path="C:\\Users\\hookcu\\Documents\\workspace-spring-too\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";
					
			try {
				new File(path).mkdir();  //디렉토리가 없으면 만들어라 
				dto.getFile1().transferTo(new File(path+filename));  //파일을 디렉토리에 저장
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		dto.setPicture_url(filename);
		productService.insertProduct(dto);
		return "redirect:/shop/product/list.do";
		
	}
	//상품정보 편집 페이지로 이동
	@RequestMapping("edit/{product_id}")
	public ModelAndView edit(@PathVariable("product_id") int product_id,ModelAndView mav) {
		mav.setViewName("/shop/product_edit"); //이동할 페이지의 이름
		//전달한 데이터를 저장
		mav.addObject("dto" ,productService.detailProduct(product_id));
		return mav; //페이지 이동
	}
	
	@RequestMapping("update.do")
	public String update(ProductDTO dto) {
		String filename="-";
		
		if(!dto.getFile1().isEmpty()) {//첨부파일이 존재하면
			filename=dto.getFile1().getOriginalFilename();
			//배포디렉토리
			String path="C:\\\\Users\\\\hookcu\\\\Documents\\\\workspace-spring-tool-suite-4-4.3.1.RELEASE\\\\.metadata\\\\.plugins\\\\org.eclipse.wst.server.core\\\\tmp0\\\\wtpwebapps\\\\spring02\\\\WEB-INF\\\\views\\\\images\\\\";
			try {
				new File(path).mkdir();
				dto.getFile1().transferTo(new File(path+filename));  //지정된 디렉토리로 카피
			}catch(Exception e){
				e.printStackTrace();
			}
			dto.setPicture_url(filename);
		}else {//첨부파일이없는경우
			ProductDTO dto2= productService.detailProduct(dto.getProduct_id());
			dto.setPicture_url(dto2.getPicture_url());
		}
		productService.updateProduct(dto);
		return "redirect:/shop/product/list.do";
	}
	@RequestMapping("delete.do")
	public String delete(@RequestParam int product_id) {
		//첨부파일의 이름
		String filename = productService.fileInfo(product_id);  //파일이름을 그대로 form으로 받아올수도있지만  그렇게되면 파일이름이 한글인경우 깨질수있다 
		if(filename != null && !filename.equals("-")) {
			String path="C:\\Users\\hookcu\\Documents\\workspace-spring-tool-suite-4-4.3.1.RELEASE\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\spring02\\WEB-INF\\views\\images\\";
			File f = new File(path+filename);
			if(f.exists()) {  //파일이 존재하면 그이유는 이거 하는 동안 다른사람이 삭제할수도있으니깐 확인하기
				f.delete(); //파일삭제
			}
		}
		productService.deleteProduct(product_id);  //레코드삭제
		return "redirect:/shop/product/list.do";
				
	}
	
}
