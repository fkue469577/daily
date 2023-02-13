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
            ,{field:'word', title:'单词', templet: (obj)=>{
                return `<div class="word-class" lay-event="wordFilter">${obj.word}</div>`
                }}
            ,{field:'explain', title:'词义'}
            ,{title:'是否完成', templet: (obj)=>obj.completed? `<span style="color: green">是</span>`:"否", sort: true}
            ,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
        ]]
        ,page: true
    });
    table.on('tool(test)', function(obj){
        return;
        var layEvent = obj.event
        if(layEvent === 'wordFilter'){
            layer.open({
                type: 1
                ,title: false //不显示标题栏
                ,closeBtn: false
                ,area: '300px;'
                ,shade: 0.8
                , shadeClose: true
                ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">你知道吗？亲！<br>layer ≠ layui<br><br> layer 只是作为 layui 的一个弹层模块，由于其用户基数较大，所以常常会有人以为 layui 是 <del>layerui</del><br><br>layer 虽然已被 Layui 收编为内置的弹层模块，但仍然会作为一个独立组件全力维护、升级 ^_^</div>'
                ,success: function(layero){
                    var btn = layero.find('.layui-layer-btn');
                    btn.find('.layui-layer-btn0').attr({
                        href: '../index.htm'/*tpa=http://www.ilayuis.com/*/
                        ,target: '_blank'
                    });
                }
            });
        }
    })
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
        title: model.id? "编辑":"创建" + '单词',
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
