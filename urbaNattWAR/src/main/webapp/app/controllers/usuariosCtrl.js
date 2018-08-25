app.controller(
				"usuariosCtrl",
				function($state, $scope, $rootScope, MESSAGES, CONFIG,
						$uibModal, $filter, usuariosSvc) {

					
					$scope.mostrarEditar = false;
					$scope.estados = [ 'Activo', 'Inactivo' ];
					$scope.roles = [ 'Administrador', 'Vendedor', 'Sin rol' ];
					function Usuario() {
					}
					;

					$scope.buscarUsuarios = function() {
						var us = new Usuario();
						us.nombreUsuario = $scope.nombreUsuario == null
								|| $scope.nombreUsuario == '' ? "TODOS"
								: $scope.nombreUsuario;
						us.rol = $scope.rol == null || $scope.rol == '' ? "TODOS"
								: $scope.rol;
						usuariosSvc.consultarUsuarios(us).then(function(res) {
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

					$scope.editarUsuario = function(usuario) {
						$scope.mostrarTabla = false;
						$scope.usuario = usuario;
					}
					
					$scope.eliminarUsuario = function(usuario) {
						usuariosSvc
						.eliminarUsuario(usuario)
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
						var cont = 0;
						$scope.mensaje = "Debe ingresar los campos obligatorios:"
						if ($scope.usuario.nombreUsuario == null
								|| $scope.usuario.nombreUsuario == '') {
							$scope.mensaje = $scope.mensaje + " Usuario"
							cont++;
						}
						if ($scope.usuario.contrasenia == null
								|| $scope.usuario.contrasenia == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " Contraseña"
							cont++;
						}
						if ($scope.usuario.estado == null
								|| $scope.usuario.estado == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " Estado"
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
						usuariosSvc
								.agregarUsuario($scope.usuario)
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
