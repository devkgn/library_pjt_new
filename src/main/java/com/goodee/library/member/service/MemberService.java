package com.goodee.library.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.library.member.dao.MemberDao;
import com.goodee.library.member.dto.MemberDto;

@Service
public class MemberService {

	private static final Logger LOGGER = LogManager.getLogger(MemberService.class);

	@Autowired
	MemberDao dao;
	
	public Map<String,String> createMember(MemberDto dto) {
		LOGGER.info("회원가입 결과 처리");
		// 정상적일때는 200, 잘못되었을 경우 404
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생했습니다.");
		int result = 0;
		if(dao.idDoubleCheck(dto.getM_id()) > 0) {
			map.put("res_code", "409");
			map.put("res_msg", "중복된 아이디입니다.");
		} else {
			result = dao.createMember(dto);
			if(result > 0) {
				map.put("res_code", "200");
			}
		}
		return map;
	}
	
	public Map<String,String> loginMember(MemberDto dto, HttpSession session){
		LOGGER.info("로그인 결과 처리");
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생했습니다.");
		
		MemberDto loginedMember = dao.selectMemberOne(dto);
		
		if(loginedMember != null) {
			map.put("res_code", "200");
			map.put("res_msg", loginedMember.getM_name()+"님, 환영합니다.");
			
			session.setAttribute("loginedMember", loginedMember);
			session.setMaxInactiveInterval(60*30);
		}
		return map;
	}
	
	public List<MemberDto> selectMemberAll(){
		LOGGER.info("회원 목록 조회");
		List<MemberDto> resultList = new ArrayList<MemberDto>();
		resultList = dao.selectMemberAll();
		return resultList;
	}
	
	public Map<String,String> updateMember(MemberDto dto, HttpSession session){
		LOGGER.info("회원 정보 수정 요청");
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "회원 정보 수정중 오류가 발생했습니다.");
		if(dao.updateMember(dto) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "회원 정보가 수정되었습니다.");
			MemberDto loginedMemberDto = dao.selectMemberByNo(dto.getM_no());
			session.setAttribute("loginedMember", loginedMemberDto);
			session.setMaxInactiveInterval(60*30);
		}
		return map;
	}
	
	
}
