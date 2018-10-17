<%@ page import="com.lheia.eia.common.FuncConstants" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaHrEvalScore/eiaHrEvalStatis.js"/>
    <asset:stylesheet src="/eiaHrEval/eiaHrEvalStatis.css"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief mt0" lay-filter="">
        <ul class="layui-tab-title">
            <li class="layui-this">普通员工</li>
            <g:if test="${isShowAss}">
                <li>部门助理</li>
            </g:if>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <div class="layui-tab-item layui-show">
                <blockquote class="layui-elem-quote larry-btn">
                    <form class="layui-form">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <select name="ratingYearEmp" id="ratingYearEmp" lay-filter="ratingYearEmp">
                                    <option value="">请选择考核年</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select name="ratingMonthEmp" id="ratingMonthEmp" lay-filter="ratingMonthEmp">
                                    <option value="">请选择考核月</option>
                                    <option value="01">1月</option>
                                    <option value="02">2月</option>
                                    <option value="03">3月</option>
                                    <option value="04">4月</option>
                                    <option value="05">5月</option>
                                    <option value="06">6月</option>
                                    <option value="07">7月</option>
                                    <option value="08">8月</option>
                                    <option value="09">9月</option>
                                    <option value="10">10月</option>
                                    <option value="11">11月</option>
                                    <option value="12">12月</option>
                                </select>
                            </div>
                            <div class="layui-btn-group top-group">
                                <a class="layui-btn search_btn pl12" onclick="getEmpSelect()"><i class="larry-icon">&#xe939;</i> 查询</a>
                                <a class="layui-btn search_btn pl12" id="empExport" onclick="empExport()"><i class="larry-icon">&#xe8cd;</i> 导出</a>
                            </div>
                        </div>
                    </form>
                </blockquote>
                <table style="table-layout:fixed" class="layui-copy mt15">
                    <thead>
                    <tr>
                        <th>项目</th>
                        <th rowspan="2">考核年月</th>
                        <th colspan="3">工作绩效</th>
                        <th colspan="3">工作态度表现</th>
                        <th rowspan="2">最终得分</th>
                        <th rowspan="2" width="8%" >操 作</th>
                    </tr>
                    <tr>
                        <th>评分对象</th>
                        <th>工作执行力(20)</th>
                        <th>工作业绩(20)</th>
                        <th>工作技能(20)</th>
                        <th>工作态度(20)</th>
                        <th>团队精神(10)</th>
                        <th>企业文化认知(10)</th>
                    </tr>
                    </thead>
                    <tbody id="eiaHrEvalEmpContent">
                    </tbody>
                </table>
            </div>
            <div class="layui-tab-item">
                <blockquote class="layui-elem-quote larry-btn">
                    <form class="layui-form">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <select name="ratingYearAss" id="ratingYearAss" lay-filter="ratingYearAss">
                                    <option value="">请选择考核年</option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select name="ratingMonthAss" id="ratingMonthAss" lay-filter="ratingMonthAss">
                                    <option value="">请选择考核月</option>
                                    <option value="01">1月</option>
                                    <option value="02">2月</option>
                                    <option value="03">3月</option>
                                    <option value="04">4月</option>
                                    <option value="05">5月</option>
                                    <option value="06">6月</option>
                                    <option value="07">7月</option>
                                    <option value="08">8月</option>
                                    <option value="09">9月</option>
                                    <option value="10">10月</option>
                                    <option value="11">11月</option>
                                    <option value="12">12月</option>
                                </select>
                            </div>
                            <div class="layui-btn-group top-group">
                                <a class="layui-btn search_btn pl12" onclick="getAssSelect()"><i class="larry-icon">&#xe939;</i> 查询</a>
                                <a class="layui-btn search_btn pl12" id="assExport" onclick="assExport()"><i class="larry-icon">&#xe8cd;</i> 导出</a>
                            </div>
                        </div>
                    </form>
                </blockquote>
                <table style="table-layout:fixed" class="layui-copy mt15">
                    <thead>
                    <tr>
                        <th>项目</th>
                        <th rowspan="2">考核年月</th>
                        <th colspan="3">员工评分</th>
                        <th colspan="4">部门经理评分</th>
                        <th rowspan="2">最终得分</th>
                        <th rowspan="2" width="8%" >操 作</th>
                    </tr>
                    <tr>
                        <th>评分对象</th>
                        <th>团队精神(10)</th>
                        <th>领导能力(10)</th>
                        <th>专业能力(10)</th>
                        <th>工作执行力(20)</th>
                        <th>工作绩效(20)</th>
                        <th>工作态度(20)</th>
                        <th>领导能力(10)</th>
                    </tr>
                    </thead>
                    <tbody id="eiaHrEvalAssContent">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="eiaHrEvalScoreId" value=""/>
</body>
</html>