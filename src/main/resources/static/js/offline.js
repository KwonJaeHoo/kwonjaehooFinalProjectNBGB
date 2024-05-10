$(function(){function c()
{
	p();
	var e=h();
	var r=0;
	var u=false;
	l.empty();
	
	while(!u){
		if(s[r]==e[0].weekday){
			u=true
		}
		else{
			l.append('<div class="blank"></div>')
			;r++
		}
	}
	
	for(var c=0;c<42-r;c++){
		if(c>=e.length){
			l.append('<div class="blank"></div>')
		}
		else{
			var v=e[c].day;
			var m=g(new Date(t,n-1,v))?'<div class="today">':"<div>";
			l.append(m+""+v+"</div>")
		}
	}
	
	var y=o[n-1];
	
	a.css("background-color",y).find("h1").text(i[n-1]+" "+t);
	f.find("div").css("color",y);
	l.find(".today").css("background-color",y);
	d()
}

function h(){var e=[];for(var r=1;r<v(t,n)+1;r++){
	e.push({day:r,weekday:s[m(t,n,r)]})
}
return e
}

function p(){
	f.empty();
	for(var e=0;e<7;e++){
		f.append("<div>"+s[e].substring(0,3)+"</div>")
	}
}

function d(){
	var t;
	var n=$(".calendar").css("width",e+"px");
	n.find(t=".calendar_weekdays, .calendar_content").css("width",e+"px").find("div").css({width:e/7+"px",height:q/7+"px","line-height":q/7+"px"});
	n.find(".calendar_header").css({height:q*(1/5)+"px"}).find('i[class^="icon-chevron"]').css("line-height",q*(1/5)+"px")
}

function v(e,t){
	return(new Date(e,t,0)).getDate()
}

function m(e,t,n){
	return(new Date(e,t-1,n)).getDay()
}

function g(e){
	return y(new Date)==y(e)
}

function y(e){
	return e.getFullYear()+"/"+(e.getMonth()+1)+"/"+e.getDate()
}

function b(){
	var e=new Date;
	t=e.getFullYear();
	n=e.getMonth()+1
}

var e=480;

var q=350;

var t=2013;

var n=9;

var r=[];

var i=["JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"];

var k=["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];

var s=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];

var o=["#16a085","#1abc9c","#c0392b","#27ae60","#FF6860","#f39c12","#f1c40f","#e67e22","#2ecc71","#e74c3c","#d35400","#2c3e50"];

var u=$(".calendar");

var a=u.find(".calendar_header");

var f=u.find(".calendar_weekdays");

var l=u.find(".calendar_content");

b();

c();

a.find('i[class^="icon-chevron"]').on("click",function(){
	var e=$(this);
	var r=function(e){
		n=e=="next"?n+1:n-1;
		
		if(n<1){
			n=12;
			t--
		}
		else if(n>12){
			n=1;
			t++
		}
		
		c()
	};
	
	if(e.attr("class").indexOf("left")!=-1){
		r("previous")
	}
	else{
		r("next")
	}
	
})

l.on("click", "div", function() {
    var $clicked = $(this);
    var selectedDate = $clicked.text();
    var selectedMonth = k[n-1];
    var selectedYear = t;
    
    // 이전에 선택한 날짜의 글자색을 원래대로 돌리기
    l.find("div.selected").css("color", ""); 
    
    // 오늘 날짜 이전의 날짜를 선택하지 못하도록 설정
    var today = new Date();
    var clickedDate = new Date(selectedYear, n-1, selectedDate);
    if (clickedDate <= today) {
        alert("해당 날짜는 예약 하실 수 없습니다.");
        l.find("div.selected").removeClass("selected").css("background-color", "");
        $("#resMonth").val("");
        $("#resDate").val("");
        $("#resYear").val("");
        return; // 오늘 날짜 이전의 날짜를 클릭하면 아무 동작도 하지 않음
    }
    
    if (!$clicked.hasClass("selected")) {
        l.find("div.selected").removeClass("selected").css("background-color", "");
        $clicked.addClass("selected").css("background-color", o[n-1]);
        l.find(".selected").css("color", "#ffffff"); // 선택한 날짜의 글자색 변경
        l.find(".today").css("color", "#787878");
        l.find(".today").removeClass("today").css("background-color", "");
    }
    
    // 선택한 날짜 정보를 필드에 설정
    $("#resMonth").val(selectedMonth);
    $("#resDate").val(selectedDate);
    $("#resYear").val(selectedYear);
    
    // 시간 버튼에 대한 이벤트 핸들러 등록
    $(document).ready(function(){
        $(".btntime").on("click", function(){
            $(".btntime").removeClass("btntime-clicked");
            $(this).addClass("btntime-clicked");
        });
    });
});
})

