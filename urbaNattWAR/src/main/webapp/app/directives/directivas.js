app.directive('sgrlNumerico',
		function() {
			return {
				restrict : 'A',
				require : 'ngModel',
				link : function(scope, element, attrs, ctrl) {
					var validateNumber = function(inputValue) {

						if (inputValue === undefined) {
							return '';
						}
						var transformedInput = inputValue
								.replace(/[^0-9]/g, '');
						if (transformedInput !== inputValue) {
							ctrl.$setViewValue(transformedInput);
							ctrl.$render();
						}
						var isNotEmpty = (transformedInput.length === 0) ? true
								: false;
						ctrl.$setValidity('notEmpty', isNotEmpty);
						return transformedInput;
					};

					ctrl.$parsers.unshift(validateNumber);
					ctrl.$parsers.push(validateNumber);
					attrs.$observe('notEmpty', function() {
						validateNumber(ctrl.$viewValue);
					});
				}
			};
		});



	
app.directive('sgrlTexto',
		function() {
	
	
	
			return {
				restrict : 'A',
				require : 'ngModel',
				link : function(scope, element, attrs, ctrl) {
					var validateTexto = function(inputValue) {

						if (inputValue === undefined) {
							return '';
						}
						var transformedInput = inputValue.replace(
								/[^A-Za-z ]/g, '');
						if (transformedInput !== inputValue) {
							ctrl.$setViewValue(transformedInput);
							ctrl.$render();
						}
						var isNotEmpty = (transformedInput.length === 0) ? true
								: false;
						ctrl.$setValidity('notEmpty', isNotEmpty);
						return transformedInput;
					};

					ctrl.$parsers.unshift(validateTexto);
					ctrl.$parsers.push(validateTexto);
					attrs.$observe('notEmpty', function() {
						validateNumber(ctrl.$viewValue);
					});
				}
			};
			
			
			
		});

app
.factory(
		'procesarInformacion',
		[
				'ModalConfirm',
				function(ModalConfirm) {
					return function(excepcion) {
							var initModalConfirm = {
								message :excepcion.message,
								accionAceptar : excepcion.accion};
							ModalConfirm.openSelector(initModalConfirm);
					}
				} ]);


