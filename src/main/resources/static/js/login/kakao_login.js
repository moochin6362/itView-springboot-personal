
document.addEventListener('DOMContentLoaded', function() {
	    if (!Kakao.isInitialized()) {
	        Kakao.init("36c4525b1f76f6d071d405f80d50e8fa");
	    }
	
	    Kakao.Auth.createLoginButton({
	        container: '#kakao-login-btn',
	        success: function(authObj) { console.log(authObj); },
	        fail: function(err) { console.error(err); }
	    });
	});
	

