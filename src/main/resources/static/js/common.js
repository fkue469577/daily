var swfurl = "/webupload/Uploader.swf";
var successMsg="操作成功";

var layerIndex;

function lyload(msg) {
    if (msg == undefined)
       msg = "请求数据中，请稍等...";
    layerIndex = layer.msg(msg, {icon: 16, time: 0, shade: 0.1});
}

function lyhide() {
    setTimeout(()=>layer.close(layerIndex), 300)
}

jQuery.browser={};
(function(){
    jQuery.browser.msie=false;
    jQuery.browser.version=0;
    if(navigator.userAgent.match(/MSIE ([0-9]+)./)){
        jQuery.browser.msie=true;
        jQuery.browser.version=RegExp.$1;
    }
})();

//初始化ajax
function initAjax() {

    //jquery 全局ajax设置
    $.ajaxSetup({
        success: function (data) {//请求成功后触发
        },
        error: function (xhr, status, e) {//请求失败遇到异常触发,先执行error,再执行complete
            switch (xhr.status) {
                case (500):
                    layer.alert("500-存在技术问题，请与平台联系");
                    break;
                case (401):
                    layer.msg("登录信息已经过期")
                    setTimeout(()=>{
                        location.href = "/login";
                    }, 3000)
                    break;
                case (403):
                    layer.alert("403-无权限执行此操作");
                    break;
                case (404):
                    layer.alert("404-找不到此页内容了");
                    break;
                case (408):
                    layer.alert("408-请求超时");
                    break;
                default:
                    layer.alert("请检查网络是否断开或者重新登陆");
                    //layer.alert("后端代码出错：" + xhr.status + "未知错误");
            }
            lyhide();
        },
        complete: function (xhr, status) {//完成请求后触发。即在success或er    ror触发后触发
            lyhide();
            var obj = JSON.parse(xhr.responseText);
            if(obj.authorization) {
                localStorage.setItem("Authorization", obj.authorization)
            }

        },
        beforeSend: function (xhr) {//发送请求前触发
            xhr.setRequestHeader("Authorization", localStorage.getItem("Authorization"))
            lyload();
        }
    });
}

initAjax();

function lysuccess(data, url) {
    if (data == undefined || data > 0)
        layer.alert(successMsg, function () {

            if (url != undefined)
                location.href = url;
            else
                location.reload();
        })
    else
        layer.alert("操作失败：" + data);

}

function lysuccess2(data, url) {
    if (data == 1)
        layer.alert(successMsg, function () {
            if (url != undefined)
                location.href = url;
            else
                location.reload();
        });
    else {
        layer.alert(data);
    }

}

function lyreload() {
    layer.alert(successMsg, function () {
        location.reload();
    })
}

/*====================获取url参数============================*/

function Request(name, defaults) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null && r != undefined)
        return decodeURI(r[2]);
    else {
        return defaults;
    }
}

function RequestAll() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {

            theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);

        }
    }
    return theRequest;
}

jQuery.extend({
    postJson: function(url, data, callback) {
        $.ajax({
            url: url,
            data: (typeof data != "function")? JSON.stringify(data): null,
            method: "post",
            contentType: "application/json",
            success: function(result) {
                if(typeof data == "function") {
                    callback(result);
                }
                if(typeof callback == "function") {
                    callback(result);
                }
            }
        })

    },
    postFile: function(url, data, callback) {
        $.ajax({
            method:'POST',
            url: url,
            data: data,
            processData:false,
            contentType:false,
            success: function(res) {
                if(typeof data == "function") {
                    data(res);
                    return
                }
                if(typeof callback == "function") {
                    callback(res)
                    return
                }
            }
        })
    }

})

