layui.use(['jquery','element'], function(){
    var $ = layui.jquery,
        element = layui.element;

    $.ajax({
        url:"data/messageEdit.json",
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            $('#messageSource').text(data.messageSource);
            $('#messageBody').text(data.messageBody);
            $('#recipient').text(data.recipient);
            $('#messageContent').text(data.messageContent);
        }
    });

});