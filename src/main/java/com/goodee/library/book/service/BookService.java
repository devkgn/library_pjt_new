package com.goodee.library.book.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.library.book.dao.BookDao;
import com.goodee.library.book.dto.BookDto;

@Service
public class BookService {

	private static final Logger LOGGER = LogManager.getLogger(BookService.class);
	
	@Autowired
	BookDao dao;
	
	public Map<String,String> createBook(BookDto dto) {
		LOGGER.info("도서 정보 등록 요청");
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 정보 등록이 실패했습니다.");
		if(dao.createBook(dto) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "도서 정보가 등록되었습니다.");
		}
		return map;
	}
	
	public int selectBookCount(String b_name){
		int result = 0 ; 
		result = dao.selectBookCount(b_name);
		return result;
	}
	
	public List<BookDto> selectBookList(BookDto dto){
		return dao.selectBookList(dto);
	}
	
	public List<BookDto> selectBookListToday(){
		return dao.selectBookListToday();
	}
}
