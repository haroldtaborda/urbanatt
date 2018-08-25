//registers a service factory function
app.factory('loginSvc', function($http, $q, CONFIG, $window) {
    "use strict";
    return {
        login: function(userId, password) {
            var deferred = $q.defer();
            var response = {};
            
            $http({
                method: 'POST',
                data: {
                    "userName": userId,
                    "password": password
                },
                url: CONFIG.endpoint +"/admin/login"
            }).success(function(data, status, headers, config, statusText) {
              if(typeof data.loginResult !== 'undefined'){
                $window.sessionStorage.setItem(CONFIG.userAppId, userId);
                $window.sessionStorage.setItem(CONFIG.roleName, data.loginResult.roleName);
                $window.sessionStorage.setItem(CONFIG.tokenHeader, data.loginResult.token);
              }
                response.data = data;
                response.status = status;
                response.headers = headers;
                response.config = config;
                response.statusText = statusText;
                deferred.resolve(response);
            }).error(function(data, status, headers, config, statusText) {
                var response = {};
                response.data = data || "Request failed";
                response.status = status;
                response.headers = headers;
                response.config = config;
                response.statusText = statusText;
                deferred.reject(response);
            });
            return deferred.promise;
        }
    };
});