/*====================Jquery 拓展方法============================*/
$.fn.extend({
    //表单加载json对象数据
    autofill: function (json) {
        var form = this;
        $.each(json, function (key, value) {

            var tag = form.find("[name=" + key + "]"); //获取标签

            if (tag.is('input')) { //若是input标签

                if (tag.attr("type") == "checkbox") {

                    if (value !== null) {
                        var checkArray = value.toString().split(",");
                        for (var i = 0; i < tag.length; i++) {
                            for (var j = 0; j < checkArray.length; j++) {

                                if (tag[i].value == checkArray[j]) {
                                    $(tag[i]).removeAttr("checked");
                                    tag[i].click();
                                }
                            }
                        }
                    }
                }
                else if (tag.attr("type") == "radio") {

                    tag.each(function () {
                        for (var i = 0; i < tag.length; i++) {
                            if (tag[i].value == value) {
                                $(tag[i]).removeAttr("checked");
                                tag[i].click();
                            }
                        }
                    });

                } else if (tag.attr("type") == "file") { //file类型不允许赋值
                    tag.val(null);
                }
                else {
                    tag.val(value);
                }

            } else if (tag.is("select")) { //若是select标签
                tag.val(value);

            } else if (tag.is("textarea")) { //若是textarea
                tag.val(value);
            } else if (tag.is("script")) { //script标签
                tag.text(value);
            }

        })

    },
    attres: function() {
        var attributes = this.get(0).attributes;
        var keys = Object.keys(attributes);
        var attrMap = {};
        for (let key of keys) {
            attrMap[attributes[key].name] = attributes[key].value
        }
        return attrMap;
    },
    validator: function (fields, container) {  //tooltip  popover

        $(this).bootstrapValidator({
            container: container,
            excluded: ':disabled',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fields
        })
    },
    check: function(callback, unalert) {
        var b = true;
        $(this).find("[required]").each(function(index, ele) {
            var o = $(ele);
            if(!o.val() || o.val().trim() == "") {
                checkAlert(o);
                b = false;
            }
        });
        setTimeout(function() {
            $(".layui-layer-tips").remove();
        }, 3000);
        if(b) {
            if(typeof callback == "function") {
                if(unalert) {
                    callback();
                } else {
                    layer.confirm("是否提交？", function () {
                        callback();
                    });
                }
            }
        }
    },
    checkCommit: function(option) {
        var b = true;
        var _this = this;
        $(_this).find("[required]").each(function(index, ele) {
            var o = $(ele);
            if(!o.val() || o.val().trim() == "") {
                checkAlert(o);
                b = false;
            }
        });
        setTimeout(function() {
            $(".layui-layer-tips").remove();
        }, 3000);
        if(b) {
            if(option.unalert) {
                submitForm();
            } else {
                layer.confirm("是否提交？", function () {
                    submitForm();
                });
            }
        }

        function submitForm() {
            var form = $(_this).serializeObject();
            if(option.dataBefore) {
                option.dataBefore(form);
            }
            $.ajax({
                method: "post",
                url: option.url,
                data: JSON.stringify(form),
                contentType: "application/json",
                success: function(result) {
                    refresh(result, ()=>{
                        if(option.callback) {
                            option.callback(result.data);
                        } else if($(".layui-laypage-btn").length>0 && option.index!=undefined) {
                            $(".layui-laypage-btn").click();
                            layer.close(option.index);
                            layer.msg(successMsg)
                        } else {
                            history.go(0);
                        }
                    }, option.timeout? option.timeout: 0);
                }
            })
        }
    },
});

/* =========================  end  ===================================== */

/*=============File控件选择图片的时候在Html5下马上预览=================*/
function InitFile(fileId, imgId) {

    $("#" + imgId).click(function () {
        $("#" + fileId).trigger("click");
    });

    document.getElementById(fileId).onchange = function (evt) {
        // 如果浏览器不支持FileReader，则不处理
        if (!window.FileReader) return;
        var files = evt.target.files;
        for (var i = 0, f; f = files[i]; i++) {
            if (!f.type.match('image.*')) {
                continue;
            }
            var reader = new FileReader();
            reader.onload = (function (theFile) {
                return function (e) {
                    // img 元素
                    document.getElementById(imgId).src = e.target.result;
                };

            })(f);
            reader.readAsDataURL(f);

        }
    }

}

/*=============返回历史页面======================*/
function back() {
    history.back();
}

function prints() {
    window.print();
}

/*===============获取唯一id========================*/
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

function rds() {
    return "?v=" + uuid();
}

/*============jquery表单序列化成object json============*/

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

/*==============下载方法=======================*/
function dowmload(url, params, method) {

    if (method == undefined)
        method = "post";

    var $form = $("#openDownLoadWin");
    if ($form.length == 0) {

        var html = "<form id='openDownLoadWin' target='_blank' method='" + method + "' style='display:none'>";
        html += "</form>";

        $("body").append(html);
        $form = $("#openDownLoadWin");
    }

    $form.attr("action", url);
    $form.empty();
    var inputs = "";
    for (var key in params) {
        inputs += "<input name='" + key + "' type='hidden' value='" + params[key] + "'/>";
    }

    $form.append(inputs);
    $form.submit();
}

