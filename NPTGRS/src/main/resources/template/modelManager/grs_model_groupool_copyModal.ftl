<#include "/template/Credit_Common/common.ftl">
<#if _MODEL_GROUP_LIST??&&_MODEL_GROUP_POOL_LIST??>
    <#assign base = _BASE_MODEL_LIST>
    <#assign group = _MODEL_GROUP_LIST>
    <#assign pool = _MODEL_GROUP_POOL_LIST>
    <#assign activeModal = _BASE_ACTIVE_MODAL>
    <#assign activeGroup = _BASE_ACTIVE_MODAL_GROUP>
    <#assign activePool = _BASE_ACTIVE_MODAL_GROUP_POOL>
<link rel="stylesheet" href="${wctx}/pub/CreditStyle/transfer/style.css" />
<script>
    var addData=[];
    (function($){
        $.fn.transferItem = function(options){
            function transferItem($this,options){
                this.init($this,options)
            }

            transferItem.prototype = {
                init: function($this,options){
                    this.el = $this;
                    this.ops = options;
                    this.transferAllCheck = this.el.find(".transfer-all-check");
                    this.switchBtn = this.el.find(".to-switch");
                    this.allCheckedBoxes = this.el.find(".tyue-checkbox-input");
                    this.alldivBoxes = this.el.find(".ty-tree-div");

                    this.checkBoxEvent();
                    this.allCheckEvent();
                    this.switchEvent();
                    this.checkBoxesDbClick();
                    time = null;
                },
                //按钮切换事件
                switchEvent : function(){
                    var that = this;
                    this.switchBtn.on("click",function(){
                        var id=$('#targetSelect').val();
                        if(!id){
                            alert('请选择需要接收数据类的目标模型')
                            return;
                        }
                        that.transferAllCheck.removeAttr("checked","checked");
                        var _this = $(this);

                        var a_tagClass = null;
                        var direction='left';
                        if(_this.hasClass("ty-transfer-btn-toright")){
                            findCheckbox = _this.parents(".ty-transfer").find(".transfer-list-left li");
                            inputCheckbox = _this.parents(".ty-transfer").find(".transfer-list-right ul");
                            a_tagClass = "ty-transfer-btn-toright";
                        }else{
                            direction='right';
                            findCheckbox = _this.parents(".ty-transfer").find(".transfer-list-right li");
                            inputCheckbox = _this.parents(".ty-transfer").find(".transfer-list-left ul");
                            a_tagClass = "ty-transfer-btn-toleft";
                        }
                        var checkBox = findCheckbox.find(":checked");
                        if(checkBox != 0){
                            var arrVal = [];
                            checkBox.each(function(){
                                $(this).removeAttr("checked");
                                var id=$(this).attr("id");
                                if(direction==='left'){
                                    addData.push(id);
                                }else {//从右往左移移除对应的id
                                    remove(addData, id);
                                }

                                var appendText = $(this).parents(".ty-tree-div").parent("li");
                                arrVal.push(appendText);
                                that.removeActiveEvent(a_tagClass,"active");
                                that.addActiveEvent(a_tagClass,"disabled");
                            });
                            inputCheckbox.prepend(arrVal);
                        }

                    })
                },

                //所有标签单击选中事件
                checkBoxEvent : function(){
                    var that = this;
                    this.allCheckedBoxes.on("click",function(){
                        clearTimeout(time);
                        time = setTimeout(function(){
                            var classNames = that.checkTagClass($(this));
                            if($(this).is(":checked")){
                                that.removeActiveEvent(classNames[0],"disabled");
                                that.addActiveEvent(classNames[0],"active");
                                if(!$("."+classNames[1]).hasClass("active")){
                                    that.addActiveEvent(classNames[1],"disabled");
                                }
                            }else{
                                var siblingsTag = $(this).parents(".ty-tree-div").parent("li").siblings("li").find(".tyue-checkbox-input");
                                if(!siblingsTag.is(":checked")){
                                    that.removeActiveEvent(classNames[0],"active");
                                    that.addActiveEvent(classNames[0],"disabled");
                                    $(this).parents(".ty-transfer").find(".transfer-all-check").removeAttr("checked","checked")
                                }
                            }
                        }.bind(this),200);

                    });
                },

                //所有按钮双击事件
                checkBoxesDbClick : function(){
                    var that = this;

                    this.alldivBoxes.bind("dblclick",function(event){
                        var _this = $(this);
                        $(this).removeAttr("checked");

                        if(_this.parents(".ty-transfer-list").hasClass("transfer-list-left")){
                            inputCheckbox = _this.parents(".ty-transfer").find(".transfer-list-right ul");
                            btnCheckbox = that.el.find(".ty-transfer-btn-toright");
                        }else{
                            inputCheckbox = _this.parents(".ty-transfer").find(".transfer-list-left ul");
                            btnCheckbox = that.el.find(".ty-transfer-btn-toleft");
                        }

                        var siblingsTag = _this.parent("li").siblings("li").find(".tyue-checkbox-input");
                        if(!siblingsTag.is(":checked")){
                            btnCheckbox.removeClass("active");
                        }

                        var appendText = _this.parent("li");



                        inputCheckbox.prepend(appendText);
                        appendText.find(".tyue-checkbox-input").removeAttr("checked");

                    });
                },

                //全选按钮事件
                allCheckEvent : function(){
                    var that = this;
                    this.transferAllCheck.on("click",function(){
                        var checkBoxs = $(this).parents(".ty-transfer-list-foot").siblings(".ty-transfer-list-body").find(":checkBox");

                        var classNames = that.checkTagClass($(this));

                        if($(this).prop("checked") == true){
                            checkBoxs.attr("checked","checked");
                            that.removeActiveEvent(classNames[0],"disabled");
                            that.addActiveEvent(classNames[0],"active");
                            if(!$("."+classNames[1]).hasClass("active")){
                                that.addActiveEvent(classNames[1],"disabled");
                            }
                        }else{
                            checkBoxs.removeAttr("checked","checked");
                            that.removeActiveEvent(classNames[0],"active");
                            that.addActiveEvent(classNames[0],"disabled");
                        }
                    })
                },
                //按钮添加class事件
                checkTagClass : function($that){
                    var parentsTransfer = $that.parents(".ty-transfer-list");
                    var tagClass = null;
                    var tagRemoveClass = null;

                    if(parentsTransfer.hasClass("transfer-list-left")){
                        tagClass = "ty-transfer-btn-toright"
                        tagRemoveClass = "ty-transfer-btn-toleft";
                    }else{
                        tagClass = "ty-transfer-btn-toleft"
                        tagRemoveClass = "ty-transfer-btn-toright";
                    }
                    return [tagClass,tagRemoveClass];
                },
                addActiveEvent : function(position,addClasses){
                    this.el.find("."+position).addClass(addClasses);
                },
                removeActiveEvent : function(position,addClasses){
                    this.el.find("."+position).removeClass(addClasses);
                },
            }
            new transferItem(this,options)
        }

        /*
          * 穿梭框 end
          */

    })(jQuery);

    $(function () {
        $("#ued-transfer").transferItem();
    });
