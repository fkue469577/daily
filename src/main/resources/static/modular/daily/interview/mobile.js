var current=1, size=10;
var titleList;

function page() {
    var data = $("#searchForm").serializeObject();
    data.page = current;
    data.size = size;
    $.ajax({
        type: "get",
        url: "/daily/interview/page",
        headers: {
            "Authorization": localStorage.getItem("Authorization")
        },
        data: data,
        success: function(res) {

            var html = res.data.map((e, i)=>
                `<div class="item">
                <a href="/daily/interview/mobile/detail/${e.id}">
                <div class="term">
                    <span class="title">题目标题: </span>
                    <span class="">${e.title??""}</span>
                </div>
                <div class="term">
                    <span class="title">标题: </span>
                    <span class="">${e.name}</span>
                </div>
                <div class="">
                    ${e.substr_context}
                </div>
                </a>
            </div>`).join("");
            $(".thelist").append(html);
            current++;
        }
    })

}

$("select[name=titleId]").change(function(){
    var id = $("select[name=titleId]").val()
    $("select[name=subTitleId]").remove();
    var children = titleList?.filter(e=>e.id==id)?.[0]?.children;
    if(children) {
        var html = children.map(e=>`<option value="${e.id}">${e.name}</option>`).join("")
        html = `<select name="subTitleId" style="height: 44px; padding: 0 5px; margin-left: 5px;">
						<option value="">--子标题--</option>
						${html}
					</select>`;

        $("select[name=titleId]").after(html);
        form.render(null, 'searchForm');
    }
    page();
});


function repage() {
    $('.thelist').html('');
    current=1;
    page()
}

$(function (){
    $.ajax({
        type: "get",
        url: "/daily/interview/condition",
        headers: {
            "Authorization": localStorage.getItem("Authorization")
        },
        success: function(result) {
            titleList = result.data.titleList;
        }
    })
    page();
    window.parent.fullScreen?.();
})







var myScroll,
    pullDownEl,
    pullDownOffset,
    pullUpEl,
    pullUpOffset,
    generatedCount = 0;

function pullDownAction () {
    setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
        var el, li, i;
        // el = document.getElementById('thelist');
        //
        // for (i=0; i<5; i++) {
        //     li = document.createElement('li');
        //     li.innerText = 'Generated row ' + (++generatedCount);
        //     el.insertBefore(li, el.childNodes[0]);
        // }
        current=1;
        $(".thelist").html("")
        page()
        document.getElementById("pullUp").style.display="";
        myScroll.refresh();
    }, 1000);
}

function pullUpAction () {
    setTimeout(function () {
        var el, li, i;
        // el = document.getElementById('thelist');
        //
        // for (i=0; i<1; i++) {
        //     li = document.createElement('li');
        //     li.innerText = 'Generated row ' + (++generatedCount);
        //     el.appendChild(li, el.childNodes[0]);
        // }
        page();
        myScroll.refresh();
    }, 1000);
}

function loaded() {
    pullDownEl = document.getElementById('pullDown');
    pullDownOffset = pullDownEl.offsetHeight;
    pullUpEl = document.getElementById('pullUp');
    pullUpOffset = 10;
    //pullUpOffset = pullUpEl.offsetHeight;
    myScroll = new iScroll('list', {
        useTransition: true,
        topOffset: pullDownOffset,
        onRefresh: function () {
            //that.maxScrollY = that.wrapperH - that.scrollerH + that.minScrollY;
            //that.minScrollY = -that.options.topOffset || 0;
            //alert(this.wrapperH);
            //alert(this.scrollerH);
            if (pullDownEl.className.match('loading')) {
                pullDownEl.className = '';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';}
            if (pullUpEl.className.match('loading')) {
                pullUpEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
            }

            document.getElementById("pullUp").style.display="none";
            // document.getElementById("show").innerHTML="onRefresh: up["+pullUpEl.className+"],down["+pullDownEl.className+"],Y["+this.y+"],maxScrollY["+this.maxScrollY+"],minScrollY["+this.minScrollY+"],scrollerH["+this.scrollerH+"],wrapperH["+this.wrapperH+"]";
        },
        onScrollMove: function () {
            // document.getElementById("show").innerHTML="onScrollMove: up["+pullUpEl.className+"],down["+pullDownEl.className+"],Y["+this.y+"],maxScrollY["+this.maxScrollY+"],minScrollY["+this.minScrollY+"],scrollerH["+this.scrollerH+"],wrapperH["+this.wrapperH+"]";
            if (this.y > 0) {
                pullDownEl.className = 'flip';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Release to refresh...';
                this.minScrollY = 0;
            }
            if (this.y < 0 && pullDownEl.className.match('flip')) {
                pullDownEl.className = '';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
                this.minScrollY = -pullDownOffset;
            }

            if ( this.scrollerH < this.wrapperH && this.y < (this.minScrollY-pullUpOffset) || this.scrollerH > this.wrapperH && this.y < (this.maxScrollY - pullUpOffset) ) {
                document.getElementById("pullUp").style.display="";
                pullUpEl.className = 'flip';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Release to refresh...';
            }
            if (this.scrollerH < this.wrapperH && this.y > (this.minScrollY-pullUpOffset) && pullUpEl.className.match('flip') || this.scrollerH > this.wrapperH && this.y > (this.maxScrollY - pullUpOffset) && pullUpEl.className.match('flip')) {
                document.getElementById("pullUp").style.display="none";
                pullUpEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
            }
        },
        onScrollEnd: function () {
            // document.getElementById("show").innerHTML="onScrollEnd: up["+pullUpEl.className+"],down["+pullDownEl.className+"],Y["+this.y+"],maxScrollY["+this.maxScrollY+"],minScrollY["+this.minScrollY+"],scrollerH["+this.scrollerH+"],wrapperH["+this.wrapperH+"]";
            if (pullDownEl.className.match('flip')) {
                pullDownEl.className = 'loading';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Loading...';
                pullDownAction();	// Execute custom function (ajax call?)
            }
            if (pullUpEl.className.match('flip')) {
                pullUpEl.className = 'loading';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Loading...';
                pullUpAction();	// Execute custom function (ajax call?)
            }
        }
    });

    //setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}

document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);