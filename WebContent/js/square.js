// ------------------------------------------------------   热门   ---------------------------------------------------
function showHotImages() {
	$.getScript(webContext + "/ImageServlet?cmd=hot").error(function(){
        $("#list").html("服务器繁忙，请稍后重试！");
    });
}
function getHotImages(result) {
    if (result.ret !== '0') {
        alert("服务器繁忙，请稍后重试");
    }
    var data = result.data;
    var html = [];
    for (var i = 0; i < data.length; i++) {
        var isFirst = false;
        if (i === 0) {
            html.push('<div class="item first">\
                           <img class="first" src="' + data[i].path + '">\
                       </div>');
        } else {
            html.push('<div class="item">\
                           <img src="' + data[i].path + '">\
                       </div>');
        }
    }
    $("#hot_content").html(html.join(''));
}