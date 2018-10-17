<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>共享查询</title>
    <asset:stylesheet src="/eiaPlatForm/eiaDataShare.css"/>
    <asset:javascript src="/eiaPlatForm/eiaDataShareIndex.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt15">
    <div class="layui-tab layui-tab-brief" lay-filter="eiaDataShareTab">
        <ul class="layui-tab-title">
            <li class="layui-this">环评项目</li>
            <li>规划公示</li>
            <li>区域概况</li>
            <li>环境敏感区</li>
        </ul>
        <div class="layui-tab-content data-share-con">
            <!--环评项目-->
            <div class="layui-tab-item layui-show" tab_name="dataShare">
                <!--查询框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="dataShareSearch" class="layui-input larry-search-input" value="" id="dataShareSearch" placeholder="项目名称、环境影响评价行业类型、行业类型及代码、生产工艺、产品或功能">
                        </div>
                    </div>
                    <div class="layui-btn-group top-group">
                        <a class="layui-btn search_btn pl12" data-type="getSelectProject"><i class="larry-icon">&#xe939;</i> 查询</a>
                    </div>
                    <!--筛选栏-->
                    <div class="filter-box mt15">
                        <ul class="filter-ul">
                            <li class="filter-li">
                                <div class="inner-box lf">数据来源</div>
                                <div class="inner-box rg filter-inner dataSourceFilter"></div>
                            </li>
                            <li class="filter-li">
                                <div class="inner-box lf">审批年度</div>
                                <div class="inner-box rg filter-inner yearFilter"></div>
                            </li>
                            <li class="filter-li">
                                <div class="inner-box lf">建设性质</div>
                                <div class="inner-box rg filter-inner natureFilter"></div>
                            </li>
                            <li class="filter-li">
                                <div class="inner-box lf">我的收藏</div>
                                <div class="inner-box rg filter-inner collectFilter"></div>
                            </li>
                            <li class="filter-li">
                                <div class="inner-box lf">所属区域</div>
                                <div class="inner-box rg region-name-rg">
                                    <input type="text" id="regionNameProjectDrop" name="regionNameProjectDrop" class="layui-input" readonly>
                                    <input type="hidden" id="regionNameProject" name="regionNameProject" value="">
                                </div>
                            </li>
                        </ul>
                    </div>
                </blockquote>

                <div class="show-box mt15">
                    <div class="empty-note">没有更多数据</div>
                    <ul class="show-ul project-ul"></ul>
                    <div id="projectPagination" class="pagination"></div>
                </div>
            </div>
            <!--规划公示-->
            <div class="layui-tab-item" tab_name="planShow">
                <!--查询框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="planShowSearch" class="layui-input larry-search-input" value="" id="planShowSearch" placeholder="标题、来源机关、公布内容">
                        </div>
                    </div>
                    <div class="layui-btn-group top-group">
                        <a class="layui-btn search_btn pl12" data-type="getSelectPlanShow"><i class="larry-icon">&#xe939;</i> 查询</a>
                    </div>
                    <!--筛选栏-->
                    <div class="filter-box mt15">
                        <ul class="filter-ul">
                            <li class="filter-li">
                                <div class="inner-box lf">我的收藏</div>
                                <div class="inner-box rg filter-inner collectFilter"></div>
                            </li>
                            <li class="filter-li">
                                <div class="inner-box lf">公布日期</div>
                                <div class="inner-box rg region-name-rg">
                                    <input type="text" id="pubDate" name="pubDate" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off" value="">
                                </div>
                            </li>
                        </ul>
                    </div>
                </blockquote>

                <div class="show-box mt15">
                    <div class="empty-note">没有更多数据</div>
                    <ul class="show-ul plan-show-ul">
                    </ul>
                    <div id="planShowPagination"  class="pagination"></div>
                </div>
            </div>
            <!--区域概况-->
            <div class="layui-tab-item" tab_name="areaInfo">
                <!--查询框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="areaInfoSearch" class="layui-input larry-search-input" value="" id="areaInfoSearch" placeholder="项目名称">
                        </div>
                    </div>
                    <div class="layui-btn-group top-group">
                        <a class="layui-btn search_btn pl12" data-type="getSelectAreaInfo"><i class="larry-icon">&#xe939;</i> 查询</a>
                    </div>
                    <!--筛选栏-->
                    <div class="filter-box mt15">
                        <ul class="filter-ul">
                            <li class="filter-li">
                                <div class="inner-box lf">数据类别</div>
                                <div class="inner-box rg filter-inner dataTypeFilter"></div>
                            </li>
                            <li class="filter-li">
                                <div class="inner-box lf">我的收藏</div>
                                <div class="inner-box rg filter-inner collectFilter"></div>
                            </li>
                            <li class="filter-li">
                                <div class="inner-box lf">所属区域</div>
                                <div class="inner-box rg region-name-rg">
                                    <input type="text" id="regionNameAreaInfoDrop" name="regionNameAreaInfoDrop" class="layui-input" readonly>
                                    <input type="hidden" id="regionNameAreaInfo" name="regionNameAreaInfo" value="">
                                </div>
                            </li>
                        </ul>
                    </div>
                </blockquote>

                <div class="show-box mt15">
                    <div class="empty-note">没有更多数据</div>
                    <ul class="show-ul area-info-ul"></ul>
                    <div id="areaInfoPagination" class="pagination"></div>
                </div>
            </div>
            <!--环境敏感区-->
            <div class="layui-tab-item" tab_name="sensArea">
                <!--查询框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="sensAreaSearch" class="layui-input larry-search-input" value="" id="sensAreaSearch" placeholder="项目名称">
                        </div>
                    </div>
                    <div class="layui-btn-group top-group">
                        <a class="layui-btn search_btn pl12" data-type="getSelectSensShow"><i class="larry-icon">&#xe939;</i> 查询</a>
                    </div>
                    <!--筛选栏-->
                    <div class="filter-box mt15">
                        <ul class="filter-ul">
                            <li class="filter-li">
                                <div class="inner-box lf">数据类别</div>
                                <div class="inner-box rg filter-inner dataTypeFilter"></div>
                            </li>
                            <li class="filter-li">
                                <div class="inner-box lf">我的收藏</div>
                                <div class="inner-box rg filter-inner collectFilter"></div>
                            </li>
                            <li class="filter-li">
                                <div class="inner-box lf">所属区域</div>
                                <div class="inner-box rg region-name-rg">
                                    <input type="text" id="regionNameSensAreaDrop" name="regionNameSensAreaDrop" class="layui-input" readonly>
                                    <input type="hidden" id="regionNameSensArea" name="regionNameSensArea" value="">
                                </div>
                            </li>
                        </ul>
                    </div>
                </blockquote>

                <div class="show-box mt15">
                    <div class="empty-note">没有更多数据</div>
                    <ul class="show-ul sens-area-ul">
                    </ul>
                    <div id="sensAreaPagination" class="pagination"></div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>