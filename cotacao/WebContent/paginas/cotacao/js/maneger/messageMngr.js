function messageMngr() {
    this.layout  = "bottomCenter";
    this.theme   = "relax";
    this.timeout = 2000;
}

messageMngr.prototype.success = function(text, modal) {
    noty({
        "text": text,
        "layout": this.layout,
        "type": "success",
        "modal": modal,
        "theme": this.theme,
        "timeout": this.timeout
    });
}

messageMngr.prototype.error = function(text, modal) {
    noty({
        "text": text,
        "layout": this.layout,
        "type": "error",
        "modal": modal,
        "theme": this.theme,
        "timeout": this.timeout
    });
    
    throw new Error(text);
}

messageMngr.prototype.info = function(text, modal) {
    noty({
        "text": text,
        "layout": this.layout,
        "type": "alert",
        "modal": modal,
        "theme": this.theme,
        "timeout": this.timeout
    });
}

messageMngr.prototype.warning = function(text, modal) {
    noty({
        "text": text,
        "layout": this.layout,
        "type": "warning",
        "modal": modal,
        "theme": this.theme,
        "timeout": this.timeout
    });
}

var messageMngr = new messageMngr();