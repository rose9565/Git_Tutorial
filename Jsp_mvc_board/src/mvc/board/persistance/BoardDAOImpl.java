package mvc.board.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import mvc.board.vo.BoardVO;

public class BoardDAOImpl implements BoardDAO {
	
	//싱글톤 방식으로 객체생성
     private static BoardDAOImpl instance = new BoardDAOImpl();
	
	 public static BoardDAOImpl getInstance() {
		if(instance == null) {
			instance = new BoardDAOImpl();
		}
		return instance;
	}
	 //커넥션 풀 객체보관
	 DataSource dataSource;
	 //생성자
	 //커넥션 풀 사용
	 private BoardDAOImpl() {
		 try {
			 Context context = new InitialContext();
			 dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
    
	//게시글 갯수 구하기
	public int getArticleCnt() {
		
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT COUNT(*) as cnt FROM mvc_board_tbl";			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			 selectCnt	=rs.getInt("cnt");  //글 갯수가 몇개인지 파악
			}
			
		} catch(SQLException e) {
			
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return selectCnt;
	}

	@Override
	public ArrayList<BoardVO> getArticleList(int start, int end) {
		
		//1. 큰 바구니 선언
		
		ArrayList<BoardVO> dtos = null;
		
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();			
			String sql = "SELECT * " + 
					"FROM (SELECT num, writer, pwd, subject, content, readCnt, " + 
					              "ref, ref_step, ref_level, ref_date, ip, rowNum rNum " + 
					   "FROM (" +
					          "SELECT * FROM mvc_board_tbl " +
					           "ORDER BY ref DESC, ref_step ASC" + 
					        ")" +
					     ")" + 
					   " WHERE rNum >= ? AND rNum <= ?";  //rNum은 최신글 순서대로 보여준 것 
					   	
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  start);
			pstmt.setInt(2,  end);
			

			rs = pstmt.executeQuery();
			
			//결과가 존재하면
			if(rs.next()) {						
			//2. 큰바구니 생성     
			dtos = new ArrayList<BoardVO>();
			
				do {
				//3. 작은 바구니 생성
					BoardVO vo = new BoardVO();
							
						
					//4. 게시글 1건을 읽어서 rs 결과를 작은 바구니에 담아라.
					vo.setNum(rs.getInt("num"));
					vo.setWriter(rs.getString("writer"));
					vo.setPwd(rs.getString("pwd"));
					vo.setSubject(rs.getString("subject"));
					vo.setContent(rs.getString("content"));
					vo.setReadCnt(rs.getInt("readCnt"));
					vo.setRef(rs.getInt("ref"));
					vo.setRef_step(rs.getInt("ref_step"));
					vo.setRef_level(rs.getInt("ref_level"));
					vo.setRef_date(rs.getTimestamp("ref_date"));
					vo.setIp(rs.getString("ip"));
					
					//5. 큰바구니에 작은 바구니(BoardVO, 게시글 1건씩)를 담아라
					   dtos.add(vo);
				   
				   
			    } while(rs.next());  //rs가 반복하는 경우 
			  
			
		}
						
		} catch(SQLException e) {
			 e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		return dtos;
	}
	
    //조회수 증가
	@Override
	public void addReadCnt(int num) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		
		try {
			conn = dataSource.getConnection();			
			String sql = "UPDATE mvc_board_tbl SET readCnt = readCnt +1  WHERE num=?";					   	
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  num);		
			
