var table = layui.table,
	form = layui.form,
	laydate = layui.laydate;

$(function() {
	page();
})

form.on("select(titleId)", function(obj) {
	page();
	$.get("/daily/interview/getSubTitle/"+obj.value, function(res) {
		console.log(res);
		var subTitle = $("select[name=subTitleId]");
		var html = "<option value>--子标题--</option>";
		res.data.forEach(e=>{
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
			,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
		]]
		,page: true
		, done: function (res, curr, count) {
			$(".p-b-c").click(function() {
				var _this = $(this);
				var id = res.data[_this.attr("data-index")].id;
				$.get(`/daily/interview/get/${id}`, function(res) {
					var data = res.data;
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
	form.val("form", {"titleId": model.titleId})

	form.render();

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

}