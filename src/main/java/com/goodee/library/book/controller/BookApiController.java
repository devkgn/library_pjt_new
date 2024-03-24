package com.goodee.library.book.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;
import com.goodee.library.book.util.UploadFileService;

@Controller
public class BookApiController {

	private static final Logger LOGGER = LogManager.getLogger(BookApiController.class);
	
	@Autowired
	BookService bookService;
	
	@Autowired
	UploadFileService uploadFileService;
	
	@ResponseBody
	@PostMapping("/book")
	public Map<String,String> createBook(BookDto dto, @RequestParam("file") MultipartFile file){
		LOGGER.info("도서 등록 기능");
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생했습니다.");
		String savedFileName = uploadFileService.upload(file);
		if(savedFileName != null) {
			dto.setB_thumbnail(savedFileName);
			map = bookService.createBook(dto);
		}else {
			map.put("res_msg", "파일 업로드가 실패하였습니다.");
		}
		return map;
	}	
}