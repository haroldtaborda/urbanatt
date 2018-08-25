var app = angular.module('sbAdministrative')

app.controller("loginCtrl", function($state, $scope, CONFIG, MESSAGES, loginSvc, $rootScope, $uibModal,$window) {
    var vm = this;

    $window.sessionStorage.clear();

    vm.authenticate = function() {
        $rootScope.$broadcast('startLoading')
        loginSvc.login(vm.userId, vm.password).then(function(res) {
            $rootScope.$broadcast('endLoading');
            if (res.data.responseResult.result) {
                $state.go("app.inicio");
            } else {
                var informationAlert = $uibModal.open({
                    animation: true,
                    templateUrl: "app/views/modals/informationModal.html",
                    controller: function($scope) {
                        $scope.title = "Inicio de sesión";
                        $scope.message = res.data.responseResult.dtls.errorDescription;
                        $scope.acept = function() {
                          informationAlert.close();
                        }
                    }
                });
            }
        }, function(entryError) {
            $rootScope.$broadcast('endLoading');

        });
    };
});
