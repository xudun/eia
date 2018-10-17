/**金额输入框强制小数点后4位**/
var dotFour = function(value){
    var valF = value ? parseFloat(value) : parseFloat(0),
        valR = Math.round(valF*10000)/10000,
        valS = valR.toString(),
        rs = valS.indexOf('.');
    if(rs<0){
        rs = valS.length;
        valS += '.';
    }
    while (valS.length <= rs + 4){
        valS += '0';
    }
    return valS;
};
/**
 * 参数：cur_this：当前上下文
 *       res： done回调函数传回的值
 *       countFields：需要计算的字段数组
 * **/
function tableAddSubTotalRow(cur_this, res, countFields) {
    var subTotalCount = {},
        curTableId = cur_this.id,
        $curTable = $('#'+curTableId),
        pageDataArr = res.data,
        totalCount = res.totalDetail[0];

    //初始化计算列
    for(var i=0; i<countFields.length; i++){
        subTotalCount[countFields[i]] = 0;
    }
    //计算列小计数
    for(var i=0; i<pageDataArr.length; i++){
        for(var name in pageDataArr[i]){
            countFields.indexOf(name) >= 0 ? subTotalCount[name] += parseFloat(pageDataArr[i][name]) : "";
        }
    }

    //type:0:小计；1：总计
    function insertCol(type) {
        var thisColDataIndex = type == 0 ? cur_this.limit : cur_this.limit+1,
            thisColTdText = type == 0 ? "小计" : "总计",
            thisColTdData = type == 0 ? subTotalCount : totalCount;

        //插入数据行
        var $col = $('.layui-table-main tbody tr',$curTable.next()).eq(0).clone();
        var $colTd = $('td',$col);
        $col.attr("data-index",thisColDataIndex);
        $('.layui-table-cell',$col).html("");
        for(var i=0; i<$colTd.length; i++){
            var tdField = $colTd.eq(i).attr('data-field'),
                tdConText = "-";
            if(tdField == 0){
                tdConText = thisColTdText;
            }
            if(countFields.indexOf(tdField) >= 0){
                tdConText = dotFour(thisColTdData[tdField]);
            }
            $('.layui-table-cell',$colTd.eq(i)).html(tdConText);
        }
        $('.layui-table-main tbody',$curTable.next()).append($col);

        //插入左固定行
        var $colL = $('.layui-table-fixed-l tbody tr',$curTable.next()).eq(0).clone();
        $colL.attr("data-index",thisColDataIndex).find('.layui-table-cell').html(thisColTdText);
        $('.layui-table-fixed-l tbody',$curTable.next()).append($colL);
        //插入右固定行
        if($('.layui-table-fixed-r tbody tr',$curTable.next()).length){
            var $colR = $('.layui-table-fixed-r tbody tr',$curTable.next()).eq(0).clone();
            $colR.attr("data-index",thisColDataIndex).find('.layui-table-cell').html('');
            $('.layui-table-fixed-r tbody',$curTable.next()).append($colR);
        }
    }


    insertCol(0);
    insertCol(1);
}