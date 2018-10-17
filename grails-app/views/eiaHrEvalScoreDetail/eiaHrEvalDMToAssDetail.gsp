<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaHrEval/eiaHrEval.css"/>
    <asset:javascript src="/eiaHrEvalScoreDetail/eiaHrEvalDMToAssDetail.js"/>
    <asset:stylesheet src="/eiaHrEval/eiaHrEvalDMToAssDetail.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper">
    <div class="fixed fixed-w">
        <div class="layer-title">
            <fieldset class="layui-elem-field layui-field-title site-title">
                <legend id="title">
                    <a name="methodRender">员工月度考核表（助理）</a>
                </legend>
            </fieldset>
        </div>
        <blockquote class="layui-elem-quote larry-btn">
            员工月度考核表（助理）
            <ul class="title-right">
                <li>所属部门：<span id="orgCode"></span><span id="orgName"></span></li>
                <li>考核月份：<span id="assessmentMonth"></span></li>
                <li>直属领导：<span id="leader"></span></li>
            </ul>
        </blockquote>
    </div>
    <div id="eiaHrEvalTable" class="mt100 font12">
        <div class="float-left" id="titleLeft">
            <div class="mt100 fixed fixed-bg">
                <table class="eiaHrEvalTable mt15">
                    <tr>
                        <th width="50px">项目</th>
                        <th width="110px">考核方面</th>
                        <th width="50px">分值</th>
                        <th width="581px" colspan="2">评分标准</th>
                    </tr>
                </table>
            </div>
            <table class="eiaHrEvalTable mt26">
                <tr>
                    <th width="50px" rowspan="16">部<br>门<br>经<br>理<br>评<br>分</th>
                    <th width="110px" rowspan="4">工作执行力</th>
                    <th width="50px" rowspan="4">20</th>
                    <th width="80px">18-20分</th>
                    <th width="500px">执行能力强，积极主动完成工作任务</th>
                </tr>
                <tr>
                    <th>15-17分</th>
                    <th>执行能力良好，基本按时完成工作任务</th>
                </tr>
                <tr>
                    <th>12-14分</th>
                    <th>执行能力一般，在他人监督催促下才能完成本职工作</th>
                </tr>
                <tr>
                    <th>0-12分</th>
                    <th>执行能力较差，因自身原因未能完成领导分派的工作任务</th>
                </tr>
                <tr>
                    <th rowspan="4">工作绩效</th>
                    <th rowspan="4">20</th>
                    <th>18-20分</th>
                    <th>能出色完成工作，承担极多工作量，工作质量优秀</th>
                </tr>
                <tr>
                    <th>15-17分</th>
                    <th>能有计划的完成工作，工作量较大，工作质量良好</th>
                </tr>
                <tr>
                    <th>12-14分</th>
                    <th>在指导下承担部分工作，工作量一般，工作质量一般</th>
                </tr>
                <tr>
                    <th>0-12分</th>
                    <th>工作不力，工作量小，工作质量较差</th>
                </tr>
                <tr>
                    <th rowspan="4">工作态度</th>
                    <th rowspan="4">20</th>
                    <th>18-20分</th>
                    <th>工作积极主动，有强烈的责任感，能够超额完成工作任务</th>
                </tr>
                <tr>
                    <th>15-17分</th>
                    <th>工作较积极，态度诚恳，基本按规定时间完成工作任务</th>
                </tr>
                <tr>
                    <th>12-14分</th>
                    <th>责任心一般，无太大的工作动力，需监督方可完成工作</th>
                </tr>
                <tr>
                    <th>0-12分</th>
                    <th>工作不主动，无责任心</th>
                </tr>
                <tr>
                    <th rowspan="4">领导能力</th>
                    <th rowspan="4">10</th>
                    <th>9-10分</th>
                    <th>善于分配工作，并能积极传授工作知识技能</th>
                </tr>
                <tr>
                    <th>7-8分</th>
                    <th>能够较好的分配工作，有效传授工作知识技能</th>
                </tr>
                <tr>
                    <th>6-7分</th>
                    <th>基本能够合理地分配工作，具备一定指导员工工作的能力</th>
                </tr>
                <tr>
                    <th>0-6分</th>
                    <th>欠缺分配工作、指导员工的工作方法，工作任务完成偶有困难</th>
                </tr>
                <tr>
                    <th colspan="5">领导评语</th>
                </tr>
            </table>
        </div>
        <div id="personTable" class="float-left mb15" style="overflow-x:auto">
            <form id="perTableForm">
                <input type="hidden"   id="staffId"  name="staffId" value="" />
                <table class="eiaHrEvalTable" id="rightTitle">
                    <tr class="titleTop pr1 pt15">
                        %{--<input type="hidden" name="jobRatingScoreId" id="jobRatingScoreId" value="" />--}%
                        %{--<input type="hidden" id="staffId" value="" />--}%
                        <input type="hidden" id="jobRatingType" value="" />
                        %{--<input type="hidden" id="orgId" value="" />--}%
                    </tr>
                    <tr class="mt1">
                    </tr>
                </table>
                <table id="perTable" class="eiaHrEvalTable">
                    <tr id="workExecution">
                    </tr>
                    <tr id="performance">
                    </tr>
                    <tr id="workingAttitude">
                    </tr>
                    <tr id="leadership">
                    </tr>
                    <tr id="scoreId">
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>