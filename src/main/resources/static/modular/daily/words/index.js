var form = layer.form
    , table = layui.table

$(function() {
    page();
})

function page() {
    table.render({
        elem: '#test'
        ,url:'/daily/words/page'
        ,toolbar: '#toolbarDemo'
        ,title: '用户数据表'
        , where: $("#searchForm").serializeObject()
        ,parseData: function(res) {
            res.code = 0;
            return res;
        }
        ,cols: [[
            {type:'checkbox'}
            ,{field:'word', title:'标题'}
            ,{field:'explain', title:'词义'}
            ,{title:'是否完成', templet: (obj)=>obj.completed? `<span style="color: green">是</span>`:"否", sort: true}
            ,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
        ]]
        ,page: true
    });
}

table.on('tool(test)', function (obj) {
    if (obj.event === 'edit') { //编辑
        $.get("/daily/words/get/"+obj.data.id, function(result) {
            openWin(result.data)
        });
    } else if(obj.event === "complete") {
        $.get("/daily/words/complete/"+obj.data.id, function(result) {
            refresh(result, ()=>$(".layui-laypage-btn").click())
        });
    }
});
//头工具栏事件
table.on('toolbar(test)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
        case 'create':
            openWin({})

            break;
    };
});

function openWin(model) {
    layer.open({
        type: 1,
        area: ['500px', '300'],
        title: model.id? "编辑":"创建" + '笔记',
        shadeClose: true, //点击遮罩关闭
        content: template("tpl", {model: model})
        ,btn: ['提交','取消']
        ,btnAlign: 'r'
        ,skin: 'layer-ext-myskin'
        ,yes:function () {
            $("#form").checkCommit({
                url: "/daily/words/save",
            })
        }
    });
}