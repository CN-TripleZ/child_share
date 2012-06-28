// haiyuchen

var UploadImgObj = {
		
		getUploadImage : function () {
			return $j("#photoImg")[0];
		},
		// 相片选择后回调方法
		imgSelectedCallback : function() {
		}
};

var pictureSource,destinationType;

var PictureUpload = {
		init : function() {
			pictureSource=navigator.camera.PictureSourceType;  
        	destinationType=navigator.camera.DestinationType; 
		},
		capturePhoto : function() { 
			// navigator.camera.getPicture(PictureUpload.onPhotoURISuccess, PictureUpload.onFail, { quality: 50, destinationType: destinationType.DATA_URL });
			navigator.camera.getPicture(PictureUpload.onPhotoURISuccess, PictureUpload.onFail, { quality: 50, destinationType: destinationType.FILE_URI });
		},
		selectPic : function() {
			// navigator.camera.getPicture(PictureUpload.onPhotoURISuccess, PictureUpload.onFail, { quality: 50,  destinationType: destinationType.DATA_URL, sourceType: pictureSource.PHOTOLIBRARY });
			navigator.camera.getPicture(PictureUpload.onPhotoURISuccess, PictureUpload.onFail, { quality: 50,  destinationType: destinationType.FILE_URI, sourceType: pictureSource.PHOTOLIBRARY });
		},
		onPhotoURISuccess: function (imageURI) {  
			window.location.hash="#upload";
    		var photoDiv = $('#photoDiv');  
			$("#photoPath").val(imageURI);
			//$("#photoImg").attr('src', 'data:image/jpeg;base64,' + imageURI);
    		$("#photoImg").attr('src', imageURI);
			if (UploadImgObj.imgSelectedCallback) {
				UploadImgObj.imgSelectedCallback();
			}
		},
		/*
		var onPhotoDataSuccess = function(imageData) {  
	        var image = document.getElementById('photo');  
	        image.style.display = 'block';  
	        image.src = "data:image/jpeg;base64," + imageData;  
    	}
		*/
		onFail : function(message) {
			console.error("An error has occurred while capture one photo. [ " + message + "]");
		}
}

function uploadPhoto(){
	var imageURI = $("#photoPath").val();
    var options = new FileUploadOptions();
    options.fileKey = "file";
    options.fileName = imageURI.substr(imageURI.lastIndexOf('/') + 1);
    options.mimeType = "image/jpeg";
	options.chunkedMode = false;
    var params = new Object();
    params.userId = CookieUtil.get('userid');
    params.description = encodeURIComponent($("#description").val());
    
    options.params = params;
    
    var ft = new FileTransfer();
    ft.upload(imageURI, webContext + "/ImageServlet?cmd=add", onPhotoUploadSuccess, onPhotoUploadFail, options);
}
var onPhotoUploadSuccess = function(r){
    console.log("Code = " + r.responseCode);
    console.log("Response = " + r.response);
    console.log("Sent = " + r.bytesSent);
    window.location.hash="#main";
}
var onPhotoUploadFail = function(error){
    console.error("An error has occurred while photo upload. The error code = " + error.code);
}