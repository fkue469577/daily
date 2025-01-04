$(function() {
    var reg = /(\<\/?\w+\>|\\n|\,|，|\.|。)/g;
    var html = $(".content").html().split(reg)
            .filter(e=>e)
            .map(e=>e.match(reg)? e: `<span>${e}</span>`).join("")
    $(".content").html(html);
})
$(".triangle-facing-right").click(function () {
    history.go(-1)
})

var speech;
// 语音状态 un_start 未开始，speaking 朗读中，paused 已暂停, errored 错误
var speech_status = "un_start";
var content_i=0, content_length;
$(".audio-play").click(()=>{
    console.log(speech, speechSynthesis);
    if(!speech) {
        var span_arr = $(".content span");
        speechSynthesis.cancel();
        content_length = span_arr.length;
        speech = new SpeechSynthesisUtterance(span_arr[content_i].innerText);
        speech.lang = 'zh-CN';
        var classVal = span_arr[content_i].getAttribute("class")
        span_arr[content_i].setAttribute("class", classVal+" " +"reading_back");
        speech.onend = function(event) {
            if(content_length==content_i+1) {
                content_i = 1
            } else {
                content_i++;
            }
            classVal = span_arr[content_i-1].getAttribute("class").replace("reading_back", "")
            span_arr[content_i-1].setAttribute("class", classVal)
            classVal = span_arr[content_i].getAttribute("class")
            span_arr[content_i].setAttribute("class", classVal+" " +"reading_back");
            speech.text = span_arr[content_i].innerText;
            speechSynthesis.speak(speech);
            speech_status="speaking"
        };
        speech.onerror = function(event) {
            speech_status="errored"
            console.log('合成错误', event);
        };
        speech.onpause = function(event) {
            speech_status="paused";
            console.log('暂停', event);
        };
        speech.onresume = function(event) {
            speech_status="speaking"
            console.log('恢复暂停', event);
        };
    }

    if(speech_status=="un_start" || speech_status=="errored") {
        speechSynthesis.speak(speech);
        speech_status="speaking"
    } else if(speech_status=="speaking") {
        speechSynthesis.pause();
    } else if(speech_status=="paused") {
        speechSynthesis.resume();
    }
})