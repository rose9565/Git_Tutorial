package mvc.board.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import mvc.board.persistance.BoardDAO;
import mvc.board.persistance.BoardDAOImpl;
import mvc.board.vo.BoardVO;

public class BoardServiceImpl implements BoardService {

	//게시글 목록
	@Override
	public void boardList(HttpServletRequest req, HttpServletResponse res) {
		
		//3단계. 화면으로부터 입력받은 값을 받아온다.
		
		//페이지
		int pageSize = 5;  //한페이지에 출력할 글 갯수
	    int pageBlock = 3; //한 블럭당 페이지 갯수
	    
	    int cnt = 0;   //글 갯수
	    int start = 0; //현재 페이지 시작 글번호
	    int end = 0;  //현재 페이지 마지막 글번호
	    int number = 0;  // 출력용 글번호 , 보여주는 용도
	    String pageNum = "";  //페이지 번호
	    int currentPage = 0;  //현재 페이지
	    
	    int pageCount = 0;    //페이지 갯수
	    int startPage = 0;  //시작페이지
	    int endPage = 0;    //마지막페이지
		
		//4단계. 다형성 적용. 싱글톤 방식으로 dao 객체 생성
	    BoardDAO dao = BoardDAOImpl.getInstance();	
	    
		//5-1단계. 글 갯수 구하기
	    cnt = dao.getArticleCnt();
	    System.out.println("cnt => "+ cnt);
	    
	    pageNum = req.getParameter("pageNum");  //페이지번호가 1~6까지  , page선언만 한 것
	    
	    if(pageNum == null) {
	    	pageNum = "1";  //첫 페이지를 1페이지로 지정
	    }
	    //글 30건 기준
	    currentPage = Integer.parseInt(pageNum);   //현재 페이지 1
	    System.out.println("currentPage:" + currentPage);
	    
	    //페이지 갯수 6 =(30 / 5 ) + (0)
	    pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0); //페이지 갯수 + 나머지 있으면 1 나누고 남는게 있으면 1 아니면 0
	    
	    //현재 페이지 시작 글번호(페이지별)
	    //1 = (1-1) *5 + 1
	    start = (currentPage - 1) * pageSize + 1;
	    //5 = 1 + 5 -1
	    //현재 페이지 마지막 글번호(페이지별)
	    end = start + pageSize - 1;
	    
	    //출력용 글번호
	    //30 = 30 - (1 - 1)*5;
	    number = cnt - (currentPage - 1) * pageSize;
	    
	    System.out.println("start:" + start);
	    System.out.println("end:" + end);
	    
		
		//5-2단계. 게시글 목록 조회
	    if(cnt>0) {   //글 갯수가 1개라도 있을경우 전정국 30
	    ArrayList<BoardVO> dtos	= dao.getArticleList(start, end);
	    req.setAttribute("dtos", dtos);
	    }
		
		//6단계  request나 session에 차리결과를 저장( jsp에 전달하기 위함)
	   
	    //시작페이지
	    //1 = (1 / 3) * 3 + 1;
	    startPage = (currentPage / pageBlock) * pageBlock + 1;
	    if(currentPage % pageBlock == 0) startPage -= pageBlock;
	       
	     System.out.println("startPage:" + startPage);
	     //마지막페이지
	     endPage = startPage + pageBlock - 1;
	     if(endPage > pageCount) endPage = pageCount;
	       
	     System.out.println("endPage" + endPage);
	     System.out.println("==================");
	     
	     req.setAttribute("cnt", cnt);  //글 갯수
	     req.setAttribute("number", number);  //출력용 글번호
	     req.setAttribute("pageNum", pageNum);  //페이지 번호
	     