		    pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} 
		
		

	}
    //상세페이지 조회
	@Override
	public BoardVO getArticle(int num) {
		BoardVO vo =null;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();			
			String sql = "SELECT * FROM mvc_board_tbl  WHERE num=?";					   	
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  num);		
			
		    rs = pstmt.executeQuery();
		    
		    if(rs.next()) {
		    	//존재한다면 작은 바구니를 생성 
		    	vo = new BoardVO();
		    	//게시글 1을 읽어서 작은바구니에 컬럼별로 담는다.    	
		    	vo.setNum(rs.getInt("num"));
				vo.setWriter(rs.getString("writer"));
				vo.setPwd(rs.getString("pwd"));
				vo.setSubject(rs.getString("subject"));
				vo.setContent(rs.getString("content"));
				vo.setReadCnt(rs.getInt("readCnt"));
				vo.setRef(rs.getInt("ref"));
				vo.setRef_step(rs.getInt("ref_step"));
				vo.setRef_level(rs.getInt("ref_level"));
				vo.setRef_date(rs.getTimestamp("ref_date"));
				vo.setIp(rs.getString("ip"));
		    }		    		    			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} 
		return vo;
	}

	@Override
	public int numPwdCheck(int num, String strPwd) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM mvc_board_tbl WHERE num=? AND pwd=?";			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  num);
			pstmt.setString(2,  strPwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			 selectCnt =1;	  //글 갯수가 몇개인지 파악
			}
			
		} catch(SQLException e) {
			
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return selectCnt;
		
	}

	@Override
	public int updateBoard(BoardVO vo) {
		
		int updateCnt =0;
		Connection conn = null;
	    PreparedStatement pstmt =null;
	    
	    
	    try {
			conn = dataSource.getConnection();			
			String sql = "UPDATE mvc_board_tbl SET content=?, subject=?  WHERE num=?";					   	
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  vo.getContent());
			pstmt.setString(2,  vo.getSubject());
			pstmt.setInt(3,  vo.getNum());
			
		    updateCnt = pstmt.executeUpdate();		    	
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} 
			
		
		return updateCnt;
	}

	@Override
	public int insertBoard(BoardVO vo) {
		 int insertCnt = 0;
		 Connection conn = null;
		 PreparedStatement pstmt =null;
		 ResultSet rs = null;
		 String sql = null;
		 
		 try {
				conn = dataSource.getConnection();
				//답변글이 아닌경우(제목 글인 경우)
				int num = vo.getNum();
				int ref = vo.getRef();
				int ref_step = vo.getRef_step();
				int ref_level = vo.getRef_level();
				
				//답변글인 경우
				if(num == 0) {
					//최신글부터 가져온다.
					sql = "SELECT MAX(num) as maxNum FROM mvc_board_tbl";
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					//첫 글이 아닌 경우 : num과 ref는 동일
					if(rs.next()) {
						ref = rs.getInt("maxNum") + 1;
					} else {
					//첫 글인 경우
					    ref = 1;
					}
					ref_step = 0;
					ref_level = 0;  //답변글이 아니기 때문
				
				} else {
			           //답변글인 경우 
		        	   //삽입할 글 보다 아래쪽 글들이 한줄씩 밀려내려간다. 즉 ref_step(행)이 1씩 증가 ref_step을 update
				       sql ="UPDATE mvc_board_tbl SET ref_step=ref_step+1 WHERE ref=? AND ref_step > ?";
				       
				       pstmt = conn.prepareStatement(sql);
				       pstmt.setInt(1, ref);
				       pstmt.setInt(2, ref_step);
				       
				       pstmt.executeUpdate();
				       //현재 내 답변 글
				       ref_step++;
				       ref_level++;
				       
				}
				pstmt.close();
				
				sql = "INSERT INTO mvc_board_tbl(num, writer, pwd, subject, content, readCnt, ref, ref_step, ref_level, ref_date, ip) VALUES(board_seq.nextval,?,?,?,?,0,?,?,?,?,?)";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getWriter());
				pstmt.setString(2, vo.getPwd());
				pstmt.setString(3, vo.getSubject());
				pstmt.setString(4, vo.getContent());
				pstmt.setInt(5, ref);
				pstmt.setInt(6, ref_step);
				pstmt.setInt(7, ref_level);
				pstmt.setTimestamp(8, vo.getRef_date());
				pstmt.setString(9, vo.getIp());
				
				insertCnt = pstmt.executeUpdate();
				
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
					
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		  
		 
		return insertCnt;
	}
	
}	
		
	
    
	
	

			

			
		
	
