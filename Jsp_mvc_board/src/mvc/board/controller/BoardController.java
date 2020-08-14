package mvc.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.board.service.BoardServiceImpl;


@WebServlet("*.bo")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
     
    public BoardController() {
        super();
        
    }

	//1단계 HTTP 요청 받음
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		action(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws 
	    ServletException, IOException {	
		doGet(req, res);
	}
	
	public void action(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		
		
		//2단계 요청분석
		//한글 안깨지게 처리
		req.setCharacterEncoding("UTF-8");
		
		String viewPage= "";
		String uri = req.getRequestURI(); //컨텍스트명(=플젝명) + 나머지 주소  => jsp_mvcBoard/boardList.bo
		String contextPath = req.getContextPath();  //컨텍스트명(=플젝명) => jsp_mvcBoard
		String url = uri.substring(contextPath.length());   //uri.substring(beginIndex)l = > boardList.bo
		
		BoardServiceImpl service = new BoardServiceImpl();
		//2단계. 요청분석
		
		//글목록
		if(url.equals("/boardList.bo") || url.equals("/*.bo")) {
		 System.out.println("url ==> /boardList.bo");
		 
		 service.boardList(req, res);
		 System.out.println("url ==> /boardList.bo");
		 viewPage ="/board/boardList.jsp";
			
	    //게시글 상세페이지
		} else if(url.equals("/contentForm.bo")) {
			System.out.println("url ==> /contentForm.bo");
			
			service.contentForm(req, res);
			
			viewPage ="/board/contentForm.jsp";
		
		//게시글 수정 비밀번호 인증 페이지
		} else if(url.equals("/modifyForm.bo")) {
			System.out.println("url ==> /modifyForm.bo");
		
			int num =Integer.parseInt (req.getParameter("num"));
		    int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		      
		    req.setAttribute("num", num);
		    req.setAttribute("pageNum", pageNum);
				
			
			viewPage ="/board/modifyForm.jsp";	
			
		//글 수정 상세페이지	
		} else if(url.equals("/modifyView.bo")) {
			System.out.println("url ==> /modifyView.bo");
			
			service.modifyView(req, res);
			
		  viewPage ="/board/modifyView.jsp";	
		  
	   //글 수정 처리 페이지
		
		} else if(url.equals("/modifyPro.bo")) {
			System.out.println("url ==> /modifyPro.bo");
		    service.modifyPro(req, res);
			viewPage = "/board/modifyPro.jsp";	
			
		}  //글 쓰기 화면	
		else if(url.equals("/writeForm.bo")) {
			System.out.println("url ==> /writeForm.bo");
			service.writeForm(req, res);
			
			viewPage = "/board/writeForm.jsp";	
			
		//글쓰기 처리 페이지	
	   } else if(url.equals("/writePro.bo")) {
			System.out.println("url ==> /writePro.bo");
			service.writePro(req, res);
			
			viewPage = "/board/writePro.jsp";
	   
	   }
		RequestDispatcher dispathcer = req.getRequestDispatcher(viewPage);
		dispathcer.forward(req, res);
		
		
	}
}
	
