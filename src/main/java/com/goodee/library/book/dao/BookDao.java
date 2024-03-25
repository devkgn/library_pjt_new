package com.goodee.library.book.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goodee.library.book.dto.BookDto;

@Repository
public class BookDao {
	
	private static final Logger LOGGER = LogManager.getLogger(BookDao.class);
	private final String namespace = "com.goodee.library.bookMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public int createBook(BookDto dto) {
		LOGGER.info("도서 정보 데이터베이스 추가");
		int result = 0;
		try {
			result = sqlSession.insert(namespace+"createBook",dto);
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LOGGER.error(errors.toString());
		}
		return result;
	}
	
	public int selectBookCount(String b_name) {
		LOGGER.info("도서 전체 갯수 조회");
		int result = 0;
		try {
			result = sqlSession.selectOne(namespace+"selectBookCount",b_name);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<BookDto> selectBookList(BookDto dto){
		LOGGER.info("도서 목록 조회");
		List<BookDto> resultList = new ArrayList<BookDto>();
		try {
			resultList = sqlSession.selectList(namespace+"selectBookList",dto);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultList;	
	}

	public List<BookDto> selectBookListToday(){
		LOGGER.info("오늘 등록된 도서 목록 조회");
		List<BookDto> resultList = new ArrayList<BookDto>();
		try {
			resultList = sqlSession.selectList(namespace+"selectBookListToday");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	public BookDto selectBookDetail(long b_no) {
		LOGGER.info("단일 도서 정보 조회");
		BookDto result = new BookDto();
		try {
			result = sqlSession.selectOne(namespace+"selectBookDetail",b_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateBookDetail(BookDto dto) {
		LOGGER.info("단일 도서 정보 수정");
		int result = 0; 
		try {
			result = sqlSession.update(namespace+"updateBookDetail",dto);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteBookOne(long b_no) {
		LOGGER.info("도서 정보 삭제");
		int result = 0; 
		try {
			result = sqlSession.delete(namespace+"deleteBookOne",b_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
