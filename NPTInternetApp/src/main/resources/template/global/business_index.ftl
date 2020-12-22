<link href="/pub/index/css/main.css" rel="stylesheet">
<div class="container">
    <div class="box">
        <div class="row">
        <#include "business_search.ftl">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 col-sm-12">
                        <iframe src="http://localhost:7070/npt/internet/blackred/paginationModelMainDataIndex.action?busName=${busName!}&&codeName=${codeName!}"
                                id="frame_content" frameborder="0" marginwidth="0" scrolling="no"
                                style="width: 100%;"></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/pub/index/js/jquery.js"></script>
<script>
    $("#frame_content").load(function () {
        var mainheight = $(this).contents().find("body").height() + 30;
        $(this).height(mainheight);
    });

</script>