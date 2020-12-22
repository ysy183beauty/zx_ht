function fileQueued(file) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings.name);
		progress.setStatus("等待上传...");
		progress.toggleCancel(this);

	} catch (ex) {
		this.debug(ex);
	}

}

function fileDialogComplete() {
	this.startUpload();
}

function uploadStart(file) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings.name);
		progress.setStatus("文件正在上传...");
		progress.toggleCancel(this);
	}
	catch (ex) {}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings.name);
		progress.setProgress(percent);
		progress.setStatus("文件正在上传...");
		progress.toggleCancel(this);
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings.name);
		progress.setComplete();
		progress.setStatus("文件上传成功.");
		progress.toggleDel(serverData, this.customSettings.delFileAction);

		this.startUpload();
	} catch (ex) {
		this.debug(ex);
	}
}

function initUploadedFiles(existsFiles) {
	$("#" + this.customSettings.progressTarget).children().remove();
	try {
		for(var index = 0; index < existsFiles.files.length; index++) {
			var file = existsFiles.files[index];
			
			var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings.name);
			progress.toggleDelFile(file);
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings.name);
		progress.setError();

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("文件上传失败: " + message);
			this.debug("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("文件上传失败。");
			this.debug("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("服务器文件操作错误。");
			this.debug("Error Code: IO Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("Security Error");
			this.debug("Error Code: Security Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus("Upload limit exceeded.");
			this.debug("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("Failed Validation.  Upload skipped.");
			this.debug("Error Code: File Validation Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			progress.setStatus("已取消");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("已停止");
			break;
		default:
			progress.setStatus("Unhandled Error: " + errorCode);
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}