/*************************/
function ViewPhoto(url, title) {
    if (title == undefined)
        title = "图片预览";
    var json = {
        "title": title, //相册标题
        "id": 123, //相册id
        "start": 0, //初始显示的图片序号，默认0
        "data": [   //相册包含的图片，数组格式
            {
                "alt": "图片名",
                "pid": 666, //图片id
                "src": url, //原图地址
                "thumb": "" //缩略图地址
            }
        ]
    }
    layer.photos({
        photos: json
        , shift: 5 //0-6的选择，指定弹出图片动画类型，默认随机
    });
}

function back() {
    history.go(-1);
}

function backparent() {
    parent.history.go(-1);
}

/*****************************/
String.prototype.format = function (args) {
    if (arguments.length > 0) {
        var result = this;
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                var reg = new RegExp("({" + key + "})", "g");
                result = result.replace(reg, args[key]);
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] == undefined) {
                    return "";
                }
                else {
                    var reg = new RegExp("({[" + i + "]})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
        return result;
    }
    else {
        return this;
    }
}


/**********获取url参数的方法********/
function GetQueryString(name, defaults) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return defaults;
}


/************************************/
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {

            theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);

        }
    }
    return theRequest;
}

/**************************************/
function SetUrlString(url, data) { //设置url参数

    url = url + "?";
    for (var key in data) {
        url += key + "=" + data[key] + "&";
    }
    url = url.substring(0, url.length - 1);
    return url;
}

//js本地图片预览，兼容ie[6-11]、火狐、Chrome17+、Opera11+、Maxthon3+、360浏览器、百度浏览器
function PreviewImage(fileObj, imgPreviewId, divPreviewId) {
    var allowExtention = ".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;
    var extention = fileObj.value.substring(fileObj.value.lastIndexOf(".") + 1).toLowerCase();
    var browserVersion = window.navigator.userAgent.toUpperCase();
    if (allowExtention.indexOf(extention) > -1) {
        if (fileObj.files) {//HTML5实现预览，兼容chrome、火狐7+等
            if (window.FileReader) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    document.getElementById(imgPreviewId).setAttribute("src", e.target.result);
                }
                reader.readAsDataURL(fileObj.files[0]);
            } else if (browserVersion.indexOf("SAFARI") > -1) {
                alert("不支持Safari6.0以下浏览器的图片预览!");
            }
        } else if (browserVersion.indexOf("MSIE") > -1) {
            if (browserVersion.indexOf("MSIE 6") > -1) {//ie6
                document.getElementById(imgPreviewId).setAttribute("src", fileObj.value);
            } else {//ie[7-9]
                fileObj.select();
                if (browserVersion.indexOf("MSIE 9") > -1)
                    fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问
                var newPreview = document.getElementById(divPreviewId + "New");
                if (newPreview == null) {
                    newPreview = document.createElement("div");
                    newPreview.setAttribute("id", divPreviewId + "New");
                    newPreview.style.width = document.getElementById(imgPreviewId).width + "px";
                    newPreview.style.height = document.getElementById(imgPreviewId).height + "px";
                    newPreview.style.border = "solid 1px #d2e2e2";
                }
                newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";
                var tempDivPreview = document.getElementById(divPreviewId);
                tempDivPreview.parentNode.insertBefore(newPreview, tempDivPreview);
                tempDivPreview.style.display = "none";
            }
        } else if (browserVersion.indexOf("FIREFOX") > -1) {//firefox
            var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
            if (firefoxVersion < 7) {//firefox7以下版本
                document.getElementById(imgPreviewId).setAttribute("src", fileObj.files[0].getAsDataURL());
            } else {//firefox7.0+
                document.getElementById(imgPreviewId).setAttribute("src", window.URL.createObjectURL(fileObj.files[0]));
            }
        } else {
            document.getElementById(imgPreviewId).setAttribute("src", fileObj.value);
        }
    } else {
        alert("仅支持" + allowExtention + "为后缀名的文件!");
        fileObj.value = "";//清空选中文件
        if (browserVersion.indexOf("MSIE") > -1) {
            fileObj.select();
            document.selection.clear();
        }
        fileObj.outerHTML = fileObj.outerHTML;
    }
}


function getLampIcon(status) {
    switch (status) {
        case 1:
            return "/Content/icon/l-green.png";
        case 2:
            return "/Content/icon/l-gray.png";
        case 3:
            return "/Content/icon/l-red.png";
    }
}

