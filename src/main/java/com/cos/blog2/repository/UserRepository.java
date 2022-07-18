package com.cos.blog2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog2.model.User;

//DAO
//자동으로 bean 등록이 된다.
@Repository //생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{
	//JPA Naming 전략
	
	// SELECT * FROM user WHERE username = 파라미터1  AND password = 파라미터2;
	User findByUsernameAndPassword(String username, String password);
	
	
	// UserRepository.login 을 하면 아래 쿼리가 실행되고 리턴은 User 객체로 해줌!
//	@Query(value="SELECT * FROM user WHERE username = ?1 AND password =?2", nativeQuery = true)
//	User login(String username, String password);
}
