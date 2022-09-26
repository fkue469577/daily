var form = layui.form
    , table = layui.table
var companyList;

$(function() {
    $.get("/daily/company/list", (e)=>companyList=e.data)
    page();
})

function page() {
    table.render({
        elem: '#test'
        ,url:'/daily/account/password/page'
        ,toolbar: '#toolbarDemo'
        ,title: '用户数据表'
        , where: $("#searchForm").serializeObject()
        ,parseData: function(res) {
            res.code = 0;
            return res;
        }
        ,cols: [[
            {type:'checkbox'}
            ,{field:'company', title:'公司', sort: true}
            ,{field:'url', title:'网站地址', templet: (obj)=>obj.url&&obj.url.indexOf("http")>-1?`<a href="${obj.url}" target="_blank" style="color: blue">${obj.url}</a>`: obj.url}
            ,{field:'account', title:'账户'}
            ,{field:'password', title:'密码'}
            ,{field:'describe', title:'说明'}
            ,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
        ]]
        ,page: true
    });
}

table.on('tool(test)', function (obj) {
    if (obj.event === 'edit') { //编辑
        $.get("/daily/account/password/get/"+obj.data.id, function(result) {
            openWin(result.data)
        });
    } else if(obj.event === "complete") {
        $.get("/daily/account/password/complete/"+obj.data.id, function(result) {
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
        content: template("tpl", {model: model, companyList: companyList})
        ,btn: ['提交','取消']
        ,btnAlign: 'r'
        ,skin: 'layer-ext-myskin'
        ,yes:function () {
            $("#form").checkCommit({
                url: "/daily/account/password/save",
            })
        }
    });
    form.render();
}
