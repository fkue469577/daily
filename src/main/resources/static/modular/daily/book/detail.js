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
        tree.render({
            elem: '.p-b-l'  //绑定元素
            , onlyIconControl: true
            , data: res.data
            , rightClickFunc: function(elem, data) {
                console.log(elem.offset().left, elem.width())
                // console.log(elem, data);
                // console.log(elem.offset().top, elem.offset().left);
                layer.open({
                    type: 1,
                    area: ["150px", "auto"],
                    offset: [elem.offset().top + elem.height(), elem.offset().left+elem.width()],
                    closeBtn: 0,
                    shadeClose: true,
                    title: false,
                    content: template("menuTPL", {})
                })
                $(".layui-layer-shade").on("contextmenu", e1=>false);
                $(".add").click(function (){
                    openChapter({});
                });
                $(".edit").click(function (){
                    openChapter({id: data.id, name: data.name, parentId: data.parentId});
                });
            }
            , click: function(data) {
                
            }
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

function openChapter(model) {

}
