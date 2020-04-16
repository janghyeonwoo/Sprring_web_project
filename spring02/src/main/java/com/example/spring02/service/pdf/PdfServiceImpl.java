package com.example.spring02.service.pdf;

import java.io.FileOutputStream;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.spring02.model.shop.dto.CartDTO;
import com.example.spring02.service.shop.CartService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfServiceImpl implements PdfService {
	
	@Inject
	CartService cartService;
	@Override
	public String createPdf() {
		String result="";
		try {
			Document document = new Document();  //pdf문서를 처리하는 객체
			//sample.pdf라는이름으로 저장한다.
			PdfWriter writer  = PdfWriter.getInstance(document, new FileOutputStream("d:/sample.pdf"));
			document.open(); //문서를 연다
			//한글처리가 안됨으로 ,한글폰트를 사용한다, 폰트를 포함시켰다
			BaseFont baseFont =BaseFont.createFont("c:/windows/fonts/malgun.ttf" 
					,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);  
			Font font =new Font(baseFont,12);  //폰트크기
			PdfPTable table =new PdfPTable(4);//4개의 셀을 가진 테이블
			Chunk chunk = new Chunk("장바구니",font); // 타이틀 , 제목부분
			Paragraph ph =new Paragraph(chunk);  // 문단만들기
			ph.setAlignment(Element.ALIGN_CENTER);  //가운데 정렬
			document.add(ph);
			document.add(Chunk.NEWLINE);  //줄바꿈
			document.add(Chunk.NEWLINE);
			
			PdfPCell cell1 =new PdfPCell(new Phrase("상품명",font)); //셀만들기 , 셀의 이름과 어떤폰트를 적용할것인지를 만든다
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);  //셀의 정렬방식을 정한다
			PdfPCell cell2 =new PdfPCell(new Phrase("단가",font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell3 =new PdfPCell(new Phrase("수량",font));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4 =new PdfPCell(new Phrase("금액",font));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			table.addCell(cell1);  //테이블에 셀을 붙인다.
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			
			List<CartDTO> items =cartService.listCart("hookcu329");  //장바구니 목록 가져오기
			for(int i=0; i<items.size(); i++) {
				CartDTO dto =items.get(i);
				PdfPCell cellProductName=new PdfPCell(new Phrase(dto.getProduct_name(),font));
				PdfPCell cellPrice=new PdfPCell(new Phrase(""+dto.getPrice(),font));  //Phrase는 숫자가 오면안된다 그래서 빈문자열을 붙여서 정수를 문자열로 바꿔준것이다.
				PdfPCell cellAmount=new PdfPCell(new Phrase(""+dto.getAmount(),font));
				PdfPCell cellMoney=new PdfPCell(new Phrase(""+dto.getMoney(),font));
				table.addCell(cellProductName); //테이블에 셀붙이기
				table.addCell(cellPrice);
				table.addCell(cellAmount);
				table.addCell(cellMoney);
			
			}
			document.add(table); //문서에 테이블 붙이기
			document.close(); //문서 닫기
			result ="pdf 파일이 생성되었습니다.";
		
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			result="pdf파일 생성 실패...";
		}
		return result;
	}

}