function getTerIcon(status) {
    switch (status) {
        case 1:
            return "/Content/icon/t-green.png";
        case 2:
            return "/Content/icon/t-gray.png";
        case 3:
            return "/Content/icon/t-red.png";
    }
}

function getStatus(status) {
    switch (status) {
        case 1:
            return "在线";
        case 2:
            return "离线";
        case 3:
            return "故障";
    }
}


//信息窗口
function addClickToPoint(map, marker, title, content) {

    marker.addEventListener("click", function (e) {

        var opts = {
            width: 200,     // 信息窗口宽度
            height: 100,     // 信息窗口高度
            title: title, // 信息窗口标题
            enableMessage: false //设置允许信息窗发送短息
        }

        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow("地址：" + content, opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, point); //开启信息窗口

    });

}


function initTerIcon(map, data) {

    for (var i in data) {

        //标点
        var pt = new BMap.Point(data[i].Lng, data[i].Lat);
        var myIcon = new BMap.Icon(getTerIcon(data[i].Status), new BMap.Size(40, 40));
        var marker = new BMap.Marker(pt, {icon: myIcon});
        var label = new BMap.Label(data[i].Name, {offset: new BMap.Size(20, -10)});
        marker.setLabel(label);
        map.addOverlay(marker);

        var name = data[i].Name + "  (" + getStatus(data[i].Status) + ")";
        addClickToPoint(map, marker, name, data[i].Address);

    }
}

function initLampIcon(map, data) {

    for (var i in data) {

        //标点
        var pt = new BMap.Point(data[i].Lng, data[i].Lat);
        var myIcon = new BMap.Icon(getLampIcon(data[i].Status), new BMap.Size(40, 40));
        var marker = new BMap.Marker(pt, {icon: myIcon});
        var label = new BMap.Label(data[i].Name, {offset: new BMap.Size(20, -10)});
        marker.setLabel(label);
        map.addOverlay(marker);

        var name = data[i].Name + "  (" + getStatus(data[i].Status) + ")";
        addClickToPoint(map, marker, name, data[i].Address);

    }
}

function showPhoto(src, name) {

    if (name == undefined)
        name = "预览";

    var json = {
        "title": "图片预览", //相册标题
        "id": 123, //相册id
        "start": 0, //初始显示的图片序号，默认0
        "data": [   //相册包含的图片，数组格式
            {
                "alt": name,
                "pid": 666, //图片id
                "src": src, //原图地址
                "thumb": "" //缩略图地址
            }
        ]
    }

    layer.photos({
        photos: json
        , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
    });

}


$.fn.extend({
    pageAjax: function (opt) {
        var me = this;
        if (opt.data === undefined) {
            opt.data = {};
        }
        if (opt.page === undefined) {
            opt.page = 1;
        }
        if (opt.limit === undefined) {
            opt.limit = 10;
        }
        function loadData(page, init) {
            opt.page = page;
            var data = opt.data
            data["limit"] = opt.limit;
            data["page"] = opt.page;
            $.getJSON(opt.url, data, function (result) {
                Object.keys(result).forEach(key => {
                    if(!result[key]) result[key] = {};
                })

                if (!init) {
                    var laypage = layui.laypage;
                    var option = {
                        elem: $(me),
                        limit: opt.limit,
                        curr: page,
                        layout: ['prev', 'page', 'next', "skip"],
                        count: result.count != undefined? result.count: result.data.count,
                        jump: function (obj, first) {
                            if (!first) { //首次不执行
                                loadData(obj.curr, false);
                            }
                        }
                    }
                    if(opt.layout) option.layout = opt.layout;
                    laypage.render(option);
                }

                opt.callback(result.code == undefined ? result: (result.data? result.data: result.data));
            });
        }

        loadData(opt.page);
    },
    postAjax: function(opt) {
        var me = this;
        if (opt.data === undefined) {
            opt.data = {};
        }
        if (opt.page === undefined) {
            opt.page = 1;
        }
        if (opt.limit === undefined) {
            opt.limit = 10;
        }
        function loadData(page, init) {
            opt.page = page;
            var data = opt.data
            data["limit"] = opt.limit;
            data["page"] = opt.page;
            jQuery.post(opt.url, data, function (result) {
                Object.keys(result).forEach(key => {
                    if(!result[key]) result[key] = {};
                })
                if (init === undefined) {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: $(me),
                        limit: opt.limit,
                        curr: page,
                        count: result.count != undefined? result.count: result.data.count,
                        jump: function (obj, first) {
                            if (!first) { //首次不执行
                                loadData(obj.curr, false);
                            }
                        }
                    });
                }

                opt.callback(result.code == undefined ? result: result.data);
            }, "json");
        }

        loadData(opt.page);
    }
});

