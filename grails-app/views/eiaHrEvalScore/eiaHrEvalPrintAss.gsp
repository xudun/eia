<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaHrEval/eiaHrEval.css"/>
    <asset:javascript src="/eiaHrEvalScore/eiaHrEvalPrintAss.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper">
    <blockquote class="layui-elem-quote larry-btn">
        员工月度考核表（助理）
    </blockquote>

    <blockquote class="layui-elem-quote larry-btn fixed-footer">
        <div class="layui-inline pl12">
            <a class="layui-btn search_btn pl12" data-type="printReport"><i class="larry-icon">&#xe850;</i> 打印</a>
        </div>
    </blockquote>

    <div class="font12">
        <table class="eiaHrEvalTable mt40 w872">
            <tr>
                <th width="15%">姓名：</th>
                <th width="15%" class="staffName"></th>
                <th width="15%">所属部门：</th>
                <th width="25%"><div id="orgCode"></div><div id="orgName"></div></th>
                <th width="15%" rowspan="2">考核月份：</th>
                <th width="15%" rowspan="2" id="assessmentMonth"></th>
            </tr>
            <tr>
                <th>职务：</th>
                <th id="roleName"></th>
                <th>直属领导：</th>
                <th id="leader"></th>
            </tr>
        </table>
        <div class="mt20">
            <div class="float-left">
                <table class="eiaHrEvalTable">
                    <tr>
                        <th>项目</th>
                        <th>考核方面</th>
                        <th>分值</th>
                        <th colspan="2">评分标准</th>
                    </tr>
                    <tr>
                        <th width="49px" rowspan="12">员<br>工<br>评<br>分</th>
                        <th width="109px" rowspan="4">团队精神</th>
                        <th width="49px" rowspan="4">10</th>
                        <th width="79px">9-10分</th>
                        <th width="499px">善于与他人合作共事，相互支持，并能充分发挥各自的优势，营造良好的团队工作氛围</th>
                    </tr>
                    <tr>
                        <th>7-8分</th>
                        <th>能够与他人较好的合作共事，相互支持，保证团队任务完成</th>
                    </tr>
                    <tr>
                        <th>6-7分</th>
                        <th>具备一定的团队合作精神，能够和他人配合完成工作</th>
                    </tr>
                    <tr>
                        <th>0-6分</th>
                        <th>团队合作精神不强，已对工作带来不良影响</th>
                    </tr>
                    <tr>
                        <th rowspan="4">领导能力</th>
                        <th rowspan="4">10</th>
                        <th>9-10分</th>
                        <th>善于分配工作与授权，并能积极传授工作知识技能</th>
                    </tr>
                    <tr>
                        <th>7-8分</th>
                        <th>能够较好的分配工作与授权，有效传授工作知识技能</th>
                    </tr>
                    <tr>
                        <th>6-7分</th>
                        <th>基本能够合理地分配工作和授权，具备一定指导下属工作的能力</th>
                    </tr>
                    <tr>
                        <th>0-6分</th>
                        <th>欠缺分配工作、授权及指导下属的工作方法，工作任务完成偶有困难</th>
                    </tr>
                    <tr>
                        <th rowspan="4">专业能力</th>
                        <th rowspan="4">10</th>
                        <th>9-10分</th>
                        <th>精通本岗位工作所要求的理论知识和专业技能</th>
                    </tr>
                    <tr>
                        <th>7-8分</th>
                        <th>全面了解并较好地掌握与本岗位有关的理论知识和专业技能</th>
                    </tr>
                    <tr>
                        <th>6-7分</th>
                        <th>基本掌握与本岗位有关的专业知识，对本岗位业务有一定了解，但尚需提高</th>
                    </tr>
                    <tr>
                        <th>0-6分</th>
                        <th>对于本岗位所需的专业知识有一些不了解，但不够深入</th>
                    </tr>
                    <tr>
                        <th rowspan="16">部<br>门<br>经<br>理<br>评<br>分</th>
                        <th rowspan="4">工作执行力</th>
                        <th rowspan="4">20</th>
                        <th>18-20分</th>
                        <th>执行能力强，积极主动完成工作任务</th>
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
                        <th colspan="2">考核成绩</th>
                        <th>100</th>
                        <th colspan="2">优秀： 90（含）-100分 &nbsp;&nbsp;良好：80（含）-90分 &nbsp;&nbsp;合格：70（含）-80分 &nbsp;&nbsp;不合格：70分以下</th>
                    </tr>
                </table>
            </div>
            <div class="float-left">
                <table class="eiaHrEvalTable" id="perTable">
                    <input type="hidden" id="eiaHrEvalScoreId" value="${eiaHrEvalScoreId}" />
                    <input type="hidden" id="eiaHrEvalId" value="${eiaHrEvalId}" />
                    <tr>
                        <th width="80px" class="staffName"></th>
                    </tr>
                    <tr>
                        <td width="80px"><input type="text" id="teamSpirit" name="teamSpirit" disabled value="" /></td>
                    </tr>
                    <tr>
                        <td width="80px"><input type="text" id="leadership" name="leadership" disabled value="" /></td>
                    </tr>
                    <tr>
                        <td width="80px"><input type="text" id="proAbility" name="proAbility" disabled value="" /></td>
                    </tr>
                    <tr>
                        <td><input width="80px" type="text" disabled id="workExecution" name="workExecution" value="" ></td>
                    </tr>
                    <tr>
                        <td><input width="80px" type="text" disabled id="performance" name="performance" value="" ></td>
                    </tr>
                    <tr>
                        <td><input width="80px" type="text" disabled id="workingAttitude" name="workingAttitude" value="" ></td>
                    </tr>
                    <tr>
                        <td><input width="80px" type="text" disabled id="leadershipManager" name="leadershipManager" value="" ></td>
                    </tr>
                    <tr>
                        <th  width="81px"><input width="81px" type="text" disabled id="finalScore" name="finalScore" value="" ></th>
                    </tr>
                </table>
            </div>
        </div>
        <table class="overflow eiaHrEvalTable w872">
            <tr>
                <th width="160px" style="border-top: 0px;">领导评语</th>
                <th ><input style="border-top: 0px;" type="text" disabled id="leaderComments" name="leaderComments" value="" ></th>
            </th>
            </tr>
        </table>
        <table class="ratingTableFoot w872 mt20 mb20">
            <tr>
                <td>被考核人签字：</td>
                <td>考评人签字：</td>
            </tr>
            <tr>
                <td>日期：</td>
                <td>日期：</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>