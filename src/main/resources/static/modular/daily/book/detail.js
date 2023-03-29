var tree = layui.tree,
    table = layui.table,
    form = layui.form,
    laydate = layui.laydate;


var currentChapterId
    , treeData
    , treeDataMap = {};

$(function() {
    loadTree()
})
//渲染
function loadTree() {
    $.get("/daily/book/chapter/tree", {bookId: id}, function(res) {
        treeData = res.data;
        treeHandle(treeData);
        renderTree();

        $(".layui-tree-txt:eq(0)").click()
    })
}

function renderTree() {
    tree.render({
        elem: '.p-b-l'  //绑定元素
        , onlyIconControl: true
        , data: treeData
        , accordion: true
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
                layer.close(i);
                openChapter(data.type==="book"? {bookId: id}:{bookId: id, parentId: data.id, parentName: data.name??"", seq: data.seq??""});
            });
            $(".edit").click(function (){
                if(data.type === 'book') return
                layer.close(i);
                openChapter({id: data.id, name: data.name, bookId: id, parentName: data.parentName, parentId: data.parentId, seq: data.seq});
            });
        }
        , click: function(obj) {
            $(".tree-entry-selected").removeClass("tree-entry-selected")
            $(obj.elem[0]).find(".layui-tree-entry:eq(0)").addClass("tree-entry-selected")

            currentChapterId = obj.data.type!=="book"? obj.data.id: undefined;
            pageNotes();
        }
        , spreadFunc: function() {

        }
    });
    $(".layui-tree-pack:eq(0)").show()
}


function treeHandle(tree) {
    tree.forEach((e, i)=>{
        treeDataMap[e.id]=e;
        e.titlePre = e.seq;
        if(e.children) {
            treeHandle(e.children);
        }
    })
}


function openChapter(model) {
    layer.open({
        type: 1,
        area: ['550px', 'auto'],
        title: (model.id? "编辑":"创建") + '章节',
        shadeClose: true, //点击遮罩关闭
        content: template("chapterTPL", {model: model})
        ,btn: ['提交','取消']
        ,btnAlign: 'r'
        ,skin: 'layer-ext-myskin'
        ,yes:function () {
            $("#chapterForm").checkCommit({
                timeout: false,
                url: "/daily/book/chapter/save",
                callback: (res)=>{
                    var formData = $("#chapterForm").serializeObject();
                    if(!formData.id) {
                        var children, seq="seq", children="children";
                        var parent = treeDataMap[formData.parentId];
                        children = parent["children"];
                        if(!children) {
                            children = [];
                            parent["children"] = children;
                        }
                        formData[seq]=(parent[seq]? parent[seq]+".": "") + (children.length+1)
                        formData["id"]=res
                        formData["name"]=formData["name"]
                        formData["titlePre"]=formData[seq]
                        treeDataMap[res]=formData
                        children.push(formData)
                    } else {
                        Object.assign(treeDataMap[formData.id], formData);
                    }
                    renderTree();
                    layer.closeAll();
                }
            })
        }
    });
    form.render();

    var value = [{value: model.parentId, name: model.seq + " " + model.parentName}];
    loadChapter(model.bookId, value)
}
function loadChapter(bookId, value) {
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
        data: treeData,
        on: function(data){
            if(data.isAdd){
                return data.change.slice(0, 1)
            }
        },
    })
    chapter.setValue(value)
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
            {field:'chapterName', title:'章节'}
            ,{title:'标题', templet: (obj)=>{
                    return `<div class="p-b-c" data-index="${obj.LAY_TABLE_INDEX}">${obj.title}</div>`
                }}
            ,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
        ]]
        ,page: true
        , done: function (res, curr, count) {
            $(".p-b-c").click(function() {
                var _this = $(this);
                var data = res.data[_this.attr("data-index")]
                var context = data.context
                layer.open({
                    type: 1,
                    content: `<div class="editor-content-view">${context}</div>`,
                    area: ["700px", "500px"],
                    shadeClose: true
                })
                layer.photos({
                    photos: ".editor-content-view"
                    ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
                });
            });
        }
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