		 if(cnt > 0) { ///글 갯수
	    	 req.setAttribute("startPage", startPage);  //시작페이지
	    	 req.setAttribute("endPage", endPage);  //마지막페이지
	    	 req.setAttribute("pageBlock", pageBlock); //한블럭당 페이지갯수
	    	 req.setAttribute("pageCount", pageCount);  //페이지 갯수
	    	 req.setAttribute("currentPage", currentPage);  //현재 페이지    //위에서 변수 선언문
	    	 
		 } 
		
	}
    
	//글 상세 페이지
	@Override
	public void contentForm(HttpServletRequest req, HttpServletResponse res) {
	
	// 3단계 화면으로 입력받은 값(get방식)
	//contentForm.bo?num=30&pageNum=1&number=30		
    int num = Integer.parseInt(req.getParameter("num"));  //키비교할때 쓴것임 , vo에 담겨있어서 꺼내면된다. 
    int pageNum = Integer.parseInt(req.getParameter("pageNum"));  //1,2,3 
    int number  = Integer.parseInt(req.getParameter("number"));  //게시글 번호(출력용)
    
    //4단계
    BoardDAO dao = BoardDAOImpl.getInstance();
    //5-1단계. 조회수 중가
    dao.addReadCnt(num);
   
    //5-2단계 . 상세 페이지 조회
    BoardVO vo = dao.getArticle(num); // 위에서 전달받은 num
    
    //6단계 값을 jsp에 넘겨라 
     req.setAttribute("pageNum", pageNum);
     req.setAttribute("number", number);	
	}

	//글 수정 상세페이지
	@Override
	public void modifyView(HttpServletRequest req, HttpServletResponse res) {
		
		// 3단계 화면으로 입력받은 값(hidden값, input값)
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		String strPwd = req.getParameter("pwd");
				
		//객체생성
	    BoardDAO dao = BoardDAOImpl.getInstance();
		
		//5-1.비밀번호 인증
	    int selectCnt = dao.numPwdCheck(num, strPwd);
	    System.out.println("selectCnt :" + selectCnt);
		
		//5-2.비밀번호 일치하면, 해당글 1건을 읽어서 vo를 읽어서 BoardVO 바구니에 담아라. 
	    BoardVO vo =null;
	    if(selectCnt == 1) {
	    	  vo =dao.getArticle(num);
	    }
	    
		//6.jsp전달
	    req.setAttribute("selectCnt", selectCnt);  
	    req.setAttribute("num", num);  
	    req.setAttribute("pageNum", pageNum);
	    req.setAttribute("dto", vo);  
	    		
	}

	@Override
	public void modifyPro(HttpServletRequest req, HttpServletResponse res) {
		
		int updateCnt = 0;
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		BoardVO vo = new BoardVO();
		// 3단계 화면으로 입력받은 값
	    vo.setNum(Integer.parseInt(req.getParameter("num")));
		vo.setSubject(req.getParameter("subject"));
		vo.setContent(req.getParameter("content"));
		
		
		//객체생성
	    BoardDAO dao = BoardDAOImpl.getInstance();
		
	    updateCnt = dao.updateBoard(vo);
	    
	    req.setAttribute("updateCnt", updateCnt);
	    req.setAttribute("pageNum", pageNum);
	
	    		
		
	}
    //글쓰기 화면 페이지
	@Override
	public void writeForm(HttpServletRequest req, HttpServletResponse res) {//화면에 뿌리기 전에 실행
		
		// 3단계 화면으로 입력받은 값을 받아온다. 
		//신규 제목글(답변글이 아닐경우)
		 int num=0;
		 int pageNum=0;
		 int ref=0;
		 int ref_step=0;
		 int ref_level=0;
		
		 //pageNum은  답변글 작성시
		 if(req.getParameter("num") != null) {
			 num = Integer.parseInt(req.getParameter("num"));	
			 ref = Integer.parseInt(req.getParameter("ref"));
			 System.out.println("=============="+ref);
			 ref_step = Integer.parseInt(req.getParameter("ref_step"));
			 System.out.println("=============="+ref_step);
			 ref_level = Integer.parseInt(req.getParameter("ref_level"));
			 System.out.println("=============="+ref_level);
		 }
		 pageNum = Integer.parseInt(req.getParameter("pageNum"));  //페이지 번호는 공통으로 탄다. 
		
		//6단계 
		 req.setAttribute("num", num);
		 req.setAttribute("pageNum", pageNum);
		 req.setAttribute("ref", ref);
		 req.setAttribute("ref_step", ref_step);
		 req.setAttribute("ref_level", ref_level);
		
	}

	@Override
	public void writePro(HttpServletRequest req, HttpServletResponse res) {
		
	  //바구니 생성
	  BoardVO vo = new BoardVO();
	  int insertCnt =0;
	  //3-1. writeForm 화면으로 부터 입력받은 값 hidden값 받아와서 바구니에 담는다.  
	    int  num = Integer.parseInt(req.getParameter("num"));	
		int  ref = Integer.parseInt(req.getParameter("ref"));
		int  ref_step = Integer.parseInt(req.getParameter("ref_step"));
		int  ref_level = Integer.parseInt(req.getParameter("ref_level"));
		int  pageNum = Integer.parseInt(req.getParameter("pageNum")); 
		
		vo.setNum(num);
		vo.setRef(ref);
		vo.setRef_step(ref_step);
		vo.setRef_level(ref_level);
		
	  //3-2  input 값 받아와서 바구니에 담는다. 
		
	  String writer = req.getParameter("writer");
	  String pwd = req.getParameter("pwd");
	  String subject = req.getParameter("subject");
	  String content = req.getParameter("content");
	  
	  vo.setWriter(writer);
	  vo.setPwd(pwd);
	  vo.setSubject(subject);
	  vo.setContent(content);
	  
	  
	  //3-3단계
	  vo.setRef_date(new Timestamp(System.currentTimeMillis()));
	  vo.setIp(req.getRemoteAddr());
	  
	  //4.다형성 적용, 싱글톤
	  BoardDAO dao = BoardDAOImpl.getInstance();	
	
	  //5. 글쓰기 처리
	  insertCnt = dao.insertBoard(vo); 
	  System.out.println("insertCnt ==> " + insertCnt);
		
	  //6. 세션
	  req.setAttribute("insertCnt", insertCnt);	
	  req.setAttribute("pageNum", pageNum);	
	   
		
	}

}




