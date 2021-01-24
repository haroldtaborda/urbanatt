app.controller(
				"preciosClienteCrtl",
				function($state, $scope, MESSAGES, CONFIG,
						$uibModal, $filter, facturasSvc, usuariosSvc, productosSvc, $rootScope) {

					// variables paginador
					$scope.currentPage = 0;
					$scope.pages = [];
					$scope.descuento=0;
					$scope.vendedores=[ 'Alirio Urbano Martinez', 'Vendedor uno', 'Vendedor dos', 'Vendedor tres', 'Local' ];
					$scope.estados = [ 'Activo', 'Inactivo' ];
					$scope.mostrarEditar = false;
					$scope.dias=0;
					$scope.numeroRecibo=null;
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
				    
				    		
					$scope.buscarUsuarios = function() {
						var us = new Usuario();
						us.numeroId = $scope.numeroId == null || $scope.numeroId == '' ? "TODOS"
								: $scope.numeroId;
						
						facturasSvc.consultarPreciosTabla(us.numeroId).then(function(res) {
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
						.eliminarPrecio(usuario)
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
						$scope.productoVenta=null;
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
						$scope.productosFactura=usuario.productos;
						$scope.clienteSeleccionado={};
						$scope.clienteSeleccionado.numId=usuario.idCliente;
						$scope.clienteSeleccionado.nombreCliente=usuario.nombreCliente;
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
						$scope.mensaje = "Debe ingresar los campos obligatorios:";
						if ($scope.clienteSeleccionado == null
								|| $scope.clienteSeleccionado.idCliente == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " Cliente"
							cont++;
						}
						if ($scope.productosFactura == null
								|| $scope.productosFactura.length == 0) {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " Productos"
							cont++;
						}
						if ($scope.factura.estado == null
								|| $scope.factura.estado == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " estado"
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
							if($scope.productosFactura[i].seleccionado == true && ($scope.productosFactura[i].valor==null || $scope.productosFactura[i].valor==0)){
								var informationAlert = $uibModal
										.open({
											animation : true,
											templateUrl : "app/views/modals/informationModal.html",
											controller : function($scope) {
												$scope.title = "Validación productos";
												$scope.message ="Se debe ingresar un valor para cada producto seleccionado de venta";
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
						facturasSvc
								.crearPrecios($scope.factura)
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
						$scope.clientesFiltro=null;
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
