var tree = layui.tree,
    table = layui.table,
    form = layui.form,
    laydate = layui.laydate;


var currentChapterId
    , treeData
    , treeDataMap = {};

var editor;

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
        elem: '.p-b-chapter'  //绑定元素
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
            $(".addNotes").click(function() {
                layer.close(i);
                openWin({bookId: id, chapterId: data.id})
            });
        }
        , click: function(obj) {
            $(".tree-entry-selected").removeClass("tree-entry-selected")
            $(obj.elem[0]).find(".layui-tree-entry:eq(0)").addClass("tree-entry-selected")

            currentChapterId = obj.data.type!=="book"? obj.data.id: undefined;
            loadNotes();
            loadContent();
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

function loadNotes() {
    var where = !currentChapterId?{bookId: id}: {chapterId: currentChapterId};

    $.get("/daily/book/notes/list", where, function(res) {
        var html = res.data.map(e=>`<div data="${encodeURIComponent(JSON.stringify(e))}" class="notes-item">${e.seq} ${e.title}</div>`).join("");
        $(".notes-content").html(html);

        $(".notes-item").click(function(e) {
            var _this = $(this);
            var data = JSON.parse(decodeURIComponent(_this.attr("data")))
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

        // 菜单右击事件
        $(".notes-item").contextmenu(function(e) {
            var _this = $(this);
            if(e.which == 3) {
                var event = event || window.event;
                var offsetY = event.clientY>document.body.offsetHeight-100? event.clientY-90: event.clientY;
                var i = layer.open({
                    type: 1,
                    area: ["150px", "auto"],
                    offset: [offsetY, event.clientX],
                    closeBtn: 0,
                    shadeClose: true,
                    title: false,
                    content: template("chapterMenuTPL", {})
                })

                $(".edit").click(function() {
                    var data = JSON.parse(decodeURIComponent(_this.attr("data")))
                    layer.close(i);
                    openWin(data)
                });

                return false;
            }
        });
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
                    loadNotes()
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

$(".p-b-menu").click(function() {
    var i = $(this).find("i");
    if(i.hasClass("layui-icon-shrink-right")) {
        $(".p-b-l").hide();
        i.removeClass("layui-icon-shrink-right")
        i.addClass("layui-icon-spread-left")
        $(".p-b-r").css("width", "100%")
    } else {
        $(".p-b-l").show();
        i.removeClass("layui-icon-spread-left")
        i.addClass("layui-icon-shrink-right")
        $(".p-b-r").css("width", "75%")
    }
});
function loadContent() {
    $.get("/daily/book/chapter/get/"+currentChapterId, function(res) {
        if(typeof editor!="undefined" && editor) {
            editor.destroy();
        }

        const E = window.wangEditor
        // 切换语言
        const LANG = location.href.indexOf('lang=en') > 0 ? 'en' : 'zh-CN'
        E.i18nChangeLanguage(LANG)
        editor = E.createEditor({
            selector: '#context-text-area',
            // html: res.data.context,
            config: {
                placeholder: 'Type here...',
                MENU_CONF: {
                    uploadImage: {
                        fieldName: 'your-fileName',
                        base64LimitSize: 10 * 1024 * 1024 // 10M 以下插入 base64
                    }
                },
                customPaste: (editor, event) => {
                    console.log(editor, event);
                    event.preventDefault();
                    return false;
                },
                    onChange(editor) {
                }
            }
        })
        editor.setHtml(res.data.context??"");
        var toolbar = E.createToolbar({
            editor,
            selector: '#context-toolbar',
            config: {}
        })
        $("#editor-text-area").css("height", "295px");
        $(".p-b-r #context-toolbar .w-e-toolbar").prepend(`
            <div class="w-e-bar-item">
                <span style="width: 51px;padding: 5px;background-color: #009688;color: white;cursor: pointer;" id="save">保存
                </span>
            </div>
        `);

        $("#save").click(function() {
            var data = {id: currentChapterId, context: editor.getHtml()}
            $.post("/daily/book/chapter/save", data, function(){

            });
        });
    })
}
