package com.springboot.ahuboard.service;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


//jdbc 임포트 주의
import com.springboot.ahuboard.entity.Board;
import com.springboot.ahuboard.entity.BoardFile;
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
        
        //(+추가) images에 들어있는 이미지 번호의 정보에 게시글 정보(result)를 주입
        
        if(images != null && !images.isEmpty()) {
        	
        	for(long seq : images) {
        		BoardFile boardFile = boardFileRepository.findById(seq).orElseThrow(); //찾아서
        		boardFile.setBoard(result);//게시글 정보 추가하고
        		boardFileRepository.save(boardFile);//저장 (수정)
        	}
        
        	
        }


//        String resultStr = String.valueOf(result);

        return result;
    }
    
    //게시글 삭제 + 딸린 이미지 삭제
	@Override
	public void delete(Long no) {
		//게시글 정보 찾아와서
		Board board = boardRepository.findById(no).orElseThrow();
		//이미지 찾고(메소드 없으므로 JPA 작명정책에 맞춰서 생성)
		List<BoardFile> list = boardFileRepository.findAllByBoard(board);
		//이미지 먼저 지우고(실물 파일)
		File dir = boardFileProperties.getImagePath();
		for(BoardFile boardFile : list) {
			File target = new File(dir, String.valueOf(boardFile.getSeq()));
			target.delete();
		}
		//첨부 파일 db 삭제
		boardFileRepository.deleteAll(list);
		//게시글 삭제
		boardRepository.deleteById(no);
		
	}

	//자동으로 오래된 파일을 지우는 메소드
	@Override
//	@Scheduled(cron = "* * * * * *")// 테스트(1초 간격)
	@Scheduled(cron = "0 0 * * * *")// 라이브(1시간간격, 매시 정각)
	public void autoClearTempFile() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);//1일 전으로 설정
		List<BoardFile> list = boardFileRepository.getOldData(c.getTime()); //오래된 파일 조회
		File dir = boardFileProperties.getImagePath();
		for(BoardFile boardFile : list) {
			File target = new File(dir, String.valueOf(boardFile.getSeq()));
			target.delete();
			boardFileRepository.delete(boardFile);
			
		}
		
	}
	
	@Override
	public void edit(Board board, List<Long> images) {
		Board origin = boardRepository.findById(board.getNo()).orElseThrow();
		origin.setTitle(board.getTitle());
		origin.setWriter(board.getWriter());
		origin.setContent(board.getContent());
		//비밀번호 변경 추가
		origin.setPassword(board.getPassword());
		
		Board result = boardRepository.save(origin);
//		model.addAttribute("no",result.getNo());
//		attr.addAttribute("no", result.getNo());
//		attr.addAttribute("title", result.getTitle());
//		attr.addAttribute("writer", result.getWriter());
//		attr.addFlashAttribute("no", result.getNo());
		
//		return "redirect:detail?no="+result.getNo();
		//파일 확정
		if(images != null && !images.isEmpty()) {
			for(Long seq : images ) {
				BoardFile boardFile = boardFileRepository.findById(seq).orElseThrow();
				boardFile.setBoard(result);
				boardFileRepository.save(boardFile);
			}
		}
		
	}
}
