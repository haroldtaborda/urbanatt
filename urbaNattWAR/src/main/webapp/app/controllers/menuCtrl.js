app.controller("menuCtrl", function($state, logoutSvc, CONFIG, $window, $uibModal, MESSAGES) {
    var vm = this;

    vm.roleName = $window.sessionStorage.getItem(CONFIG.roleName);

    vm.goTo = function(stateToGo) {
        if (stateToGo === 'login') {
            logoutSvc.logoutUser().then(function(res) {
                if (res.data.responseResult.result) {
                    $state.go(stateToGo);
                } else {
                    var informationAlert = $uibModal.open({
                        animation: true,
                        templateUrl: "app/views/modals/informationModal.html",
                        controller: function($scope) {
                            $scope.title = "Cerrar sesión";
                            $scope.message = "Vuelve pronto";
                            $scope.acept = function() {
                                $state.go('login')
                                informationAlert.close();
                            }
                        }
                    });
                }
            }, function(res) {
                var informationAlert = $uibModal.open({
                    animation: true,
                    templateUrl: "app/views/modals/informationModal.html",
                    controller: function($scope) {
                        $scope.title = "Cerrar sesión";
                        $scope.message = "Regresar al inicio";
                        $scope.acept = function() {
                            $state.go('login')
                            informationAlert.close();
                        }
                    }
                });
            });
        } else {
            $state.go(stateToGo);
        }
    }

});
