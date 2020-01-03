//registers a service factory function
app.factory('comprasSvc', function($http, $q, CONFIG, $window) {
    "use strict";
    return {
    	crearFactura: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/crearCompra"
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
        abonarFactura: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/abonarCompra"
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
        eliminarAbono: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/eliminarAbonoCompra"
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
        modificarAbono: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/modificarAbonoCompra"
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
        generarReporte: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/generarReporteCompra"
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
        eliminarFactura: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/eliminarCompra"
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
        revertirFactura: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/revertirCompra"
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
        consultasFacturas: function(usuario) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/facturas/consultasCompras/"+usuario.numeroFactura+"/"+usuario.estado+"/"+usuario.numeroId+"/"+usuario.laboratorio
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
        consultarAbonos: function(numeroFactura) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/facturas/consultarAbonosCompras/"+numeroFactura
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