var miniToolBar = [

    'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
    'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
    '| justifyleft justifycenter justifyright justifyjustify |',
    'link unlink | emotion image video  | map',
    '| horizontal print preview fullscreen', 'drafts', 'formula'
];


function getFileName(obj) {
    var pos = obj.lastIndexOf("/") * 1;
    return obj.substring(pos + 1);
}

function getFileExt(obj) {
    var result = /\.[^\.]+/.exec(obj);
    return result;
}

function getFileNameExt(obj) {
    return getFileName(obj)
}

//获取select选项
function getOpt(val, name) {
    return '<option value="' + val + '">' + name + '</option>';
}

//初始化省市区下拉控件
function initArea(opt) {
    var emptyOpt = '<option value="0">--请选择--</option>';
    if (opt !== undefined) {
        var area1 = opt[0];
        var area2 = opt[1];
        var area3 = opt[2];
        var $area1 = $("#" + area1.id);
        var $area2 = $("#" + area2.id);
        var $area3 = $("#" + area3.id);

        //加载省份
        function load1(val) {
            $.getJSON("/area/getAreaList", {pid: 0}, function (data) {

                $area1.empty();
                $area1.append(emptyOpt);

                $area2.empty();
                $area2.append(emptyOpt);

                $area3.empty();
                $area3.append(emptyOpt);

                for (var index in data) {
                    var item = data[index];
                    $area1.append(getOpt(item.id, item.areaname));
                }
                if (val !== undefined)
                    $area1.val(val);
            })
        }

        //加载城市
        function load2(pid, val) {
            $.getJSON("/area/getAreaList", {pid: pid}, function (data) {
                $area2.empty();
                $area2.append(emptyOpt);

                $area3.empty();
                $area3.append(emptyOpt);

                for (var index in data) {
                    var item = data[index];
                    $area2.append(getOpt(item.id, item.areaname));
                }
                if (val !== undefined)
                    $area2.val(val);

            })
        }

        //加载区域
        function load3(pid, val) {
            $.getJSON("/area/getAreaList", {pid: pid}, function (data) {
                $area3.empty();
                $area3.append(emptyOpt);
                for (var index in data) {
                    var item = data[index];
                    $area3.append(getOpt(item.id, item.areaname));
                }
                if (val !== undefined)
                    $area3.val(val);
            })
        }

        //省份下拉框改变事件
        $area1.change(function () {
            var val = $(this).val();
            $area2.empty();
            $area2.append(emptyOpt);
            $area3.empty();
            $area3.append(emptyOpt);
            if(val!=0){
                load2(val);
            }
        });

        //城市下拉框改变事件
        $area2.change(function () {
            var val = $(this).val();
            $area3.empty();
            $area3.append(emptyOpt);
            if(val!=0){
                load3(val);
            }
        });


        load1(area1.val);

        if(area1.val!=undefined && area1.val!=0){
            load2(area1.val,area2.val);
            if(area2.val!==undefined && area2.val!=0){
                load3(area2.val,area3.val);
            }
        }

    }
}

function currentTime() {
    var result;

    var now = new Date();

    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日

    var dayOfWeekStr;
    var dayOfWeek = now.getDay(); //获取当前星期X(0-6,0代表星期天)
    switch (dayOfWeek) {
        case 0:
            dayOfWeekStr = "星期日";
            break;
        case 1:
            dayOfWeekStr = "星期一";
            break;
        case 2:
            dayOfWeekStr = "星期二";
            break;
        case 3:
            dayOfWeekStr = "星期三";
            break;
        case 4:
            dayOfWeekStr = "星期四";
            break;
        case 5:
            dayOfWeekStr = "星期五";
            break;
        case 6:
            dayOfWeekStr = "星期六";
            break;
    }

    result = year + "年" + month + "月" + day + "日" + " " + dayOfWeekStr;
    return result;
}

