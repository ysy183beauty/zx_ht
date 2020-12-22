<!--页脚开始-->


<!-- --------------------------------new------------------------------- -->

  <!--Footer-->
<div class="foot">
			<p class="text-center">主办单位：“智慧大理”项目建设管理办公室</p>
			<p class="text-center">备案号：滇ICP备15008445号 云安备53290102000378号</p>
			<div class="text-center">
				<ul>
					<li><img src="${ctx}/r/cms/www/red/img/images/i1.png">征信备案</li>
					<li><img src="${ctx}/r/cms/www/red/img/images/i2.png">党政机关</li>
					<li><a target="_about" href="/jeeadmin/jeecms/login.do">管理登录</a></li>
				</ul>
			</div>
</div>
<script>
    //    判断用户是否登录
    function login(url,param,domId) {
		<#if user??>
			publicityNavi(url,param,domId);
		<#else>
			$("#nav li").bind("click",function(){
				$(this).find("a").attr("href","/login.jspx");
			});
		</#if>
    }
</script>
  <!--Footer-->





