/**
 * Created by Administrator on 2017/7/18.
 */
//    添加新的一组值
function plusTd(id,tab,inp){
    $("#"+id+">tbody").append("<tr onchange='changeValue("+tab+","+inp+")'> <td><input class='min' type='text' onchange='compareTr(this)'></td> <td><input class='max' onchange='compareTd(this)' type='text'></td> <td><input type='text' ></td> </tr>");
}
//    删除最后一组值
function minusTd(id,tab,inp){
    $("#"+id+">tbody tr:last-child").remove();
    changeValue(tab,inp);
    if($("#"+id+">tbody tr").length<1){
        $("#"+id).parent().css("display","none");
    }
}
function compareTd(e){
    var max=parseInt($(e).val());
    var min=parseInt($(e).parent().prev().find(".min").val());
    if( max < min || max == min){
        $(e).val("上限值必须大于下限值！");
//            console.log(max);
    }
}
function compareTr(e){
    var min=parseInt($(e).val());
    var prevMax=parseInt($(e).parent().parent().prev().find(".max").val());
    if( min < prevMax || prevMax == min){
        $(e).val("要大于上一行的上限值！");
//            console.log(max);
    }
}
//    输入框获取值
function changeValue(tab,inp){
    var arrA=[];
    var arrAtext;
    var arrBtext;
    var baseText;
    $(tab).find("tr").each(function(){
        var arrB=[];
        $(this).find("input").each(function(){
            baseText=$(this).val();
//                console.log(baseText);
            if(baseText != ""){
                arrB.push(baseText);
            }
            arrBtext=arrB.join("#");

        });
        if(arrBtext != ""){
            arrA.push(arrBtext);
        }
        arrAtext=arrA.join();
    });
    $(inp).val(arrAtext);
}
//    输入框赋予值
function giveValue(box,tab,inp){
    var display_flag= $("#"+box).css("display");
    if( display_flag == "none"){
        var arrAtext=$("#"+inp).val();
        $("#"+tab+">tbody tr").remove();
        if (arrAtext){
            var arrA=arrAtext.split(",");
            arrA.forEach(function(elem,index,arr){
                var arrB=elem.split("#");
                if(arrB.length == 1){
                    arrB.push("");
                    arrB.push("");
                }
                if(arrB.length == 2){
                    arrB.push("");
                }
                $("#"+tab+">tbody").append("<tr onchange='changeValue("+tab+","+inp+")'> <td><input class='min' type='text' onchange='compareTr(this)' value="+arrB[0]+"></td> <td><input class='max' onchange='compareTd(this)' type='text' value="+arrB[1]+"></td> <td><input type='text'  value="+arrB[2]+"></td> </tr>");
            });
        }
        else{
            $("#"+tab+">tbody").append("<tr onchange='changeValue("+tab+","+inp+")'> <td><input class='min' type='text' onchange='compareTr(this)'></td> <td><input class='max' onchange='compareTd(this)' type='text'></td> <td><input type='text'></td> </tr>");
        }
        $("#"+box).css("display","block");
    }
    else{
        $("#"+box).css("display","none");
    }
}