</script>
<div class="ty-transfer mt20 ml20" id="ued-transfer">
    <div class="fl ty-transfer-list transfer-list-left">
        <div class="ty-transfer-list-head">
            <div  class="form-inline"> <label style="width: 57px;"> 源模型</label>
                <select id="sourceSelect" onchange="selectOnchang(this,'source')" style="width: 330px;">
                    <option value="">请选择</option>
                    <option value="" disabled="disabled">${activeModal.modelName}</option>
                    <#list activeGroup as c>
                        <#if c_index=0>
                            <option value="${c.id}" selected>&nbsp;&nbsp;&nbsp;&nbsp;${c.groupName}</option>
                        <#else>
                            <option value="${c.id}">&nbsp;&nbsp;&nbsp;&nbsp;${c.groupName}</option>
                        </#if>
                    </#list>
                </select>
            </div>
            <div  class="form-inline">
                <label style="width: 56px;"> 数据筛选</label>
                <input type="text" style="width: 316px;" oninput="filterData(this,'source')">
            </div>
        </div>
        <div class="ty-transfer-list-body">
            <ul class="ty-tree-select" id="source">
                <#list activePool as p>
                <li>
                    <div class="ty-tree-div">
                        <label class="tyue-checkbox-wrapper">
                            <span class="tyue-checkbox">
                                <input type="checkbox" class="tyue-checkbox-input" id="${p.id}">
                                <span class="tyue-checkbox-circle">
                                </span>
                            </span>
                            <span class="tyue-checkbox-txt" title="${p.id}">
                                ${p.poolTitle}
                            </span>
                        </label>
                    </div>
                </li>
                </#list>
            </ul>
        </div>
        <div class="ty-transfer-list-foot">
            <div class="ty-tree-div">
                <div class="tyc-check-blue fl">
                    <input type="checkbox" class="transfer-all-check" id="tyc-check-blue">
                    <span>
                    </span>
                </div>
                <div class="ty-tree-text">
                    全选
                </div>
            </div>
        </div>
    </div>
    <div class="fl ty-transfer-operation">
        <span class="ty-transfer-btn-toright to-switch">
        </span>
       <span class="ty-transfer-btn-toleft to-switch">
        </span>
    </div>
    <div class="fl ty-transfer-list transfer-list-right">
        <div class="ty-transfer-list-head">
            <div class="form-inline"> <label style="width: 57px;">目标模型</label>
                <select id="targetSelect" onchange="selectOnchang(this,'target')" style="width: 330px;">
                    <option value="">请选择</option>
                    <#list base?keys as key>
                        <option value="key" disabled="disabled">${base[key].modelName}</option>
                        <#list group[key]  as c>
                         <option value="${c.id}">&nbsp;&nbsp;&nbsp;&nbsp;${c.groupName}</option>
                        </#list>
                    </#list>
                </select>
            </div>
            <div class="form-inline">
                <label style="width: 56px;"> 数据筛选</label>
                <input type="text" style="width: 316px;" oninput="filterData(this,'target')">
            </div>
        </div>
        <div class="ty-transfer-list-body">
            <ul class="ty-tree-select" id="target">

            </ul>
        </div>
        <div class="ty-transfer-list-foot">
            <div class="ty-tree-div">
                <div class="tyc-check-blue fl">
                    <input type="checkbox" class="transfer-all-check" id="tyc-check-blue">
                    <span>
                    </span>
                </div>
                <div class="ty-tree-text">
                    全选
                </div>
            </div>
        </div>
    </div>
    <div class="clearboth">
    </div>   
