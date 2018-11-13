app.controller(
				"facturasCtrl",
				function($state, $scope, MESSAGES, CONFIG,
						$uibModal, $filter, facturasSvc, usuariosSvc, productosSvc, $rootScope) {

					
					$scope.mostrarEditar = false;
					$scope.dias=0;
					$scope.numeroRecibo=null;
					$scope.estados = [ 'Nueva', 'Pendiente', 'Pagada', 'Cancelada', 'Cerrada' , 'En abono' ];
					$scope.roles = [ 'Administrador', 'Vendedor', 'Sin rol' ];
					$scope.tipos = [ 'CREDITO', 'CONTADO' ];
					$scope.sucursales = [];
					function Usuario() {
					}
					;

					
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
					
					$rootScope.facturaArevertir=null;
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
								var t= {};
								$scope.productos=[];
								for (var i = 0; i < res.data.length; i++) {
									t=res.data[i];
									t.seleccionado=false;
									t.cantidad=0;
									$scope.productos.push(t);
								}
							} else {
								console.log("exito sin datos");
								$scope.productos = [];
							}
						}, function(entryError) {
							console.log("error");
							$scope.productos = [];

						});
					}
					
					$scope.consultarFacturasPorCliente=function(){
						
						usuariosSvc.consultarSucursalesPorCC($scope.factura.idCliente).then(function(res) {
							if (res.data != null) {
								$scope.sucursales = res.data;
							} else {
								$scope.sucursales = [];
							}
						}, function(entryError) {
							$scope.sucursales = [];

						});
						
					}
					$scope.valorTotalFactura=0;
					$scope.sumarTotalFactura=function(){
						$scope.valorTotalFactura=0;
						for (var i = 0; i < $scope.productos.length; i++) {
							if($scope.productos[i].cantidad && $scope.productos[i].cantidad > 0){
								$scope.productos[i].valor = $scope.productos[i].valor == null ? 0 : $scope.productos[i].valor;
								$scope.valorTotalFactura=($scope.valorTotalFactura+($scope.productos[i].cantidad*$scope.productos[i].valor));
							}
						}
						
					}
					
					$scope.eliminarUsuario = function(usuario) {
						facturasSvc
						.eliminarFactura(usuario)
						.then(
								function(res) {
									if (res.data.responseResult.result) {
										$scope.mostrarTabla = true;
										var dto ={};
										dto.titulo="Extio";
										dto.mensaje="Registro eliminado exitosamente";
										mostrarMensaje(dto);
										$scope.buscarUsuarios();
									} else {
										var dto ={};
										dto.titulo="Extio";
										dto.mensaje="Se presentaron errores al eliminar el registro";
										mostrarMensaje(dto);
									}
								},
								function(entryError) {
									var dto ={};
									dto.titulo="Extio";
									dto.mensaje="Se presentaron errores al eliminar el registro";
									mostrarMensaje(dto);

								});
					}
					
					$scope.revertirFactura = function(usuario) {
						$rootScope.facturaArevertir=usuario;
						$state.go('app.revertirFacturas');
						
					}
					
					$scope.consultarAbonos = function(factura) {
						var informationAlert = $uibModal
						.open({
							animation : true,
							templateUrl : "app/views/modals/consultaAbonos.html",
							controller : function($scope) {
								$scope.abonosTabla =[];
								facturasSvc.consultarAbonos(factura.idFactura).then(function(res) {
									if (res.data != null) {
										$scope.abonosTabla = res.data;
									} else {
										$scope.abonosTabla = [];
									}
								}, function(entryError) {
									$scope.abonosTabla = [];
								});
								
								
								$scope.modificarAbono = function(abono) {
									modificarAbo(abono);
									$scope.abonosTabla =[];
									facturasSvc.consultarAbonos(factura.idFactura).then(function(res) {
										if (res.data != null) {
											$scope.abonosTabla = res.data;
										} else {
											$scope.abonosTabla = [];
										}
									}, function(entryError) {
										$scope.abonosTabla = [];
									});
								}
								
								$scope.eliminarAbono = function(abono) {
									eliminarAbon(abono);
									$scope.abonosTabla =[];
									facturasSvc.consultarAbonos(factura.idFactura).then(function(res) {
										if (res.data != null) {
											$scope.abonosTabla = res.data;
										} else {
											$scope.abonosTabla = [];
										}
									}, function(entryError) {
										$scope.abonosTabla = [];
									});
								}
								
								$scope.cancelAbonos = function() {
									informationAlert.close();
									$scope.buscarUsuarios();
								}
								$scope.refrescarAbonos = function() {
									$scope.abonosTabla =[];
									facturasSvc.consultarAbonos(factura.idFactura).then(function(res) {
										if (res.data != null) {
											$scope.abonosTabla = res.data;
										} else {
											$scope.abonosTabla = [];
										}
									}, function(entryError) {
										$scope.abonosTabla = [];
									});
								}
							}
						});
					}
					
					function modificarAbo(abono){
						//abrimos el modal para realizar un abono
						var informationAlert = $uibModal
						.open({
							animation : true,
							templateUrl : "app/views/modals/abonosModal.html",
							controller : function($scope) {
								$scope.numeroRecibo=abono.numeroFactura;
									$scope.abonoFactura=abono.valorPagado;
								$scope.acept = function() {
									//abonar llamar el servicio
									if($scope.abonoFactura == null || $scope.abonoFactura == ''){
										var dto ={};
										dto.titulo="Error";
										dto.mensaje="Debe ingresar el valor a abonar";
										mostrarMensaje(dto);
										return;
									}
									abono.numeroFactura=$scope.numeroRecibo;
									abono.valorPagado=$scope.abonoFactura;
									facturasSvc
							.modificarAbono(abono)
							.then(
									function(res) {
										if (res.data.responseResult.result) {
											informationAlert.close();
											var dto ={};
											dto.titulo="Extio";
											dto.mensaje="Registro modificado exitosamente";
											mostrarMensaje(dto);
										} else {
											informationAlert.close();
											var dto ={};
											dto.titulo="Extio";
											dto.mensaje="Se presentaron errores al modificar el abono";
											mostrarMensaje(dto);
										}
									},
									function(entryError) {
										informationAlert.close();
										var dto ={};
										dto.titulo="Extio";
										dto.mensaje="Se presentaron errores al modificar el abono";
										mostrarMensaje(dto);

									});
								}
								$scope.cancel = function() {
									informationAlert.close();
									$scope.buscarUsuarios();
								}
							}
						});
						}
					
					function eliminarAbon(abono){
						facturasSvc
						.eliminarAbono(abono)
						.then(
								function(res) {
									if (res.data.responseResult.result) {
										informationAlert.close();
										var dto ={};
										dto.titulo="Extio";
										dto.mensaje="Registro eliminado exitosamente";
										mostrarMensaje(dto);
									} else {
										informationAlert.close();
										var dto ={};
										dto.titulo="Extio";
										dto.mensaje="Se presentaron errores al eliminar el abono";
										mostrarMensaje(dto);
									}
								},
								function(entryError) {
									informationAlert.close();
									var dto ={};
									dto.titulo="Extio";
									dto.mensaje="Se presentaron errores al eliminar el abono";
									mostrarMensaje(dto);

								});
					}
					
					$scope.buscarUsuarios = function() {
						var us = new Usuario();
						us.numeroFactura = $scope.numeroFactura == null
								|| $scope.numeroFactura == '' ? "TODOS"
								: $scope.numeroFactura;
						us.estado = $scope.estado == null || $scope.estado == '' ? "TODOS"
								: $scope.estado;
						us.numeroId = $scope.numeroId == null || $scope.numeroId == '' ? "TODOS"
								: $scope.numeroId;
						us.dias = $scope.dias == null || $scope.dias == '' ? 0
								: $scope.dias;
						facturasSvc.consultasFacturas(us).then(function(res) {
							if (res.data != null) {
								$scope.facturasTabla = res.data;
							} else {
								$scope.facturasTabla = [];
							}
						}, function(entryError) {
							$scope.facturasTabla = [];

						});
					}

					function inicializar() {
						$rootScope.facturaArevertir=null;
						$scope.mostrarEditar = false;
						$scope.productosFactura=[];
						$scope.mensaje = "";
						$scope.nombreUsuario = "";
						$scope.facturasTabla = [];
						$scope.factura = new Usuario();
						$scope.mostrarTabla = true;
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

					
					$scope.seleccionarProducto = function(productoSeleccionado){
						if(validarSeleccion(productoSeleccionado,$scope.productosFactura)){
							productoSeleccionado.seleccionado=false;
							var index = $scope.productosFactura.indexOf(productoSeleccionado);
							$scope.productosFactura.splice(index,1);
						}else {
						productoSeleccionado.seleccionado=true;
						$scope.productosFactura.push(productoSeleccionado);
						}
					}
					
					function validarSeleccion(desc, tabla){
						var index = tabla.indexOf(desc);
						if(index > 0){
							return true;
						}
						else{
							return false;
						}
					}
					
					$scope.cancelar = function() {
						$scope.valorTotalFactura=0;
						$scope.mostrarTabla = true;
						$scope.factura = new Usuario();
					}
					

					$scope.editarUsuario = function(usuario) {
						$scope.mostrarTabla = false;
						$scope.factura = usuario;
					}
					
					$scope.abonarFactura = function(factura) {
						//abrimos el modal para realizar un abono
						var informationAlert = $uibModal
						.open({
							animation : true,
							templateUrl : "app/views/modals/abonosModal.html",
							controller : function($scope) {
								$scope.acept = function() {
									//abonar llamar el servicio
									if($scope.abonoFactura == null || $scope.abonoFactura == ''){
										var dto ={};
										dto.titulo="Error";
										dto.mensaje="Debe ingresar el valor a abonar";
										mostrarMensaje(dto);
										return;
									}
									var fac={};
									fac.valorPagado=$scope.abonoFactura;
									fac.numeroRecibo=$scope.numeroRecibo;
									fac.idFactura=factura.idFactura;
									fac.numeroFactura=factura.numeroFactura;
									facturasSvc
									.abonarFactura(fac)
									.then(
											function(res) {
												if (res.data.responseResult.result) {
													informationAlert.close();
													var dto ={};
													dto.titulo="Extio";
													dto.mensaje="Registro almacenado exitosamente";
													mostrarMensaje(dto);
													$scope.buscarUsuarios();
												} else {
													informationAlert.close();
													var dto ={};
													dto.titulo="Extio";
													dto.mensaje="Se presentaron errores al realizar el abono";
													mostrarMensaje(dto);
												}
											},
											function(entryError) {
												informationAlert.close();
												var dto ={};
												dto.titulo="Extio";
												dto.mensaje="Se presentaron errores al realizar el abono";
												mostrarMensaje(dto);

											});
								}
								$scope.cancel = function() {
									informationAlert.close();
									$scope.buscarUsuarios();
								}
							}
						});
					}

					$scope.estadoUsuario = function(estado) {
						if (estado == null || estado == 'Activo') {
							return true;
						} else {
							return false;
						}
					}

					function validarObligatorios() {
						var cont = 0;
						$scope.mensaje = "Debe ingresar los campos obligatorios:"
						if ($scope.factura.numeroFactura == null
								|| $scope.factura.numeroFactura == '') {
							$scope.mensaje = $scope.mensaje + " numeroFactura"
							cont++;
						}
						if ($scope.factura.idCliente == null
								|| $scope.factura.idCliente == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " Cliente"
							cont++;
						}
						if ($scope.factura.fechaFactura == null
								|| $scope.factura.fechaFactura == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " fechaFactura"
							cont++;
						}
						if (cont > 0) {
							return true
						} else {
							return false;
						}

					}
					$scope.agregarUsuario = function() {
						$scope.mensaje = "";
						if (validarObligatorios()) {
							var mensaje = $scope.mensaje;
							var informationAlert = $uibModal
									.open({
										animation : true,
										templateUrl : "app/views/modals/informationModal.html",
										controller : function($scope) {
											$scope.title = "Campos obligatorios";
											$scope.message = mensaje;
											$scope.acept = function() {
												informationAlert.close();
											}
										}
									});
							return;
						}
						
						for (var i = 0; i < $scope.productosFactura.length; i++) {
							if($scope.productosFactura[i].seleccionado == true && $scope.productosFactura[i].cantidad==0){
								var informationAlert = $uibModal
										.open({
											animation : true,
											templateUrl : "app/views/modals/informationModal.html",
											controller : function($scope) {
												$scope.title = "Validación productos";
												$scope.message ="Se debe ingresar una cantidad para cada producto seleccionado";
												$scope.acept = function() {
													informationAlert.close();
												}
											}
										});
								return;
							}
						}
						$scope.factura.productos=$scope.productosFactura;
						$scope.factura.valorFactura=$scope.valorTotalFactura;
						facturasSvc
								.crearFactura($scope.factura)
								.then(
										function(res) {
											if (res.data.responseResult.result) {
												$scope.mostrarTabla = true;
			
												var dto ={};
												dto.titulo="Extio";
												dto.mensaje="Resitro agregado con exito";
												mostrarMensaje(dto);
												$scope.buscarUsuarios();
											} else {
												
												var dto ={};
												dto.titulo="Información";
												dto.mensaje=res.data.responseResult.dtls.errorDescription;
												mostrarMensaje(dto);
												
											}
										},
										function(entryError) {
											var dto ={};
											dto.titulo="Extio";
											dto.mensaje="Se presento un error al guardar el registro";
											mostrarMensaje(dto);

										});
					}

					$scope.abrirAgregarUsuario = function() {
						$scope.valorTotalFactura=0;
						$scope.mostrarTabla = false;
						$scope.factura = new Usuario();
						consultarProductos();
						$scope.productosFactura=[];
					}

				});
