package mvc.board.persistance;

import java.util.ArrayList;

import mvc.board.vo.BoardVO;

public interface BoardDAO {
	
	 //게시글 갯수  구하기
	 public int getArticleCnt();
	
	 //게시글 목록 조회
	 public ArrayList <BoardVO> getArticleList(int start, int end); //목록은 vo가 복수개 
	 
	 //조회수 증가
	 public void addReadCnt(int num);
	 
	 //상세페이지 조회, 수정 상세페이지
	 public BoardVO getArticle(int num);
	 
	//게시글 수정 - 비밀번호 인증
	public int numPwdCheck(int num, String strPwd);
	
	//게시글 수정 처리
	public int updateBoard(BoardVO vo);
	
	//게시글쓰기 처리
	public int insertBoard(BoardVO vo);
	
	
}
