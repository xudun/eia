<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaHrEvalScoreDetail/eiaHrEvalEmpToAssDetail.js"/>
    <asset:stylesheet src="/eiaHrEval/eiaHrEvalEmpToAssDetail.css"/>
    <asset:stylesheet src="/eiaHrEval/eiaHrEval.css"/>
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
                    <th width="50px" rowspan="12">员<br>工<br>评<br>分</th>
                    <th width="110px" rowspan="4">团队精神</th>
                    <th width="50px" rowspan="4">10</th>
                    <th width="80px">9-10分</th>
                    <th width="500px">善于与他人合作共事，相互支持，并能充分发挥各自的优势，营造良好的团队工作氛围</th>
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
            </table>
        </div>
        <div id="personTable" class="float-left mb15" style="overflow-x:auto">
            <form id="perTableForm">
                <table class="eiaHrEvalTable" id="rightTitle">
                    <tr class="titleTop pt15 pr1">
                        %{--<input type="hidden" name="jobRatingScoreId" id="jobRatingScoreId" value="" />--}%
                        %{--<input type="hidden" id="staffId" value="" />--}%
                        %{--<input type="hidden" id="jobRatingType" value="" />--}%
                        %{--<input type="hidden" id="orgId" value="" />--}%
                    </tr>
                </table>
                <table id="perTable" class="eiaHrEvalTable">
                    <tr id="teamSpirit">
                    </tr>
                    <tr id="leadership">
                    </tr>
                    <tr id="proAbility">
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>