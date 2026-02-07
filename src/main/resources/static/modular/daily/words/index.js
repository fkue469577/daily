var form = layer.form
    , table = layui.table;

var audio;

$(function () {
    page();
})

function page() {
    table.render({
        elem: '#test'
        , url: '/daily/words/page'
        , toolbar: '#toolbarDemo'
        , title: '用户数据表'
        , where: $("#searchForm").serializeObject()
        , parseData: function (res) {
            res.code = 0;
            return res;
        }
        , cols: [[
            {type: 'checkbox'}
            , {
                field: 'word', title: '单词', templet: (obj) => {
                    return `<div class="word-class" lay-event="wordFilter">${obj.word}</div>`
                }
            }
            , {field: 'explain', title: '词义'}
            , {
                title: "读音", templet: (obj) => {
                    return `<div class="voice-c"><i class="voice-player" lay-event="voicePlayerFilter"></i></div>`
                }
            }
            , {
                title: '是否完成',
                templet: (obj) => obj.completed ? `<span style="color: green">是</span>` : "否",
                sort: true
            }
            , {title: "操作", width: 180, align: 'center', toolbar: '#barDemo'}
        ]]
        , page: true
    });
    table.on('tool(test)', function (obj) {
        window.obj = obj;
        var layEvent = obj.event
        if (layEvent === 'edit') { //编辑
            $.get("/daily/words/get/" + obj.data.id, function (result) {
                openWin(result.data, () => {
                    layer.closeAll();
                    $(".layui-laypage-btn").click()
                })
            });
        } else if (layEvent === "placement") {
            $.get("/daily/words/placement/" + obj.data.id, (res) => {
                refresh(res, () => $(".layui-laypage-btn").click())
            })
        } else if (layEvent === "complete") {
            $.get("/daily/words/complete/" + obj.data.id, function (result) {
                refresh(result, () => $(".layui-laypage-btn").click())
            });
        } else if (layEvent === "uncomplete") {
            $.get("/daily/words/uncomplete/" + obj.data.id, function (result) {
                refresh(result, () => $(".layui-laypage-btn").click())
            });
        } else if (layEvent === "delete") {
            $.get("/daily/words/delete/" + obj.data.id, function (result) {
                refresh(result, () => $(".layui-laypage-btn").click())
            });
        } else if (layEvent === 'wordFilter') {
            $.get("/daily/words/getWordDetail", {"word": encodeURIComponent(obj.data.word)}, function (res) {
                var xmls = res.data.match(/(?<=(\<translation\>\<content\>\<\!\[CDATA\[)).*(?=(\]\]\>\<\/content\>\<\/translation\>))/g)?.[0];
                var symbol = res.data.match(/(?<=(\<us\-phonetic\-symbol\>)).*(?=(\<\/us\-phonetic\-symbol\>))/g)?.[0]
                layer.open({
                    type: 1,
                    area: ['500px', '300'],
                    title: `${obj.data.word}`,
                    shadeClose: true, //点击遮罩关闭
                    content: `<i class="voice-player" lay-event="voicePlayerFilter" style="display: inline-block; cursor: pointer;"></i><span>[${symbol}]</span></br>${xmls}`
                    , btn: ['提交', '取消']
                    , btnAlign: 'r'
                    , skin: 'layer-ext-myskin'
                    , yes: function () {
                        $("#form").checkCommit({
                            url: "/daily/words/save"
                            , index: index
                            , unalert: true
                        })
                    }
                });
                $(".layui-layer .voice-player").click(function () {
                    baiduVoicePlay($(this), obj.data.word)
                })
            })
        } else if (layEvent === "voicePlayerFilter") {
            // youdaoVoicePlay(obj);
            baiduVoicePlay($(obj.tr).find(".voice-player"), obj.data.word);
        }
    })
}

//头工具栏事件
table.on('toolbar(test)', function (obj) {
    var checkStatus = table.checkStatus(obj.config.id);
    switch (obj.event) {
        case 'create':
            openWin({}, () => {
                layer.closeAll();
                $(".layui-laypage-btn").click();
            })
            break;
        case 'imports':
            openImports()
            break;
        case 'oneClickPlacement':
            $.get("/daily/words/oneClickPlacement")
            break;
        case 'oneClickComplete':
            $.get("/daily/words/oneClickComplete")
            break;
    }
    ;
});

function openWin(model, callback) {
    var index = layer.open({
        type: 1,
        area: ['500px', '300'],
        title: model.id ? "编辑" : "创建" + '单词',
        shadeClose: true, //点击遮罩关闭
        content: template("tpl", {model: model})
        , btn: ['提交', '取消']
        , btnAlign: 'r'
        , skin: 'layer-ext-myskin'
        , yes: function () {
            $("#form").checkCommit({
                url: "/daily/words/save"
                , index: index
                , unalert: true
                , callback: callback
            })
        }
    });
}

function openImports() {
    layer.open({
        type: 1,
        area: ['500px', '300px'],
        title: '导入单词',
        shadeClose: true, //点击遮罩关闭
        content: `<form id="form"><textarea style="width: 450px; height: 160px;"></textarea></form>`
        , btn: ['提交', '取消']
        , btnAlign: 'r'
        , skin: 'layer-ext-myskin'
        , yes: function () {
            var data = $("#form textarea").val();
            var words = data.split("\n").map(e => {
                var arrs = e.replaceAll(/\t+/g, "|").split("|");
                return {word: arrs[0], explain: arrs.length > 0 ? arrs[1] : ""}
            })

            $.ajax({
                url: "/daily/words/imports",
                type: "POST",
                data: JSON.stringify(words),
                headers: {"Content-Type": "application/json"}
            })

            // $.post("/daily/words/imports", words, (res)=>{refresh(res)})
        }
    });
}


function youdaoVoicePlay(obj) {
    var audioSrc = "http://dict.youdao.com/dictvoice?type=0&audio=" + obj.data.word
    var _obj = $(obj.tr).find(".voice-player");
    if (audio && audio.src === audioSrc) {
        if (!audio.paused) {
            audio.pause();
            $(".voice-playing").removeClass("voice-playing");
        } else {
            audio.play();
            _obj.addClass("voice-playing");
        }

        return;
    }

    if (audio && audio.src != audioSrc) {
        audio.pause();
    }

    $(".voice-playing").removeClass("voice-playing");
    audio = new Audio(audioSrc);
    audio.loop = true;
    audio.play();
    _obj.addClass("voice-playing")
}


function baiduVoicePlay(voice_player, word) {
    var audioSrc = "/daily/words/audio/" + word
    if (voice_player.hasClass("voice-playing")) {
        audio.pause();
        $(".voice-playing").removeClass("voice-playing");
        return;
    }

    var request = new XMLHttpRequest();
    request.open("get", audioSrc, true);
    request.responseType = "blob";
    request.onload = () => {
        if (request.status === 200) {
            // 获取文件blob数据并保存
            let blob = request.response;
            let srcUrl = window.URL.createObjectURL(blob);


            if (audio && audio.src != audioSrc) {
                audio.pause();
            }

            $(".voice-playing").removeClass("voice-playing");
            audio = new Audio(srcUrl);
            audio.loop = true;
            audio.play();
            window.voice_play = voice_player
            voice_player.addClass("voice-playing")
        }
    };
    request.send();
}
