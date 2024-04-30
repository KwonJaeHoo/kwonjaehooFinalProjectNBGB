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
    // 클릭한 날짜의 배경색을 변경
    var $clicked = $(this);
    if (!$clicked.hasClass("selected")) { // 선택된 요소가 아닌 경우에만 처리
        l.find("div.selected").removeClass("selected").css("background-color", ""); // 선택 클래스를 가진 모든 요소의 선택 클래스를 제거하고 배경색 초기화
        $clicked.addClass("selected").css("background-color", o[n-1]); // 클릭한 요소에 선택 클래스를 추가하고 배경색 변경
        l.find(".today").css("color", "#787878");
        l.find(".today").removeClass("today").css("background-color", "");
    }
    
	var selectedDate = $(".calendar_content").find(".selected").text();
	var selectedMonth = k[n-1];
	var selectedYear = t;
	$("#resMonth").val(selectedMonth);
	$("#resDate").val(selectedDate);
	$("#resYear").val(selectedYear);
	
	$(document).ready(function(){
		$(".btntime").on("click", function(){
			// 현재 클릭된 요소에 대해서만 클래스 조작
			$(".btntime").removeClass("btntime-clicked");
			$(this).addClass("btntime-clicked");
		});
	});
});

})

