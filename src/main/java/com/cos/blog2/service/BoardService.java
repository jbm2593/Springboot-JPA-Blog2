package com.cos.blog2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog2.model.Board;
import com.cos.blog2.model.User;
import com.cos.blog2.repository.BoardRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을 해줌. IOC를 해준다. (메모리 대신에 띄어준다?)
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	//글 작성 처리
	@Transactional
	public void 글쓰기(Board board, User user) { //title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	//게시글 리스트 처리
	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	//게시글 상세보기 처리
	@Transactional(readOnly = true)
	public Board boardDetail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	//게시글 조회수 추가
	@Transactional
    public int updateCount(int id) {
        return boardRepository.updateCount(id);
    }
	
	//게시글 검색 기능 처리
	public Page<Board> boardSearch(String searchKeyword, Pageable pageable) {
		return boardRepository.findByTitleContaining(searchKeyword, pageable);
	}
	
	//게시글 삭제 처리
	@Transactional
	public void boardDelete(int id) {
		 boardRepository.deleteById(id);	
	}
	
	//게시글 수정 처리
	@Transactional
	public void boardEdit(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시 (Service가 종료될 때 트랜잭션이 종료된다. 이때 더티체킹 - 자동 업데이트가 됨. db flush
	}
	
}
