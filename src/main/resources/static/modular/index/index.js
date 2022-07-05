$.get("/index/menu", function(result) {
	$(".i-l-b").html(template("menuTPL", {list: result.data.menuList}))
	$(".layui-icon-friends").text(result.data.name);

	$(".l-b-i").click(function() {
		$(".l-b-i").removeClass("l-b-i-select");
		$(this).addClass("l-b-i-select");
		$("iframe").attr("src", $(this).attr("src"))
	});
	$(".l-b-i").eq(0).click();
})

$(".h-c-logout").click(function() {
	sessionStorage.removeItem("Authorization");
	layer.msg("退出成功");
	setTimeout(()=>location.href="/login", 1000)
});