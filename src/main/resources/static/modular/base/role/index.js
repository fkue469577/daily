var roleId = "";
$(function () {
	$(".jslb-listwrap").eq(0).click();
})

//        角色列表点击
$(document).on("click", ".jslb-listwrap", function() {
	var _this = $(this);
	_this.parents(".jslb").siblings().find(".jslb-list").slideUp();
	_this.parents(".jslb").siblings().find(".jslb-listwrap").removeClass("jslb-listwrappre");
	if(_this.hasClass("jslb-listwrappre")){
		_this.siblings().stop().slideUp();
		_this.removeClass("jslb-listwrappre");
	}else{
		_this.siblings().stop().slideDown();
		_this.addClass("jslb-listwrappre");
		$(".flex.gm-wrap").addClass("hidden");

		$(".submit").show();
		roleId = _this.attr("data-id");
		loadMenuAndPermission(roleId, _this.attr("data-tips"))
	}
});

//        权限3级点击
$(document).on("click", ".permission-3", function() {

	var _this = $(this);
	if(_this.hasClass("qx-per")) {
		removePermission(_this.attr("data-id"), function (result) {
			refresh(result, function () {
				_this.removeClass("qx-per");
			}, 0, false)
		})
	} else{
		if(!_this.parent().prev().hasClass("qx-per")) {
			layer.msg("父菜单必须选中才能编辑权限");;
			return;
		}

		addPermission(_this.attr("data-id"), function (result) {
			refresh(result, function () {
				_this.addClass("qx-per");
			}, 0, false)
		})
	}
});
//        权限1级点击
$(document).on("click", ".permission-1", function() {

	var _this = $(this);
	if(_this.hasClass("qx-per")) {
		removeRole(_this.attr("data-id"), (result)=>{
			refresh(result, ()=>{
				_this.removeClass("qx-per")
				_this.next().find(".khgl-wrap").removeClass("qx-per");
				_this.next().find(".qxxz-con").removeClass("qx-per");
			}, 0, false);
		})
	} else {
		addRole(_this.attr("data-id"), (result)=> {
			refresh(result, () => {
				_this.addClass("qx-per")
				_this.next().find(".khgl-wrap").addClass("qx-per");
				_this.next().find(".qxxz-con").addClass("qx-per");
			}, 0, false)
		})
	}
});

function removeRole(menuId, callback) {
	$.postJson("/system/role/removeMenuRelation", {roleId: roleId, menuId: menuId}, function (result) {
		callback(result);
	});
}
function addRole(menuId, callback) {
	$.postJson("/system/role/addMenuRelation", {roleId: roleId, menuId: menuId}, function (result) {
		callback(result);
	});
}
function removePermission(permissionId, callback) {
	$.postJson("/system/role/removePermissionRelation", {roleId: roleId, permissionId: permissionId}, function (result) {
		callback(result);
	})
}
function addPermission(permissionId, callback) {
	$.postJson("/system/role/addPermissionRelation", {roleId: roleId, permissionId: permissionId}, function (result) {
		callback(result);
	})
}

//        全部权限点击
$(document).on("click", ".qbqx-wrap", function() {
	$(this).toggleClass("qx-per");
	if($(this).hasClass("qx-per")){
		$(".qxxz-con").addClass("qx-per");
		$(".khgl-wrap").addClass("qx-per");
		$(".qxyj-wrap").addClass("qx-per");
	}else{
		$(".qxxz-con").removeClass("qx-per");
		$(".khgl-wrap").removeClass("qx-per");
		$(".qxyj-wrap").removeClass("qx-per");
	}
});
//        数据权限点击
$(document).on("click", ".sjqx-wrap", function() {
	$(this).siblings().removeClass("sjqx-wrappre");
	$(this).addClass("sjqx-wrappre");
});
//        修改名称点击
$(document).on("click", ".xgmc", function() {
	$(this).parents(".jslb-list").find(".gm-wrap").toggleClass("hidden");
});

//添加角色
$(document).on("click", ".xzjs-wrap", function () {
	openRole({});
})

//      角色名称修改
$(document).on("click", ".edit-button", function () {
	var _this = $(this);
	$(_this.parent()).checkCommit({url: "/system/role/save"})
})

//      编辑角色
$(document).on("click", ".edit", function () {
	$(".submit").show();

	var _this = $(this);
	roleId = _this.attr("data-id");
	loadMenuAndPermission(roleId, _this.attr("data-tips"))
})

function loadMenuAndPermission(roleId, tips) {
	$.get("/system/menu/getMenuListExpendRole/"+roleId, {}, function (result) {
		$(".tree-container").html(template("treeTPL", {list: result.data}));
		$("#tips").text(tips);
		layer.closeAll("dialog");
	});
}

//      删除角色
$(document).on("click", ".del", function () {
	var _this = $(this);
	layer.confirm("是否删除该角色?", function () {
		$.get("/system/role/remove/" + _this.parents(".jslb").attr("data-id"), function (result) {
			refresh(result)
		});
	})
})

//      添加权限
$(document).on("click", ".qxxz-con-add", function () {
	var _this = $(this);
	window._this = _this;
	$("#permissionForm").check(function () {
		layer.open({
			type: 1,
			area: ['auto', 'auto'],
			title: '添加权限',
			shadeClose: true, //点击遮罩关闭
			content: template("permissionTPL", {permission: {menu_id: _this.attr("data-id")}})
			, btn: ['提交', '取消']
			, yes: function () {
				layer.confirm("确定提交?", function () {
					$.post("/permission/addOrUpdate", $("#permissionForm").serializeObject(), function (result) {
						layer.alert(result.msg, function() {
							if(result.code) {
								var form = $("#permissionForm").serializeObject()
								_this.before(`<div class="flex align qxxz-con"><i class="qxxz-icon"></i>`+form.name+`</div>`);
								_this.parent().prev().removeClass("qx-per");
							}
							layer.closeAll();
						});
					});
				})
			}
		});
	});
})

function openRole(role) {
	layer.open({
		type: 1,
		area: ['auto', 'auto'],
		title: '新建角色',
		shadeClose: true, //点击遮罩关闭
		content: template("addTPL", {role: role})
		, btn: ['提交', '取消']
		, yes: function () {
			$("#addForm").checkCommit({url: "/system/role/insert"})
		}
	});
}