</div>

</#if>
<script>
    var pools=getPools();
function getPools() {
    var pools={};
     <#list pool?keys as key>
        var list_${key}=[];
         <#assign pp = pool[key]>
         <#list pool[key]  as data>
                var p_${data_index}={};
               p_${data_index}['id']='${data.id}';
                p_${data_index}['poolTitle']='${data.poolTitle}';
                list_${key}.push(p_${data_index})
         </#list>
            pools['${key}']=list_${key};
     </#list>
    return pools;
}


    function remove(arr, item) {
        var result=[];
        arr.forEach(function(element){
            if(element!=item){
                result.push(element);
            }
        });
        return result;
    }

    function selectOnchang(ele,divId) {
        addData=[];
      var id = $(ele).val();
        var pool=pools[id];
        var options=""
        if(pool!==undefined && pool.length>0){
            for(var i=0;i<pool.length; i++){
                   options=options+' <li> <div class="ty-tree-div"> <label class="tyue-checkbox-wrapper"> <span class="tyue-checkbox">'+
                     '<input type="checkbox" class="tyue-checkbox-input" id="'+pool[i].id+'">'+
                     '<span class="tyue-checkbox-circle"> </span> </span>'+
                    '<span class="tyue-checkbox-txt" title="'+pool[i].id+'">'+
                           pool[i].poolTitle+
                     '</span> </label> </div> </li>'
            }
        }else {
            options='<div>暂无数据类</div>'
        }

        $('#'+divId).html(options);
        $("#ued-transfer").transferItem();
    }
    function submitCopyModal() {
        var id=$('#targetSelect').val();
        if(!id){
            alert('请选择需要接收数据类的目标模型')
            return;
        }

        if(addData.length<=0){
            alert('没有复制任何数据类不需要保存')
            return;
        }

        $.nptPOST("copyModalGroupsPool.action",{groupPoolId:addData.join('@_@'),targetGroupId:id},function (data) {
            debugger;
            $('#copyModal').modal("hide");
        });
    }
  function filterData(_this,id) {
      var v=$(_this).val();
      $('#'+id+' li').hide();
      $('#'+id+' .tyue-checkbox-txt').each(function () {
            var one=$(this).text();
            if(one.indexOf(v)>-1){
                $(this).parent().parent().parent().show();
            }
      });
  }
</script>