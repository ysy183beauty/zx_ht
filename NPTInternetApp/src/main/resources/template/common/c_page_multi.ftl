<#include "./common.ftl">
<#--<link rel="stylesheet" type="text/css" href="${wctx}/pub/CreditStyle/pagination/pagination.css" />-->
<script type="text/javascript" src="${wctx}/pub/CreditStyle/pagination/jquery.pagination.js"></script>
<style type="text/css">
	[data-role='pagination']{
		text-align: right;
	}
</style>
<script type="text/javascript">

	/**
	* 分页对象构造函数
	* @param container 分页容器
	* @param url 分页内容刷新地址
	* @param param 分页内容刷新参数
	* @param callback 分页列表初始化完成后的回调函数
	*/
	function Pagination(container, url, param, callback) {
		this.container = $(container).eq(0);
		this.url = url;
		this.param = param || {};
		this.callback = callback;
		
		this.init();
	}
	Pagination.prototype = {
		// 内容容器
		getContent: function(){
			return this.container.find("[data-role='pageData']").eq(0);
		},
		// 分页容器
		getPagination: function(){
			return this.container.find("[data-role='pagination']").eq(0);
		},
		// 分页参数所在元素
		getPageElement: function(){
			return this.container.find("[data-role='pageElement']").eq(0);
		},
		// 从分页参数元素上获取具体值
		getPageValue: function(name, defaultValue){
			var value = this.getPageElement().attr("data-" + name);
			if(value)
				return value;
			else
				return defaultValue || "";
		},
		// 初始化分页
		init : function() {

			var pageNo = this.param.currPage || 1;

			var totalCount = this.getPageValue("totalCount", 0);
			totalCount = totalCount.replace(/,/g, "");
			
			var totalPage = this.getPageValue("totalPage", 1);
			totalPage = totalPage.replace(/,/g, "");
			var pageSize = this.getPageValue("pageSize", 1);
			pageSize = pageSize.replace(/,/g, "");
			
			if (pageNo > totalPage && totalPage > 0) {
				this.refresh(totalPage);
				return;
			}
			this.getPagination().pagination(totalCount, {
				callback : this.selectPage,
				prev_text : "上一页",
				next_text : "下一页 ",
				items_per_page : pageSize,// 每页数量
				num_edge_entries : 2,//两侧首尾分页条目数
				num_display_entries : 8,//连续分页主体部分分页条目数
				current_page : pageNo - 1,
				pageObj: this
			//当前页索引
			});
			var showSecurity = window.showSecurity;
			if($.isFunction(showSecurity)){
				showSecurity();
			}
			if ($.isFunction(this.callback)) {
				this.callback();
			}
		},
		// 外部事件触发刷新页面
		refresh: function(pageNo, searchParam){
			if (typeof pageNo == "object") {
				searchParam = pageNo;
				pageNo = undefined;
			}
			var self = this;
			var pageNo = pageNo || self.param.currPage;
			if (searchParam) {
				self.param = searchParam;
			}
			self.param.currPage = pageNo || 1;
			$.ajax({
				url : self.url,
				async : false,
				type : "post",
				data : self.param,
				success : function(result, code, dd) {
					self.getContent().html(result);
					self.init();
				},
				error : function() {
				}
			});
			return false;
		},
		// 分页按钮点击触发刷新页面
		selectPage: function(pageIndex, jq) {
				this.pageObj.refresh(pageIndex + 1);
			return true;
		}
	}
</script>