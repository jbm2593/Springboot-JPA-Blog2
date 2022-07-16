package com.cos.blog2.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.blog2.model.User;
import com.cos.blog2.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을 해줌. IOC를 해준다. (메모리 대신에 띄어준다?)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void 회원가입(User user) {
			userRepository.save(user);
	}
}
