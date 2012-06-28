// 王磊

function init() {
	// 若用户已登录，默认页面，展现列表
	// TODO: 后续将其隐藏起来
	//showList();
	document.addEventListener("deviceready", onDeviceReady, true);
	
	$("#main").on("pageshow",function(){ 
		showList(); 
	})
	
	var myScroll = new iScroll('wrapper', {
		hScrollbar: false, 
		vScrollbar: false, 
		snap: true,
		checkDOMChanges: true,
		onScrollMove: function(e){
			var x = this.x;
			var index = parseInt(x * -1/274);
			
			$('#list_scroll').css('margin-left', x + index*6 );
		},
		onScrollEnd: function() {
			var x = this.x;
			var index = parseInt(x * -1/274);

			$('#list_scroll').css('margin-left', x + index*6);
			console.log(x);
			console.log(index);
			$('#time_line .spine_plus').removeClass('spine_current');
			$( $('#time_line .spine_plus').get(parseInt(index))).addClass('spine_current');
			
		}
	});

	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
};

var onDeviceReady = function() {
	// 文件上传对象初始化
	PictureUpload.init();
};

(function(){
	if(document.querySelector(".pub-menus")){
	    var animation = E4M.getAnimation("push");
	    animation.set("stop", function(node, newClassName, styles){
	        var pub = document.querySelector(".pub-menus");
	        var opend = pub.getAttribute("data-opened");
	        
	        if("1" != opend){
	            E4M.removeClass(".pub-menus-entry", "on");
	        }
	        
	    });
	    document.querySelector(".pub-menus").addEventListener("click", function(e){
	        e.preventDefault();
	        var opend = this.getAttribute("data-opened");
	        
	        if(opend && "1" == opend){        
	            animation.transition(".pub-menus", "push-menus", {width:"70px", right:"10px"});
	            animation.transition(".pub-menus-main", "push-menus-main", {marginLeft:"-250px"});
	            this.setAttribute("data-opened", 0);
	        }else{
	            E4M.addClass(".pub-menus-entry", "on");
	            animation.transition(".pub-menus", "push-menus", {width:"320px", right:"-52px"});
	            animation.transition(".pub-menus-main", "push-menus-main", {marginLeft:"0"});
	            this.setAttribute("data-opened", 1);
	        }
	    }, false);

	    document.querySelector(".pub-menus-other").addEventListener("click", function(e){
	        e.preventDefault();
	        e.stopPropagation();
	    }, false);  
	}
	
	
	
	if(document.querySelector(".pub-menus2")){
	    var animation = E4M.getAnimation("push");
	    animation.set("stop", function(node, newClassName, styles){
	        var pub = document.querySelector(".pub-menus2");
	        var opend = pub.getAttribute("data-opened");
	        
	        if("1" != opend){
	            E4M.removeClass(".pub-menus-entry2", "on");
	        }
	        
	    });
	    document.querySelector(".pub-menus2").addEventListener("click", function(e){
	        e.preventDefault();
	        var opend = this.getAttribute("data-opened");
	        
	        if(opend && "1" == opend){        
	            animation.transition(".pub-menus2", "push-menu2", {width:"70px", right:"10px"});
	            animation.transition(".pub-menus-main2", "push-menus-main2", {marginLeft:"-250px"});
	            this.setAttribute("data-opened", 0);
	        }else{
	            E4M.addClass(".pub-menus-entry2", "on");
	            animation.transition(".pub-menus2", "push-menus2", {width:"320px", right:"-52px"});
	            animation.transition(".pub-menus-main2", "push-menus-main2", {marginLeft:"0"});
	            this.setAttribute("data-opened", 1);
	        }
	    }, false);

	    document.querySelector(".pub-menus-other2").addEventListener("click", function(e){
	        e.preventDefault();
	        e.stopPropagation();
	    }, false);  
	}
	
})("pub-menus");

(function(){
    var o = document.querySelector(".login.bg");

    if(null != o){
        var login = E4M.getAnimation("login");
        login.set("start", function(node, newClassName, styles){
            var bg = document.querySelector(".login.bg");
            var top = parseInt(document.defaultView.getComputedStyle(bg, null).top||-1, 10);

            if(0 === top){
                E4M.addClass(".login.form-panel", "on");
            }
        });
        login.set("stop", function(node, newClassName, styles){
            E4M.addClass(".login.form-panel", "on");
        });
        login.transition(".login.bg", "ease-up", {top:0});
    }
})("login");
