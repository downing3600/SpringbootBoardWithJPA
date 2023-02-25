package com.springboot.ahuboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ahuboard.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

}
