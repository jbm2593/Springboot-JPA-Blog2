package com.cos.blog2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.cos.blog2.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	// 댓글작성 네이티브 쿼리 추가
	@Modifying
	@	Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(int userId, int boardId, String content); //업데이트된 행의 개수를 리턴해줌.
	

}
