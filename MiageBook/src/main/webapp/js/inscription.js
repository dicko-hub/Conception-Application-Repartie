$(document).ready(function(){
	$("#pseudo").blur(function(event){
    	event.preventDefault();
    	var pseudo = $("#pseudo").val()
    		$.ajax({
    			url:"/book/rest/inscription?pseudo="+pseudo,
    			success: function(data) {
    				if(data=='no'){
    					$("#msgbox").fadeTo(200,0.1,function(){
    						$(this).html('Ce pseudo est déjà pris').addClass('busy').fadeTo(900,1);
    					});
    				}else{
    					$("#msgbox").fadeTo(200,0.1,function(){
    						$(this).html('Ce pseudo est disponible').addClass('dispo').fadeTo(900,1);
    					});
    				}
    			}
    		});
    });
});