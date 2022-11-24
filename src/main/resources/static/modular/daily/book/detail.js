var tree = layui.tree,
    table = layui.table,
    form = layui.form,
    laydate = layui.laydate;

var currentChapterId;

$(function() {
    loadTree()
})
//渲染
function loadTree() {
    $.get("/daily/book/chapter/tree", {bookId: id}, function(res) {
        treeHandle(res.data[0].children);

        tree.render({
            elem: '.p-b-l'  //绑定元素
            , onlyIconControl: true
            , data: res.data
            , rightClickFunc: function(elem, data) {
                var i = layer.open({
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
                    layer.close(i)
                    openChapter(data.type==="book"? {bookId: id}:{bookId: id, parentId: data.id, parentName: data.name});
                });
                $(".edit").click(function (){
                    if(data.type === 'book') return
                    layer.close(i)
                    openChapter({id: data.id, name: data.name, parentId: data.parentId, parentName: data.parentName});
                });
            }
            , click: function(obj) {
                $(".tree-entry-selected").removeClass("tree-entry-selected")
                $(obj.elem[0]).find(".layui-tree-entry:eq(0)").addClass("tree-entry-selected")

                currentChapterId = obj.data.type!=="book"? obj.data.id: undefined;
                pageNotes();
            }
        });

        $(".layui-tree-txt:eq(0)").click()
        $(".layui-tree-pack:eq(0)").show()
    })
}


function treeHandle(tree, seq="") {
    tree.forEach((e, i)=>{
        var order = seq+"."+(i+1);
        e.name = order + " " + e.name;
        if(e.children) {
            treeHandle(e.children, order);
        }
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
    layer.open({
        type: 1,
        area: ['550px', 'auto'],
        title: model.id? "编辑":"创建" + '章节',
        shadeClose: true, //点击遮罩关闭
        content: template("chapterTPL", {model: model})
        ,btn: ['提交','取消']
        ,btnAlign: 'r'
        ,skin: 'layer-ext-myskin'
        ,yes:function () {
            $("#chapterForm").checkCommit({
                url: "/daily/book/chapter/save",
            })
        }
    });
    form.render();

    var value = [{value: model.parentId, name: model.parentName??""}];
    loadChapter(model.bookId, value)

    form.on("select(bookId)", function(res) {
        loadChapter(res, value);
    });
}
function loadChapter(bookId, value) {
    $.get(`/daily/book/chapter/listByBookId/${id}`, function(res) {
        var chapter = xmSelect.render({
            el: '#parentId',
            model: { label: { type: 'text' } },
            tree: {
                show: true,
                strict: false,
                expandedKeys: [ -1 ],
            },
            name: "parentId",
            height: 'auto',
            data: res.data,
            on: function(data){
                if(data.isAdd){
                    return data.change.slice(0, 1)
                }
            },
        })
        chapter.setValue(value)
    });
}

function pageNotes() {
    var where = !currentChapterId?{bookId: id}: {chapterId: currentChapterId};
    table.render({
        elem: '#test'
        ,url:'/daily/book/notes/page'
        ,toolbar: '#toolbarDemo'
        ,title: '用户数据表'
        , where: where
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

table.on('tool(test)', function (obj) {
    if (obj.event === 'edit') { //编辑
        $.get("/daily/book/notes/get/"+obj.data.id, function(result) {
            openWin(result.data)
        });
    }
});
//头工具栏事件
table.on('toolbar(test)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
        case 'create':
            openWin({bookId: id, chapterId: currentChapterId})

            break;
    };
});

function openWin(model) {
    var i = layer.open({
        type: 1,
        area: ['700px', '600px'],
        title: model.id? "编辑":"创建" + '章节笔记',
        shadeClose: true, //点击遮罩关闭
        content: template("noteTPL", {model: model})
        ,btn: ['提交','取消']
        ,btnAlign: 'r'
        ,skin: 'layer-ext-myskin'
        ,yes:function () {
            $("textarea[name=context]").val(editor.getHtml());
            $("#form").checkCommit({
                url: "/daily/book/notes/save"
                , callback: function() {
                    layer.close(i)
                    pageNotes()
                }
            })
        }
    });
    form.render();


    const E = window.wangEditor
    // 切换语言
    const LANG = location.href.indexOf('lang=en') > 0 ? 'en' : 'zh-CN'
    E.i18nChangeLanguage(LANG)
    var editor = E.createEditor({
        selector: '#editor-text-area',
        html: model.context,
        config: {
            placeholder: 'Type here...',
            MENU_CONF: {
                uploadImage: {
                    fieldName: 'your-fileName',
                    base64LimitSize: 10 * 1024 * 1024 // 10M 以下插入 base64
                }
            },
            onChange(editor) {
            }
        }
    })
    var toolbar = E.createToolbar({
        editor,
        selector: '#editor-toolbar',
        config: {}
    })
    $("#editor-text-area").css("height", "295px")
}