app.directive('sgrlCurrency', ['$filter', '$locale', function ($filter, $locale) {
    return {
        require: 'ngModel',
        scope: {
            min: '=?min',
            max: '=?max',
            currencySymbol: '@',
            ngRequired: '=?ngRequired',
            fraction: '=?fraction'
        },
        link: function (scope, element, attrs, ngModel) {

            if (attrs.ngCurrency === 'false') return;

            scope.fraction = (typeof scope.fraction !== 'undefined')?scope.fraction:2;

            function decimalRex(dChar) {
                return RegExp("\\d|\\-|\\" + dChar, 'g');
            }

            function clearRex(dChar) {
                return RegExp("\\-{0,1}((\\" + dChar + ")|([0-9]{1,}\\" + dChar + "?))&?[0-9]{0," + scope.fraction + "}", 'g');
            }

            function clearValue(value) {
                value = String(value);
                var dSeparator = $locale.NUMBER_FORMATS.DECIMAL_SEP;
                var cleared = null;

                if(value.indexOf($locale.NUMBER_FORMATS.DECIMAL_SEP) == -1 && 
                   value.indexOf('.') != -1 &&
                   scope.fraction)
                {
                    dSeparator = '.';
                }

                // Replace negative pattern to minus sign (-)
                var neg_dummy = $filter('currency')("-1", getCurrencySymbol(), scope.fraction);
                var neg_regexp = RegExp("[0-9."+$locale.NUMBER_FORMATS.DECIMAL_SEP+$locale.NUMBER_FORMATS.GROUP_SEP+"]+");
                var neg_dummy_txt = neg_dummy.replace(neg_regexp.exec(neg_dummy), "");
                var value_dummy_txt = value.replace(neg_regexp.exec(value), "");

                // If is negative
                if(neg_dummy_txt == value_dummy_txt) {
                    value = '-' + neg_regexp.exec(value);
                }

                if(RegExp("^-[\\s]*$", 'g').test(value)) {
                    value = "-0";
                }

                if(decimalRex(dSeparator).test(value))
                {
                    cleared = value.match(decimalRex(dSeparator))
                        .join("").match(clearRex(dSeparator));
                    cleared = cleared ? cleared[0].replace(dSeparator, ".") : null;
                }

                return cleared;
            }

            function getCurrencySymbol() {
            //    if (angular.isDefined(scope.currencySymbol)) {
            //        return scope.currencySymbol;
            //    } else {
                    return '$';
             //   }
            }

            function reformatViewValue(){
                var formatters = ngModel.$formatters,
                    idx = formatters.length;

                var viewValue = ngModel.$$rawModelValue;
                while (idx--) {
                  viewValue = formatters[idx](viewValue);
                }

                ngModel.$setViewValue(viewValue);
                ngModel.$render();
            }

            ngModel.$parsers.push(function (viewValue) {
                var cVal = clearValue(viewValue);
                //return parseFloat(cVal);
                // Check for fast digitation (-. or .)
                if(cVal == "." || cVal == "-.")
                {
                    cVal = ".0";
                }
                return parseFloat(cVal);
            });

            element.on("blur", function () {
                ngModel.$commitViewValue();
                reformatViewValue();
            });

            ngModel.$formatters.unshift(function (value) {
                return $filter('currency')(value, getCurrencySymbol(), scope.fraction);
            });

            ngModel.$validators.min = function(cVal) {
                if (!scope.ngRequired && isNaN(cVal)) {
                    return true;
                }
                if(typeof scope.min  !== 'undefined') {
                    return cVal >= parseFloat(scope.min);
                }
                return true;
            };
            
            scope.$watch('min', function (val) {
                ngModel.$validate();
            });

            ngModel.$validators.max = function(cVal) {
                if (!scope.ngRequired && isNaN(cVal)) {
                    return true;
                }
                if(typeof scope.max  !== 'undefined') {
                    return cVal <= parseFloat(scope.max);
                }
                return true;
            };

            scope.$watch('max', function (val) {
                ngModel.$validate();
            });


            ngModel.$validators.fraction = function(cVal) {
                if (!!cVal && isNaN(cVal)) {
                    return false;
                }

                return true;
            };

            scope.$on('currencyRedraw', function() { 
                ngModel.$commitViewValue();
                reformatViewValue(); 
            });

            element.on('focus',function(){
                var viewValue = ngModel.$$rawModelValue;

                if(isNaN(viewValue) || viewValue === '' || viewValue == null)
                {
                    viewValue = '';
                }
                else
                {
                    viewValue = parseFloat(viewValue).toFixed(scope.fraction);
                }
                ngModel.$setViewValue(viewValue);
                ngModel.$render();
            });
        }
    }
}]);

app.directive('dateLowerThan', ["$filter", function ($filter) {

	
	var isValidDate = function (dateStr) {
	    if (dateStr == undefined)
	        return false;
	    var dateTime = Date.parse(dateStr);

	    if (isNaN(dateTime)) {
	        return false;
	    }
	    return true;
	};
	
	var getDateDifference = function (fromDate, toDate) {
	    return Date.parse(toDate) - Date.parse(fromDate);
	};

	var isValidDateRange = function (fromDate, toDate) {
	    if (fromDate == "" || toDate == "")
	        return true;
	    if (isValidDate(fromDate) == false) {
	        return false;
	    }
	    if (isValidDate(toDate) == true) {
	        var days = getDateDifference(fromDate, toDate);
	        if (days < 0) {
	            return false;
	        }
	    }
	    return true;
	};
	
	
	
	
    return {
    	
    	
    	
    	
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {           
            var validateDateRange = function (inputValue) {
                var fromDate = $filter('date')(inputValue, 'yyyy-MM-dd HH:mm:ss Z');
                var toDate = $filter('date')(attrs.dateLowerThan, 'short');
                var isValid = isValidDateRange(fromDate, toDate);
                ctrl.$setValidity('dateLowerThan', isValid);
                return inputValue;
            };

            ctrl.$parsers.unshift(validateDateRange);
            ctrl.$formatters.push(validateDateRange);
            attrs.$observe('dateLowerThan', function () {
                validateDateRange(ctrl.$viewValue);
            });
        }
    };
}]);


