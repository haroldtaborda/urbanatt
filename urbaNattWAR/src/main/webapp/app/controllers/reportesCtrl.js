app.controller(
				"reportesCtrl",
				function($state, $scope, $rootScope, MESSAGES, CONFIG,
						$uibModal, $filter, facturasSvc, usuariosSvc, productosSvc) {

					
					$scope.mostrarEditar = false;
					$scope.estados = [ 'Nueva', 'Pendiente', 'Pagada', 'Cancelada', 'Cerrada' , 'En abono' ];
					$scope.reportes = [ 'Facturas', 'Productos', 'Cuentas por cobrar','Facturas por cliente', 'Total ventas' ];
					$scope.tipos = [ 'Mes', 'Año' ];
					$scope.meses = [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Nomviembre', 'Diciembre' ];
					function Usuario() {
					}
					;

					$scope.factura=false;
					$scope.producto=false;
					$scope.fechaIniOpen = false;
					$scope.fechaFinOpen = false;
				    
					$scope.optionsIni = {maxDate: new Date()};
					$scope.optionsFin = {maxDate: new Date(),minDate: new Date($filter('date')($scope.fechaIni, 'yyyy-MM-dd')+"T23:00:00.000Z")};

					$scope.fechaIniShow = function () {
						$scope.fechaIniOpen = true;
				    }

					$scope.fechaFinShow = function () {
						$scope.fechaFinOpen = true;
				    }
				    
					$scope.updateConstraints = function(){
						$scope.optionsFin = {maxDate: new Date(),minDate: new Date($filter('date')($scope.fechaIni, 'yyyy-MM-dd')+"T23:00:00.000Z")};
				    }
					$scope.seleccionarTipo = function() {
						if($scope.tipoReporte == 'Facturas'){
							$scope.factura=true;
							$scope.producto=false;
							$scope.totalVentas=false;
						}
						else if ($scope.tipoReporte == 'Productos'){
							$scope.factura=false;
							$scope.totalVentas=false;
							$scope.producto=true;
						}
						else if ($scope.tipoReporte == 'Total ventas'){
							$scope.totalVentas=true;
							$scope.producto=false;
							$scope.factura=false;
						}
					}
					
					$scope.generarReporte = function(){
						if($scope.tipoReporte== null || $scope.tipoReporte== ''){
							var t ={};
							t.titulo="Error";
							t.mensaje="Debe seleccionar el reporte a generar y llenar los datos"
							mostrarMensaje(t);
							return;
						}
						var datos = new Usuario();
						datos.tipoReporte=$scope.tipoReporte;
						datos.nombreReporte=$scope.nombreReporte;
						//datos.fechaInicio= new Date($scope.fechaInicial);
						//datos.fechaFinal= new Date($scope.fechaFinal);
						datos.diasFactura=$scope.diasFactura;
						datos.idCliente=$scope.idCliente;
						datos.idProducto=$scope.idProducto;
						datos.tipoFactura=$scope.tipoFactura;
						datos.mes=$scope.mes;
						datos.fecha=new Date($scope.fecha);
						facturasSvc.generarReporte(datos).then(function(res) {
							var t ={};
							t.titulo="Exito";
							t.mensaje="Reporte generado exitosamente en la ruta configurada."
							mostrarMensaje(t);
						}, function(entryError) {
							$scope.clientes = [];

						});
					}
					
					
					function consultarClientes(){
						var us = new Usuario();
						us.tipoId ="TODOS";
						us.numId ="TODOS";
						us.nombreCliente ="TODOS";
						usuariosSvc.consultarClientes(us).then(function(res) {
							if (res.data != null) {
								$scope.clientes = res.data;
							} else {
								$scope.clientes = [];
							}
						}, function(entryError) {
							$scope.clientes = [];

						});
					}

					function consultarProductos(){
						var us = new Usuario();
						us.nombreProducto = "TODOS";
						us.tipo =  "TODOS";
						console.log("llega");
						productosSvc.consultarProductos(us).then(function(res) {
							if (res.data != null) {
								console.log("exito");
								$scope.productos = res.data;
							} else {
								console.log("exito sin datos");
								$scope.productos = [];
							}
						}, function(entryError) {
							console.log("error");
							$scope.productos = [];

						});
					}
				
					function inicializar() {
						$scope.mostrarEditar = false;
						$scope.mensaje = "";
						consultarProductos();
						consultarClientes();

					}
					function mostrarMensaje(dto){
						var informationAlert = $uibModal
						.open({
							animation : true,
							templateUrl : "app/views/modals/informationModal.html",
							controller : function(
									$scope) {
								$scope.title = dto.titulo;
								$scope.message = dto.mensaje;
								$scope.acept = function() {
									informationAlert
											.close();
								}
							}
						});
					}
					
					inicializar();

					$scope.cancelar = function() {
						//login
						$state.go("app.inicio");
					}

					
	
					
				});
