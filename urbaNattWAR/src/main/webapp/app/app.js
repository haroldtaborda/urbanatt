// declare a module
var app = angular.module("sbAdministrative", ['ui.router', 'ngSanitize', 'ngCsv', 'ui.bootstrap', 'underscore']);
// configure the module
//State Navegation - el location
app.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
    $stateProvider
        .state('login', {
            url: "/login",
            templateUrl: 'app/views/login.html',
            controller: 'loginCtrl as login'
        })
        .state('app', {
            url: "/app",
            templateUrl: 'app/views/menuTmpl.html',
            controller: 'menuCtrl as menu',
            abstract: true
        })
        .state('app.inicio', {
            url: "/inicio",
            templateUrl: "app/views/index.html"
        })
         .state('app.clientes', {
            url: "/clientes",
            templateUrl: "app/views/usuarios/clientes.html",
            controller: 'clientesCtrl as clie',
        })
            .state('app.productos', {
            url: "/productos",
            templateUrl: "app/views/usuarios/productos.html",
            controller: 'productosCtrl as pro',
        })
          
        .state('app.facturas', {
            url: "/facturas",
            templateUrl: "app/views/usuarios/facturas.html",
            controller: 'facturasCtrl as fac',
        })
        
         .state('app.reportes', {
            url: "/reportes",
            templateUrl: "app/views/usuarios/reportes.html",
            controller: 'reportesCtrl as rep',
        })
        
         .state('app.revertirFacturas', {
            url: "/revertirFacturas",
            templateUrl: "app/views/usuarios/revertirFacturas.html",
            controller: 'revertirFacturasCtrl as ver',
        })
        
          .state('app.sucursales', {
            url: "/sucursales",
            templateUrl: "app/views/usuarios/sucursales.html",
            controller: 'sucursalesCtrl as su',
        })

        ;
    $urlRouterProvider.otherwise('/login');
    
    
    $httpProvider.interceptors.push(['$q', '$location', '$window', '$rootScope', function ($q, $location, $window, $rootScope) {
             return {
                 'response': function(response) {
                     if (response.status === 401) {
                         console.log("Response 401");
                     }
                     if($rootScope.usuarioSesion == null || $rootScope.usuarioSesion.nombreUsuario==null){
                     	 $window.location = '/urbaNatt/#/app/login';
                     }
                     return response || $q.when(response);
                 },
                 'responseError': function(rejection) {
                     if (rejection.status === 401) {
                         $window.location.pathname = '/login';
                     }
                     return $q.reject(rejection);
                 }
             };
         }]);

   
});

app.constant("CONFIG", {
    endpoint: "${app.endpoint}",
    userAppId: "userAppId",
    tokenHeader: "tokenHeader",
    roleName: "roleName",
    launchYear: 2019,
    roles: ["Administrador", "Vendedor", "Sin rol"],
    estados: ["Activo", "Inactivo"],
    appVersion: "1.0",
    months: [{
        month: "Jan",
        num: "01"
    }, {
        month: "Feb",
        num: "02"
    }, {
        month: "Mar",
        num: "03"
    }, {
        month: "Apr",
        num: "04"
    }, {
        month: "May",
        num: "05"
    }, {
        month: "Jun",
        num: "06"
    }, {
        month: "Jul",
        num: "07"
    }, {
        month: "Ago",
        num: "08"
    }, {
        month: "Sept",
        num: "09"
    }, {
        month: "Oct",
        num: "10"
    }, {
        month: "Nov",
        num: "11"
    }, {
        month: "Dec",
        num: "12"
    }]

});

app.constant("MESSAGES", {
    NOT_MATCH: "No se encontraron registros",
    GENERAL_ERROR: "Ha ocurrido un error, intentelo nuevamente",
    SUCCESS_UPDATE: "Parametro Actualizado correctamente"
});

app.run(function ($rootScope, CONFIG, MESSAGES) {
    $rootScope.CONFIG = CONFIG;
    $rootScope.MESSAGES = MESSAGES;
    
    $rootScope.pageSize=8;

    $rootScope.configurarPaginador = function(listaDatos,pages,currentPage){
        var ini = currentPage - 4;
        var fin = currentPage + 5;
        if (ini < 1) {
          ini = 1;
          if (Math.ceil(listaDatos.length / $rootScope.pageSize) > $rootScope.pageSize)
            fin = $rootScope.pageSize;
          else
            fin = Math.ceil(listaDatos.length / $rootScope.pageSize);
        } else {
          if (ini >= Math.ceil(listaDatos.length / $rootScope.pageSize) - $rootScope.pageSize) {
            ini = Math.ceil(listaDatos.length / $rootScope.pageSize) - $rootScope.pageSize;
            fin = Math.ceil(listaDatos.length / $rootScope.pageSize);
          }
        }
        if (ini < 1) ini = 1;
        for (var i = ini; i <= fin; i++) {
          pages.push({
            no: i
          });
        }

        if (currentPage >= pages.length){
          currentPage =   pages.length - 1;
        }

    }
    
});
