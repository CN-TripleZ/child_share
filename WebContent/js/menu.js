//********************** 
//author 王磊
//create Date: 2012-06-09
//**********************

function initMenu(container, callback){
	
	var html = "";
	
	if(container){
		container.innerHTML = html;
	}
	
	container.on('onclick', function(evt){
		var nodename = evt.getTarget();
		if(typeof callback == "function"){
			callback();
		}
	});
}