var table = layui.table,
	form = layui.form,
	laydate = layui.laydate;
var sub_titleId_html = `<div class="layui-form-item" id="form_sub_titleId">
                <label class="layui-form-label">面试子标题</label>
                <div class="layui-input-block">
                    <select name="subTitle" >%s</select>
                </div>
            </div>`

$(function() {
	page();
})

form.on("select(titleId)", function(obj) {
	page();
	getSubTitle(obj.value, (data)=>{
		var subTitle = $("select[name=subTitleId]");
		var html = "<option value>--子标题--</option>";
		data.forEach(e=>{
			html += `<option value="${e.id}">${e.name}</option>`
		})
		subTitle.html(html);
		form.render();
	})
})
form.on("select(subTitleId)", function(res) {
	page();
})


function page() {
	table.render({
		elem: '#test'
		,url:'/daily/interview/page'
		,toolbar: '#toolbarDemo'
		,title: '用户数据表'
		, where: $("#searchForm").serializeObject()
		,parseData: function(res) {
			res.code = 0;
			return res;
		}
		,cols: [[
			{type:'checkbox'}
			,{title: "序号", type: "numbers"}
			,{title:'面试标题', field: "title", width: 100}
			,{title:'标题', templet: (obj)=>{
					return `<div class="p-b-c" data-index="${obj.LAY_TABLE_INDEX}">${obj.name}</div>`
				}}
			,{title: '内容', field: 'substr_context'}
			,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
		]]
		,page: true
		, done: function (res, curr, count) {
			$(".p-b-c").click(function() {
				var _this = $(this);
				var id = res.data[_this.attr("data-index")].id;
				$.get(`/daily/interview/get/${id}`, function(res) {
					var data = res.data;
					if($("#searchForm #name").val()) {
						data.context = addBackgroundColor(data.context, $("#searchForm #name").val())
					}
					layer.open({
						type: 1,
						title: data.name,
						content: `<div class="editor-content-view">${data.context}</div>`,
						area: ["700px", "500px"],
						maxmin: true,
						shadeClose: true,
						success: function (layero, index, that) {
							layero.find("img").css("width", "100%")
						},
						full: function (){
							window.parent.fullScreen()
						},
						restore: function () {
							window.parent.smallScreen();
						}
					})
					layer.photos({
						photos: ".editor-content-view"
						,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
					});

				})
			});

		}
	});
}

table.on('tool(test)', function (obj) {
	if (obj.event === 'edit') { //编辑
		$.get("/daily/interview/get/"+obj.data.id, function(result) {
			openWin(result.data)
		});
	}
});
//头工具栏事件
table.on('toolbar(test)', function(obj){
	var checkStatus = table.checkStatus(obj.config.id);
	switch(obj.event){
		case 'create':
			openWin({titleId: $("select[name=titleId]").val()})
			break;
	};
});
function openWin(model) {
	var searchName = $("#searchForm #name").val();
	var index = layer.open({
		type: 1,
		area: ['700px', '585px'],
		title: model.id? "编辑":"创建" + '面试题',
		shadeClose: true, //点击遮罩关闭
		content: template("tpl", {model: model})
		,btn: ['提交','取消']
		,btnAlign: 'r'
		,maxmin: true
		,skin: 'layer-ext-myskin'
		,yes:function () {
			$("textarea[name=context]").val(editor.getHtml());
			$("#form").checkCommit({
				url: "/daily/interview/save",
				index: index
			})
		}
		,full: function(layero, index, that) {
			$("#editor-text-area").css("height", Math.max(layero.height()-267-$("#editor-toolbar").height(), 200));
			window.parent.fullScreen()
		}
		,restore: function(layero, index, that) {
			$("#editor-text-area").css("height", "200px")
			window.parent.smallScreen()
		}
	});
	if(model.subTitleId) {
		getSubTitle(model.titleId, data=>{
			if(data && data.length>0) {
				form.val("form", {"titleId": model.titleId, "subTitleId": model.subTitleId})
				$("#form_titleId").after(sub_titleId_html.replace("%s", data.map(e=>`<option value="${e.id}">${e.name}</option>`)), );
			}
			form.render();
		})
	} else {
		form.render();
	}

	const E = window.wangEditor
	// 切换语言
	const LANG = location.href.indexOf('lang=en') > 0 ? 'en' : 'zh-CN'
	E.i18nChangeLanguage(LANG)
	window.editor = E.createEditor({
		selector: '#editor-text-area',
		html: model.context,
		config: {
			placeholder: 'Type here...',
			MENU_CONF: {
				uploadImage: {
					fieldName: 'your-fileName',
					base64LimitSize: 10 * 1024 * 1024 // 10M 以下插入 base64
				}
			},
			onChange(editor) {
			}
		}
	})
	var toolbar = E.createToolbar({
		editor,
		selector: '#editor-toolbar',
		config: {}
	})
	$("#editor-text-area").css("height", "200px")

	form.on("select(tpl_titleId)", function (obj){
		getSubTitle(obj.value, data=>{
			$("#form_sub_titleId").remove();
			if(data && data.length>0) {
				$("#form_titleId").after(sub_titleId_html.replace("%s", data.map(e=>`<option value="${e.id}">${e.name}</option>`)), );
				form.render();
			}
		})
	});
}

function getSubTitle(id, callback) {
	$.get("/daily/interview/getSubTitle/"+id, function(res) {
		if(callback) {
			callback(res.data)
		}
	})
}


function addBackgroundColor(html, pattern) {
	var reg = new RegExp(`(?<!<\\/[^>]*>)\\b${pattern}\\b(?![^<]*>)`, 'gi')

// 使用正则表达式在文本中查找匹配项
	var matches = [];
	var match;
	while ((match = reg.exec(html)) !== null) {
		matches.push(match.index)
	}

	var returnHtml="";
	if(matches.length==0) {
		return html;
	}
	returnHtml+=html.substring(0, matches[0]);
	for (let i = 0; i < matches.length; i++) {
		returnHtml += `<span style="background-color: yellow;">${pattern}</span>` + html.substring(matches[i]+pattern.length, i<matches.length-1? matches[i+1]: html.length)
	}
	return returnHtml;
}