var drfz_options={};
(function($){
    $.fn.dropDownForZ = function(opts){
        var $this = this;
        var code = this.attr("id");
        var default_options = {
            width:'200px',
            height:'100px'
        };

        drfz_options[code] = $.extend({},default_options,drfz_options[code],opts)
        var zNodes ;
        var setting;
        if(drfz_options[code].mode == "multi"){
            setting = {
                check: {
                    enable: true,
                    chkStyle: "checkbox",
                    chkboxType: { "Y": "s", "N": "ps" }
                },
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onClick: onClick,
                    onCheck: onCheck
                },
                view: {
                    showIcon: false
                }
            };
        }else{
            setting = {
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onClick: onClick
                },
                view: {
                    showIcon: false
                }
            };
        }

        function onCheck(e, treeId, treeNode) {
            $("#"+code).val('');
            $("#"+code+'Code').val('');

            var zTree = $.fn.zTree.getZTreeObj("treeDemo"+code),
                nodes = zTree.getCheckedNodes(true),
                v = "";

            for(var i = 0; i < nodes.length; i++){

                var id = nodes[i].id.toString();

                if(id.indexOf('P') > -1){
                    continue;
                }

                var text = nodes[i].name;
                var sourceText = $("#"+code).val();
                var sourceId = $("#"+code+'Code').val();

                $("#"+code).val(sourceText==''?text:(sourceText+ ',' + text) );
                $("#"+code+'Code').val(sourceId==''?id:(sourceId+ ',' + id));
            }

            //返回选中的当前节点
            if(drfz_options[code].selecedSuccess){
                (drfz_options[code].selecedSuccess)(nodes[0]);
            }

            //返回 选中的所有节点
            if(drfz_options[code].selectedNodes){
                (drfz_options[code].selectedNodes)(nodes);
            }
        };

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"+code),
                nodes = zTree.getSelectedNodes(),
                v = "";
            if(drfz_options[code].disableParent==true){
                if(nodes[0].children.length>0)
                    return false
            }
            var id = nodes[0].id;
            var text = nodes[0].name;

            if(drfz_options[code].mode == "multi"){
                zTree.checkNode(nodes[0], null, true, true);
                //onCheck(null, null, null);
            }else{
                $("#"+code).val(text);
                $("#"+code+'Code').val(id)
                hideMenu();
                if(drfz_options[code].selecedSuccess){
                    (drfz_options[code].selecedSuccess)(nodes[0]);
                }
            }
        };

        var showMenu = function() {
            $("#menuContent"+code).css({}).slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        };
        var hideMenu = function() {
            $("#menuContent"+code).fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        };
        var onBodyDown = function(event) {
            if (!( event.target.id == "menuContent"+code || $(event.target).parents("#menuContent"+code).length>0)) {
                hideMenu();
            }
        };
        var setDisable = function(){
            $($this).off('click');
        };
        var setEnable = function(){
            $($this).on('click',function(){showMenu()});
        };
        var setParentNodeDisabled = function(){   //父节点禁选
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo"+code);
            var nodes = treeObj.transformToArray(treeObj.getNodes());
            for (var i=0, l=nodes.length; i < l; i++) {
                if (nodes[i].isParent){
                    treeObj.setChkDisabled(nodes[i], true);
                }
            }
        };

        ///根据文本框的关键词输入情况自动匹配树内节点 进行模糊查找
        function AutoMatch(txtObj) {
            console.log("txtObj");
            console.log(txtObj);
            if (txtObj.value.length > 0) {
                $.fn.zTree.init($("#treeDemo"+code), setting, zNodes);
                var zTree = $.fn.zTree.getZTreeObj("treeDemo"+code);
                var nodeList = zTree.getNodesByParamFuzzy("name", txtObj.value);
                console.log(nodeList);
                //将找到的nodelist节点更新至Ztree内
                $.fn.zTree.init($("#treeDemo"+code), setting, nodeList);

                showMenu();
            } else {
                //隐藏树
                //hideMenu();
                $.fn.zTree.init($("#treeDemo"+code), setting, zNodes);
            }
        }

        $(function(){
            // 加载数据
            var parentElement = $($this).parent();
            if(!(typeof opts==="string")){
                parentElement.append('<input id="'+code+'Code" name="'+code+'Code" value="'+(drfz_options[code].defaultValue?drfz_options[code].defaultValue:'')+'" type="hidden"/>');
                parentElement.append('<div id="menuContent'+code+'" class="menuContent" style="display:none; position: absolute;z-index: 9999999999999 "> <ul id="treeDemo'+code+'" class="ztree" style="margin-top:4px; border: 1px solid #d2d2d2;background: #fefefe;width:'+drfz_options[code].width+';height:'+drfz_options[code].height+';overflow-y:scroll;overflow-x:auto;"></ul> </div>')
            }
            $.ajax({
                async : false,
                cache:false,
                type: 'get',
                dataType : 'json',
                url: drfz_options[code].url,
                error: function () {
                    alert('获取节点失败');
                },
                success:function(data){
                    zNodes = data;
                    $(".menuContent").css("width","100%");
                }
            });
            $($this).on('click',function(){showMenu()});
            $.fn.zTree.init($("#treeDemo"+code), setting, zNodes);

            if(opts.ifSearch == true){
                $($this).on('keyup',function(){AutoMatch($this[0])});
            }
            if(opts.parentNodeDisabled == true){
                setParentNodeDisabled();
            }

            if(typeof opts==="string"&&opts=='disable'){
                setDisable()
            }
            if(opts==="string"&&opts=='enable'){
                setEnable()
            }

        });

    }
})(jQuery);