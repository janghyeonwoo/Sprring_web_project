package com.example.spring02.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.BoardDTO;
import com.example.spring02.service.board.BoardService;
import com.example.spring02.service.board.Pager;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Inject
	BoardService boardService;
	
	//댓글이 달려있는경우 등 추가적인 설정필요 예를들면 삭제못하게하던가
	@RequestMapping("delete.do") 
	public String delete(int bno) throws Exception{
		boardService.delete(bno);
		return "redirect:/board/list.do";
	}
	
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute BoardDTO dto, HttpSession session) throws Exception{
		//로그인한 사용자의 아이디 
		String writer = (String)session.getAttribute("userid");
		dto.setWriter(writer);
		//레코드가 저장됨
		boardService.create(dto);
		//목록갱신
		return "redirect:/board/list.do";
	}
	
	@RequestMapping("write.do")
	public String write() {
		return "board/write";
	}
	
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam(defaultValue="1") int curPage,
			@RequestParam(defaultValue="all") String search_option,
			@RequestParam(defaultValue="") String keyword) throws Exception{
		int count=boardService.countArticle(search_option, keyword); //레코드 개수
		
		Pager pager =new Pager(count,curPage);
		int start = pager.getPageBegin();
		int end =pager.getPageEnd();
		System.out.println("aa"+ search_option);
		
		List<BoardDTO> list = boardService.listAll(start,end,search_option,keyword); //목록
		
		
		ModelAndView mav =new ModelAndView();
		mav.setViewName("board/list"); //이동할 페이지 지정
		Map<String,Object> map = new HashMap<>();
		map.put("list",list); //맵에 자료 저장
		map.put("count",count);
		map.put("search_option",search_option);
		map.put("keyword",keyword);
		map.put("pager",pager);
		mav.addObject("map",map); //데이터 저장
		return mav ; //페이지 이동(출력)
		
	}
	
	@RequestMapping(value="view.do" ,method=RequestMethod.GET)
	public ModelAndView view(@RequestParam int bno, @RequestParam int curPage , @RequestParam String search_option, @RequestParam String keyword ,HttpSession session)throws Exception{
		boardService.increaseViewcnt(bno);  //조회수 증가 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/view");
		mav.addObject("dto", boardService.read(bno));
		mav.addObject("curPage", curPage);
		mav.addObject("search_option", search_option);
		mav.addObject("keyword", keyword );
		return mav;
		
	}
	
	@RequestMapping("update.do")
	public String update(BoardDTO dto) throws Exception{
		boardService.update(dto);
		return "redirect:/board/list.do";
	}
	@RequestMapping("getAttach/{bno}")
	@ResponseBody  //화면으로 넘어가는 것이 아닌 데이터가 넘어간다
	public List<String> getAttach(@PathVariable("bno") int bno){
		return boardService.getAttach(bno);
	}
}
