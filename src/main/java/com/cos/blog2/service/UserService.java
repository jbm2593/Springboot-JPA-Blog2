package com.cos.blog2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog2.model.RoleType;
import com.cos.blog2.model.User;
import com.cos.blog2.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을 해줌. IOC를 해준다. (메모리 대신에 띄어준다?)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired // DI가 되서 주입이 된다.
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
			String rawPassword = user.getPassword(); //원문
			String encPassword = encoder.encode(rawPassword); //해쉬
			user.setPassword(encPassword);
		    user.setRole(RoleType.USER);
			userRepository.save(user);
	}
	
//스프링 시큐리티 로그인 하려고 주석처리
//	@Transactional(readOnly = true) //Select 할 떄 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성)
//	public User 로그인(User user) {
//			return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
//	}
}
