package com.example.spring02.model.memo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring02.model.memo.dto.MemoDTO;


public interface MemoDAO {
	//servlet.context.xml에서 설정해서 사용 가능한것이다.
	//<mybatis-spring:scan base-package 이태그를 이용하였다.
	@Select("select * from memo order by idx desc")
	//memoDAO는 인터페이스여서 객체를 만들수없는데 사용할수 있는 이유는 어노테이션때문이다.
	//list()는 미완성이지만 sql문은 완성이 되어있따  그렇기 때문에 객체로 만들어진다.
	public List<MemoDTO>list();

	// @Param("memo")은 insert 문의  #{memo}에 들어간다
	@Insert("insert into memo(idx,writer,memo) select COALESCE(max(idx)+1,1),#{writer},#{memo} from memo") 
	public void insert(@Param("writer") String writer,@Param("memo") String memo);
	
	@Select("select * from memo where idx=#{idx}")
	public MemoDTO memo_view(@Param("idx") int idx);
	
	@Update("update memo set writer =#{writer}, memo=#{memo} where idx=#{idx}")
	public void memo_update(MemoDTO dto);
	
	@Delete("delete from memo where idx=#{idx}")
	public void memo_delete(@Param("idx") int idx );
}
