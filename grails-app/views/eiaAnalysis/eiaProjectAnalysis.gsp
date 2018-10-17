<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目统计分析</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaBoard/eiaTaskBoard.css"/>
    <asset:javascript src="/eiaAnalysis/eiaProjectAnalysis.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief" lay-filter="projectTab">
        <ul class="layui-tab-title">
            <li class="layui-this" tab-name="projectCount">部门项目统计</li>
            <li tab-name="projectFinance">项目台账统计</li>
        </ul>
        <div class="layui-tab-content pt0" style="height: 100px;">
            <!--部门项目统计-->
            <div class="layui-tab-item layui-show">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="projectCountList" lay-filter="projectCountList" openState="0"></table>
                    <input type="hidden" id="startTime" value="">
                    <input type="hidden" id="endTime" value="">
                </div>
            </div>
            <!--项目台账统计-->
            <div class="layui-tab-item ">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="projectFinanceList" lay-filter="projectFinanceList"></table>
                </div>

            </div>
        </div>
    </div>
</div>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="depTp">
{{#  if(!d.ifStaff){ }}
{{#  if(!d.LAY_TABLE_INDEX){ }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe8e2;</i>&nbsp;&nbsp;{{d.orgName}}</a>
{{#  } else { }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe849;</i>&nbsp;&nbsp;{{d.orgName}}</a>
{{#  } }}
{{#  } else { }}
{{#  if(!d.ifStaff) { }}
<a class="">{{d.orgName}}</a>
{{#  } else { }}
<a class="">{{d.staffName}}</a>
{{#  } }}
{{#  } }}
</script>
<script type="text/html" id="projAccTp">
{{#  if(d.ifStaff == true && d.projectNum > 0){ }}
<a class="depLink">{{d.projectNum}}</a>
{{#  } else { }}
<a class="">{{d.projectNum}}</a>
{{#  } }}
</script>
<script type="text/html" id="projDoingTp">
{{#  if(d.ifStaff == true && d.projectDoing > 0){ }}
<a class="depLink">{{d.projectDoing}}</a>
{{#  } else { }}
<a class="">{{d.projectDoing}}</a>
{{#  } }}
</script>
<script type="text/html" id="projUnCompTp">
{{#  if(d.ifStaff == true && d.projectUnComp > 0){ }}
<a class="depLink">{{d.projectUnComp}}</a>
{{#  } else { }}
<a class="">{{d.projectUnComp}}</a>
{{#  } }}
</script>
<script type="text/html" id="projCompTp">
{{#  if(d.ifStaff == true && d.projectComp > 0){ }}
<a class="depLink">{{d.projectComp}}</a>
{{#  } else { }}
<a class="">{{d.projectComp}}</a>
{{#  } }}
</script>
<script type="text/html" id="ysProjTp">
{{#  if(d.ifStaff == true && d.ysNum > 0){ }}
<a class="depLink">{{d.ysNum}}</a>
{{#  } else { }}
<a class="">{{d.ysNum}}</a>
{{#  } }}
</script>
<script type="text/html" id="esProjTp">
{{#  if(d.ifStaff == true && d.esNum > 0){ }}
<a class="depLink">{{d.esNum}}</a>
{{#  } else { }}
<a class="">{{d.esNum}}</a>
{{#  } }}
</script>
<script type="text/html" id="ssProjTp">
{{#  if(d.ifStaff == true && d.ssNum > 0){ }}
<a class="depLink">{{d.ssNum}}</a>
{{#  } else { }}
<a class="">{{d.ssNum}}</a>
{{#  } }}
</script>
<!--人员项目统计查询-->
<script type="text/html" id="projectCountTmp">
<div class="layui-inline">
    <div class="layui-inline">
        <input type="text" class="layui-input" id="startDate" name="startDate" placeholder="起始时间">
    </div>
    <div class="layui-inline">
        <input type="text" class="layui-input" id="endDate" name="endDate" placeholder="结束时间">
    </div>
</div>
<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>
<!--项目台账统计查询-->
<script type="text/html" id="financeTmp">
<div class="table-top">
    <legend id="title">
        <a name="methodRender" class="pageTitle"></a>
    </legend>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称、项目编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        </div>
    </div>
</div>
</script>
</body>
</html>