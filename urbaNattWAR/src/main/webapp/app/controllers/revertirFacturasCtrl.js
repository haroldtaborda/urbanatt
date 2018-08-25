app.controller(
				"revertirFacturasCtrl",
				function($state, $scope, $rootScope, MESSAGES, CONFIG,
						$uibModal, $filter, facturasSvc, usuariosSvc, productosSvc) {

					function Usuario() {
					}
					;
					$scope.productos="";
					if($rootScope.facturaArevertir != null && $rootScope.facturaArevertir.productos!=null){
						for (var i = 0; i < $rootScope.facturaArevertir.productos.length; i++) {
							$scope.productos=$scope.productos+", "+ $rootScope.facturaArevertir.productos[i].nombreProducto
							+"-"+$rootScope.facturaArevertir.productos[i].cantidad+" unidades";
						}
						
						$scope.productos=$scope.productos.substring(2,$scope.productos.length);
					}
					
					$scope.tipos = [ 'CREDITO', 'CONTADO' ];
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
					
					function mostrarMensajeCerrar(dto){
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
									$rootScope.facturaArevertir=null;
									$state.go('app.facturas');
								}
							}
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
					
					$scope.cancelar=function(){
						$rootScope.facturaArevertir=null;
						$state.go('app.facturas');
					}
					
					consultarClientes();
					
					$scope.revertirFactura = function() {
						facturasSvc
						.revertirFactura($rootScope.facturaArevertir)
						.then(
								function(res) {
									if (res.data.responseResult.result) {
										$scope.mostrarTabla = true;
										var dto ={};
										dto.titulo="Extio";
										dto.mensaje="Registro revertido exitosamente";
										mostrarMensajeCerrar(dto);
										
									} else {
										var dto ={};
										dto.titulo="Extio";
										dto.mensaje="Se presentaron errores al revertir el registro";
										mostrarMensaje(dto);
									}
								},
								function(entryError) {
									var dto ={};
									dto.titulo="Extio";
									dto.mensaje="Se presentaron errores al revertir el registro";
									mostrarMensaje(dto);

								});
					
					}
				

				});
