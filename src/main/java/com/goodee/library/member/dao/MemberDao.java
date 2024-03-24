package com.goodee.library.member.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.goodee.library.member.dto.MemberDto;

@Repository
public class MemberDao {

	private static final Logger LOGGER = LogManager.getLogger(MemberDao.class);

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private final String namespace = "com.goodee.library.memberMapper.";
	
	public int idDoubleCheck(String m_id) {
		LOGGER.info("아이디 중복 검사");
		int result = 0; 
		try {
			result = sqlSession.selectOne(namespace+"idDoubleCheck",m_id);
		}catch(Exception e) {
			LOGGER.error("아이디 중복 검사시 에러 발생");
		}
		return result;
	}	

	public int createMember(MemberDto dto){
		LOGGER.info("회원정보 데이터베이스 추가");
		int result = 0; 
		try {
			dto.setM_pw(passwordEncoder.encode(dto.getM_pw()));
			result = sqlSession.insert(namespace+"createMember",dto);
		}catch(Exception e){
			LOGGER.error("회원정보 데이터베이스 추가시 에러 발생");
		}
		return result;
	}
	
	public MemberDto selectMemberOne(MemberDto dto) {
		LOGGER.info("아이디 기준 멤버 1명 조회");
		MemberDto loginedMember = new MemberDto();
		try {
			loginedMember = sqlSession.selectOne(namespace+"selectMemberOne",dto.getM_id());
			if(loginedMember!= null && passwordEncoder.matches(dto.getM_pw(),loginedMember.getM_id())) {
				loginedMember = null;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return loginedMember;
	}
	
	public List<MemberDto> selectMemberAll(){
		LOGGER.info("회원 목록 조회");
		List<MemberDto> resultList = new ArrayList<MemberDto>();
		try {
			resultList = sqlSession.selectList(namespace+"selectMemberAll");
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LOGGER.error(errors.toString());
		}
		return resultList;
	}
	
	public int updateMember(MemberDto dto) {
		LOGGER.info("회원 정보 수정");
		int result = 0; 
		try {
			result = sqlSession.update(namespace+"updateMember",dto);			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public MemberDto selectMemberByNo(long m_no) {
		LOGGER.info("pk 기준으로 단일 회원 조회");
		MemberDto result = new MemberDto();
		try {
			result = sqlSession.selectOne(namespace+"selectMemberByNo",m_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}