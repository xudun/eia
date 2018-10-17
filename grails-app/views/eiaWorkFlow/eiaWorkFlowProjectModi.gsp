<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>流程确认</title>
    <meta name="layout" content="main"/>
    <style>
    .flow-confirm.layui-form .layui-form-label {
        width: 95px;
    }

    .flow-confirm .layui-input-block {
        margin-left: 125px;
    }

    .flow-confirm .layui-textareaBig {
        width: 100%;
        height: 200px
    }
    </style>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend>
                <a name="methodRender" class="pageTitle">流程确认</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form flow-confirm">
        <blockquote class="layui-elem-quote larry-btn mb15">
            <span id="currNodesName"></span>

            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe830;</i> 提交
                    </button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>提交至</label>

                    <div class="layui-input-block">
                        <select name="nodeUser" id="nodeUser" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
             %{--   <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>提交时间</label>

                    <div class="layui-input-block">
                        <input type="text" id="approvalDate" name="approvalDate" class="layui-input" lay-verify="required|date"
                               placeholder="年-月-日" autocomplete="off" value="">
                    </div>
                </div>--}%
            </div>

            <div class="layui-col-xs6">
                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00">*</span>相关工作人员</label>

                    <div class="layui-input-block">
                        <input type="text" id="userNamesDept" name="userNamesDept" class="layui-input"
                               lay-verify="required" readonly>
                        <input type="hidden" id="userNames" name="userNames" value="">
                    </div>
                </div>
            </div>
        </div>



        <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">*</span>审批意见</label>

            <div class="layui-input-block">
                <textarea name="opinion" id="opinion" placeholder="请输入审批意见" class="layui-textarea readonly"
                          lay-verify="required" readonly value=""></textarea>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">*</span>修改内容</label>

            <div class="layui-input-block">
                <textarea name="modiContent" id="modiContent" placeholder="请输入修改内容" class="layui-textareaBig"
                          lay-verify="required" value=""></textarea>
            </div>
        </div>
        %{--<button class="layui-btn "  style="margin-left: 120px" lay-submit="" lay-filter="save">确定</button>--}%
    </form>
</div>

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<asset:javascript src="/eiaWorkFlow/eiaWorkFlowProjectModi.js"/>
</body>
</html>