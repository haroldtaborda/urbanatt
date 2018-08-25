//registers a service factory function
app.factory('logoutSvc', function($http, $q, CONFIG, $window) {
    "use strict";
    return {
        logoutUser: function() {
            var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                headers: {
                    "X-Auth-User": $window.sessionStorage.getItem(CONFIG.userAppId),
                    "X-Auth-Token": $window.sessionStorage.getItem(CONFIG.tokenHeader)
                },
                url: CONFIG.endpoint + "/admin/logout"
            }).success(function(data, status, headers, config, statusText) {
                $window.sessionStorage.clear();
                response.data = data;
                response.status = status;
                response.headers = headers;
                response.config = config;
                response.statusText = statusText;
                deferred.resolve(response);
            }).error(function(data, status, headers, config, statusText) {
                $window.sessionStorage.clear();
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
