
/* 
 * UNITY Developers
 */

$(document).ready(function() {
  /* colorbox*/
  $(".example7").colorbox({width:"80%", height:"80%", iframe:true});
  $("#department, #service, #student, #teacher, #staff, #submit_add, #submit_response, #submit_del, #group_name_selection, #class_selection, #newOwner_selection, #newName_selection").hide();
  $("#postForMyWall_selection, #postForGroupWall_selection, #postForFellowWall_selection").hide();
  $("#send_request_ok").hide();
  $("#Ok").hide();
  $("#OkGroup").hide();
  $("#post_for").hide();

  $("#academic_role").change(function(){
        if ($(this).val() == "student" ) {
                $("#service").hide();
                $("#staff").hide();
                $("#teacher").hide();
                $("#student").show("slow");
                $("#department").show("slow");
        } else {
            if ($(this).val() == "teacher" ) {
                $("#service").hide();
                $("#student").hide();
                $("#staff").hide();
                $("#teacher").show("slow");
                $("#department").show("slow");
            }
            else
             if ($(this).val() == "staff" ) {
                $("#department").hide();
                $("#student").hide();
                $("#teacher").hide();
                $("#staff").show("slow");
                $("#service").show("slow");
            }
            else {
                $("#department").hide();
                $("#student").hide();
                $("#teacher").hide();
                $("#staff").hide();
                $("#service").hide();
            }
        }
  });

  $("#connectionType").change(function(){
     if ($(this).val() == "fellow" ) {
         $("#submit_add").hide("fast");
         $("#submit_response").show("fast");
     }
     else {
         $("#submit_response").hide("fast");
         $("#submit_add").show("fast");
     }
  });

  $("#delType").change(function(){
     $("#submit_del").show("fast");
  });

  $("#groupType").change(function(){
    if ($(this).val() == "class" ) {
        $("#group_name_selection").hide("fast");
        $("#class_selection").show("fast");
     }
     else {
         $("#class_selection").hide("fast");
         $("#group_name_selection").show("fast");
     }
  });

  $("#editType").change(function() {
    if ($(this).val() == "1" ) {
        $("#newName_selection").hide("fast");
        $("#newOwner_selection").show("fast");
    }
    else {
        $("#newOwner_selection").hide("fast");
        $("#newName_selection").show("fast");
    }
  });

  $("#post_type").change(function() {
    if ($(this).val() == "wall" ) {
        $("#postForGroupWall_selection").hide("fast");
        $("#postForFellowWall_selection").hide("fast");
        $("#postForMyWall_selection").show("fast");
    }
    else if ($(this).val() == "group" ) {
        $("#postForMyWall_selection").hide("fast");
        $("#postForFellowWall_selection").hide("fast");
        $("#postForGroupWall_selection").show("fast");
    }
    else {
        $("#postForMyWall_selection").hide("fast");
        $("#postForGroupWall_selection").hide("fast");
        $("#postForFellowWall_selection").show("fast");
    }
  });

  $("#send_request").click(function(){
        $("#send_request").hide("slow"); 
        $("#send_request_ok").show("slow");  
  });

  /* input post */
  /*$('input[type="text"]').addClass("idleField");
        $('input[type="text"]').focus(function() {
                $(this).removeClass("idleField").addClass("focusField");
        if (this.value == this.defaultValue){
                this.value = '';
        }
        if(this.value != this.defaultValue){
                this.select();
        }
    });
    $('input[type="text"]').blur(function() {
        $(this).removeClass("focusField").addClass("idleField");
        if ($.trim(this.value == '')){
                this.value = (this.defaultValue ? this.defaultValue : '');
        }
    });*/

    /* */
    $("#followTutor").click(function(){
        $("#followTutor").hide("slow"); 
        $("#Ok").show("slow");
        $("#Ok").hide(5000);
    });

    $("#addFellow").click(function(){
        $("#addFellow").hide("slow"); 
        $("#Ok").show("slow");
        $("#Ok").hide(5000);
    });

    /* delete Friend*/
    $("#relFellow").click(function(){
        $(this).hide(2000); 
    });

    $("#relTutor").click(function(){
        $(this).hide(2000); 
    });

    $("#relFacilitator").click(function(){
        $(this).hide(2000); 
    });

    /* leave group */
    $("#registeredMember").click(function(){
        $(this).hide(2000); 
    });

    /* join group */
    $("#joinG").click(function(){
        $(this).hide("slow"); 
        $("#Ok").show(2000);
        $("#Ok").hide(5000);
    });

    $("#privacy").click(function() {
        $("#post_for").show("fast");
    });          
});
