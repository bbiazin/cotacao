function contatoMngr(){
    this.StorageKey = "contato";
};

contatoMngr.prototype.findAll = function(callBackFn){
    var url = "json/contatos.json";
    
    $.ajax({
        "url" : url,
        "async" : true,
        "success" : function(data){
            callBackFn(data);
        }
    });
};

contatoMngr.prototype.findById = function(id){
    
};

contatoMngr.prototype.save = function(contato){
    
}

var contatoMngr = new contatoMngr();