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
  
  <c:if test="${updateCnt == 0 }">
    <script type="text/javascript">
       errorAlert(updateError);
    </script>
    </c:if>
    
    <c:if test="${updateCnt != 0 }">
    <script type="text/javascript">
       alert("글이 수정되었습니다.");
       window.location="boardList.bo?pageNum=${pageNum}";
    </script>
    </c:if>
    
    
</body>
</html>