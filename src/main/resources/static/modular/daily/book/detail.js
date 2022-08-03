var tree = layui.tree,
    table = layui.table,
    form = layui.form,
    laydate = layui.laydate;

$(function() {
    loadTree()
})
//渲染
function loadTree() {
    $.get("/daily/book/chapter/tree", {bookId: id}, function(res) {
        var data = JSON.parse(JSON.stringify(res.data).replaceAll("name", "title"))
        tree.render({
            elem: '.p-b-l'  //绑定元素
            , onlyIconControl: true
            , data: data
            , click: function(obj) {
                console.log(obj)
            }
            // ,edit: ['add', 'update'] //操作节点的图标
        });
    })
}

function page(chapterId) {
    table.render({
        elem: '#test'
        ,url:'/daily/book/notes/page'
        ,toolbar: '#toolbarDemo'
        ,title: '用户数据表'
        , where: {bookId: id, chapterId: chapterId}
        ,parseData: function(res) {
            res.code = 0;
            return res;
        }
        ,cols: [[
            {field:'bookName', title:'书本名称'}
            ,{field:'chapterName', title:'章节'}
            ,{field:'title', title:'标题'}
            ,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
        ]]
        ,page: true
    });
}