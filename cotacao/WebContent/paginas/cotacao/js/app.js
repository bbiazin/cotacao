var app = angular.module("cotacaoApp", ["ui.router", "ui.bootstrap", "ngTagsInput", "ui.utils.masks"]);

// Configurações das rotas
app.config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state("home", {
        "url": "/home",
        "views": {
            "content": {
                "controller": "moduleCtrl",
                "templateUurl": "html/form/GSIS001.html"
            }
        }
    }).state("home.cotacao", {
        "url": "/cotacao",
        "views": {
            "content@": {
                "controller": "cotacaoCtrl",
                "templateUrl": "html/form/GCOT001.html"
            }
        }
    }).state("home.contatos", {
        "url": "/contatos",
        "views": {
            "content@": {
                "controller": "contatoCtrl",
                "templateUrl": "html/form/GCOT002.html"
            }
        }
    });
});

app.controller("routerCtrl", function($scope, $state) {
    $state.go("home.cotacao");
});

app.controller("cotacaoCtrl", function($scope, $modal, $stateParams) {
    $scope.cotacao   = {"habilitadoList": [], "itemList": []};
    $scope.itemIndex = -1;
    
    $scope.validarEmail = function(tag){        
        try {
            var email        = tag.email ? tag.email : tag.nome;
            var atCharIndex  = email.indexOf("@");
            var dotCharIndex = atCharIndex > 0 ? email.substr(atCharIndex).indexOf(".") : -1;
        
            if (atCharIndex <= 0 || dotCharIndex <= 0){
                throw new Error("Email inválido.");
            }
            
            if (!tag.id){
                tag.email = tag.nome;
            }      
            
            if (_.filter($scope.cotacao.habilitadoList, function(o){ return o.email == tag.email}).length > 1){
                throw new Error("habilitado " + tag.nome + " já informado.");
            }
        } catch (ex){
            $scope.cotacao.habilitadoList.pop();
            messageMngr.error(ex.message);
        }
    }
    
    $scope.buscarContato = function(query){
        var result = _.filter($scope.contatoList, function(contato){  
            var nome  = contato ? contato.nome.toString().toUpperCase()  : "";
            var email = contato ? contato.email.toString().toUpperCase() : "";
            
            query = query.toString().toUpperCase();
                
            return nome.indexOf(query) == 0 || email.indexOf(query) == 0;
        });
        
        return result;
    }
    
    $scope.setContatoList = function(contatoList){
        $scope.contatoList = contatoList;        
        $scope.$apply();
    }
    
    $scope.selecionarItem = function(item){
        $scope.itemIndex = $scope.cotacao.itemList.indexOf(item);
    }

    $scope.addItem = function() {
        var modalInstance = $modal.open({
            "templateUrl": "html/modal/GCOT001.html",
            "controller": "cotacaoModalInstanceCtrl",
            "resolve": {
                "cotacao": function() {
                },
                "item" : function(){                    
                }
            }
        });
        
        modalInstance.result.then(function(newitem){ 
            $scope.cotacao.itemList.push(angular.copy(newitem));
        });
    };

    $scope.editarItem = function() {
        try {
            if (!$scope.itemIndex >= 0){
                throw new Error("Não há item selecionado para edição.");
            }

            var item = $scope.cotacao.itemList[$scope.itemIndex];

            var modalInstance = $modal.open({
                "templateUrl": "html/modal/GCOT001.html",
                "controller": "cotacaoModalInstanceCtrl",
                "resolve": {
                    "cotacao": function() {
                        return $scope.cotacao;
                    },
                    "item": function() {
                        return item;
                    }
                }
            });

            modalInstance.result.then(function(newitem){ 
                $scope.cotacao.itemList[$scope.itemIndex] = angular.copy(newitem);
            });
        } catch (ex){
            messageMngr.error(ex.message);
        }
    }
    
    $scope.removerItem = function(){
        if ($scope.itemIndex >= 0){
            $scope.cotacao.itemList.splice($scope.itemIndex, 1);
        }
    }
    
    $scope.enviarCotacao = function(){
        try {
            if ($scope.cotacao.habilitadoList.length == 0){
                $("#FGCOT001").find("[ng-model='this.cotacao.habilitadoList']").find("input").focus();
                throw new Error("Não há habilitado informado para esta cotação.");
            }
            
            if (!$scope.cotacao.assunto){
                $("#FGCOT001").find("[ng-model='this.cotacao.assunto']").focus();
                throw new Error("O assunto deve ser informado.");
            }
            
            if (!$scope.cotacao.itemList.length){
                throw new Error("Não há item informado para esta cotação.");
            }
        } catch(ex){
            messageMngr.error(ex.message);            
        }
    }
    
    contatoMngr.findAll($scope.setContatoList);
});

app.controller("cotacaoModalInstanceCtrl", function($scope, $modalInstance, cotacao, item) {
    $scope.item = item ? angular.copy(item) : {"descricao" : "", "quantidade" : 0};
    
    $scope.salvar = function() { 
        try {
            if ($scope.item.descricao.length == 0){
                $("#MGCOT001").find("[ng-model='this.item.descricao']").focus();
                throw new Error("A descrição do item deve ser informada.");
            }
            
            if ($scope.item.quantidade <= 0){
                $("#MGCOT001").find("[ng-model='this.item.quantidade']").focus();
                throw new Error("A quantidade do item deve ser maior que zero.");
            }
            
            $modalInstance.close($scope.item);
        } catch (ex){
            messageMngr.error(ex.message);            
        }
    };
});

app.controller("contatoCtrl", function($scope, $modal, $stateParams){
    $scope.contatoList  = [];
    $scope.contatoTemp  = {};
    $scope.contatoIndex = -1;
    
    $scope.listarContatos = function(){
        contatoMngr.findAll(function(contatoList){ 
            $scope.contatoList = contatoList;
            $scope.$apply();
        });       
    }
    
    $scope.excluir = function(){  
        $scope.contatoList.splice($scope.contatoIndex, 1);        
        $scope.contatoIndex = -1;
    }
    
    $scope.editar = function(){
        $scope.contatoTemp = angular.copy($scope.contatoList[$scope.contatoIndex]);
    }    
    
    $scope.limpar = function(){
        $scope.contatoTemp  = {};
        $scope.contatoIndex = -1;
    }
    
    $scope.salvar = function(){ 
        if ($scope.contatoTemp.id){
            $scope.contatoList[$scope.contatoIndex] = angular.copy($scope.contatoTemp);
        } else {
            $scope.contatoTemp.id = $scope.contatoList.length + 1;
            $scope.contatoList.push($scope.contatoTemp);
        }
        
        $scope.limpar();
    }
    
    $scope.selecionar = function(contato){
        $scope.contatoIndex = $scope.contatoList.indexOf(contato);
    }
    
    $scope.listarContatos();
});