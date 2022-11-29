$.get("/index/menu", function(result) {
	$(".i-l-b").html(template("menuTPL", {list: result.data.menuList}))
	$(".layui-icon-friends").text(result.data.name);

	$(".l-b-i").click(function() {
		$(".l-b-i").removeClass("l-b-i-select");
		$(this).addClass("l-b-i-select");
		localStorage.setItem("current_menu_src", $(this).attr("src"))
		$("iframe").attr("src", $(this).attr("src"))
	});
	var current_menu = $(`.l-b-i[src='${localStorage.getItem("current_menu_src")}']`)
	current_menu = current_menu.get(0)? current_menu: $(".l-b-i").eq(0)
	current_menu.click();
})

$(".h-c-logout").click(function() {
	localStorage.removeItem("Authorization");
	layer.msg("退出成功");
	setTimeout(()=>location.href="/login", 1000)
});
