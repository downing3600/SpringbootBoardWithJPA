package com.springboot.ahuboard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.ahuboard.entity.Board;
import com.springboot.ahuboard.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

	List<BoardFile> findAllByBoard(Board board);

	//(-삭제 됨) 오래된 파일을 지우는 로직이
	//(-삭제 됨)조회를 먼저 하는것으로 변경 되었기 때문에 필요 없어짐
	//bf.board is null - 게시글 정보가 없는 파일(임시 파일)
	//bf.uploadtime < :time - 전달된 시간보다 이전인 파일(오래된 파일)
//	@Modifying
//	@Transactional
//	@Query("delete BoardFile bf where bf.board is null and bf.uploadTime <:time")
//	void autoClear(@Param("time") Date date);

	@Query("select bf from BoardFile bf where bf.board is null and bf.uploadTime <:time")
	List<BoardFile> getOldData(@Param(value = "time") Date time);

}
