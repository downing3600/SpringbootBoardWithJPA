package com.springboot.ahuboard.service;

import java.util.List;

import com.springboot.ahuboard.entity.Board;

public interface BoardService {
    Board write (Board board, List<Long> images);

}
