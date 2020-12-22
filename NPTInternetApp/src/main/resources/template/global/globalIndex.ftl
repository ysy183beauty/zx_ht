<#assign data = _MODEL_LIST>
<#assign cates = _MODEL_CATES>
<section id="modelList">
    <div class="container">
        <div class="box first">
            <div class="center gap">
                <h2>信用模型</h2>
                <p class="lead">基于信用主体，从各个视角搭建数据模型，以便全方位了解信用主体的各方面信息。
                    <br>
                    针对特殊的业务域可搭建特殊的业务模型，比如红黑榜模型，重点人群模型，中介信用模型等。
                </p>
            </div><!--/.center-->
            <ul class="portfolio-filter row">
                <li class="col-xs-4 col-md-2 col-sm-2"><a class="btn btn-primary active" href="#" data-filter="*">全部模型</a></li>
            <#if cates?? && cates?size gt 0>
                <#list cates as cate>
                <li class="col-xs-4 col-md-2 col-sm-2"><a class="btn btn-primary " href="#" data-filter=".${cate.name}">${cate.title}</a></li>
                </#list>
            </#if>
            </ul>
            <ul class="portfolio-items row">
            <#if data?? && data?size gt 0 >
                <#list data as oneModel>
                    <li class="col-sm-3 portfolio-item ${oneModel.modelCategory!}">
                        <div class="item-inner">
                            <div class="center">
                                <div class="portfolio-image he_border1">
                                    <img class="he_border1_img"
                                         src="/pub/index/images/model/category/${oneModel.modelCategory!}.jpg" alt="">
                                    <div class="he_border1_caption">
                                        <p class="he_border1_caption_p">${oneModel.modelNote!}</p>
                                        <a href="/npt/global/modelIndex.action?modelId=${oneModel.modelId?string("#")}"
                                           target="_blank">
                                        </a>
                                    </div>
                                </div>
                                <h4>${oneModel.modelTitle!}</h4>
                            </div>
                        </div>
                    </li>
                </#list>
            <#else >
                <h3 class="center">系统暂未添加任何模型，敬请等待</h3>
            </#if>
            </ul>
        </div>
    </div>
</section>

<section>
    <div class="container">
        <div class="row"></div>
    </div>
</section>
