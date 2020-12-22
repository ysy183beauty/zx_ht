<table class="table table-bordered table-condensed" style="margin-top: 10px">
    <tbody>
    <tr>
        <td width="20%" class="bold">说明</td>
        <td width="80%" align="left">
        <#if appeal.desc??>
            <pre>${appeal.desc!}</pre>
        <#else>
            无
        </#if>
        </td>
    </tr>
    <tr>
        <td class="bold">附件</td>
        <td align="left">
        <#if appeal.attach??>
            <a onclick="downloadAttach('${appeal.id}')" class="btn blue large"><i class="icon-download-alt">下&nbsp;&nbsp;载&nbsp;</i></a>
        <#else>
            无
        </#if>
        </td>
    </tr>
    </tbody>
</table>