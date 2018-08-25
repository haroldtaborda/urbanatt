//Procesa el mensaje de error
getError = function(resp){
    
    var debug = false;
    
    var message = resp.data;
    if (debug){
        console.log("1 "+message);
    }
    if (message.responseResult){
        message=message.responseResult;
    }
    if (debug){
        console.log("2 "+message);
    }
    if (message.dtls){
        message=message.dtls;
    }
    if (debug){
        console.log("3 "+message);
    }
    if (message.errorDescription){
        message = message.errorDescription;
    }
    if (debug){
        console.log("4 "+message);
    }
    return message;
}