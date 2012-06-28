// haiyuchen
var ChildObj = {
		getPhotoList : function (userId, callback) {
			if (callback && typeof callback == 'function') {
				window.getPhotos = callback;
			}
			$.getScript(webContext + "/ImageServlet?cmd=query&userId=" + userId).error(function(){
				$("#list").html("服务器繁忙，请稍后重试！");
			});
		}
};