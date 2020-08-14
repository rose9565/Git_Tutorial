<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
     <h2 align="center">글쓰기</h2>
     <form action="writePro.bo" method="post" name="writeForm">
       <input type="hidden" name="num"  value="${num}">
       <input type="hidden" name="pageNum"  value="${pageNum}">
       <input type="hidden" name="ref"  value="${ref}">
       <input type="hidden" name="ref_step"  value="${ref_step}">
       <input type="hidden" name="ref_level"  value="${ref_level}">   
       
     <table align="center" >
        <tr>
           <th>작성자</th>
           <td>
              <input class="input" type="text" name="writer" maxlength="20" 
                 placeholder="작성자를 입력하세요." autofocus required>
           </td>       
        </tr>
        
         <tr>
           <th>비밀번호</th>
           <td>
              <input class="input" type="password" name="pwd" maxlength="20" 
                 placeholder="비밀번호를 입력하세요."  required>
           </td>       
        </tr>
        
        
         <tr>
           <th>제목</th>
           <td>
              <input class="input" type="text" name="subject" maxlength="50" 
                 placeholder="제목을 입력하세요."  required>
           </td>       
        </tr>
        
        <tr>
           <th>내용</th>
           <td>
              <textarea class="input" rows="10" cols="40"  name="content"  style="width:270px"
                 placeholder="글내용을 입력하세요." word-break:break-all></textarea>
           </td>       
        </tr>
         
         <tr>
            <th colspan="2">
              <input class="button" type="submit" value="작성">
              <input class="button" type="reset" value="초기화">
              <input class="button" type="button" value="목록"
                    onclick="window.location='boardList.bo?pageNum=${pageNum}'">
              
            
            </th>
         
         </tr>
           
     </table>
    </form>
</body>
</html>