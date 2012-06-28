/**  
* cookie管理对象  
*/  
var CookieUtil={   
   /**  
    * 设置Cookie  
    * @param {string} 设置cookie的名称  
    * @param {string} 设置cookie的值  
    * @param {object} 其他选项{是一个对象作为参数}  
    */      
   set   :   function(name,value,option){   
      var str=name+"="+escape(value);
      if(option){   
         if(option.expireDays){//过期日期   
            var date=new Date();   
            var ms=option.expireDays*24*3600*1000;   
            date.setTime(date.getTime()+ms);   
            str+="; expires="+date.toGMTString();   
         }   
         if(option.path)str+="; path="+path; //设置访问路径   
         if(option.domain)str+="; domain"+domain; //设置访问主机   
         if(option.secure)str+="; true"; //设置安全性   
      }   
      document.cookie=str;   
   },   
   /**  
    * 获取Cookie  
    * @param  {string} cookie的名称  
    * @return {string} cookie的值  
    */      
   get   :   function(name){   
      var cookieArray=document.cookie.split("; "); //得到分割的cookie名值对   
      for(var i=0;i<cookieArray.length;i++){   
         var arr=cookieArray[i].split("="); //将名和值分开   
         if(arr[0]==name)   
            return unescape(arr[1]); //如果是指定的cookie，则返回它的值   
      }   
      return "";   
   },   
   /**  
    * 删除Cookie  
    * @param  {string} 需要删除的cookie名称  
    */      
   del : function(){   
      this.set(name,"",{expireDays:-1}); //将过期时间设置为过去来删除一个cookie   
   }   
};

//-------------------------------------------------- 登录 --------------------------------------------------
function login() {
	var params = {};
	params["id"] = $("#id_login").val();
	params["password"] = $("#password_login").val();
	params["cmd"] = "query";
	$.ajax({
		url: webContext + "/UserServlet",
		data: params,
		dataType: 'script'
	}).error(function(data){
		alert("服务器繁忙!");
	});
}

var getUserInfo = function (result) {
	if (result.ret == -9999) {
		alert('用户名不存在，或者密码错误！');
		return;
	}
	if (result.ret != 0) {
		alert('服务器繁忙，请稍后重试！');
	}
	if (result.ret == 0) {
		var id = result.data.id;
		var name = result.data.name;
		CookieUtil.set('userid', id);
		CookieUtil.set('username', name);
		//$("#main h1").html(name + " - 亲子卷轴");
		window.location.hash="#main";
		//setTimeout(showList, 1000);
		//console.log('aa');
		// showList();
		
	}
}

// -------------------------------------------------- 注册 --------------------------------------------------
function register(){
	var params = {};
	params["id"] = $("#id").val();
	params["password"] = $("#password").val();
	params["passwordConfirm"] = $("#passwordConfirm").val();
	params["cmd"] = "add";
	params["name"] = $("#name").val();
	$.ajax({
		url: webContext + "/UserServlet",
		data: params,
		type: 'post',
		dataType: 'script'
	});
}

var addUserInfo = function(data) {
	CookieUtil.set('userid', data.id);
	CookieUtil.set('username', data.name);
	window.location.hash="#main";
	showList();
};

//  -------------------------------------------------- 注销 --------------------------------------------------

function logout(){
	CookieUtil.set('userid', '');
	CookieUtil.set('username', '');
	window.location.hash="#login";
}

/*
var backToLogin = function() {
	window.location.hash="#login";
}
var backToRegister = function() {
	window.location.hash="#register";
}
*/