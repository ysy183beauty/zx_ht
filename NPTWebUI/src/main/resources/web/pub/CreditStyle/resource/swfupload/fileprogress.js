function getJQueryObj(id) {
	return $("#" + id);
}

function FileProgress(file, targetID, name) {
	this.fileProgressID = file.id;
	this.name = name;
	
	this.fileContainer = getJQueryObj(this.fileProgressID);
	this.fileInput = getJQueryObj(this.fileProgressID + "_input");
	
	if(!this.fileContainer[0]) {
		this.fileContainer = jQuery("<table style='border:0px;'></table>");
		this.fileContainer.attr("id", this.fileProgressID);
		
		this.fileInput = jQuery("<input type='hidden'></input>");
		this.fileInput.attr("id", this.fileProgressID + "_input");
        this.fileInput.attr("name", this.fileProgressID + "_input");
		this.fileInput.appendTo(this.fileContainer);
		
		var tr = jQuery("<tr valign='middle'></tr>");
		tr.appendTo(this.fileContainer);
		
		var td_fileBtn = jQuery("<td></td>");
		td_fileBtn.appendTo(tr);
		
		var fileBtn = jQuery("<input></input>");
		fileBtn.addClass("attachmentBtn");
		fileBtn.appendTo(td_fileBtn);
		
		var td_fileName = jQuery("<td></td>");
		td_fileName.appendTo(tr);
		
		var fileName = jQuery("<span></span>");
		var sourceFileName = file.name;
		if(sourceFileName != null && sourceFileName.length > 15){
			sourceFileName = sourceFileName.substring(0, 13) + "...";
		}
		fileName.html(sourceFileName);
		fileName.addClass("fileName");
		fileName.appendTo(td_fileName);
		
		var td_processBar = jQuery("<td style='width: 100px;padding:0 5px;'></td>");
		td_processBar.appendTo(tr);
		
		var processBarContainer = jQuery("<div></div>");
		processBarContainer.addClass("processBarContainer");
		processBarContainer.appendTo(td_processBar);
		
		var processBar = jQuery("<div></div>");
		processBar.addClass("progressBarInProgress");
		processBar.appendTo(processBarContainer);
		
		var td_processBarStatus = jQuery("<td></td>");
		td_processBarStatus.addClass("progressBarStatus");
		td_processBarStatus.appendTo(tr);
		
		var td_progressCancel = jQuery("<td></td>");
		td_progressCancel.appendTo(tr);
		
		var progressCancel = jQuery("<a></a>");
		progressCancel.addClass("progressCancel");
		progressCancel.attr("href", "#");
		progressCancel.appendTo(td_progressCancel);
		
		getJQueryObj(targetID).append(this.fileContainer);
	} else {
		this.reset();
	}
}

FileProgress.prototype.reset = function () {
	var td_progressBar = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(2);
	var progressBar = td_progressBar.children("div").eq(0).children().eq(0);
	progressBar.addClass("progressBarInProgress");
	progressBar.width("0%");
};

FileProgress.prototype.setProgress = function (percentage) {
	var td_progressBar = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(2);
	var progressBar = td_progressBar.children("div").eq(0).children().eq(0);
	
	progressBar.addClass("progressBarInProgress");
	progressBar.width(percentage + "%");
};

FileProgress.prototype.setComplete = function () {
	var td_progressBar = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(2);
	var progressBar = td_progressBar.children("div").eq(0).children().eq(0);
	progressBar.addClass("progressBarComplete");
	progressBar.width("");
	
	td_progressBar.css("display", "none");
	
	var progressBarStatus = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(3);
	progressBarStatus.css("display", "none");
	
	if(this.name) {
		this.fileInput.attr("name", "newFiles_" + this.name);
	} else {
		this.fileInput.attr("name", "newFiles");
	}
};

FileProgress.prototype.setError = function () {
	var td_progressBar = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(2);
	var progressBar = td_progressBar.children("div").eq(0).children().eq(0);
	progressBar.addClass("progressBarError");
	progressBar.width("");
};

FileProgress.prototype.setCancelled = function () {
	var td_progressBar = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(2);
	var progressBar = td_progressBar.children("div").eq(0).children().eq(0);
	progressBar.addClass("progressBarError");
	progressBar.width("");
	
	$("#" + this.fileContainer.attr("id")).remove();
};

FileProgress.prototype.toggleCancel = function (swfUploadInstance) {
	var td_progressCancel = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(4);
	var processCancel = td_progressCancel.children("a").eq(0);
	
	if (swfUploadInstance) {
		var fileID = this.fileProgressID;
		
		processCancel.unbind("click");
		processCancel.click(function () {
			swfUploadInstance.cancelUpload(fileID);
			return false;
		});
	}
};

FileProgress.prototype.toggleDel = function (serverData, delFileAction) {
	this.fileInput.val(serverData);
	
	var fileObj = eval('('+serverData+')');
	var fileName = fileObj.fileName;
	
	var td_progressCancel = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(4);
	var processCancel = td_progressCancel.children("a").eq(0);
	
	var this_ = this;
	
	processCancel.unbind("click");
	processCancel.click(function () {
		$.ajax({
			type: "POST",
			url: delFileAction,
			data: {filePath: fileName},
			success:function(msg){
				if(msg=="success"){
					this_.setCancelled();
				}else{
					window.alert(msg);
				}  
			},
			error:function(msg){
				window.alert("文件删除失败！");
			}
		});
		return false;
	});
};

FileProgress.prototype.toggleDelFile = function (fileObj) {
	this.fileInput.val(fileObj.id);
	
	var td_progressCancel = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(4);
	var processCancel = td_progressCancel.children("a").eq(0);
	
	var td_progressBar = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(2);
	td_progressBar.css("display", "none");
	
	var this_ = this;
	
	processCancel.unbind("click");
	processCancel.click(function () {
		if(this_.name) {
			this_.fileInput.attr("name", "delFiles_" + this_.name);
		} else {
			this_.fileInput.attr("name", "delFiles");
		}
		
		this_.fileContainer.css("display", "none");
		
		return false;
	});
};

FileProgress.prototype.setStatus = function (status) {
	var progressBarStatus = $("#" + this.fileContainer.attr("id") + " tr:first td").eq(3);
	
	progressBarStatus.html(status);
};