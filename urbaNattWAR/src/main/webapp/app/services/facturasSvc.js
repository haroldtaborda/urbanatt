//registers a service factory function
app.factory('facturasSvc', function($http, $q, CONFIG, $window) {
    "use strict";
    return {
    	crearFactura: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/crearFactura"
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
        crearPrecios: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/crearPrecios"
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
                url: CONFIG.endpoint + "/facturas/abonarFactura"
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
                url: CONFIG.endpoint + "/facturas/eliminarAbono"
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
                url: CONFIG.endpoint + "/facturas/modificarAbono"
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
        actualizarPrecios: function(info) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: info,
                url: CONFIG.endpoint + "/facturas/actualizarPrecios"
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
         actualizarPrecioIndividual: function(info) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: info,
                url: CONFIG.endpoint + "/facturas/actualizarPrecioIndividual"
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
                url: CONFIG.endpoint + "/facturas/generarReporte"
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
         eliminarPrecio: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/facturas/eliminarPrecio"
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
                url: CONFIG.endpoint + "/facturas/eliminarFactura"
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
                url: CONFIG.endpoint + "/facturas/revertirFactura"
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
                url: CONFIG.endpoint + "/facturas/consultasFacturas/"+usuario.numeroFactura+"/"+usuario.estado+"/"+usuario.numeroId+"/"+usuario.dias+"/"+usuario.nombreCliente
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
          consultarPreciosTabla: function(idCliente,nombre) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/facturas/consultarPreciosTabla/"+idCliente+"/"+nombre
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
        consultarPrecios: function(idCliente) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/facturas/consultarPrecios/"+idCliente
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
                url: CONFIG.endpoint + "/facturas/consultarAbonos/"+numeroFactura
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
