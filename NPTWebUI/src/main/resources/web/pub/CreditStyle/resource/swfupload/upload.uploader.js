var jQuery_swfUploaders={};
var jQuery_uploaders={};
(function($) {
	$.fn.initUploader = function (opts) {
		return this.each(function() {
			var self_ = $(this);
			var conf = $.extend({},opts);
			if(conf !== false) {
				var uploader = new uploader_component();
				uploader.init(this, conf);
				
				jQuery_swfUploaders[self_.attr("id")] = uploader;
			}
		});
	};
	
	$.fn.resetUploader = function(){
		var swfUploader = jQuery_swfUploaders[$(this).attr("id")];
		if(swfUploader) {
			swfUploader.reset();
		}
	};
	
	function uploader_component () {
		return {
			settings : {
			  	contextPath : "",
			  	uploadText : "添加附件",
			  	file_size_limit : "10MB",
				file_upload_limit : 0,
			  	file_types : "*.*",
				file_types_description : "所有文件"
			},
		  	
		  	_movieContainer : null,
		  	
			init : function(elem, conf) {
				this.container = $(elem);
				if(this.container.size === 0) { 
					window.alert("错误的容器节点!"); 
					return; 
				}
				
				if(!this.container.attr("id")) {
					this.container.attr("id","uploader_" + this.cntr);
				} 
				
				var opts = $.extend({},conf);
				
				this.settings = $.extend({},this.settings,opts);
				
				this.buildContainer();
			},
			
			buildContainer : function() {
				var movieContainerHtml = "<div></div>";
				var uploadProcessContainerHtml = "<div></div>";
				var addFileBtnHtml = "<span></span>";
				var uploadQueueHtml =  "<span></span>";
				
				this._movieContainer = jQuery(movieContainerHtml);
				this._movieContainer.appendTo(this.container);
				
				var addFileBtn = jQuery(addFileBtnHtml);
				addFileBtn.attr("id", this.container.attr("id") + "_btn");
				addFileBtn.attr("title", "按住Ctrl键可选择多个附件");
				addFileBtn.appendTo(this._movieContainer);
				
				var uploadProcessContainer = jQuery(uploadProcessContainerHtml);
				uploadProcessContainer.attr("id", this.container.attr("id") + "_div");
				uploadProcessContainer.appendTo(this.container);
				
				var ctx = this.settings.contextPath;
				
				var thisJSSrc = $("script[src$='upload.uploader.js']").attr("src");
				if(thisJSSrc.lastIndexOf("/") != -1) {
					pathtojsfiles = thisJSSrc.substring(0, thisJSSrc.lastIndexOf("/")) + "/";
				}
				
				var uploadText = this.settings.uploadText;
				
				var _swfuploader = new SWFUpload({
					flash_url : pathtojsfiles + "swf/swfupload.swf",
					upload_url: ctx + "/uploadFile/uploadFile.action",
					
					// File Upload Settings
					file_size_limit : this.settings.file_size_limit,
					file_types : this.settings.file_types,
					file_types_description : this.settings.file_types_description,
					file_upload_limit : this.settings.file_upload_limit,
	
					custom_settings : {
						progressTarget : uploadProcessContainer.attr("id"),
						delFileAction : ctx + "/uploadFile/deleteFile.action",
						name : this.settings.name
					},
	
					file_queued_handler : fileQueued,
					file_queue_error_handler : this.fileQueueError,
					upload_start_handler : uploadStart,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : uploadSuccess,
					file_dialog_complete_handler: fileDialogComplete,
					init_uploaded_files_handler: initUploadedFiles,
					
					button_image_url: pathtojsfiles + "images/SmallSpyGlassWithTransperancy_17x18.png",
					button_width: "80",
					button_height: "18",
					button_placeholder_id: addFileBtn.attr("id"),
					button_text: '<span class="button">' + uploadText + '</span>',
					button_text_style: ".button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; }",
					button_text_left_padding: 18,
					button_text_top_padding: 0,
					button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
					button_cursor: SWFUpload.CURSOR.HAND,
					
					debug: false
				});
				
				if(this.settings.existFiles && this.settings.existFiles.files) {				
					_swfuploader.initUploadedFiles(this.settings.existFiles);
				}
				
				jQuery_uploaders[this.container.attr("id")] = _swfuploader;
			},
			
			reset : function(){
				var swfuploader = jQuery_uploaders[this.container.attr("id")];
				swfuploader.initUploadedFiles(this.settings.existFiles);
			},
			
			fileQueueError : function(file, errorCode, message) {
				switch (errorCode) {
				case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					window.alert("不允许上传大于" + this.settings.file_size_limit + "的文件。");
					break;
				case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
					window.alert("不能够上传大小为0字节的文件。");
					break;
				case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					window.alert("错误的文件类型。");
					break;
				default:
					if (file !== null) {
						window.alert("不可预知的错误类型。");
					}
					break;
				}
			}
		};
	}
})(jQuery);