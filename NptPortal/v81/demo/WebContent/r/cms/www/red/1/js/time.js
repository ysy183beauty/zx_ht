// JavaScript Document
  $(function(){
    showTime();
  });
  function checkTime(i){
    if(i<10){
      i="0"+i;
    }
    return i;
  }
  function showTime(){
    var now=new Date();
    var year=now.getFullYear();
    var month=now.getMonth()+1;
    var day=now.getDate();
    var h=now.getHours();
    var m=now.getMinutes();
    var s=now.getSeconds();
    h=checkTime(h);
    m=checkTime(m);
    s=checkTime(s);
    var weekday=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
    // var t=year+"年"+month+"月"+day+"日"+""+h+":"+m+":"+s+"&nbsp;"+weekday[now.getDay()];
      var t=year+"年"+month+"月"+day+"日"+"&nbsp;"+weekday[now.getDay()];
    // $("#time").html(t);
    var timer=setTimeout('showTime()',1000);
  }



