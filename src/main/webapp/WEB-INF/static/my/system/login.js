function checkFields(loginForm){
	email = $("#userName").val();
	var emailExp = /^[\w\-\.\+]+\@(emc|dell)\.com$/;
	if (email.match(emailExp)){
       return true;
   }else{
	   $("#messageInfo").css('visibility','visible').fadeIn().removeClass('hidden');
	   $("#error").text("无效的Dell EMC邮箱");
	   return false;
   }
	
}