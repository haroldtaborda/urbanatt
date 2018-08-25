//registers a service factory function
app.factory('productosSvc', function($http, $q, CONFIG, $window) {
    "use strict";
    return {
    	crearProducto: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/productos/crearProducto"
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
        eliminarProducto: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/productos/eliminarProducto"
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
        consultarProductos: function(usuario) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/productos/consultarProductos/"+usuario.nombreProducto+"/"+usuario.tipo
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
