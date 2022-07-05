$(function () {
	$("#kaptcha").on('click', function () {
		$("#kaptcha").attr('src', '/kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
	});

	$(".m-b").click(function () {
		$("input[name=password]").val($.md5($("#password").val()));
		$("form").checkCommit({url: "/login", unalert: true,
			callback: function(data) {
				sessionStorage.setItem("Authorization", data.accessToken)
				location.href = "/";
			}})
	});
});
