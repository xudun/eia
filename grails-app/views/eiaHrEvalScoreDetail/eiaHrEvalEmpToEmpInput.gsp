<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaHrEvalScoreDetail/eiaHrEvalEmpToEmpInput.js"/>
    <asset:stylesheet src="/eiaHrEval/eiaHrEvalEmpToEmpInput.css"/>
    <asset:stylesheet src="/eiaHrEval/eiaHrEval.css"/>
</head>

<body class="pb68">

<div class="layui-fluid larry-wrapper">
    <div class="fixed fixed-w">
        <div class="layer-title">
            <fieldset class="layui-elem-field layui-field-title site-title" >
                <legend id="title">
                    <a name="methodRender">员工月度考核表</a>
                </legend>
            </fieldset>
        </div>

        <ul class="title-right">
            <li>所属部门：<span id="orgCode"></span><span id="orgName"></span></li>
            <li>考核月份：<span id="assessmentMonth"></span></li>
            <li>直属领导：<span id="leader"></span></li>
        </ul>

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <a class="layui-btn pl12" data-type="save"><i class="larry-icon">&#xe9d1;</i> 保存</a>
                    <button type="reset" class="layui-btn layui-btn-primary pl12" onclick="clearForm()"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

    </div>
    <div id="eiaHrEvalTable" class="mt116 font12">
        <div class="float-left" id="titleLeft">
            <div class="mt116 fixed fixed-bg">
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
                    <th width="50px" rowspan="12">工<br>作<br>态<br>度<br>表<br>现</th>
                    <th width="110px" rowspan="4">工作态度</th>
                    <th width="50px" rowspan="4">20</th>
                    <th width="80px">18-20分</th>
                    <th width="500px">工作积极主动，有强烈的责任感，能够超额完成工作任务</th>
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
                    <th rowspan="4">团队精神</th>
                    <th rowspan="4">10</th>
                    <th>9-10分</th>
                    <th>善于与他人合作共事，相互支持，并能充分发挥各自的优势，营造良好的团队工作氛围</th>
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
                    <th rowspan="4">企业文化认知</th>
                    <th rowspan="4">10</th>
                    <th>9-10分</th>
                    <th>充分认可企业文化，主动积极在实际工作中践行并宣扬</th>
                </tr>
                <tr>
                    <th>7-8分</th>
                    <th>对企业文化认知尚佳，对工作中体现出的企业文化有一定的认同</th>
                </tr>
                <tr>
                    <th>6-7分</th>
                    <th>对企业文化认知模糊，对公司各项规章制度不关心，不重视</th>
                </tr>
                <tr>
                    <th>0-6分</th>
                    <th>不尊重企业文化，对规章制度和公司举办的各项活动不积极配合的</th>
                </tr>
            </table>
        </div>
        <div id="personTable" class="float-left mb15" style="overflow-x:auto">
            <form id="perTableForm">
                <input type="hidden"   id="jobRatingName"  name="jobRatingName" value="" />
                <input type="hidden"   id="staffNameList"  name="staffNameList" value="" />
                <table class="eiaHrEvalTable" id="rightTitle">
                    <tr class="titleTop pt15 pr1">
                        %{--<input type="hidden" name="jobRatingScoreId" id="jobRatingScoreId" value="" />--}%
                        %{--<input type="hidden" id="staffId" value="" />--}%
                        %{--<input type="hidden" id="jobRatingType" value="" />--}%
                        %{--<input type="hidden" id="orgId" value="" />--}%
                    </tr>
                    %{--<tr class="mt1">--}%
                    %{--</tr>--}%
                </table>
                <table id="perTable" class="eiaHrEvalTable">
                    <tr class="lessThan20" id="workingAttitude">
                    </tr>
                    <tr class="lessThan10" id="teamSpirit">
                    </tr>
                    <tr class="lessThan10" id="cultureCognition">
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>