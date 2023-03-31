var form = layer.form
    , table = layui.table;

var audio;

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
            , {title: "读音", templet:(obj)=>{
                return `<div class="voice-c"><i class="voice-player" lay-event="voicePlayerFilter"></i></div>`
                }}
            ,{title:'是否完成', templet: (obj)=>obj.completed? `<span style="color: green">是</span>`:"否", sort: true}
            ,{title: "操作", width: 180, align: 'center', toolbar: '#barDemo' }
        ]]
        ,page: true
    });
    table.on('tool(test)', function(obj){
        window.obj = obj;
        var layEvent = obj.event
        if (layEvent === 'edit') { //编辑
            $.get("/daily/words/get/"+obj.data.id, function(result) {
                openWin(result.data)
            });
        } else if(layEvent === "complete") {
            $.get("/daily/words/complete/"+obj.data.id, function(result) {
                refresh(result, ()=>$(".layui-laypage-btn").click())
            });
        } else if(layEvent === 'wordFilter'){
            $.get("/daily/words/getWordDetail", {"word": encodeURIComponent(obj.data.word)}, function(res) {
                var xmls = res.data.match(/(?<=(\<translation\>\<content\>\<\!\[CDATA\[)).*(?=(\]\]\>\<\/content\>\<\/translation\>))/g);
                layer.open({
                    type: 1,
                    area: ['500px', '300'],
                    title: obj.data.word,
                    shadeClose: true, //点击遮罩关闭
                    content: xmls.map(e=>`<div>${e}</div>`).join("")
                    ,btn: ['提交','取消']
                    ,btnAlign: 'r'
                    ,skin: 'layer-ext-myskin'
                    ,yes:function () {
                        $("#form").checkCommit({
                            url: "/daily/words/save"
                            , index: index
                        })
                    }
                });
            })
        } else if(layEvent === "voicePlayerFilter") {
            var audioSrc = "http://dict.youdao.com/dictvoice?type=0&audio="+obj.data.word
            var _obj = $(obj.tr).find(".voice-player");
            if(audio && audio.src===audioSrc) {
                if(!audio.paused) {
                    audio.pause();
                    $(".voice-playing").removeClass("voice-playing");
                } else {
                    audio.play();
                    _obj.addClass("voice-playing");
                }

                return;
            }

            if(audio && audio.src!=audioSrc) {
                audio.pause();
            }

            $(".voice-playing").removeClass("voice-playing");
            audio = new Audio(audioSrc);
            audio.loop = true;
            audio.play();
            _obj.addClass("voice-playing")
        }
    })
}

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
    var index = layer.open({
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
                url: "/daily/words/save"
                , index: index
            })
        }
    });
}
