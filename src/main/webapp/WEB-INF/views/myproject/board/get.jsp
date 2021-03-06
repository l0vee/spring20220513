<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css"
	integrity="sha512-GQGU0fMMi238uA+a/bdWJfpUGKUkBdgfFdgBm72SUQ6BeyWjoY/ton0tEjH+OSH9iP4Dfh+7HM0I9f5eR0L/4w=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
	referrerpolicy="no-referrer"></script>

<script>
	$(document).ready(function() {
		$("#edit-button1").click(function() {
			$("#input1").removeAttr("readonly");
			$("#textarea1").removeAttr("readonly");
			$("#modify-submit1").removeClass("d-none");
			$("#delete-submit1").removeClass("d-none");
		});

		$("#delete-submit1").click(function(e) {
			e.preventDefault();

			if (confirm("삭제하시겠습니까?")) {
				let form1 = $("#form1");
				let actionAttr = "${appRoot}/board/remove";
				form1.attr("action", actionAttr);

				form1.submit();
			}

		});

		// reply-edit-toggle 버튼 클릭시 댓글 보여주는 div 숨기고,
		// 수정 form 보여주기
		$(".reply-edit-toggle-button").click(function() {
			console.log("버튼클릭");
			const replyId = $(this).attr("data-reply-id");
			const displayDivId = "#replyDisplayContainer" + replyId;
			const editFormId = "#replyEditFormContainer" + replyId;

			console.log(replyId);
			console.log(displayDivId);
			console.log(editFormId);

			$(displayDivId).hide();
			$(editFormId).show();
		});

		// reply-delete-button 클릭시
		$(".reply-delete-button").click(function() {
			const replyId = $(this).attr("data-reply-id");
			const message = "댓글을 삭제하시겠습니까?";

			if (confirm(message)) {
				$("#replyDeleteInput1").val(replyId);
				$("#replyDeleteForm1").submit();
			}
		});
	});
</script>




<title>Insert title here</title>
</head>
<body>
	<my:navBar></my:navBar>
	<!-- .container>.row>.col>h1{글 본문} -->
	<div class="container">
		<div class="row">
			<div class="col">

				<h3>${board.id }번
					<i class="fa-solid fa-book"></i>
				</h3>

				<h1>
					글 본문
					<button id="edit-button1" class="btn btn-secondary">
						<i class="fa-solid fa-pen-to-square"></i>
					</button>
				</h1>


				<form action="${appRoot }/myproject/board/modify" method="post">
					<input type="hidden" name="id" value="${board.id }" />

					<div>
						<label class="form-label" for="input1">제목</label> <input
							class="form-control" type="text" name="title" required
							id="input1" value="${board.title }" readonly />
					</div>

					<div>
						<label class="form-label" for="textarea1">본문</label>
						<textarea class="form-control" name="body" id="textarea1"
							cols="100" rows="10" readonly>${board.body }</textarea>
					</div>

					<div>
						<label for="input2" class="form-label">작성일시</label> <input
							class="form-control" type="datetime-local"
							value="${board.inserted }" readonly />
					</div>


					<button id="modify-submit1" class="btn btn-primary d-none">수정</button>
				</form>

				<c:url value="/myproject/board/remove" var="removeLink" />
				<form action="${removeLink }" method="post">
					<input type="hidden" name="id" value="${board.id }" />
					<button id="delete-submit1" class="btn btn-danger d-none">삭제</button>
				</form>

				<hr />

				<h3>댓글 ${board.numOfReply } 개</h3>

				<c:url value="/myreply/reply/add" var="replyAddLink" />
				<form action="${replyAddLink }" method="post">
					<input type="hidden" name="boardId" value="${board.id }" /> 
					댓글 : <input type="text" name="content" size="50" />

					<button class="btn btn-outline-secondary">
						<i class="fa-solid fa-comment-dots"></i>
					</button>
				</form>

				<hr />

				<div>
					<c:forEach items="${replyList }" var="reply">

						<div style="border: 1px solid black; margin-bottom: 3px;">
							${reply.inserted } :

							<c:url value="/myreply/reply/modify" var="replyModifyLink" />
							<form action="${replyModifyLink }" method="post">
								<input type="hidden" value="${reply.id }" name="id" /> <input
									type="hidden" name="boardId" value="${board.id }" /> <input
									type="text" value="${reply.content }" name="content" />
								<button class="reply-edit-toggle-button"
									id="replyEditToggleButton${reply.id }"
									data-reply-id="${reply.id }">
									<i class="fa-solid fa-pen-to-square"></i>
								</button>
							</form>

							<c:url value="/myreply/reply/remove" var="replyRemoveLink" />
							<form action="${replyRemoveLink }" method="post">
								<input type="hidden" name="id" value="${reply.id }" /> <input
									type="hidden" name="boardId" value="${board.id }" />
								<button class="reply-delete-button" data-reply-id="${reply.id }">
									<i class="fa-solid fa-trash-can"></i>
								</button>
							</form>
						</div>

					</c:forEach>
				</div>
			</div>
		</div>
	</div>

</body>
</html>













