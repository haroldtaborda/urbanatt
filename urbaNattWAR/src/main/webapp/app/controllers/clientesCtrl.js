app.controller(
				"clientesCtrl",
				function($state, $scope, $rootScope, MESSAGES, CONFIG,
						$uibModal, $filter, usuariosSvc, generalesSvc,$rootScope) {

					
					$scope.mostrarEditar = false;
					$scope.estados = [ 'Activo', 'Inactivo' ];
					$scope.tiposId = [ 'Tarjeta de identidad', 'Cédula ciudadania', 'Cédula extrangeria', 'NIT' ];
					function Usuario() {
					}
					;

					$scope.departamentos=[];
					$scope.ciudades=[];
					
					
					
					$scope.consultarCiudadPorDepto = function(){
						if($scope.cliente.idDepartamento != null && $scope.cliente.idDepartamento!= ''){
							generalesSvc.consultarCiudadesPorDepto($scope.cliente.idDepartamento).then(function(res) {
								if (res.data != null) {
									$scope.ciudades = res.data;
								} else {
									$scope.ciudades = [];
								}
							}, function(entryError) {
								$scope.ciudades = [];

							});
						}
						else{
							$scope.ciudades=[];
						}
					}
					$scope.buscarClientes = function() {
						var us = new Usuario();
						us.tipoId = $scope.tipoId == null
								|| $scope.tipoId == '' ? "TODOS"
								: $scope.tipoId;
						us.numId = $scope.numId == null || $scope.numId == '' ? "TODOS"
								: $scope.numId;
						us.nombreCliente = $scope.nombreCliente == null || $scope.nombreCliente == '' ? "TODOS"
								: $scope.nombreCliente;
						usuariosSvc.consultarClientes(us).then(function(res) {
							if (res.data != null) {
								$scope.clientesTabla = res.data;
							} else {
								$scope.clientesTabla = [];
							}
						}, function(entryError) {
							$scope.clientesTabla = [];

						});
					}

					function inicializar() {
						$scope.mostrarEditar = false;

						$scope.mensaje = "";
						$scope.nombreUsuario = "";
						$scope.clientesTabla = [];
						$scope.cliente = new Usuario();
						$scope.mostrarTabla = true;
						consultarDeptos();
					}
					function consultarDeptos(){
						generalesSvc.consultarDeptos().then(function(res) {
							if (res.data != null) {
								$scope.departamentos = res.data;
							} else {
								$scope.departamentos = [];
							}
						}, function(entryError) {
							$scope.departamentos = [];

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

					$scope.cancelar = function() {
						$scope.mostrarTabla = true;
						$scope.cliente = new Usuario();
					}

					$scope.editarUsuario = function(cliente) {
						
						$scope.consultarCiudadPorDeptoEditar(cliente);
					}
					
					$scope.consultarCiudadPorDeptoEditar = function(cliente){
						if(cliente.idDepartamento != null && cliente.idDepartamento!= ''){
							generalesSvc.consultarCiudadesPorDepto(cliente.idDepartamento).then(function(res) {
								if (res.data != null) {
									
									$scope.ciudades = res.data;
									$scope.mostrarTabla = false;
									$scope.cliente = cliente;
									$scope.cliente.idCliente=parseInt($scope.cliente.idCliente);
								} else {
									
									$scope.ciudades = [];
									$scope.mostrarTabla = false;
									$scope.cliente = cliente;
								}
							}, function(entryError) {
								
								$scope.ciudades = [];
								$scope.mostrarTabla = false;
								$scope.cliente = cliente;

							});
						}
						else{
							$scope.ciudades=[];
						}
					}
					
					$scope.agregarSucursal = function(cliente){
						$rootScope.clienteSucursales=cliente;
						$state.go('app.sucursales');
					}

					$scope.eliminarUsuario = function(cliente) {
						usuariosSvc
						.eliminarCliente(cliente)
						.then(
								function(res) {
									if (res.data.responseResult.result) {
										$scope.mostrarTabla = true;
										var dto ={};
										dto.titulo="Extio";
										dto.mensaje="Registro eliminado exitosamente";
										mostrarMensaje(dto);
										$scope.buscarClientes();
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
						if ($scope.cliente.tipoId == null
								|| $scope.cliente.tipoId == '') {
							$scope.mensaje = $scope.mensaje + " Tipo identificación"
							cont++;
						}
						if ($scope.cliente.numId == null
								|| $scope.cliente.numId == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " Número identificación"
							cont++;
						}
						if ($scope.cliente.nombreCliente == null
								|| $scope.cliente.nombreCliente == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " Nombre"
							cont++;
						}
						if ($scope.cliente.direccion == null
								|| $scope.cliente.direccion == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.direccion + ","
							}
							$scope.mensaje = $scope.mensaje + " Dirección"
							cont++;
						}
						if ($scope.cliente.telCelular == null
								|| $scope.cliente.telCelular == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " Telefono celular"
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
						//$scope.cliente.idCiudad=$scope.cliente.idCiudad.id;
						//$scope.cliente.idDepartamento=$scope.cliente.idDepartamento.id;
						usuariosSvc
								.agregarCliente($scope.cliente)
								.then(
										function(res) {
											if (res.data.responseResult.result) {
												$scope.mostrarTabla = true;
				
												var dto ={};
												dto.titulo="Extio";
												dto.mensaje="Resitro agregado con exito";
												mostrarMensaje(dto);
												$scope.buscarClientes();
											} else {
												var dto ={};
												dto.titulo="Extio";
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
						$scope.mostrarTabla = false;
						$scope.cliente = new Usuario();
					}

				});
