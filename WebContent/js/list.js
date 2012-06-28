// dmyang, haiyu
var listData = {};

function template(str, obj) {
	return str.replace(/\{(\w+)\}/g, function(m) {
		return obj[m.replace(/[{}]/g, '')];
	});
};

function getPhotos(result) {
	if (result.ret === -1) {
		alert("用户不存在");
		return;
	}
	
	listData = result.data;
	
	if (result.ret != 0) {
		alert("服务器繁忙，请稍后重试");
		return;
	}
	var listHtml = [];
	var data = result.data;
	if (data.length == 0) {
		$("#list").html("暂时没有数据，上传照片？<a href='#photo'>上传<a>");
		return;				
	}
	var t = '\
		<li>\
			<img src="{img}" />\
			<div class="img-info">\
				<span class="img-title">{title}</span>\
				<span class="img-comment">\
					<span class="comment-btn"></span>\
					<span class="comment-num">{comment}</span>\
				</span>\
			</div>\
		</li>';
		
	for (var i = 0; i < data.length; i++) {
		var d = data[i];
		listHtml.push(template(t, {
			img: d.path,
			title: d.description,
			comment: d.comment || 0
		}));
	}
	
	$("#list").html(listHtml.join(''));
	setTimeout(function(){
        if (window.myScroll) {
        window.myScroll.refresh();
        }
    },0);
	// 初始化时间轴
	initScrollBar();
};

var showList = function() {
	//$("#content div").hide();
	//$("#list").show();
	window.location.hash="#main";
	var userId = CookieUtil.get("userid");
	// 展现
	ChildObj.getPhotoList(userId);// $.getJSON('../data.json', function(d) {console.log(d)
	//getPhotos({'ret':'0', 'msg':'load success', 'data':[{'description':'出生了', 'path':'../img/591.jpg', 'upload_time':'2012-06-09 14:13:23.0','comment':'10'},{'description':'很开心', 'path':'../img/588.jpg', 'upload_time':'2012-06-09 14:13:23.0', 'comment':'10'},{'description':'今天小孩会坐起来玩玩具了', 'path':'../img/593.jpg', 'comment':'100', 'upload_time':'2012-06-09 14:13:23.0'},{'description':'某一天，你也会很可爱，你是妈妈的幸福', 'path':'../img/595.jpg', 'comment':'5', 'upload_time':'2012-06-09 14:13:23.0'},{'description':'开心开心', 'path':'../img/592.jpg', 'comment':'1200', 'upload_time':'2012-06-09 14:13:23.0'},{'description':'开心了', 'path':'../img/599.jpg', 'comment':'15', 'upload_time':'2012-06-09 14:13:23.0'}]});
	/*
	var userId = CookieUtil.get("userid");
	$.getScript(webContext + "/ImageServlet?cmd=query&userId=" + userId).error(function(){
		$("#list").html("服务器繁忙，请稍后重试！");
	});
	*/
};

var scrollTimeBar = function(iscroll, e) {
	var x = iscroll.x;
	// var left = parseInt($('#list_scroll').css('margin-left').replace('px', ''));
	$('#list_scroll').css('margin-left', x);
};

Date.prototype.pattern=function(fmt) { 
    var o = {        
    "M+" : this.getMonth()+1, //月份        
    "d+" : this.getDate(), //日        
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时        
    "H+" : this.getHours(), //小时        
    "m+" : this.getMinutes(), //分        
    "s+" : this.getSeconds(), //秒        
    "q+" : Math.floor((this.getMonth()+3)/3), //季度        
    "S" : this.getMilliseconds() //毫秒        
    };        
    var week = {        
    "0" : "\u65e5",
    "1" : "\u4e00",
    "2" : "\u4e8c",
    "3" : "\u4e09",
    "4" : "\u56db",
    "5" : "\u4e94",
    "6" : "\u516d"
    };        
    if(/(y+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));        
    }        
    if(/(E+)/.test(fmt)){        
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);        
    }        
    for(var k in o){        
        if(new RegExp("("+ k +")").test(fmt)){        
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));        
        }        
    }        
    return fmt;        
}      

var item_length = 0;

function initScrollBar() {
	item_length = $('#list li:last').outerWidth();
	var ml = parseInt($('#list li:last').css('margin-left'), 10);
	var data = listData;
	var length = data.length;
	// set width
	$('#list').css('width', (item_length + ml) * length - ml);
	$('#list_scroll').css('width', item_length * length);
	$('#time_line').css('width', item_length * length);
	// init the scrollIcon
	var item_list = [];
	for (var i = 0; i < data.length; i ++) {
		left = i * item_length + 10;
		//item_list.push('<i id="add_event_plus_' + data[i].id + '" class="spine_plus" style="left:' + left + 'px;"></i>');
		item_list.push('<i class="spine_plus" style="left:' + left + 'px;"></i>');
		// TODO:
		var upload_time = "";
		if (data[i].upload_time) {
			upload_time = data[i].upload_time.substring(5, 10);
		}
		item_list.push('<div class="spine_date" style="left:' + (left-15) + 'px; top:16px;">' + upload_time + '</div>');
		
	}
	$("#time_line").append(item_list.join(''));
	
	$("#time_line .spine_plus:first").addClass('spine_current');
	
	$('#time_line .spine_plus').live('mouseover', function(e) {
		$('.spine_plus').removeClass('spine_current');
		$(this).addClass('spine_current');
	}).bind('click', function(e){
		return false;
	});
};

/*
function scrollCallback(x, y, data) {
	// set margin-left
	$('#list_scroll').css('margin-left', x);
}
*/

$("#time_line").bind('click', function(e){
	// $('#add_event_plus').css("left", e.offsetX);
	var offsetX = e.offsetX;
	var left = offsetX/item_length;
	//alert(left);
	$('.pub-menus2').show().css('top', 370).css('z-index', 10);
	/*
	$('#time_line .spine_plus').removeClass('spine_current');
	$( $('#time_line .spine_plus').get(parseInt(left))).addClass('spine_current');
	*/
});
