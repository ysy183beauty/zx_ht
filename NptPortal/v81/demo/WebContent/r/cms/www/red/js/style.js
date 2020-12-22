$(function() {
	$('#change_color li').click(function() {
		var index = $(this).index();
		console.log(index)
		if(index == 0) {
			$('.zx_tab .hd').css('border-bottom', '1px solid #2e71b8');
		}
		if(index == 1) {
			$('.zx_tab .hd').css('border-bottom', '1px solid red');
			$('.page').removeClass('black');
			$('.page').addClass('red');
		}
		if(index == 2) {
			$('.zx_tab .hd').css('border-bottom', '1px solid #000');
			$('.page').removeClass('red');
			$('.page').addClass('black');
		}
	})
	var window_height = $(window).height();
	var body_height = $('body').height()
	if(body_height < window_height) {
		$('.foot').css('position', 'fixed');
	} else {
		$('.foot').css('position', 'static');
	}
	$('.search_li li').click(function() {
		$('.search_li li').removeClass('active');
		$(this).addClass('active');
		console.log("1");
		var id=$(this).attr("id");
		if(id=="company"){
            $("#queryCondition").attr("placeholder","请输入企业名称，法定代表人姓名，工商注册号，组织机构代码");
		}
		else if(id=="person"){
            $("#queryCondition").attr("placeholder","请输入个人姓名");
		}
		else if(id=="site"){
            $("#queryCondition").attr("placeholder","请输入搜索关键字");
		}
	})
});