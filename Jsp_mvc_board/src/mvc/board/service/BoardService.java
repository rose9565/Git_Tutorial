package mvc.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BoardService {
	
	//글목록
	public void boardList(HttpServletRequest req, HttpServletResponse res);
	
	//글 상세 페이지
	public void contentForm(HttpServletRequest req, HttpServletResponse res);
	
	//글 수정 상세페이지
	public void modifyView(HttpServletRequest req, HttpServletResponse res);
	
	//글 수정 처리 페이지
	public void modifyPro(HttpServletRequest req, HttpServletResponse res);
	
	//글쓰기 화면페이지
	public void writeForm(HttpServletRequest req, HttpServletResponse res);
	
	//글쓰기 처리페이지
	public void writePro(HttpServletRequest req, HttpServletResponse res);
	
	//글쓰기 삭제페이지
	public void deletePro(HttpServletRequest req, HttpServletResponse res);
	
	
}
