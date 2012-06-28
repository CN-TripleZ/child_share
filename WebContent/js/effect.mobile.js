/******************************************************
 * Copyright (c) 2012, TENPAY.COM All rights reserved *
 ******************************************************/

/**
 * 浮层/页面切换效果/touch
 * @author carlli
 * @encode utf-8
 * @version 1.4
 * @data 2012.5.31
 * @modify
 * -------------------------------------------------------------------
 * 2012.5.31  carlli  v1.0  create
 * 2012.6.2   carlli  v1.1  新增PageLoader接口
 * 2012.6.5   carlli  v1.2  新增Animation接口，优化PageLoader接口
 * 2012.6.6   carlli  v1.3  Animation接口新增stopped()接口
 * 2012.6.8   carlli  v1.4  Animation接口新增transform/transition/fixedDuration接口
 * -------------------------------------------------------------------
 * @usage
 * -------------------------------------------------------------------
 * + Animation getAnimation(String name)
 *      + Object Types
 *      + Boolean start(String type, String selector String property, int speed, int begin, int change, int duration)
 *      + void stop()
 *      + Boolean stopped()
 *      + void set(String type, Function callback, Array args, Object context)
 *      + void remove(String type)
 *      + void transform(String selector, String newClassName, String replaceClassName)
 *      + void transition(String selector, String newClassName, String replaceClassName)
 *      + void bind(String selector, Boolean multi)
 *      + int fixedDuration(int duration, int offset)
 *      + Object get(String type)
 *      + void destory()
 * + PageLoader getPageLoader(String name)
 *      + void bind(String selector, String method, String data, Boolean isPushState, Object on)
 *      + void loadPage(String url, String method, String data, Boolean isPushState, Object on)
 *      + void html(String selector, String data, String flag)
 *      + void loadScript(String url, String charset)
 *      + void set(String type, Function callback, Array args, Object context)
 *      + void remove(String type)
 *      + Object get(String type)
 *      + void destory()
 * + TouchSlide getTouchSlide(String name)
 *      + void bind(String selector)
 *      + void destory()
 * + Object UA 
 * + void destory();
 * + void removeClass(Node|String node, String className)
 * + void addClass(Node|String node, String className)
 * + void replaceClass(Node|String node, String replaceClassName, String newClassName)
 * + Boolean checkUA(String key) 
 * -------------------------------------------------------------------
 */
