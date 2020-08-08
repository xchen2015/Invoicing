/**
 * Javascript functions to show top nitification
 * Error/Success/Info/Warning messages
 * Developed By: Ravi Tamada
 * url: http://androidhive.info
 * © androidhive.info
 * 
 * Created On: 10/4/2011
 * version 1.0
 * 
 * Usage: call this function with params 
 showNotification(params);
 **/

function showNotification(params){
    // options array
    var options = { 
        'showAfter': 0, // number of sec to wait after page loads
        'duration': 0, // display duration
        'autoClose' : false, // flag to autoClose notification message
        'type' : 'success', // type of info message error/success/info/warning
        'message': '', // message to display
        'link_notification' : '', // link flag to show extra description
        'description' : '' // link to description to display on clicking link message
    }; 
    // Extending array from params
    $.extend(true, options, params);
    
    var msgclass = 'tau-system-message-success'; // default success message will shown
    if(options['type'] == 'error'){
        msgclass = 'tau-system-message-error'; // over write the message to error message
    } else if(options['type'] == 'information'){
        msgclass = 'tau-system-message-information'; // over write the message to information message
    } else if(options['type'] == 'warning'){
        msgclass = 'tau-system-message-warning'; // over write the message to warning message
    } 
    
    // Parent Div container
	
	var container = '<div id="tau-system-message_notifyBar" class="tau-system-message '+msgclass+ '">';

	container += '<div class="tau-system-message-body"><div class="tau-img"></div>';
	container += '<div class="tau-txt"><h3>' + options['message'] + '</h3>';
	container += '<p>' + options['description'] + '</p>';
	container += '</div><div class="tau-system-message-button" style="display: block;"><div class="tau-btn" onclick="return closeNotification()"></div></div></div></div>';
    
    $notification = $(container);
    
    // Appeding notification to Body
    $('body').append($notification);
    
    var divHeight = $('div#tau-system-message_notifyBar').height();
    // see CSS top to minus of div height
    $('div#tau-system-message_notifyBar').css({
        top : '-'+divHeight+'px'
    });
    
    // showing notification message, default it will be hidden
    $('div#tau-system-message_notifyBar').show();
    
    // Slide Down notification message after startAfter seconds
    slideDownNotification(options['showAfter'], options['autoClose'],options['duration']);
    
    $('.link_notification').bind('click', function(){
        $('.info_more_descrption').html(options['description']).slideDown('fast');
    });
    
}
// function to close notification message
// slideUp the message
function closeNotification(duration){
    var divHeight = $('div#tau-system-message_notifyBar').height();
    setTimeout(function(){
        $('div#tau-system-message_notifyBar').animate({
            top: '-'+divHeight
        }); 
        // removing the notification from body
        setTimeout(function(){
            $('div#tau-system-message_notifyBar').remove();
        },200);
    }, parseInt(duration * 1000));   
    

    
}

// sliding down the notification
function slideDownNotification(startAfter, autoClose, duration){    
    setTimeout(function(){
        $('div#tau-system-message_notifyBar').animate({
            top: 0
        }); 
        if(autoClose){
            setTimeout(function(){
                closeNotification(duration);
            }, duration);
        }
    }, parseInt(startAfter * 1000));    
}




