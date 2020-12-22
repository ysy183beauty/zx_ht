$(function(){
    /* 轮播 */
    var aBox = $('.con_bix1_lb');
    var aLi = $('.con_bix1_lb ul').find('li');
    var radios = $('.radios').find('span');
    var i = 0;

    radios.eq(0).css('background','#ff0000');
    aLi.eq(0).show();

    aBox.hover(function(){
        clearInterval(timer);
    },function(){
        timer = setInterval(slide,3000);
    });

    var timer = setInterval(slide,3000);
    function slide(){
        i++;
        if(i > aLi.length -1){
            i = 0;
        }
        aLi.eq(i).animate({
            zIndex : '1',
            opacity : '1'
        }).siblings().animate({
            zIndex : '0',
            opacity : '0'
        });
        radios.eq(i).css('background','#ff0000').siblings().css('background','');
    }
    radios.hover(function(){
        i = $(this).index();
        $(this).css('background','#ff0000').siblings().css('background','');
        aLi.eq(i).animate({
            zIndex : '1',
            opacity : '1'
        }).siblings().animate({
            zIndex : '0',
            opacity : '0'
        });
        aLi.stop(true,true);
    })

    /* 新闻切换 */
    var cbtTLi = $('.cbt_T ul').find('li');
    var cbtTUl = $('.cbt_B').find('ul');

    cbtTLi.eq(0).find('a').css('color','#ff0000').next('span').show();

    cbtTLi.click(function(){
        var cbtTLiZ = $(this).index();
        $(this).find('a').css('color','#ff0000').next('span').show();
        $(this).siblings().find('a').css('color','').next('span').hide();
        cbtTUl.eq(cbtTLiZ).show().siblings().hide();
    });

    $('.con_box4_txt').each(function(){
        var c4_t = $(this).find('.c4_t');
        var c4_box = $(this).find('.c4_box');
        c4_t.each(function(){
            var bordtS = $(this).find('.bordt');
            var c4_b = '';
            bordtS.eq(0).css('border-bottom','2px solid #0168B7');
            c4_box.each(function(){
                c4_b = $(this).find('.c4_b');
                bordtS.on('mouseover',function(){
                    var bordtSL = $(this).index();
                    $(this).css('border-bottom','2px solid #0168B7').siblings().css('border-bottom','');
                    c4_b.eq(bordtSL).show().siblings().hide();
                })
            })
        })
    })
});