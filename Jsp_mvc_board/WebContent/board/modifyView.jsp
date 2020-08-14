<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h2><center>글 수정</center></h2>
  <c:if test="${selectCnt == 0}">
  <script type="text/javascript">
     errorAlert(pwdError)
     </script>
  </c:if>
  
  <c:if test="${selectCnt != 0}">
     <form action="modifyPro.bo"  method="post"  name="modifyForm">
      <input type="hidden" name="num" value="${num}">  
       <input type="hidden" name="pageNum" value="${pageNum}">
      <table align="center">
         <tr>
            <th colspan="2"> 수정할 정보를 입력하세요.!!</th>        
         </tr>
         
         <tr>
            <th style="width:150px">작성자</th> 
            <th style="width:150px">${dto.writer}</th>                   
         </tr>
         
         <tr>
            <th style="width:150px">글제목</th> 
            <td>
            <input class="input" type="text" name="subject" maxlength="50" value="${dto.subject}" style="width:270px"> 
            </td>                 
         </tr>
         
         <tr>
            <th style="width:150px">글내용</th> 
            <td>
            <textarea class="input"  rows="10" cols="40"  name="content"  word-break:break-all>
                  ${dto.content} </textarea>
            </td>                 
         </tr>
      
         <tr>
          <th colspan="2">
            <input class="input" type="submit" value="수정">
            <input class="input" type="reset" value="초기화">
            <input class="input" type="reset" value="목록보기"
                onclick="window.location='boardList.bo?pageNum=${pageNum}'">            
           </th> 
      </table>    
     </form>
  </c:if>
</body>
</html>