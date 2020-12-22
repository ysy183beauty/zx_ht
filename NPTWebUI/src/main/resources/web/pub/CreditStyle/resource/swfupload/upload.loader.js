function uploaderInclude()
{
    var pathtojsfiles = "";
    
    var thisJSSrc = $("script[src$='upload.loader.js']").attr("src");
	if(thisJSSrc.lastIndexOf("/") != -1) {
		pathtojsfiles = thisJSSrc.substring(0, thisJSSrc.lastIndexOf("/")) + "/";
	}
    
    var modules = [
        { include: true, incfile:'swfupload.js'},
        { include: true, incfile:'handlers.js'},
        { include: true, incfile:'fileprogress.js'},
        { include: true, incfile:'upload.uploader.js'}
    ];
    
    var filename;
    for(var i=0;i<modules.length; i++) {
        if(modules[i].include === true) {
        	filename = pathtojsfiles+modules[i].incfile;
    		document.write('<script type="text/javascript" src="' + filename + '"></script>'); 
        }
    }
};

uploaderInclude();