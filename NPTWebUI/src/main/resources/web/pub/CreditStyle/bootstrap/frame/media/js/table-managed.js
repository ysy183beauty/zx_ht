var TableManaged = function () {

    return {

        //main function to initiate the module
        init: function (id) {

            if (!jQuery().dataTable) {
                return;
            }

            // begin first table
            $('#'+id).dataTable();

            jQuery('#'+id+' .group-checkable').change(function () {
                var set = jQuery(this).attr("data-set");
                var checked = jQuery(this).is(":checked");
                jQuery(set).each(function () {
                    if (checked) {
                        $(this).attr("checked", true);
                    } else {
                        $(this).attr("checked", false);
                    }
                });
                jQuery.uniform.update(set);
            });

            jQuery('#'+id+'_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
            jQuery('.span6 #'+id+'_wrapper .dataTables_filter input').removeClass("medium").addClass("small");
            jQuery('#'+id+'_wrapper .dataTables_length select').addClass("m-wrap"); // modify table per page dropdown
            // jQuery('#sample_1_wrapper .dataTables_length select').select2(); // initialzie select2 dropdown

            // begin second table
            // $('#sample_2').dataTable();
            //
            // jQuery('#sample_2 .group-checkable').change(function () {
            //     var set = jQuery(this).attr("data-set");
            //     var checked = jQuery(this).is(":checked");
            //     jQuery(set).each(function () {
            //         if (checked) {
            //             $(this).attr("checked", true);
            //         } else {
            //             $(this).attr("checked", false);
            //         }
            //     });
            //     jQuery.uniform.update(set);
            // });
            //
            // jQuery('#sample_2_wrapper .dataTables_filter input').addClass("m-wrap small"); // modify table search input
            // jQuery('#sample_2_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
            // // jQuery('#sample_2_wrapper .dataTables_length select').select2(); // initialzie select2 dropdown
            //
            // // begin: third table
            // $('#sample_3').dataTable();
            //
            // jQuery('#sample_3 .group-checkable').change(function () {
            //     var set = jQuery(this).attr("data-set");
            //     var checked = jQuery(this).is(":checked");
            //     jQuery(set).each(function () {
            //         if (checked) {
            //             $(this).attr("checked", true);
            //         } else {
            //             $(this).attr("checked", false);
            //         }
            //     });
            //     jQuery.uniform.update(set);
            // });
            //
            // jQuery('#sample_3_wrapper .dataTables_filter input').addClass("m-wrap small"); // modify table search input
            // jQuery('#sample_3_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
            // // jQuery('#sample_3_wrapper .dataTables_length select').select2(); // initialzie select2 dropdown

        },
        draw:function(id,data){
            $("#"+id).dataTable().fnDestroy();
            $("#"+id+">tbody").html(data);
            $("#"+id).dataTable().fnDraw();
            jQuery('#'+id+'_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
            jQuery('.span6 #'+id+'_wrapper .dataTables_filter input').removeClass("medium").addClass("small");
            jQuery('#'+id+'_wrapper .dataTables_length select').addClass("m-wrap"); // modify table per page dropdown
        }
        ,
        simpleInit:function (id) {
            $("#"+id).dataTable({
                "aLengthMenu": [[ -1], ["全部"] ],
                "iDisplayLength":-1,
            });
        },
        simpleDraw:function (id,data) {
            $("#"+id).dataTable().fnDestroy();
            $("#"+id+">tbody").html(data);
            $("#"+id).dataTable({
                "aLengthMenu": [[ -1], ["全部"] ],
                "iDisplayLength":-1,
            }).fnDraw();
        }
    };

}();