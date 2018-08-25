
//registers a service factory function
app.factory('usuariosSvc', function($http, $q, CONFIG, $window) {
    "use strict";
    return {
    	agregarUsuario: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/usuarios/crearUsuario"
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
        agregarSucursal: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/usuarios/crearSucursal"
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
        eliminarUsuario: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/usuarios/eliminarUsuario"
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
        eliminarSucursal: function(usuario) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: usuario,
                url: CONFIG.endpoint + "/usuarios/eliminarSucursal"
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
        
        agregarCliente: function(cliente) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: cliente,
                url: CONFIG.endpoint + "/usuarios/crearCliente"
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
        eliminarCliente: function(cliente) {
    		var deferred = $q.defer();
            var response = {};
            $http({
                method: 'POST',
                data: cliente,
                url: CONFIG.endpoint + "/usuarios/eliminarCliente"
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
        consultarUsuarios: function(usuario) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/usuarios/consultarUsuarios/"+usuario.nombreUsuario+"/"+usuario.rol
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
        
        consultarSucursalesPorCC: function(idCliente) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/usuarios/consultarSucursalesPorCC/"+idCliente
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
        
        consultarSucursales: function(idCliente) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/usuarios/consultarSucursales/"+idCliente
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
        
        consultarClientes: function(usuario) {
        	var deferred = $q.defer();
            var response = {};
            $http({
                method: 'GET',
                url: CONFIG.endpoint + "/usuarios/consultarClientes/"+usuario.tipoId+"/"+usuario.numId+"/"+usuario.nombreCliente
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
