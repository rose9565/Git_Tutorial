<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>

<html>
<body>
	<h2 align="center">게시판</h2>
	<table style="width:1000px;  margin:0 auto;">
		<tr>
			<th colspan="6" align="left" style="height:25px">
				&nbsp;&nbsp;글목록(글갯수 : ${cnt}) &nbsp;&nbsp;&nbsp;
				<a href="writeForm.bo?pageNum=${pageNum}">글쓰기</a>
			</th>
		</tr>
		<tr align="center">
			<th style="width:15%">글번호(ref/r_step/r_level)</th>
			<th style="width:25%">글제목</th>
			<th style="width:10%">작성자</th>
			<th style="width:15%">작성일</th>
			<th style="width:5%">조회수</th>
			<th style="width:5%">IP</th>
		</tr>

		<!-- 게시글이 있으면 -->
		<c:if test="${cnt > 0}">

			<%-- 
				item="${dtos}" : ArrayList(BoardVO) : 게시글 목록
				var="dto" : 작은바구니 : 게시글 1건
				=> 게시글목록에 5건이 있다면 5회반복 (ArrayList에서 꺼내서 dto에 담은 다음 출력)
				=> {dto.멤버변수} / {dto.멤버메소드}
			--%>		

			<c:forEach var="dto" items="${dtos}">
				<tr>
					<td align="center">
						${number}
						<c:set var="number" value="${number-1}" />
						(${dto.ref}/${dto.ref_step}/${dto.ref_level})
					</td>
					<td>
						<!-- 답글인 경우 : 들여쓰기 > 1 -->	
						<c:if test="${dto.ref_level > 1}">
							<c:set var="wid" value="${(dto.ref_level - 1)*10}"/>
							<img src="${path}images/level.gif" border="0" width="${wid}" height="15">
						</c:if>

						
						<!-- 답글인 경우 : 들여쓰기 > 0 -->
						<c:if test="${dto.ref_level > 0}">
							<img src="${path}images/re.gif" border="0" width="20" height="15">
						</c:if>

			
						<a href="contentForm.bo?num=${dto.num}&pageNum=${pageNum}&number=${number+1}"> ${dto.getSubject()} </a>

						

						<!-- hot 이미지  -->

						<c:if test="${dto.readCnt > 10}">
							<img src="${path}images/hot.gif" border="0" width="20" height="15">
						</c:if>	
					</td>
					<td align="center">${dto.getWriter()}</td>
					<td align="center">
						<fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm" value="${dto.getReg_date()}" />

					</td>
					<td align="center">${dto.getReadCnt()}</td>
					<td align="center">${dto.getIp()}</td>
				</tr>
			</c:forEach>
		</c:if>

		

		<!-- 게시글이 없으면 -->

		<c:if test="${cnt == 0}">
			<tr>
				<td colspan="6" align="center">
					게시글이 없습니다.
				</td>
			</tr>
		</c:if>
	</table>

	

	<!-- 페이지 컨트롤  -->

	<table style="width:1000px; margin:0 auto;">
		<tr>
			<th align="center">

				<!-- 게시글이 있으면  -->

				<c:if test="${cnt > 0}">

				

					<!-- 처음[◀◀] / 이전블록[◀] -->

					<c:if test="${startPage > pageBlock}">

						<a href="boardList.bo">[◀◀]</a>

						<a href="boardList.bo?pageNum=${startPage - pageBlock}">[◀]</a>

					</c:if>

					

					<!-- 블록내의 페이지 번호 -->

					<c:forEach var="i" begin="${startPage}" end="${endPage}">

						<c:if test="${i == currentPage}">

							<span><b>[${i}]</b></span>

						</c:if>

						<c:if test="${i != currentPage}">

							<a href="boardList.bo?pageNum=${i}">[${i}]</a>

						</c:if>

					</c:forEach>

					

					<!-- 다음블록[▶] / 마지막[▶▶]  -->

					<c:if test="${pageCount > endPage}">

						<a href="boardList.bo?pageNum=${startPage + pageBlock}">[▶]</a>

						<a href="boardList.bo?pageNum=${pageCount}">[▶▶]</a>

					</c:if>

					

				</c:if>

		</tr>

	</table>

 

</body>

</html>