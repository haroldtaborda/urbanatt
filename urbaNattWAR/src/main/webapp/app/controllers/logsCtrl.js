app.controller("logsCtrl", function($state, retriesSvc, $rootScope, $uibModal, MESSAGES, _, $scope, $filter) {
    var vm = this;

    $rootScope.$broadcast('startLoading');

    vm.getRetries = function() {
        retriesSvc.getRetries().then(function(res) {
            $rootScope.$broadcast('endLoading');
            if (res.data.responseResult.result) {
                vm.retriesList = res.data.errores;
            } else {
                var informationAlert = $uibModal.open({
                    animation: true,
                    templateUrl: "app/views/modals/informationModal.html",
                    controller: function($scope) {
                        $scope.title = "Re intentos";
                        $scope.message = res.data.responseResult.dtls.errorDescription;
                        $scope.acept = function() {
                            informationAlert.close();
                        }
                    }
                })

            }
        }, function(res) {
            $rootScope.$broadcast('endLoading');
            if (res.status !== 200 && res.status !== 401) {
                var informationAlert = $uibModal.open({
                    animation: true,
                    templateUrl: "app/views/modals/informationModal.html",
                    controller: function($scope) {
                        $scope.title = "Re intentos";
                        $scope.message = MESSAGES.GENERAL_ERROR;
                        $scope.acept = function() {
                            informationAlert.close();
                        }
                    }
                })
            }
        });
    };

    vm.deleteRetry = function(appointmentId) {
        $rootScope.$broadcast('startLoading');
        retriesSvc.deleteRetry(appointmentId).then(function(res) {
            $scope.message = "Se ha eliminado el registro con exito";
            var modalInstance = $uibModal.open({
                animation: $scope.animationsEnabled,
                templateUrl: 'app/views/modals/informationModal.html',
                controller: function($uibModalInstance, $scope) {
                    $scope.acept = function() {
                        $uibModalInstance.dismiss('cancel');
                    };
                },
                scope: $scope,
                size: 'sm'
            });
            $rootScope.$broadcast('startLoading');
            vm.getRetries();
        }, function(res) {
            if (!angular.isUndefined(res.data.responseResult.dtls)) {
                $scope.message = getError(res);
            } else {
                $scope.message = MESSAGES.GENERAL_ERROR;
            }
            var modalInstance = $uibModal.open({
                animation: $scope.animationsEnabled,
                templateUrl: 'app/views/modals/informationModal.html',
                controller: function($uibModalInstance, $scope) {
                    $scope.acept = function() {
                        $uibModalInstance.dismiss('cancel');
                    };
                },
                scope: $scope,
                size: 'sm'
            });
            $rootScope.$broadcast('startLoading');
            vm.getRetries();
        });
    };

    vm.setRetry = function(appointmentId) {
        $rootScope.$broadcast('startLoading');
        retriesSvc.setRetry(appointmentId).then(function(res) {
            $scope.message = "Se ha realizado el proceso de reintento con exito";
            var modalInstance = $uibModal.open({
                animation: $scope.animationsEnabled,
                templateUrl: 'app/views/modals/informationModal.html',
                controller: function($uibModalInstance, $scope) {
                    $scope.acept = function() {
                        $uibModalInstance.dismiss('cancel');
                    };
                },
                scope: $scope,
                size: 'sm'
            });
            $rootScope.$broadcast('startLoading');
            vm.getRetries();
        }, function(res) {
            if (res.status !== 409) {
                if (!angular.isUndefined(res.data.responseResult.dtls)) {
                    $scope.message = getError(res);
                } else {
                    $scope.message = MESSAGES.GENERAL_ERROR;
                }
            } else {
                $scope.message = "Se presentaron conflictos al realizar el proceso";
            }
            var modalInstance = $uibModal.open({
                animation: $scope.animationsEnabled,
                templateUrl: 'app/views/modals/informationModal.html',
                controller: function($uibModalInstance, $scope) {
                    $scope.acept = function() {
                        $uibModalInstance.dismiss('cancel');
                    };
                },
                scope: $scope,
                size: 'sm'
            });
            $rootScope.$broadcast('startLoading');
            vm.getRetries();
        });
    };

    vm.getRetries();
});
