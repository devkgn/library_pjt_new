package com.goodee.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;

@Controller
public class HomeController {
	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);
	
	@Autowired
	BookService bookService;
	
	@RequestMapping(value= {"","/"}, method = RequestMethod.GET)
	public String home(Model model) {
		LOGGER.info("도서관 관리 시스템 시작");
		List<BookDto> newBookList = new ArrayList<BookDto>();
		newBookList = bookService.selectBookListToday();
		model.addAttribute("newBookList",newBookList);
		return "home";
	}
}
