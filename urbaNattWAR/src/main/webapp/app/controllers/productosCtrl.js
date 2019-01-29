app.controller(
				"productosCtrl",
				function($state, $scope, $rootScope, MESSAGES, CONFIG,
						$uibModal, $filter, productosSvc) {

					// variables paginador
					$scope.currentPage = 0;
					$scope.pages = [];
					
					$scope.mostrarEditar = false;
					$scope.tipos = [ 'Proteina', 'Vitamina', 'Otro' ];
					$scope.unidades = [ 'Kg','Lb','G', 'Mg','L', 'Ml','Otro' ];
					function Usuario() {
					}
					;

					$scope.buscarUsuarios = function() {
						var us = new Usuario();
						us.nombreProducto = $scope.nombreProducto == null
								|| $scope.nombreProducto == '' ? "TODOS"
								: $scope.nombreProducto;
						us.tipo = $scope.tipo == null || $scope.tipo == '' ? "TODOS"
								: $scope.tipo;
						productosSvc.consultarProductos(us).then(function(res) {
							if (res.data != null) {
								$scope.productosTabla = res.data;
								$scope.currentPage = 0;
								$scope.configPages();								
							} else {
								$scope.productosTabla = [];
							}
						}, function(entryError) {
							$scope.productosTabla = [];

						});
					}

					function inicializar() {
						$scope.mostrarEditar = false;

						$scope.mensaje = "";
						$scope.nombreProducto = "";
						$scope.productosTabla = [];
						$scope.producto = new Usuario();
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
						$scope.producto = new Usuario();
					}

					$scope.editarUsuario = function(producto) {
						$scope.mostrarTabla = false;
						$scope.producto = producto;
					}
					
					$scope.eliminarUsuario = function(usuario) {
						productosSvc
						.eliminarProducto(usuario)
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
						if ($scope.producto.nombreProducto == null
								|| $scope.producto.nombreProducto == '') {
							$scope.mensaje = $scope.mensaje + " nombreProducto"
							cont++;
						}
						if ($scope.producto.cantidad == null
								|| $scope.producto.cantidad == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " cantidad"
							cont++;
						}
						if ($scope.producto.tipo == null
								|| $scope.producto.tipo == '') {
							if (cont > 0) {
								$scope.mensaje = $scope.mensaje + ","
							}
							$scope.mensaje = $scope.mensaje + " tipo"
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
						productosSvc
								.crearProducto($scope.producto)
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
					
					
					// INICIO CONFIG PAGINADOR
					$scope.configPages = function() {
						$scope.pages.length = 0;
						$rootScope.configurarPaginador($scope.productosTabla,
								$scope.pages, $scope.currentPage);

					}
					$scope.setPage = function(index) {
						$scope.currentPage = index - 1;
					};
					//FIN INICIO CONFIG PAGINADOR 

					$scope.abrirAgregarUsuario = function() {
						$scope.mostrarTabla = false;
						$scope.producto = new Usuario();
					}

				});
