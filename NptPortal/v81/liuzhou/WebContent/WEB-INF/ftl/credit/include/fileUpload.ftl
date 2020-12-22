<#--上传插件-->
<script src="/thirdparty/alertify.js/lib/alertify.js"></script>
<script src="/thirdparty/fine-uploader/jquery.fine-uploader.js"></script>
<script type="text/template" id="qq-template-validation">
    <div class="qq-uploader-selector qq-uploader" qq-drop-area-text="将文件拖拽到此区域">
        <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
            <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
        </div>
        <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
            <span class="qq-upload-drop-area-text-selector"></span>
        </div>
        <div class="qq-upload-button-selector qq-upload-button">
            <div>上传文件</div>
        </div>
        <span class="qq-drop-processing-selector qq-drop-processing">
                <span>正在处理。。。</span>
                <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
            </span>
        <ul class="qq-upload-list-selector qq-upload-list" aria-live="polite" aria-relevant="additions removals">
            <li>
                <div class="qq-progress-bar-container-selector">
                    <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
                </div>
                <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                <img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale>
                <span class="qq-upload-file-selector qq-upload-file"></span>
                <span class="qq-upload-size-selector qq-upload-size"></span>
                <button type="button" class="qq-btn qq-upload-cancel-selector qq-upload-cancel">取消</button>
                <button type="button" class="qq-btn qq-upload-retry-selector qq-upload-retry">重试</button>
                <button type="button" class="qq-btn qq-upload-delete-selector qq-upload-delete">删除</button>
                <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
            </li>
        </ul>

        <dialog class="qq-alert-dialog-selector">
            <div class="qq-dialog-message-selector"></div>
            <div class="qq-dialog-buttons">
                <button type="button" class="qq-cancel-button-selector">关闭</button>
            </div>
        </dialog>

        <dialog class="qq-confirm-dialog-selector">
            <div class="qq-dialog-message-selector"></div>
            <div class="qq-dialog-buttons">
                <button type="button" class="qq-cancel-button-selector">取消</button>
                <button type="button" class="qq-ok-button-selector">确定</button>
            </div>
        </dialog>

        <dialog class="qq-prompt-dialog-selector">
            <div class="qq-dialog-message-selector"></div>
            <input type="text">
            <div class="qq-dialog-buttons">
                <button type="button" class="qq-cancel-button-selector">关闭</button>
                <button type="button" class="qq-ok-button-selector">确认</button>
            </div>
        </dialog>
    </div>
</script>
<script>
    $("#fine-uploader").fineUploader({
        template: 'qq-template-validation',
        request: {
            endpoint: '/credit/srv/file/o_swfupload.do'
        },
        deleteFile: {
            enabled: true // defaults to false
        },
        thumbnails: {
            placeholders: {
                waitingPath: '/thirdparty/fine-uploader/placeholders/waiting-generic.png',
                notAvailablePath: '/thirdparty/fine-uploader/placeholders/not_available-generic.png'
            }
        },
        validation: {
            allowedExtensions: ['jpeg', 'jpg', 'png', 'gif', 'txt', 'zip', 'rar'],
            itemLimit: 1,
            sizeLimit: 2 * 1024 * 1024 // 2M
        },
        showMessage: function(message) {
            return alertify.alert(message);
        },
        messages: {
            sizeError: "{file} 文件大于{sizeLimit}.",
            tooManyItemsError: "仅支持上传 {itemLimit} 个附件。",
            typeError: "{file} 文件扩展名不合法。支持的扩展名有: {extensions}。"
        }
    }).on('error', function (event, id, name, reason) {
        // do something
    }).on('complete', function (event, id, name, responseJSON) {
        // 设置文件名
        if (responseJSON.success) {
            $("input[name='attach']").val(responseJSON.newUuid);
        }
    }).on('submitDelete', function (event, id) {
        $("#fine-uploader").fineUploader('reset');
        $("input[name='attach']").val("");
        return false;
    });
</script>