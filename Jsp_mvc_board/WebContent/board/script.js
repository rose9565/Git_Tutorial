/**
 *  validation check
 */
var pwdError = "비밀번호가 일치하지 않습니다. \n확인후 다시 시도하세요.!!";
var updateError = "글수정에 실패했습니다. \n확인후 다시 시도하세요.!!";
var deleteError = "글삭제에 실패했습니다. \n확인후 다시 시도하세요.!!";
var insertError = "글쓰기에 실패했습니다. \n확인후 다시 시도하세요.!!";

function errorAlert(msg) {
	alert(msg);
	window.history.back();
}