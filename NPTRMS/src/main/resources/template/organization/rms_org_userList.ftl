
    <table class="table table-bordered table-hover" id="orgUserListTb" >
        <thead>
        <tr>
            <th width="10%">序号</th>
            <th width="25%">用户名</th>
            <th width="10%">状态</th>
            <th width="25%">所属机构</th>
            <th width="30%">操作</th>
        </tr>
        </thead>
        <#list orgUser as user>
            <tr>
                <td>${user_index+1}</td>
                <td>${user.loginName!}</td>
                <td>
                    <#if user.enable?string('y', 'n') == 'y'>
                        启用
                    <#else >
                        禁用
                    </#if>
                </td>
                <td>${orgName!}</td>
                <td>
                    <#if user.enable?string('y', 'n') == 'y'>
                        <a  class="btn mini  yellow" href="javascript:void(0);" onclick="disabledUser('${user.userId}')"><i class="icon-stop"  ></i> 禁用</a>
                    <#else >
                        <a  class="btn mini  blue" href="javascript:void(0);" onclick="enabledUser('${user.userId}')"><i class="icon-play"  ></i> 启用</a>

                    </#if>
                 </td>
            </tr>
        </#list>
        <tbody>
        </tbody>
    </table>


