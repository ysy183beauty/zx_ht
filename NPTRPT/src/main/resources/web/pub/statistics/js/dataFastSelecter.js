$(function () {
    $("input[name='dataType']").on('click', function () {
        var startDate;
        var endDate;
        if($(this).val() == 0){
            // 全部
            startDate = Date.parse("2016 01 01");
            // endDate = Date.parse("next month").set({day:1});
        }else if($(this).val() == 1){
            // 本年
            startDate = Date.parse("Jan");
            // endDate = Date.parse("next Jan").set({day:1});
        }else if($(this).val() == 2){
            // 本季
            var startMonth;
            var nowMonth = new Date().getMonth();
            if(nowMonth<3){
                startMonth = 0;
            }else if(nowMonth<6){
                startMonth = 3;
            }else if(nowMonth < 9){
                startMonth = 6;
            }else if(nowMonth <12){
                startMonth = 9;
            }

            if (startMonth == nowMonth) {
                startDate = Date.today().set({day: 1});
            } else {
                startDate = Date.today().moveToMonth(startMonth, -1).set({day: 1});
            }
            // endDate = Date.today().moveToMonth(startMonth+3).set({day:1});
        }else if($(this).val() == 3){
            // 本月
            startDate = Date.parse("month").set({day:1});
            // endDate = Date.parse("next month").set({day:1});
        }

        endDate = Date.today();

        $('#form-date-range').daterangepicker({
            opens:'left',
            startDate: startDate,
            endDate: endDate
        }, function (start, end,label) {
            $("input[name='startDate']").val(start.format('YYYYMMDD'));
            $("input[name='endDate']").val(end.format('YYYYMMDD'));
        });
        $("input[name='startDate']").val(startDate.toString('yyyyMMdd'));
        $("input[name='endDate']").val(endDate.toString('yyyyMMdd'));
    });
});