var E4M = (function(){  
    var G_LOADER_MAP = null;      // PageLoader实例MAP
    var G_TOUCH_MAP = null;       // TouchSlide实例MAP
    var G_ANIMATION_MAP = null;   // Animation实例MAP
    //----------------------------------------------------------------
    /**
     * 获取TouchSlide实例
     * @param String name 实例名称
     * @param Boolean create 在不存在时是否创建新的一个新的实例
     * @return TouchSlide slide 
     */
    function GetTouchSlide(name, create){
        var instance = null;
        G_TOUCH_MAP = G_TOUCH_MAP || {};
        if(true === create){            
            if(!G_TOUCH_MAP[name]){
                instance = G_TOUCH_MAP[name] = new _TouchSlide(name);                     
            }else{
                instance = G_TOUCH_MAP[name];
            }
        }else{
            instance = (G_TOUCH_MAP[name] || null);
        }
        return instance;
    };
    /**
     * 获取PageLoader实例
     * @param String name 实例名称
     * @param Boolean create 在不存在时是否创建新的一个新的实例
     * @return PageLoader loader 
     */
    function GetPageLoader(name, create){
        var instance = null;
        G_LOADER_MAP = G_LOADER_MAP || {};
        if(true === create){            
            if(!G_LOADER_MAP[name]){
                instance = G_LOADER_MAP[name] = new _PageLoader(name);                     
            }else{
                instance = G_LOADER_MAP[name];
            }
        }else{
            instance = (G_LOADER_MAP[name] || null);
        }
        return instance;
    };
    /**
     * 获取Animation实例
     * @param String name 实例名称
     * @param Boolean create 在不存在时是否创建新的一个新的实例
     * @return Animation animation 
     */
    function GetAnimation(name, create){
        var instance = null;
        G_ANIMATION_MAP = G_ANIMATION_MAP || {};
        if(true === create){            
            if(!G_ANIMATION_MAP[name]){
                instance = G_ANIMATION_MAP[name] = new _Animation(name);                     
            }else{
                instance = G_ANIMATION_MAP[name];
            }
        }else{
            instance = (G_ANIMATION_MAP[name] || null);
        }
        return instance;
    };
    /**
     * 获取节点对象
     * @param Node|String node
     * @return node n
     */ 
    function GetNode(node){        
        if(typeof(node) == "string"){
            return document.querySelector(node);
        }
        return node;
    };
    /**
     * 移除指定的class
     * @param Node|String node
     * @param String className
     */ 
    function RemoveClass(node, className){
        node = GetNode(node);
        if(null != node && className){
            var cls = node.className;
            var regExp = new RegExp("[\\s ]*" + className, "g");
            cls = cls.replace(regExp, "");
            node.className = cls;
        }
        node = null;
        regExp = null;
    };
    /**
     * 添加指定的class
     * @param Node|String node
     * @param String className
     */ 
    function AddClass(node, className){
        node = GetNode(node);
        if(null != node && className){
            RemoveClass(node, className);
            node.className += " " + className;
        }
        node = null;
    };
    /**
     * 替换指定的class
     * @param Node|String node
     * @param String replaceClassName
     * @param String newClassName
     */ 
    function ReplaceClass(node, replaceClassName, newClassName){
        node = GetNode(node);
        if(null != node && replaceClassName){
            var cls = node.className;
            cls = cls.replace(replaceClassName, newClassName||"");
            node.className = cls;
        }
        node = null;
    };
    /**
     * 获取UA
     * @return Object {String ua, Boolean ios, Boolean android}
     */
    function GetUA(){
        var ua = navigator.userAgent;
        
        return {
            "ua" : ua,
            "ios" : CheckUA("iPhone|iPad|iPod|iOS|Mac OS"),
            "android" : CheckUA("Android")
        };
    };
    /**
     * 检测UA
     * @param String key 关键字
     * @return Boolean true/false
     */
    function CheckUA(key){
        var ua = navigator.userAgent;
        var r = false;
        var p = new RegExp("("+ key + ")", "i");
        
        r = p.test(ua);
        
        p = null;
        return r;
    };
    //----------------------------------------------------------------
    /**
     * 动画效果（构造函数）
     * JS动画：三次缓动效果 p(t) = t^3
     * @param String name
     */     
    var _Animation = function(name){
        this.name = name;       // 名称，唯一标识
        this.timer = null;      // 定时器
        this.Types = {          
            "IN" : "easeIn",        //in
            "OUT" : "easeOut",      //out
            "IN_OUT" : "easeInOut"  //in-out
        };
        this.on = {
            onstart : null,     //开始时的回调，{Function callback, Array args, Object context}
            onstop : null,      //停止时的回调，{Function callback, Array args, Object context}
            onprogress : null   //处理过程中的回调，{Function callback, Array args, Object context}
        };
    };
    _Animation.prototype = {
        /**
         * 执行回调函数
         * @param String type 类型
         * @param Array args 消息
         */
        execCallback : function(type, args){            
            var o = this.get(type);
            var m = args || [];
            var a = [].concat(m);
            
            if(o && o.callback){
                a = a.concat(o.args||[]);
                o.callback.apply((o.context||this), a);
            }
            
            m = null; a = null;
        },
        /**
         * 设置回调
         * @param String type 类型
         * @param Function callback 回调
         * @param Array args 参数
         * @param Object context 上下文
         */
        set : function(type, callback, args, context){
            var key = "on" + type;
            if(key in this.on){
                this.on[key] = {
                    "callback" : callback || null,
                    "args" : args || [],
                    "context" : context || this
                };
            }
        },
        /**
         * 移除回调
         * @param String type 类型
         */
        remove : function(type){
            this.on["on" + type] = null;
        },
        /**
         * 获取回调
         * @param String type 类型
         * @return Object on
         */
        get : function(type){
            return this.on["on" + type] || null;
        },
        /**
         * 渐进（由慢到快）
         * @param time 时间点
         * @param begin 起始位置
         * @param change 区间改变量（结束位置 - 起始位置）
         * @param duration 持续的时间
         */
        easeIn : function(time, begin, change, duration){
            return change * (1 - Math.cos(time / duration * (Math.PI / 2))) + begin;
        },
        /**
         * 渐出（由快到慢）
         * @param time 时间点
         * @param begin 起始位置
         * @param change 区间改变量（结束位置 - 起始位置）
         * @param duration 持续的时间
         */
        easeOut : function(time, begin, change, duration){
            return change * Math.sin(time / duration * (Math.PI / 2)) + begin;
        },
        /**
         * 渐进渐出
         * @param time 时间点
         * @param begin 起始位置
         * @param change 区间改变量（结束位置 - 起始位置）
         * @param duration 持续的时间
         */
        easeInOut : function(time, begin, change, duration){
            return change / 2 * (1 - Math.cos(Math.PI * time / duration)) + begin;
        },
        /**
         * JS自定义动画开始
         * @param String type 类型
         * @param String selector CSS选择器
         * @param String property CSS属性
         * @param int speed 速度
         * @param int begin 起始位置
         * @param int change 区间改变量（结束位置 - 起始位置）
         * @param int duration 持续的时间
         * @return Boolean true/false
         */
        start : function(type, selector, property, speed, begin, change, duration){
            var animation = this;
            var time = 0;
            var ease = this[type];
            var node = document.querySelector(selector);
            if(ease && node){
                animation.execCallback("start");
                animation.timer = setTimeout(function(){
                    var shift = ease(time, begin, change, duration);
                    
                    animation.execCallback("progress", [shift]);
                    if(time < duration){                    
                        animation.timer = setTimeout(arguments.callee, speed);
                    }else{
                        animation.stop();
                    }
                    
                    node.style[property] = shift + "px";
                    time++;                    
                }, speed);
                
                return true;
            }
            return false;
        },        
        /**
         * JS自定义动画停上
         */
        stop : function(){        
            if(null != this.timer){                
                clearTimeout(this.timer);
                this.timer = null;                
            }
            this.execCallback("stop");
        },
        /**
         * JS自定义动画是否已经停止
         * @return Boolean true/false
         */
        stopped : function(){
            return (null == this.timer);
        },
        /**
         * CSS动画
         * @param String selector CSS选择器
         * @param String newClassName 目标class
         * @param String replaceClassName 需要替换的class        
         */
        css : function(selector, newClassName, replaceClassName){
            var node = document.querySelector(selector);
            var ncn = newClassName || "";
            var rcn = replaceClassName || "";
            
            if(null != node && "" != ncn){
                if("" != rcn){
                    ReplaceClass(node, rcn, ncn);
                }else{
                    AddClass(node, ncn);
                }
            }
            node = null;
        },
        /**
         * CSS动画(transform)
         * @param String selector CSS选择器
         * @param String newClassName 目标class
         * @param String replaceClassName 需要替换的class        
         */
        transform : function(selector, newClassName, replaceClassName){
            var node = document.querySelector(selector);
            if(null != node){
                var animation = this;                
                node.addEventListener("webkitAnimationEnd", function(e){
                    animation.execCallback("stop", [node, newClassName, replaceClassName]);
                    
                    this.removeEventListener("webkitAnimationEnd", arguments.callee, true);
                }, true);
                
                animation.execCallback("start");
                this.css(selector, newClassName, replaceClassName);
            }
        },
        /**
         * CSS动画(transition)
         * @param String selector CSS选择器
         * @param String newClassName 目标class
         * @param String replaceClassName 需要替换的class        
         */
        transition : function(selector, newClassName, replaceClassName){
            var node = document.querySelector(selector);
            if(null != node){
                var animation = this;
                node.addEventListener("webkitTransitionEnd", function(e){
                    animation.execCallback("stop", [node, newClassName, replaceClassName]);
                    
                    this.removeEventListener("webkitTransitionEnd", arguments.callee, true);
                }, true);
                
                animation.execCallback("start");
                this.css(selector, newClassName, replaceClassName);                
            }
            
        },
        /**
         * 绑定效果
         * @param Event e
         */
        bindEffect : function(e){
            e.preventDefault();
            
            var effect = this.getAttribute("data-effect");
            var animation = this.getAttribute("data-" + effect);
            var end = null;
            var property = null;
            var instance = GetAnimation(this.getAttribute("data-animation"));
            
            if(null != effect && null != animation && null != instance){                
                if("transition" == effect){
                    end = "webkitTransitionEnd"; 
                    property = "transition";
                }else if("transform" == effect){
                    end = "webkitAnimationEnd";
                    property = "transform";
                }else{
                    throw new Error("Unknown Animation! effect = " + effect);
                }
                
                this.addEventListener(end, function(e){
                    instance.execCallback("stop", [this]);
                    
                    this.removeEventListener(end, arguments.callee, true);
                }, false);
                
                instance.execCallback("start", [this]);
                this.style[property] = animation;
            }
        },
        /**
         * 绑定所有的
         * @param String selector CSS选择器
         */
        bindAll : function(selector){
            var list = document.querySelectorAll(selector);
            var size = list.length;
            
            for(var i = 0; i < size; i++){
                this.bindSingle(list[i]);
            }
            
            list = null;
        },
        /**
         * 绑定所有的
         * @param String selector CSS选择器
         */
        bindSingle : function(selector){
            var o = GetNode(selector);
            var eventType = null;
            if(null != o){
                o.setAttribute("data-animation", this.name);
                eventType = o.getAttribute("data-event");
                o.addEventListener(eventType, this.bindEffect, false);
            }
            
            o = null;
        },
        /**
         * 绑定
         * @param String selector CSS选择器
         * @param Boolean multi
         */
        bind : function(selector, multi){
            if(true === multi){
                this.bindAll(selector);
            }else{
                this.bindSingle(selector);
            }
        },
        /**
         * 修正持续时长，以iOS为基准
         * @param int duration 持续的时间
         * @param int offset 偏移值
         * @return int d
         */
        fixedDuration : function(duration, offset){
            var ua = GetUA();
            return (ua.ios ? duration : (duration + offset));
        },
        /**
         * 销毁
         */
        destory : function(){
            G_ANIMATION_MAP[this.name] = null;
        }
    }; // end _Animation
    //----------------------------------------------------------------
    /**
     * 页面加载器（构造函数）
     * @param String name
     */
    var _PageLoader = function(name){
        this.name = name;       //名称，唯一标识
        //--------------------------------------------
        this.on = {
            onprogress : null,      //处理过程中的回调，{Function callback, Array args, Object context}
            oncomplate : null,      //请求完成的回调，{Function callback, Array args, Object context}
            onsuccess : null,       //成功回调{Function callback, Array args, Object context}
            onerror : null,         //失败(JS异常)回调{Function callback, Array args, Object context}
            onneterror : null,      //网络失败回调{Function callback, Array args, Object context}
            onstart : null,         //请求开始时回调{Function callback, Array args, Object context}
            onend : null,           //请求结束时回调{Function callback, Array args, Object context}
            onback : null,          //回退时回调{Function callback, Array args, Object context} 
            onscriptsuccess : null, //脚本加载成功回调{Function callback, Array args, Object context}
            onscripterror : null    //脚本加载失败回调{Function callback, Array args, Object context}
        };
    };    
    //方法
    _PageLoader.prototype = {
        /**
         * 替换历史栈
         * @param String data 数据参数
         * @param String title 标题
         * @param String url URL
         */
        replaceHistoryStack : function(data, title, url){
            window.history.replaceState(data, title, url);
        },
        /**
         * 设置回调
         * @param String type 类型
         * @param Function callback 回调
         * @param Array args 参数
         * @param Object context 上下文
         */
        set : function(type, callback, args, context){
            var key = "on" + type;
            if(key in this.on){
                this.on[key] = {
                    "callback" : callback || null,
                    "args" : args || [],
                    "context" : context || this
                };
            }
        },
        /**
         * 移除回调
         * @param String type 类型
         */
        remove : function(type){
            this.on["on" + type] = null;
        },
        /**
         * 获取回调
         * @param String type 类型
         * @return Object on
         */
        get : function(type){
            return this.on["on" + type] || null;
        },
        /**
         * 执行回调函数
         * @param String type 类型
         * @param Array args 消息
         */
        execCallback : function(type, args){            
            var o = this.get(type);
            var m = args || [];
            var a = [].concat(m);
            
            if(o && o.callback){
                a = a.concat(o.args||[]);
                o.callback.apply((o.context||this), a);
            }
            
            m = null; a = null;
        },
        /**
         * 重置扩展参数
         */
        resetExtendedParameters : function(){
            for(var key in this.on){
                if(this.on.hasOwnProperty(key)){
                    this.remove(key);
                }
            }
        },
        /**
         * 设置扩展参数
         * @param Object on
         */
        setExtendedParameters : function(on){
            var ext = on || null;
            
            this.resetExtendedParameters(); //重置
            for(var key in ext){
                if(ext.hasOwnProperty(key) && (key in this.on)){
                    this.on[key] = ext[key];
                }
            }
        },
        /**
         * 添加参数
         * @param String url  原URL
         * @param String param 参数
         * @return String url 新URL
         */
        append : function(url, param){
            return url += ((url.indexOf("?") != -1 ? "&" : "?") + param);
        },
        /**
         * 分割内容
         * @param String data 数据
         * @param String flag 标识
         * @return String content 内容
         */
        slice : function(data, flag){
            var stack = [];
            var p1 = new RegExp("(<!--\\[CONTENT-START" + flag + "\\]-->)");
            var p2 = new RegExp("(<!--\\[CONTENT-END" + flag + "\\]-->)");
            var r1 = null;
            var r2 = null;
            var s = (null != (r1 = p1.exec(data)) ? r1[1] : "");
            var e = (null != (r2 = p2.exec(data)) ? r2[1] : "");

            var sl = s.length;
            var el = e.length;
            var content = "";
            
            if(sl > 0 && el > 0){
                stack.push('<!--[CONTENT-START'+flag+']-->');
                content = data.substring(data.indexOf(s) + sl, data.indexOf(e));
                stack.push(content);
                stack.push('<!--[CONTENT-END'+flag+']-->');
                
                content = stack.join('');
            }else{
                content = data;
            }
            
            p1 = null; p2 = null; r1 = null; r2 = null; s = null; e = null; sl = 0; el = 0; stack = null;
            
            return content;
        },
        /**
         * 设置HTML内容
         * @param String selector CSS选择器
         * @param String data 数据
         * @param String flag 标识
         */
        html : function(selector, data, flag){
            var o = document.querySelector(selector);
            if(null != o){
                o.innerHTML = this.slice(data, (flag||""));
            }
        },
        /**
         * 加载页面
         * @param String url 请求URL
         * @param String method 请求方式
         * @param String data 传输数据
         * @param Boolean isPushState 是否推送到历史记录
         */
        sendRequest : function(url, method, data, isPushState){        
            var xhr = new XMLHttpRequest();
            var loader = GetPageLoader(this.name);
            var isGET = (method.toLowerCase() == "get");
            
            url = (isGET ? loader.append(url, data) : url);
            data = (isGET ? "" : data);
            
            loader.execCallback("start"); //添加开始回调
            
            xhr.onreadystatechange = function(){
                if(4 == xhr.readyState){
                    try{
                        loader.execCallback("end"); //添加结束回调
                        loader.execCallback("complate", [xhr.status, xhr.responseText]);
                        if(200 === xhr.status || 0 === xhr.status){
                            if(true === isPushState){
                                loader.replaceHistoryStack(loader.name + "`" + method + "`" + data, "", url);
                            }
                            loader.execCallback("success", [xhr.responseText]); //添加成功回调
                        }else{
                            loader.execCallback("neterror", [xhr.status]); //添加网络错误回调
                        }
                    }catch(e){
                        loader.execCallback("error", [e.message]); //添加异常回调
                    }
                    xhr = null;
                }else{
                    loader.execCallback("progress", [xhr.readyState]); //添加结束回调
                }
            };
            
            xhr.open(method, url, true);  
            
            xhr.setRequestHeader("No-Cache","1");
            xhr.setRequestHeader("Pragma","no-cache");
            xhr.setRequestHeader("Cache-Control","no-cache");
            xhr.setRequestHeader("Expires","0");
            xhr.setRequestHeader("Last-Modified","Thu, 1 Jan 1970 00:00:00 GMT");
            xhr.setRequestHeader("If-Modified-Since","-1");
            xhr.setRequestHeader("X-Requested-With","XMLHttpRequest");
            
            xhr.send(data);
        },
        /**
         * 加载页面
         * @param String url 请求URL
         * @param String method 请求方式
         * @param String data 传输数据
         * @param Boolean isPushState 是否推送到历史记录
         * @param Object on 回调{
         *               onsuccess:{callback, args, Object context}, 
         *               onerror{callback, args, Object context}, 
         *               onneterror{callback, args, Object context}, 
         *               onstart{callback, args, Object context},
         *               onend{callback, args, Object context},
         *               onback{callback, args, Object context},
         *               onscriptsuccess{callback, args, Object context},
         *               onscripterror{callback, args, Object context}
         *               }
         */
        loadPage : function(url, method, data, isPushState, on){
            this.setExtendedParameters(on); // 设置扩展参数
            this.sendRequest(url, method, data, isPushState);  // 参送请求
        },
        /**
         * 绑定
         * @param String selector CSS选择器
         * @param String method 请求方式
         * @param String data 传输数据
         * @param Boolean isPushState 是否推送到历史记录
         * @param Object on 回调{
         *               onsuccess:{callback, args, Object context}, 
         *               onerror{callback, args, Object context}, 
         *               onneterror{callback, args, Object context}, 
         *               onstart{callback, args, Object context},
         *               onend{callback, args, Object context},
         *               onback{callback, args, Object context},
         *               onscriptsuccess{callback, args, Object context},
         *               onscripterror{callback, args, Object context}
         *               }         
         */
        bind : function(selector, method, data, isPushState, on){
            var o = document.querySelector(selector);
            
            if(null != o){
                o.setAttribute("data-loader", this.name);                
                o.addEventListener("click", function(e){
                    e.preventDefault();
                    e.stopPropagation();
                    
                    var loader = GetPageLoader(this.getAttribute("data-loader"));
                    var url = this.getAttribute("href") || this.getAttribute("data-url");
                    loader.loadPage(url, method, data, isPushState, on);
                }, false);
            }
        },
        /**
         * 移除script
         * @param Node head
         * @param Node script
         */
        removeScript : function(head, script){
            setTimeout(function(){
                head.removeChild(script);
                head = null;
                script = null;
            }, 200);   
        },
        /**
         * 加载脚本
         * @param String url JS地址
         * @param String charset 编码         
         */
        loadScript : function(url, charset){
            var script = document.createElement("script");
            var head = document.querySelector("head");
            var instance = GetPageLoader(this.name);
            
            script = document.createElement("script");
            script.charset = charset || "utf-8";
            script.async = true;
            script.src = url;
            script.addEventListener("load", function(e){
                instance.execCallback("scriptsuccess", [e]);                
                instance.removeScript(head, script);   
                head = null; script = null; instance = null;                
            }, false);
            script.addEventListener("error", function(e){
                instance.execCallback("scripterror", [e]);
                instance.removeScript(head, script);
                head = null; script = null; instance = null;
            }, false);
            head.appendChild(script); 
        },
        /**
         * 销毁
         */
        destory : function(){
            G_LOADER_MAP[this.name] = null;
        }
    }; //end _PageLoader
    
    //----------------------------------------------------------------
    /**
     * 触摸幻灯片（构造函数）
     * @param String name
     */
    var _TouchSlide = function(name){
        this.name = name;       //名称，唯一标识
        this.fingerStartX = 0;  //手指X轴开始位置
        this.fingerStartY = 0;  //手指Y轴开始位置
        this.currentPoint = 0;  //当前位置点
        this.right = true;      //右滑动
        this.left = false;      //左滑动
    };
    //方法
    _TouchSlide.prototype = {   
        /**
         * touchstart事件监听回调函数
         * @param Event e
         */
        touchStartHandler : function(e){
            e.preventDefault();
            
            var slide = GetTouchSlide(this.getAttribute("data-touch"));            
            var touch = e.targetTouches[0];
            slide.fingerStartX = touch.pageX;    
            
            //当前层的marginLeft
            slide.currentPoint = parseInt(this.style.marginLeft || 0, 10);
            //touch move
            this.addEventListener("touchmove", slide.touchMoveHandler, false);    
            this.addEventListener("touchend", slide.touchEndHandler, false);
        },
        /**
         * touchmove事件监听回调函数
         * @param Event e
         */
        touchMoveHandler : function(e){
            var slide = GetTouchSlide(this.getAttribute("data-touch"));
            var selector = this.getAttribute("data-selector");
            var touch = e.targetTouches[0];
            var fingerX = touch.pageX;
            var fingerStartX = slide.fingerStartX;
            var offset = fingerX - fingerStartX;

            offset = slide.currentPoint + offset;
            slide.left = !(slide.right = (fingerX > fingerStartX));
            /*
            if(fingerX > fingerStartX){ //turn right            
                offset = Math.min(offset, 0);
            }else{ //turn left
                offset = Math.max(offset, -400);
            }*/
            
            slide.turnScreen(selector, offset);
        },
        /**
         * touchend事件监听回调函数
         * @param Event e
         */
        touchEndHandler : function(e){
            var slide = GetTouchSlide(this.getAttribute("data-touch"));
            var selector = this.getAttribute("data-selector");
            var left = parseInt(this.style.marginLeft || 0, 10);
            var endPoint = Math.abs(left);
            var mod = Math.abs(endPoint % 200);
            var offset = 0;
            if(slide.right){ //turn right
                offset = 0;
                if(left <= 0){
                    if(mod <= 100){ //达到右移标准
                        offset = endPoint - mod;
                    }else{
                        offset = endPoint + (200 - mod);
                    }
                    offset = Math.min(-offset, 0); 
                }        
            }else{ //turn left        
                if(mod >= 100){ //达到左移标准
                    offset = endPoint + (200 - mod);
                }else{
                    offset = endPoint - mod;
                }
                offset = Math.max(-offset, -400);
            }
            
            //AddClass(this, "move");
            slide.turnScreen(selector, offset);
            
            //移除监听事件
            this.removeEventListener("touchmove", slide.touchMoveHandler, false);
            this.removeEventListener("touchend", slide.touchEndHandler, false);
        },
        /**
         * 翻屏
         * @param String selector 选择器
         * @param Number _offset 偏移值
         */
        turnScreen : function(selector, _offset){
            var _slide = document.querySelector(selector);
            _slide.style.marginLeft = _offset+'px';
            _slide = null;
        },
        /**
         * 绑定
         * @param String selector 选择器
         */
        bind : function(selector){
            var o = document.querySelector(selector);
            if(null != o){
                o.setAttribute("data-touch", this.name);
                o.setAttribute("data-selector", selector);
                o.addEventListener("touchstart", this.touchStartHandler, false);
            }
        },
        /**
         * 销毁对象
         */
        destory : function(){
            G_TOUCH_MAP[this.name] = null;
        }
    }; //end _TouchSlide
    
    //----------------------------------------------------------------
    //对外注册方法和属性
    return { 
        //------------------------------------------------------------------------------
        /**
         * 动画效果
         * @see _Animation
         */
        "getAnimation" : function(name){
            var instance = null;
            
            if(!name || typeof(name) != "string"){
                throw new Error("Illegal parameter! E4M.getAnimation(name)::name = " + name);
            }else{
                instance = GetAnimation(name, true);

                //方法注册
                return {
                    // 类弄[IN/OUT]
                    "Types" : instance.Types,
                    /**
                     * 设置回调
                     * @see _Animation.set(type, callback, args, context)
                     */
                    "set" : function(type, callback, args, context){instance.set(type, callback, args, context);},
                    /**
                     * 获取回调
                     * @see _Animation.get(type)
                     */
                    "get" : function(type){return instance.get(type);},
                    /**
                     * 移除回调
                     * @see _Animation.remove(type)
                     */
                    "remove" : function(type){instance.remove(type);},
                    /**
                     * JS自定义动画开始 
                     * @see _Animation.start(type, selector, property, speed, begin, change, duration)
                     */
                    "start" : function(type, selector, property, speed, begin, change, duration){return instance.start(type, selector, property, speed, begin, change, duration);},
                    /**
                     * JS自定义动画停上
                     * @see _Animation.stop()
                     */
                    "stop" : function(){instance.stop();},
                    /**
                     * JS自定义动画是否已经停止
                     * @see _Animation.stopped()
                     */
                    "stopped" : function(){instance.stopped();},
                    /**
                     * CSS动画(transform)
                     * @see _Animation.transform(selector, newClassName, replaceClassName)
                     */
                    "transform" : function(selector, newClassName, replaceClassName){instance.transform(selector, newClassName, replaceClassName);},
                    /**
                     * CSS动画(transition)
                     * @see _Animation.transition(selector, newClassName, replaceClassName)
                     */
                    "transition" : function(selector, newClassName, replaceClassName){instance.transition(selector, newClassName, replaceClassName);},
                    /**
                     * 绑定
                     * @see _Animation.bind(selector, multi)
                     */
                    "bind" : function(selector, multi){instance.bind(selector, multi);},
                    /**
                     * 修正持续时长，以iOS为基准
                     * @see _Animation.fixedDuration(duration, offset)
                     */
                    "fixedDuration" : function(duration, offset){return instance.fixedDuration(duration, offset);},
                    /**
                     * 销毁Sine实例
                     * @see _Animation.destory()
                     */
                    "destory" : function(){instance.destory();}
                };
            }
        },
        //------------------------------------------------------------------------------
        /**
         * 获取PageLoader实例
         * @param String name 唯一标识
         * @return Object
         */
        "getPageLoader" : function(name){
            var instance = null;
            
            if(!name || typeof(name) != "string"){
                throw new Error("Illegal parameter! E4M.getPageLoader(name)::name = " + name);
            }else{
                instance = GetPageLoader(name, true);
                
                //方法注册
                return {
                    /**
                     * 绑定 
                     * @see _PageLoader.bind(selector, method, data, isPushState, on)
                     */
                    "bind" : function(selector, method, data, isPushState, on){instance.bind(selector, method, data, isPushState, on);},
                    /**
                     * 加载页面
                     * @see _PageLoader.loadPage(url, method, data, isPushState, on)
                     */
                    "loadPage" : function(url, method, data, isPushState, on){instance.loadPage(url, method, data, isPushState, on);},
                    /**
                     * 设置HTML内容
                     * @see _PageLoader.html(selector, data, flag)
                     */
                    "html" : function(selector, data, flag){instance.html(selector, data, flag);},
                    /**
                     * 加载脚本
                     * @see _PageLoader.loadScript(url, charset)
                     */
                    "loadScript" : function(url, charset){instance.loadScript(url, charset);},
                    /**
                     * 设置回调
                     * @see _PageLoader.set(type, callback, args, context)
                     */
                    "set" : function(type, callback, args, context){instance.set(type, callback, args, context);},
                    /**
                     * 获取回调
                     * @see _PageLoader.get(type)
                     */
                    "get" : function(type){return instance.get(type);},
                    /**
                     * 移除回调
                     * @see _PageLoader.remove(type)
                     */
                    "remove" : function(type){instance.remove(type);},
                    /**
                     * 销毁PageLoader实例
                     * @see _PageLoader.destory()
                     */
                    "destory" : function(){instance.destory();}
                };
            }
        },
        //------------------------------------------------------------------------------
        /**
         * 获取TouchSlide实例
         * @param String name 唯一标识
         * @return Object
         */
        "getTouchSlide" : function(name){
            var instance = null;
            
            if(!name || typeof(name) != "string"){
                throw new Error("Illegal parameter! E4M.getTouchSlide(name)::name = " + name);
            }else{
                instance = GetTouchSlide(name, true);
                
                //方法注册
                return {
                    /**
                     * 绑定 
                     * @see _TouchSlide.bind(selector)
                     */
                    "bind" : function(selector){instance.bind(selector);},
                    /**
                     * 销毁TouchSlide实例
                     * @see _TouchSlide.destory()
                     */
                    "destory" : function(){instance.destory();}
                };
            }
        },
        //------------------------------------------------------------------------------
        /**
         * 获取UA信息
         * @see GetUA()
         */
        "UA" : GetUA(),
        /**
         * 检测UA
         * @see CheckUA(key)
         */
        "checkUA" : CheckUA,
        /**
         * 移除指定的class
         * @see RemoveClass(node, className)
         */ 
        "removeClass" : RemoveClass,
        /**
         * 添加指定的class
         * @see AddClass(node, className)
         */ 
        "addClass" : AddClass,
        /**
         * 替换指定的class
         * @see ReplaceClass(node, replaceClassName, newClassName)
         */ 
        "replaceClass" : ReplaceClass,
        /**
         * 销毁所有PageLoader和TouchSlide实例
         */
        "destory" : function(){
            G_LOADER_MAP = null;
            G_TOUCH_MAP = null;
            G_ANIMATION_MAP = null;
        }
    };
})();