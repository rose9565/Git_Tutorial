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
  <h2 align="center">글쓰기 -  처리 페이지</h2>
  
  <!-- 글쓰기 실패 -->
  <c:if test="${insertCnt == 0 }">
    <script type="text/javascript">
       errorAlert(insertError);
    </script>
   </c:if>
   
    <c:if test="${insertCnt != 0 }">
    <script type="text/javascript">
       alert("글이 작성되었습니다.");
       window.location="boardList.bo?pageNum=${pageNum}";
    </script>
    </c:if>
</body>
</html>