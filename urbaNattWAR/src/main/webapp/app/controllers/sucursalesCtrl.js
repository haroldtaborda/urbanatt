app.controller(
				"sucursalesCtrl",
				function($state, $scope, $rootScope, MESSAGES, CONFIG,
						$uibModal, $filter, usuariosSvc,$rootScope,generalesSvc) {

					
					$scope.mostrarEditar = false;
					$scope.estados = [ 'Bodega', 'Sucursal principal', 'Punto de venta', 'Otro' ];
					function Usuario() {
					}
					;
					
					$scope.departamentos=[];
					$scope.ciudades=[];
					
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

					$scope.buscarUsuarios = function() {
						
						usuariosSvc.consultarSucursales($rootScope.clienteSucursales.idCliente).then(function(res) {
							if (res.data != null) {
								$scope.usuariosTabla = res.data;
							} else {
								$scope.usuariosTabla = [];
							}
						}, function(entryError) {
							$scope.usuariosTabla = [];

						});
					}

					function inicializar() {
						$scope.mostrarEditar = false;
						$scope.mensaje = "";
						$scope.nombreUsuario = "";
						$scope.usuariosTabla = [];
						$scope.usuario = new Usuario();
						$scope.mostrarTabla = true;
						
						consultarDeptos();
						
						$scope.buscarUsuarios();

					}
					
						$scope.editarUsuario = function(usuario) {
						
						$scope.consultarCiudadPorDeptoEditar(usuario);
					}
					
					$scope.consultarCiudadPorDeptoEditar = function(usuario){
						if(usuario.idDepartamento != null && usuario.idDepartamento!= ''){
							generalesSvc.consultarCiudadesPorDepto(usuario.idDepartamento).then(function(res) {
								if (res.data != null) {
									
									$scope.ciudades = res.data;
									$scope.mostrarTabla = false;
									$scope.usuario = usuario;
									//$scope.cliente.idCliente=parseInt($scope.cliente.idCliente);
								} else {
									
									$scope.ciudades = [];
									$scope.mostrarTabla = false;
									$scope.usuario = usuario;
								}
							}, function(entryError) {
								
								$scope.ciudades = [];
								$scope.mostrarTabla = false;
								$scope.usuario = usuario;

							});
						}
						else{
							$scope.ciudades=[];
						}
					}
					
					
					$scope.consultarCiudadPorDepto = function(){
						if($scope.usuario.idDepartamento != null && $scope.usuario.idDepartamento!= ''){
							generalesSvc.consultarCiudadesPorDepto($scope.usuario.idDepartamento).then(function(res) {
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
						$scope.usuario = new Usuario();
					}
					
					$scope.cancelarA = function() {
						$state.go('app.clientes');
					}

				
					$scope.eliminarUsuario = function(usuario) {
						usuariosSvc
						.eliminarSucursal(usuario)
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

					$scope.estadoUsuario = function(estado) {
						if (estado == null || estado == 'Activo') {
							return true;
						} else {
							return false;
						}
					}

					function validarObligatorios() {
						return false;
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
						$scope.usuario.idCliente=$rootScope.clienteSucursales.idCliente;
						usuariosSvc
								.agregarSucursal($scope.usuario)
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
						$scope.usuario = new Usuario();
					}

				});
