app.controller(
				"facturasCtrl",
				function($state, $scope, MESSAGES, CONFIG,
						$uibModal, $filter, facturasSvc, usuariosSvc, productosSvc, $rootScope) {

					// variables paginador
					$scope.currentPage = 0;
					$scope.pages = [];
					$scope.descuento=0;
					$scope.vendedores=[ 'Alirio Urbano Martinez', 'Vendedor uno', 'Vendedor dos', 'Vendedor tres', 'Local' ];
					
					$scope.mostrarEditar = false;
					$scope.dias=0;
					$scope.numeroRecibo=null;
					$scope.estados = [ 'Nueva', 'Pendiente', 'Pagada', 'Cancelada', 'Cerrada' , 'En abono' ];
					$scope.roles = [ 'Administrador', 'Vendedor', 'Sin rol' ];
					$scope.tipos = [ 'CREDITO', 'CONTADO' ];
					$scope.sucursales = [];
					$scope.nombreCliente=null;
					$scope.estado=null;
					$scope.dias=null;
					$scope.numeroFactura=null;
					$scope.numeroId=null;
					function Usuario() {
					}
					;

					$scope.clientePrecios=[];
					
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
						productosSvc.consultarProductos(us).then(function(res) {
							if (res.data != null) {
								var t= {};
								$scope.productos=[];
								for (var i = 0; i < res.data.length; i++) {
									t=res.data[i];
									t.seleccionado=false;
									t.cantidad=0;
									$scope.productos.push(t);
								}
							} else {
								$scope.productos = [];
							}
						}, function(entryError) {
							console.log("error");
							$scope.productos = [];

						});
					}
					
					function consultarProductosRegalo(){
						var us = new Usuario();
						us.nombreProducto = "TODOS";
						us.tipo =  "TODOS";
						productosSvc.consultarProductos(us).then(function(res) {
							if (res.data != null) {
								var t= {};
								$scope.productosRegaloConsulta=[];
								for (var i = 0; i < res.data.length; i++) {
									t=res.data[i];
									t.seleccionado=false;
									t.cantidad=0;
									$scope.productosRegaloConsulta.push(t);
								}
							} else {
								$scope.productosRegaloConsulta = [];
							}
						}, function(entryError) {
							$scope.productosRegaloConsulta = [];

						});
					}
					
					$scope.consultarFacturasPorCliente=function(){
						
						usuariosSvc.consultarSucursalesPorCC($scope.clienteSeleccionado.idCliente).then(function(res) {
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
									
								}
								
								$scope.eliminarAbono = function(abono) {
									eliminarAbon(abono);
									
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
								$scope.numeroRecibo=abono.numeroRecibo;
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
					
						if (($scope.numeroFactura == null || $scope.numeroFactura == '') &&
						 ($scope.estado == null || $scope.estado == '') && 
						 ($scope.numeroId == null || $scope.numeroId == '') &&
						 	 ($scope.nombreCliente == null || $scope.nombreCliente == '') &&
						 	 	 ($scope.doas == null || $scope.doas == '')) {
		                      	var dto ={};
										dto.titulo="Error";
										dto.mensaje="Debe ingresar por lo menos un filtro";
										mostrarMensaje(dto);
										return;
							
						}
					
					else{
					
						var us = new Usuario();
						us.nombreCliente = $scope.nombreCliente == null
								|| $scope.nombreCliente == '' ? "TODOS"
								: $scope.nombreCliente;
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
								$scope.currentPage = 0;
								$scope.configPages();
							} else {
								$scope.facturasTabla = [];
							}
						}, function(entryError) {
							$scope.facturasTabla = [];

						});
					  }
					}

					function inicializar() {
						$scope.clienteSeleccionado=null;
						$scope.valorTotalFactura=0;
						$scope.descuento=0;
						$rootScope.facturaArevertir=null;
						$scope.mostrarEditar = false;
						$scope.productosFactura=[];
						$scope.productosRegalo=[];
						$scope.mensaje = "";
						$scope.nombreUsuario = "";
						$scope.facturasTabla = [];
						$scope.factura = new Usuario();
							$scope.clientePrecios=[];
						$scope.mostrarTabla = true;
						consultarProductos();
						consultarProductosRegalo();
						consultarClientes();
						

					}
					function consultarPrecios(cliente) {
						facturasSvc.consultarPrecios(cliente.numId).then(function(res) {
							if (res.data != null) {
								$scope.clientePrecios = res.data;
							} else {
								$scope.clientePrecios = [];
							}
						}, function(entryError) {
							$scope.clientePrecios = [];

						});
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
						$scope.productoVenta=null;
						}
					}
					

					$scope.seleccionarProductoRegalo = function(productoSeleccionado){
						if(validarSeleccion(productoSeleccionado,$scope.productosRegalo)){
							productoSeleccionado.seleccionado=false;
							var index = $scope.productosRegalo.indexOf(productoSeleccionado);
							$scope.productosRegalo.splice(index,1);
							$scope.sumarTotalFactura();
						}else {
						productoSeleccionado.seleccionado=true;
						$scope.productosRegalo.push(productoSeleccionado);
						$scope.productoRegalo=null;
						
						}
					}
					
					function validarSeleccion(desc, tabla){
						var index = tabla.indexOf(desc);
						if(index >= 0){
							return true;
						}
						else{
							return false;
						}
					}
					
					$scope.cancelar = function() {
						$scope.valorTotalFactura=0;
						$scope.descuento=0;
						$scope.clienteSeleccionado=null;
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
						if ($scope.clienteSeleccionado == null
								|| $scope.clienteSeleccionado.idCliente == '') {
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
							if($scope.productosFactura[i].seleccionado == true && ($scope.productosFactura[i].cantidad == null || 
									$scope.productosFactura[i].cantidad==0 || $scope.productosFactura[i].valor==null || $scope.productosFactura[i].valor==0)){
								var informationAlert = $uibModal
										.open({
											animation : true,
											templateUrl : "app/views/modals/informationModal.html",
											controller : function($scope) {
												$scope.title = "Validación productos";
												$scope.message ="Se debe ingresar una cantidad/valor para cada producto seleccionado de venta";
												$scope.acept = function() {
													informationAlert.close();
												}
											}
										});
								return;
							}
						}
						for (var i = 0; i < $scope.productosRegalo.length; i++) {
							if($scope.productosRegalo[i].seleccionado == true && ($scope.productosRegalo[i].cantidad == null || 
									$scope.productosRegalo[i].cantidad==0)){
								var informationAlert = $uibModal
										.open({
											animation : true,
											templateUrl : "app/views/modals/informationModal.html",
											controller : function($scope) {
												$scope.title = "Validación productos";
												$scope.message ="Se debe ingresar una cantidad para cada producto seleccionado de regalo";
												$scope.acept = function() {
													informationAlert.close();
												}
											}
										});
								return;
							}
						}
						$scope.factura.idCliente=$scope.clienteSeleccionado.numId;
						$scope.factura.productos=$scope.productosFactura;
						$scope.factura.productosRegalo=$scope.productosRegalo;
						$scope.factura.valorFactura=$scope.valorTotalFactura;
						$scope.factura.descuento=$scope.descuento;
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
					
					$scope.completeCliente=function(pro){
						
						var output=[];
						angular.forEach($scope.clientes,function(p){
							if(p.nombreCliente.toLowerCase().indexOf(pro.toLowerCase())>=0){
								output.push(p);
							}
						});
						$scope.clientesFiltro=output;
					}
					
					$scope.fillTextboxCli=function(string){
						$scope.clienteSeleccionado=string;
						//consulto los precios del cliente
							$scope.clientePrecios=[];
					    consultarPrecios($scope.clienteSeleccionado);
						$scope.clientesFiltro=null;
						$scope.consultarFacturasPorCliente();
					}
					
					$scope.complete=function(pro){
						
						var output=[];
						angular.forEach($scope.productos,function(p){
							if(p.nombreProducto.toLowerCase().indexOf(pro.toLowerCase())>=0){
								output.push(p);
							}
						});
						$scope.productosFiltro=output;
					}
					$scope.fillTextbox=function(string){
						$scope.productoVenta=string;
						$scope.productosFiltro=null;
						//aca busco el precio
						var valor=0;
						angular.forEach($scope.clientePrecios,function(p){
							if(p.nombreProducto.toLowerCase().indexOf($scope.productoVenta.nombreProducto.toLowerCase())>=0){
								valor=p.valor;
								$scope.productoVenta.valor=valor;
								return;
							}
						});
						$scope.productoVenta.valor=valor;
					}
					
					$scope.completeRegalo=function(pro){
						
						var output=[];
						angular.forEach($scope.productosRegaloConsulta,function(p){
							if(p.nombreProducto.toLowerCase().indexOf(pro.toLowerCase())>=0){
								output.push(p);
							}
						});
						$scope.productosRegaloFiltro=output;
					}
					$scope.fillTextboxRegalo=function(string){
						$scope.productoRegalo=string;
						$scope.productosRegaloFiltro=null;
					}

					$scope.abrirAgregarUsuario = function() {
						$scope.clienteSeleccionado=null;
						$scope.valorTotalFactura=0;
						$scope.descuento=0;
						$scope.mostrarTabla = false;
						$scope.factura = new Usuario();
						consultarProductos();
						$scope.productosFactura=[];
						$scope.productoRegalo=null;
						$scope.productoVenta=null;
						$scope.productosRegalo=[];
					}
					
					// INICIO CONFIG PAGINADOR
					$scope.configPages = function() {
						$scope.pages.length = 0;
						$rootScope.configurarPaginador($scope.facturasTabla,
								$scope.pages, $scope.currentPage);

					}
					$scope.setPage = function(index) {
						$scope.currentPage = index - 1;
					};
					//FIN INICIO CONFIG PAGINADOR 

				});
