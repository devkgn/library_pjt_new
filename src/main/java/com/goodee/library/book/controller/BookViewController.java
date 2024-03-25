package com.goodee.library.book.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;

@Controller
public class BookViewController {

	private static final Logger LOGGER = LogManager.getLogger(BookViewController.class);
	
	@Autowired
	BookService service;
	
	@GetMapping("/book")
	public String bookList(BookDto dto, Model model) {
		LOGGER.info("도서 목록 화면 이동");
		dto.setTotalData(service.selectBookCount(dto.getB_name()));
		List<BookDto> resultList = service.selectBookList(dto);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paging",dto);
		return "book/list";
	}
	
	@GetMapping("/book/add")
	public String createBookForm() {
		LOGGER.info("도서 등록 화면 이동");
		return "book/add";
	}
	
	@GetMapping("/book/{b_no}")
	public String bookDetail(@PathVariable("b_no") long b_no, Model model) {
		LOGGER.info("도서 상세 화면 이동");
		BookDto dto = service.selectBookDetail(b_no);
		model.addAttribute("bookDto",dto);
		return "book/edit";
	}


}