package com.cos.blog2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog2.model.Board;

//DAO
//자동으로 bean 등록이 된다.
@Repository //생략 가능
public interface BoardRepository extends JpaRepository<Board, Integer>{
	/* 글 검색기능 추가
	 *  findBy(컬럼이름) : 컬럼에서 키워드를 넣어서 찾겠다.
	 *  findBy(컬럼이름)Containing : 컬럼에서 키워드가 포함된 것을 찾겠다.
	 */
	Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
	
	@Modifying
    @Query("update Board p set p.count = p.count + 1 where p.id = :id")
    int updateCount(int id);
}


