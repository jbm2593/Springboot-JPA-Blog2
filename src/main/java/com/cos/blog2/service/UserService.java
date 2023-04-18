package com.cos.blog2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	//회원찾기 (카카오 회원가입시 가입자와 비가입자 분기 처리 사용)
	@Transactional(readOnly = true)
	public User userFind(String username) {
		User user =  userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
	
	//회원가입
	@Transactional
	public void userJoin(User user) {
			String rawPassword = user.getPassword(); //원문
			String encPassword = encoder.encode(rawPassword); //해쉬
			user.setPassword(encPassword);
		    user.setRole(RoleType.USER);
			userRepository.save(user);
	}
	
	//회원수정
	@Transactional
	public void userEdit(User user) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
		User porsistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		// Validate 체크 (ouath 필드에 값이 없으면 수정 가능)
		// kakao로 회원가입 되어있지 않은 사람만 패스워드, 이메일 수정 가능.
		if(porsistance.getOauth() == null || porsistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			porsistance.setPassword(encPassword);
			porsistance.setEmail(user.getEmail());
		}

		//회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 된다.
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 변화된 것들을 update문을 자동으로 날려줌.
}
	
	
	
	
//스프링 시큐리티 로그인 하려고 주석처리
//	@Transactional(readOnly = true) //Select 할 떄 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성)
//	public User 로그인(User user) {
//			return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
//	}
}
