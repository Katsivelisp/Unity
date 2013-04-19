
/* 
 * UNITY Developers
 */
$(function() {        
    /* number of fieldsets */
    var fieldsetCount = $('#formElem').children().length;
    /*current position of fieldset / navigation link */
    var current = 1;
    /* sum and save the widths of each one of the fieldsets
       set the final sum as the total width of the steps element */
    var stepsWidth = 0;
    var widths = new Array();
    $('#steps .step').each(function(i){
        var $step = $(this);
        widths[i] = stepsWidth;
        stepsWidth += $step.width();
    });

    $('#steps').width(stepsWidth);
    /* to avoid problems in IE, focus the first input of the form */
    $('#formElem').children(':first').find(':input:first').focus();	
    /* show the navigation bar */
    $('#navigation').show();
    /* when clicking on a navigation link the form slides to the corresponding fieldset */
    $('#navigation a').bind('click',function(e){
        var $this = $(this);
        var prev = current;
        $this.closest('ul').find('li').removeClass('selected');
        $this.parent().addClass('selected');
        /* we store the position of the link in the current variable */
        current = $this.parent().index() + 1;
        /*
            animate / slide to the next or to the corresponding
            fieldset. The order of the links in the navigation
            is the order of the fieldsets.
            Also, after sliding, we trigger the focus on the first 
            input element of the new fieldset
            If we clicked on the last link (confirmation), then we validate
            all the fieldsets, otherwise we validate the previous one
            before the form slided
        */
        $('#steps').stop().animate({
            marginLeft: '-' + widths[current-1] + 'px'
        },500,function(){
            if(current == fieldsetCount)
                validateSteps();
            else
                validateStep(1);
            $('#formElem').children(':nth-child('+ parseInt(current) +')').find(':input:first').focus();	
        });
        e.preventDefault();
    });
    /* clicking on the tab (on the last input of each fieldset), makes the form
       slide to the next step */
    $('#formElem > fieldset').each(function(){
        var $fieldset = $(this);
        $fieldset.children(':last').find(':input').keydown(function(e){
            if (e.which == 9){
                $('#navigation li:nth-child(' + (parseInt(current)+1) + ') a').click();
                /* force the blur for validation */
                $(this).blur();
                e.preventDefault();
            }
        });
    });
    /* validates errors on all the fieldsets records if the Form has errors in $('#formElem').data() */
    function validateSteps(){
        var FormErrors = false;
        //for(var i = 1; i < fieldsetCount; ++i){
        var error = validateStep(1);
        if(error == -1)
            FormErrors = true;
        //}
        $('#formElem').data('errors',FormErrors);	
    }
    /* validates one fieldset and returns -1 if errors found, or 1 if not */
    function validateStep(step){
        if(step == fieldsetCount) 
            return;
        var error = 1;
        var hasError = false;
        $('#formElem').children(':nth-child('+ parseInt(step) +')').find(':input:not(button)').each(function(){
            var $this = $(this);
            var valueLength = jQuery.trim($this.val()).length;
            if(valueLength == ''){
                hasError = true;
                $this.css('background-color','#FFEDEF');
                if (parseInt(step)==1) {
                    var emailReg = /^\w+([\.-]?\w+)*@hua.gr/;
                    var emailaddressVal = $("#email").val();
                    if(!emailReg.test(emailaddressVal)) {
                        $("#email").css('background-color','#FFEDEF');
                    }
                    else {
                        hasError = false;
                    }
                    if($("#academic_role").val()=='student'){
                        $("#academic_info-student").show();
                        $("#academic_info-message").hide();
                        $("#academic_info-staff").hide();
                        $("#academic_info-teacher1").hide();
                    }
                    if($("#academic_role").val()=='teacher' && $("#teacher").val()==4){
                        $("#academic_info-teacher1").show();
                        $("#par_position1").hide();
                        $("#academic_info-message").hide();
                        $("#academic_info-student").hide();
                        $("#academic_info-staff").hide();
                    }
                    if($("#academic_role").val()=='teacher' && $("#teacher").val()==5){
                        $("#academic_info-teacher1").show();
                        $("#par_position1").show();
                        $("#par_position").hide();
                        $("#academic_info-message").hide();
                        $("#academic_info-student").hide();
                        $("#academic_info-staff").hide();
                    }
                    if($("#academic_role").val()=='staff') {
                        $("#academic_info-staff").show();
                        $("#academic_info-message").hide();
                        $("#academic_info-teacher1").hide();
                        $("#academic_info-student").hide();
                    }
                    if($("#academic_role").val()=='') {
                       $("#academic_info-message").show();
                       $("#academic_info-staff").hide();
                       $("#academic_info-teacher1").hide();
                       $("#academic_info-student").hide();
                    }                                                                   
                }
            }
            else
                $this.css('background-color','#FFFFFF');	
        });
        var $link = $('#navigation li:nth-child(' + parseInt(step) + ') a');
        $link.parent().find('.error,.checked').remove();
        var valclass = 'checked';
        if(hasError){
            error = -1;
            valclass = 'error';
        }
        $('<span class="'+valclass+'"></span>').insertAfter($link);
        return error;
    }
    /* if there are errors don't allow the user to submit */
    $('#registerButton').bind('click',function(){
        if($('#formElem').data('errors')){
            alert('Please correct the errors in the Form before you proceed!');
            return false;
        }	
    });
    $("#academic_info-message").show();
    $("#par_department, #par_service, #par_student, #tutor, #par_staff, #academic_info-student, #academic_info-teacher1, #academic_info-staff").hide();
    $("#academic_role").change(function(){
        if ($(this).val() == "student" ) {
            $("#par_service").hide();
            $("#par_staff").hide();
            $("#tutor").hide();
            $("#par_student").show("slow");
            $("#par_department").show("slow");
        } else {
            if ($(this).val() == "teacher" ) {
                $("#par_service").hide();
                $("#par_student").hide();
                $("#par_staff").hide();
                $("#tutor").show("slow");
                $("#par_department").show("slow");
            }
            else{
                if ($(this).val() == "staff" ) {
                    $("#par_department").hide();
                    $("#par_student").hide();
                    $("#tutor").hide();
                    $("#par_staff").show("slow");
                    $("#par_service").show("slow");
                }
                else {
                    $("#par_department").hide();
                    $("#par_student").hide();
                    $("#tutor").hide();
                    $("#par_staff").hide();
                    $("#par_service").hide();
                }
            }
        }
    });
});