Array.prototype.distinct = function() {
    this.sort();
    for(var i=0; i < this.length-1; i++) {
        if(this[i]==this[i+1]) {
            this[i]=undefined
        }
    }
    return this.filter(e=>e)
}
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt)
{ //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

function checkAlert(obj, msg) {
    if(obj.is(':hidden')) {
        checkAlert(obj.parent(), msg);
        return;
    }
    var top = obj.offset().top-40;
    var left = obj.offset().left;
    $("body").append(`<div class="layui-layer layui-layer-tips" id="layui-layer3" type="tips" times="3" showtime="100000" contype="object" style="z-index: 19991020; position: absolute; left: `+left+`px; top: `+top+`px;">
                               <div id="" class="layui-layer-content" style="background-color: rgb(53, 149, 204);">
                                ${msg? msg: "此处不为空"}
                                <i class="layui-layer-TipsG layui-layer-TipsT" style="border-right-color: rgb(53, 149, 204);"></i>
                               </div>
                               <span class="layui-layer-setwin"></span>
                              </div>`)
}

function refresh(result, callback, time, msg) {
    if(typeof msg =="undefined" || msg) {
        layer.msg(result.msg);
    }
    if(result.code==200) {
        if(typeof time == "undefined" ) {
            setTimeout(()=>{
                callbackFunction();
            }, 2000);
        } else if(time > 0) {
            setTimeout(()=>{
                callbackFunction()
            }, time);
        } else {
            callbackFunction()
        }
    }

    function callbackFunction() {
        if(typeof callback == "function") {
            callback();
        } else {
            history.go(0);
        }
    }
}

function setCookie(name,value, expires) {
    var exp = new Date();
    exp.setTime(exp.getTime() + (expires || 24*60*60*1000));
    document.cookie = name + "="+ value + ";expires=" + exp.toGMTString() + ";path=/";
}
//读取cookies
function getCookie(name) {
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg)) return unescape(arr[2]);
    else return null;
}
//删除cookies
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

$(function () {
    var names = "admin,img,teacher,teacher_name,teacher_img,rights,scl_code,student,student_name,student_img,ss_id";
    names.split(",").forEach(e=>{
        var cookie = getCookie(e);
        if(cookie) {
            delCookie(e)
            setCookie(e, cookie);
        }
    })
})

function xmlToJson(xml) {
    // Create the return object
    var obj = {};
    if (xml.nodeType == 1) { // element
        // do attributes
        if (xml.attributes.length > 0) {
            obj["@attributes"] = {};
            for (var j = 0; j < xml.attributes.length; j++) {
                var attribute = xml.attributes.item(j);
                obj["@attributes"][attribute.nodeName] = attribute.nodeValue;
            }
        }
    } else if (xml.nodeType == 3) { // text
        obj = xml.nodeValue;
    }
    // do children
    if (xml.hasChildNodes()) {
        for(var i = 0; i < xml.childNodes.length; i++) {
            var item = xml.childNodes.item(i);
            var nodeName = item.nodeName;
            if (typeof(obj[nodeName]) == "undefined") {
                obj[nodeName] = xmlToJson(item);
            } else {
                if (typeof(obj[nodeName].length) == "undefined") {
                    var old = obj[nodeName];
                    obj[nodeName] = [];
                    obj[nodeName].push(old);
                }
                obj[nodeName].push(xmlToJson(item));
            }
        }
    }
    return obj;
};

// 方法二
$(document).on("mousewheel DOMMouseScroll", ".layui-layer-phimg", function (e) {
    $("#layui-layer-photos").css("height", "auto")
    window.ele = e;
    var delta = ele.originalEvent.wheelDelta / Math.abs(ele.originalEvent.wheelDelta);
    var photos = $(".layui-layer-photos");
    var oldWidth = photos.width();
    var oldHeight = photos.height();
    var width = oldWidth * (1 + delta * 0.1);
    var height = oldHeight * (1 + delta * 0.1);

    if(delta<-1 && width<100) return;
    photos.css("width", width+"px");
    photos.css("height", height+"px");

    var oldLeft = parseFloat(photos.css("left").replaceAll("px", ""));
    var oldTop = parseFloat(photos.css("top").replaceAll("px", ""));
    var clientX = ele.originalEvent.clientX;
    var clientY = ele.originalEvent.clientY;
    var innerWidth = window.innerWidth;
    var innerHeight = window.innerHeight;

    var top = oldTop - (height-oldHeight)*clientY/innerHeight;
    var left = oldLeft - (width-oldWidth)*clientX/innerWidth;
    photos.css("top", top);
    photos.css("left", left);
})
function isMobile(){
    //获取浏览器navigator对象的userAgent属性（浏览器用于HTTP请求的用户代理头的值）
    var info = navigator.userAgent;
    //通过正则表达式的test方法判断是否包含“Mobile”字符串
    var isPhone = /mobile/i.test(info);
    //如果包含“Mobile”（是手机设备）则返回true
    return isPhone
}