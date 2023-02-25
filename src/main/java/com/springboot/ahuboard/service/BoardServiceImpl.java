package com.springboot.ahuboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ahuboard.entity.Board;
import com.springboot.ahuboard.properties.BoardFileProperties;
import com.springboot.ahuboard.repository.BoardFileRepository;
import com.springboot.ahuboard.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardFileProperties boardFileProperties;
	
	@Autowired
	private BoardFileRepository boardFileRepository;
	

    @Autowired
    private BoardRepository boardRepository;
    @Override
    public Board write(Board board, List<Long> images) {

        //새글일 경우와 답글일 경우를 구분하여 처리(board.no가 존재 할 경우로 새글, 답글을 분기한다.)
        //-새글일 경우 - 등록후 grp를 번호와 동일하게 갱신
        //- 답글일 경우 - 원본글 정보를 이용하여 grp, seq, dep를 계산

        boolean isReply = board.getNo() != null;

        if(isReply) { // 답글일 경우 -grp, seq, dep 계산
            Board origin = boardRepository.findById(board.getNo()).orElseThrow();

            Long seq = boardRepository.getAvailableSeq(origin);
            if(seq == null) { //위치를 못찾은 경우 해당 그룹의 마지막에 추가해야 하므로 카운트를 구한다.
                seq = boardRepository.countByGrp(origin.getGrp());

            }
            else { // 찾은 경우 해당 위치 이상의 값들을 증가 처리
                boardRepository.increaseSequence(Board.builder().grp(origin.getGrp()).seq(seq).build());

            }

            //no, grp, seq, dep 변경
            board.setNo(null);//번호 초기화(시퀀스)
            board.setGrp(origin.getGrp());//그룹 유지
            board.setSeq(seq);//계산된 seq
            board.setDep(origin.getDep() + 1);// 차수 증가
        }

        Board result = boardRepository.save(board);

        if(!isReply){//새글일 경우 - grp 갱신
            result.setGrp(result.getNo());//no와 grp를 동일하게 처리
            boardRepository.save(result);

        }


        String resultStr = String.valueOf(result);

//        log.info("리설트 스트링 값 확인" + resultStr);
//
//		if(board != null) {
//			log.info(String.valueOf(board.getNo()));
//			log.info(String.valueOf(board.getTitle()));
//			log.info(String.valueOf(board.getContent()));
//			log.info(String.valueOf(board.getWriter()));
//		}

//		return "redirect:/list";
        return result;
    }
}
