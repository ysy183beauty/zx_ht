<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4><i class="icon-legal"></i> ${data.flagName}</h4>
</div>
<div class="modal-body">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>详情</div>
            <div class="tools">
                <a href="javascript:;" class="collapse"></a>
            </div>
        </div>
        <div class="portlet-body">
            <table class="table table-bordered table-hover">
                <tbody>
                <input type="hidden" name="id" value="${data.id}">
                <tr>
                    <td width="20%" align="right" class="bg">用户类型:</td>
                    <td width="30%" align="left" class="bold">${(cmsUser.typeName)!}</td>
                    <td width="20%" align="right" class="bg">用户名称:</td>
                    <td width="30%" align="left" class="bold" title="${(cmsUser.realname)!}">${(cmsUser.realname)!}</td>
                </tr>
                <tr>
                    <td align="right" class="bg">证件号码:</td>
                    <td align="left" class="bold" title="${(cmsUser.idCard)!}">${(cmsUser.idCard)!}</td>
                    <td align="right" class="bg">电话:</td>
                    <td align="left" class="bold">${data.tel!}</td>
                </tr>
                <tr>
                    <td align="right" class="bg">邮箱:</td>
                    <td align="left" class="bold">${data.email!}</td>
                    <td align="right" class="bg">创建时间:</td>
                    <td align="left" class="bold">${data.createTime!}</td>
                </tr>
                <tr>
                    <td align="right" class="bg">来源:</td>
                    <td colspan="3" align="left" class="bold">${data.source!}</td>
                </tr>
                <tr>
                    <td align="right" class="bg">标题:</td>
                    <td colspan="3" align="left" class="bold">${data.title!}</td>
                </tr>
                <tr>
                    <td align="right" class="bg">详细描述:</td>
                    <td colspan="3" align="left" class="bold"><textarea rows="10" disabled>${data.text!}</textarea></td>
                </tr>
                <tr>
                    <td align="right" class="bg">附件:</td>
                    <td colspan="3" align="left" class="bold">
                    <#if data.attach??>
                        <a onclick="downloadAttach('${data.id}')" class="btn blue large"><i class="icon-download-alt">下&nbsp;&nbsp;载&nbsp;</i></a>
                    <#else>
                        无
                    </#if>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>回复</div>
            <div class="tools">
                <a href="javascript:;" class="collapse"></a>
            </div>
        </div>
        <div class="portlet-body">
            <div class="row-fluid">
                <div class="span12">
                    <textarea rows="10" name="response" placeholder="字数请不要超过1200汉字！" required
                              <#if data.syncStatus != 0>disabled</#if>
                    >${data.response!}</textarea>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
<#if data.syncStatus == 0>
    <button type="submit" class="btn blue">&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
</#if>
    <button type="button" data-dismiss="modal" class="btn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
</div>