app.directive('dateGreaterThan', ["$filter", function ($filter) {
	
	

	
	var isValidDate = function (dateStr) {
	    if (dateStr == undefined)
	        return false;
	    var dateTime = Date.parse(dateStr);

	    if (isNaN(dateTime)) {
	        return false;
	    }
	    return true;
	};
	
	var getDateDifference = function (fromDate, toDate) {
	    return Date.parse(toDate) - Date.parse(fromDate);
	};

	var isValidDateRange = function (fromDate, toDate) {
	    if (fromDate == "" || toDate == "")
	        return true;
	    if (isValidDate(fromDate) == false) {
	        return false;
	    }
	    if (isValidDate(toDate) == true) {
	        var days = getDateDifference(fromDate, toDate);
	        if (days < 0) {
	            return false;
	        }
	    }
	    return true;
	};
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {            
            var validateDateRange = function (inputValue) {
                var fromDate = $filter('date')(attrs.dateGreaterThan, 'short');
                var toDate = $filter('date')(inputValue, 'yyyy-MM-dd HH:mm:ss Z');
                var isValid = isValidDateRange(fromDate, toDate);
                ctrl.$setValidity('dateGreaterThan', isValid);
                return inputValue;
            };

            ctrl.$parsers.unshift(validateDateRange);
            ctrl.$formatters.push(validateDateRange);
            attrs.$observe('dateGreaterThan', function () {
                validateDateRange(ctrl.$viewValue);

            });
        }
    };
}]);


app.factory("EnumFactory",function(){
	var enumFactory = {};
	/* Objeto que almacena la lista correspondiente a los valores del enum. 
	 * nombreEnum:[{value:'..valor del enum...',codigoMensaje:'..código mensaje internacionalizado..'},....]*/
	var enums = {
			IndiceTipoArchivoReporte:[{value:"PDF",extension:"pdf"},
			                          {value:"XLS",extension:"xls"},
			                          {value:"XLSX",extension:"xlsx"},
			                          {value:"TXT",extension:"txt"}]  
	                             
	};
	/* Para obtener la lista de valores del enum.
	 * param enumName: Nombre del enum, igual a como se registro en la variable enums
	 */
	enumFactory.getEnumList = function(enumName){
		return enums[enumName];
	};
	/* Para obtener la clave correspondiente al valor del enum.
	 * param enumName: Nombre del enum, igual a como se registro en la variable enums
	 * param value: valor del enum.
	 */
	enumFactory.getExtension = function(enumName,value){
		var list = enums[enumName];
		var tam = enums[enumName].length;
		for(var i=0; i< tam;i++){
			var valor = list[i].value;
			if(valor === value){
				return list[i].extension;
			}
		}
	};
	return enumFactory;
});



/**Directiva para enviar el reporte al navegador*/
app
.factory(
		'enviarNavegador',
		[
				'ModalInfo',
				function(ModalInfo) {
					return function(data) {
						
						var byteArray16 = new Uint16Array(data.objectExport);
					//	
					    var byteArray = new Uint8Array(byteArray16);
					    var file = new Blob([byteArray], {type: 'application/'+data.tipoReporte});
					    var fileURL = URL.createObjectURL(file);
					    var a = document.createElement("a");
					    document.body.appendChild(a);
					    a.style = "display: none";
					    a.href = fileURL;
					       a.download = data.nombreReporte+"."+data.tipoReporte;
					       a.click();
					    document.body.removeChild(a)
						
					}
				} ]);


