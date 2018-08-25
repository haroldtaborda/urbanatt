// declare a module
var app = angular.module("sbAdministrative", ['ui.router', 'ngSanitize', 'ngCsv', 'ui.bootstrap', 'underscore']);
// configure the module
//State Navegation - el location
app.config(function ($stateProvider, $urlRouterProvider) {
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
        .state('app.usuarios', {
            url: "/usuarios",
            templateUrl: "app/views/usuarios/usuarios.html",
            controller: 'usuariosCtrl as usua',
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
});

app.constant("CONFIG", {
    endpoint: "${app.endpoint}",
    userAppId: "userAppId",
    tokenHeader: "tokenHeader",
    roleName: "roleName",
    launchYear: 2016,
    chartColors: ["#3b683e", "#ecbe31", "#63af56", "#d5662a", "#a6502c", "#1a1a1a", "#484a49", "#767777", "#5A5A5A", "#586464", "#645A5A"],
    channelTypes: ["", "CHAT", "VIDEO LLAMADA", "LLAMADA"],
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
});
