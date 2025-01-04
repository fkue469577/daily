var table = layui.table,
	form = layui.form,
	laydate = layui.laydate;
var sub_titleId_html = `<div class="layui-form-item" id="form_sub_titleId">
                <label class="layui-form-label">面试子标题</label>
                <div class="layui-input-block">
                    <select name="subTitleId" >%s</select>
                </div>
            </div>`
var titleList;

$(function() {
	$.get("/daily/interview/condition", (result)=>{
		titleList = result.data.titleList;
	})
	page();
})

form.on("select(titleId)", function(obj) {
	$(".searchForm_subTitleId").remove();
	var children = titleList?.filter(e=>e.id==obj.value)?.[0]?.children;
	if(children) {
		var html = children.map(e=>`<option value="${e.id}">${e.name}</option>`).join("")
		html = `<div class="searchForm_subTitleId"><select name="subTitleId" lay-filter="subTitleId">
						<option value="">--子标题--</option>
						${html}
					</select></div>`;

		$(".searchForm_titleId").after(html);
		form.render(null, 'searchForm');
	}
	page();
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
			,{title: '内容', templet:(obj)=>{
				return obj.substr_context;
				}}
			,{title: "操作", width: 60, align: 'center', toolbar: '#barDemo' }
		]]
		,page: true
	});
	table.on('row(test)', (obj)=>{
		$.get(`/daily/interview/get/${obj.data.id}`, function(res) {
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
					layero.find("img").css("width", "100%");
					// if(isMobile()) {
					// 	setTimeout(()=>{
					// 		$(".layui-layer-max").click();
					// 		$(".layui-layer-title").css("font-size", "32px");
					// 		$(".layui-layer-content").css("font-size", "32px");
					// 	}, 100)
					// }
				},
				full: function (){
					window.parent.fullScreen?.()
				},
				restore: function () {
					window.parent.smallScreen?.();
				}
			})
			layer.photos({
				photos: ".editor-content-view"
				,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
			});


		})
		window._obj = obj;
	})
}

table.on('tool(test)', function (obj) {
	if (obj.event === 'edit') { //编辑
		$.get("/daily/interview/get/"+obj.data.id, function(result) {
			openWin(result.data)
		});
	}
	// if (obj.event === 'del') { //编辑
	// 	$.get("/daily/interview/get/"+obj.data.id, function(result) {
	// 		openWin(result.data)
	// 	});
	// }
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
			commitForm();
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
		var children = titleList.filter(e=>e.id==model.titleId)?.[0]?.children;
		if(children) {
			$("#form_titleId").after(sub_titleId_html.replace("%s", `<option value="">--子标题--</option>`+children.map(e=>`<option value="${e.id}">${e.name}</option>`)), );
			form.render(null, "form");
		}
		form.val("form", {"titleId": model.titleId, "subTitleId": model.subTitleId})
	} else {
		form.val("form", {"titleId": model.titleId})
		form.render(null, "form");
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
		$("#form_sub_titleId").remove();
		var children = titleList.filter(e=>e.id==obj.value)?.[0]?.children;
		if(children) {
			$("#form_titleId").after(sub_titleId_html.replace("%s", `<option value="">--子标题--</option>`+children.map(e=>`<option value="${e.id}">${e.name}</option>`)), );
			form.render(null, "form");
		}
	});

	function submit(callback) {
		$("textarea[name=context]").val(editor.getHtml());
		var data = $("#form").serializeObject()
		if(data.subTitleId) {
			data.titleId = data.subTitleId;
		}
		$.postJson("/daily/interview/save", data, callback)
	}
	function commitForm(callback) {
		$("textarea[name=context]").val(editor.getHtml());
		$("#form").check(()=>{
			submit((data)=>{
				if(data.subTitleId) {
					data.titleId = data.subTitleId;
				}

				layer.closeAll()
				$(".layui-table-page .layui-laypage-btn").click();
			})
		});
	}


	$(document).off("keydown");
	$(document).on("keydown", (event)=>{
		if(event.metaKey && event.key=="Enter") {
			commitForm();
		}
		if(event.metaKey && event.key=="s") {
			submit((res)=>{
				$("#form input[name=id]").val(res.data)
			});
			event.preventDefault()
		}
		if(!event.metaKey&&event.keyCode==13) {
			$(".layui-layer-dialog .layui-layer-btn0").click();
		}
	});
}


function addBackgroundColor(html, pattern) {
	window.html = html;
	window.pattern=pattern
	var reg = new RegExp(`(?<!<\\/[^>]*>)${pattern}(?![^<]*>)`, 'gi')

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

$(document).on("keydown", "#searchForm #name", (event)=>{
	if(event.keyCode==13) {
		page();
	}
});
