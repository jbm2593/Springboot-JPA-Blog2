package com.cos.blog2.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Getter, Setter 둘다 포함해서 생성
//@	AllArgsConstructor //전체 생성자 생성
@NoArgsConstructor //빈 생성자 생성
public class Member {

		private  int id;
		private String username;
		private String password;
		private String email;
		
		@Builder
		public Member(int id, String username, String password, String email) {
			this.id = id;
			this.username = username;
			this.password = password;
			this.email = email;
		}
		
		
		
}
