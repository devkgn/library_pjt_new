package com.goodee.library.book.util;

import java.io.File;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	private static final Logger LOGGER = LogManager.getLogger(UploadFileService.class);

	public String upload(MultipartFile file) {
		LOGGER.info("파일 서버에 등록");
		boolean result = false;
		String fileOriName = file.getOriginalFilename();
		String fileExtension =
				fileOriName.substring(fileOriName.lastIndexOf("."),fileOriName.length());
		String uploadDir = "C:\\library\\upload\\";
		
		UUID uuid = UUID.randomUUID();
		String uniqueName = uuid.toString().replaceAll("-", "");
		
		File saveFile = new File(uploadDir+"\\"+uniqueName+fileExtension);
		
		if(!saveFile.exists())
			saveFile.mkdirs();
		
		try {
			file.transferTo(saveFile);
			result = true;
		} catch(Exception e) {
			LOGGER.error(e.toString());
		}
		
		if(result) {
			LOGGER.info("파일 업로드 성공!!");
			return uniqueName+fileExtension;
		} else {
			LOGGER.info("파일 업로드 실패ㅠㅠ");
		}
		return null;
	}
}
