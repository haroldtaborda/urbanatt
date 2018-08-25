//registers a service factory function
app.factory('generalesSvc', function($http, $q, CONFIG, $window) {
    "use strict";
    return {
        consultarDeptos: function() {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/generales/consultarDeptos"
            }).success(function(data, status, headers, config, statusText) {
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
        },
        consultarCiudadesPorDepto: function(depto) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/generales/consultarCiudadesPorDepto/"+depto
            }).success(function(data, status, headers, config, statusText) {
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
