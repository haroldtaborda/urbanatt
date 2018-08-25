var linkhabilitado = true;
/**
 * Las funciones que se invoquen dentro del evento "ready" del documento se 
 * ejecutan automaticamente cuando el DOM de la pagina esta cargado
 */
jQuery(document).ready(function() {
	prepararCalendarios();
});

/**
 * Agrega el texto con el formato y la mascara que dibuja automaticamente
 * las barras divisorias entre las partes de la fecha (/)
 * 
 * @param elemento
 *            Objeto del DOM de la pagina HTML
 */
function prepararCalendario(elemento){
	var idElemento = new String(elemento.id);
	var nombreTag = 'eFF_' + idElemento.replace(/:/g, '');
	var elementoFormatoFecha = document.getElementsByTagName(nombreTag);
	elementoFormatoFecha = (elementoFormatoFecha == null)?null:(elementoFormatoFecha.length == 0)?null:elementoFormatoFecha;
	if(elementoFormatoFecha == null){
		elementoFormatoFecha = document.createElement(idElemento);
		elemento.parentNode.appendChild(elementoFormatoFecha);
		if(!elemento.disabled){
			jQuery(elemento).mask("39/19/9999");
		}
	}
}

function validarFecha(elemento){
	var fechaf=null;
	var valida= false;
	fechaf = new Date(elemento.value).toString();
	var anio  = new Date(elemento.value).getFullYear();
	if(fechaf== 'Invalid Date' || anio < 1990){
		valida=true;
	}
	if(valida==true){
       //fecha incorrecta, se limipia la fecha 
		//elemento= null;
		elemento.value=null;
	}
	
}

/**
 * Funcion que recorre el DOM de la pagina HTML buscando todos los elementos
 * tipo input que corresponden a los calendarios de richfaces para
 * agregarles el texto con el formato y la mascara que dibuja
 * automaticamente las barras divisorias entre las partes de la fecha (/)
 */
function prepararCalendarios(){
	for(var i=0; i < document.forms.length; i++){
		var forma = document.forms[i];
		var elem = forma.elements;
		for(var j = 0; j < elem.length; j++){
			if(elem[j].type == "text"){
				if(elem[j].className.indexOf("rich-calendar-input")!=-1){
					prepararCalendario(elem[j]);
				}
			}
		}
	}
}
/**
 * Función encargada de mostrar u ocultar la sección de alertas del template.
 * 
 * @param idDiv :
 *            div que se quiere mostrar u ocultar.
 * @param idLink :
 *            link que oculta o muestra el div.
 * @returns {Boolean} para evitar que se haga un submit.
 */
function ocultarMostrar(idDiv, idLink) {
	// Si no estÃ¡ oculto
	if (jQuery("div[id$='" + idDiv + "']").is(':visible')) {
		// Se oculta
		jQuery("div[id$='" + idDiv + "']").hide();
		jQuery("a[id$='" + idLink + "']").text("Abrir Alertas");

	} else {
		// Se muestra
		jQuery("div[id$='" + idDiv + "']").show();
		jQuery("a[id$='" + idLink + "']").text("Cerrar Alertas");
	}
	return false;
}

/**
 * Función encargada de mostrar un mensaje de confirmación y redireccinar a la
 * pÃ¡gina que llega como parÃ¡metro en caso que el usuario seleccione
 * <code>Aceptar</code>.
 * 
 * @param mensaje :
 *            pregunta de confirmación.
 * @param url :
 *            URL de la pÃ¡gina que se desea redireccionar.
 * @returns {Boolean} que tedermina si se redirecciona o no.
 */
function confirmar(mensaje, url) {
	if (confirm(mensaje)) {
		if (url != null) {
			document.location.href = url;
		}
	} else {
		return false;
	}
}

/**
 * Funcion encargada de validar que sólo se seleccione un radio button
 * 
 * @param elemento a seleccionar
 */
function dataTableSelectOneRadio(radio) {
    var id = radio.name.substring(radio.name.lastIndexOf(':'));
    var el = radio.form.elements;
    for (var i = 0; i < el.length; i++) {
        if (el[i].type != undefined && el[i].name.substring(el[i].name.lastIndexOf(':')) == id) {
            el[i].checked = false;
        }
    }
    radio.checked = true;
}

/**
 * Función encargada de desactivar un botón.
 * 
 * @param sufijoId :
 *            id del botón que se desea desactivar.
 */
function desactivarBoton(sufijoId) {
	jQuery("[id$='" + sufijoId + "']").attr("disabled", "true");
}

/**
 * Función encargada de activar un botón.
 * 
 * @param sufijoId :
 *            id del botón que se desea activar.
 */
function activarBoton(sufijoId) {
	jQuery("[id$='" + sufijoId + "']").removeAttr('disabled');
}

/**
 * Función encargada de deshabilitar todos los botones de la pantalla.
 */
function desactivarBotones() {
	jQuery(":button").attr("disabled", "disabled");
}

/**
 * Función encargada de habilitar todos los botones de la pantalla.
 */
function activarBotones() {
	jQuery(":button").removeAttr('disabled');
}

/**
 * Función encargada de establecer el foco al elemento con el id suministrado
 * por parÃ¡metro.
 * 
 * @param idElemento :
 *            id del elemento al que se le desea establecer el foco.
 */
function establecerFoco(idElemento) {
	jQuery("[id$='" + idElemento + "']").focus();
	jQuery("[id$='" + idElemento + "']").focus();
}

/**
 * Funcion encargada de controlar la cantidad de caracteres permitidos en un
 * TextArea
 * 
 * @param maximoCaracteres
 * @returns {Boolean}
 */
function cantidadCaracteres(elemento, maximoCaracteres) {
	if (elemento.value.length > maximoCaracteres) {
		return false;
	}
	else{
		return true;
	}
	
}

/**
 * Función encargada de mostrar un elemento.
 * @param subMenu01 : id del elemento que se desea mostrar.
 */
function mostrarElemento(subMenu01){ 
	document.getElementById(subMenu01).style.visibility="visible"; 
} 

/**
 * Función encargada de ocultar un elemento.
 * @param subMenu01 : id del elemento que se desea ocultar.
 */
function ocultarElemento(subMenu01){ 
	document.getElementById(subMenu01).style.visibility="hidden"; 
} 

/**
 * Función encargada de validar que se ingrese sólo números en
 * los campos númericos.
 * 
 * @param inputText input a validar.
 * @param event envento onkeydown
 */
function permitirSoloNumeros(inputText, event) {
	var BACKSP_KEY = 8, TAB_KEY = 9, ENTER_KEY = 13, CTRL_KEY = 17, ALT_KEY = 18, ESC_KEY = 27, INS_KEY = 45, DEL_KEY = 46;
	var keyCode = event.keyCode ? event.keyCode : event.charCode;

	// Permite pegar
	if (event.ctrlKey && (event.keyCode == 86 || event.keyCode == 118)) {
		return true;
	}		
	
	if (isNumber(keyCode) && event.ctrlKey == false && event.shiftKey == false
			&& event.altKey == false) {
		return true;
	}

	// Ini, End, Pg Up, Pg Dn
	if (33 <= keyCode && keyCode <= 36) {
		return true;
	}

	// Arrow keys
	if (37 <= keyCode && keyCode <= 40) {
		return true;
	}

	// Numeric keyboard
	if (96 <= keyCode && keyCode <= 105) {
		return true;
	}

	// Special keys
	if (BACKSP_KEY == keyCode || TAB_KEY == keyCode || ENTER_KEY == keyCode
			|| CTRL_KEY == keyCode || ALT_KEY == keyCode || ESC_KEY == keyCode
			|| INS_KEY == keyCode || DEL_KEY == keyCode) {
		return true;
	}
	
	return false;
}

function validarCaracteresEspeciales(inputText, event){
	var BACKSP_KEY = 8, TAB_KEY = 9, ENTER_KEY = 13, CTRL_KEY = 17, ALT_KEY = 18, ESC_KEY = 27, INS_KEY = 45, DEL_KEY = 46;
	var keyCode = event.keyCode ? event.keyCode : event.charCode;
	
	// Permite pegar
	if (event.ctrlKey && (event.keyCode == 86 || event.keyCode == 118)) {
		return true;
	}
	if (isNumber(keyCode) && event.ctrlKey == false && event.shiftKey == false
			&& event.altKey == false) {
		return true;
	}
	
	// Arrow keys
	if (37 <= keyCode && keyCode <= 40) {
		return true;
	}

	// Numeric keyboard
	if (96 <= keyCode && keyCode <= 105) {
		return true;
	}
	// Special keys
	if (BACKSP_KEY == keyCode || TAB_KEY == keyCode || ENTER_KEY == keyCode
			|| CTRL_KEY == keyCode  || ESC_KEY == keyCode
			|| INS_KEY == keyCode || DEL_KEY == keyCode) {
		return true;
	}
	
	 //Combinación tecla shift + letra
	  if(65<=keyCode &&  keyCode<=90 ){
	   return true;
	  }
	
	// Numeric keyboard
  if (96 <= keyCode && keyCode <= 105) {
      return false;
  }
  
	if (!isNumber(keyCode) && event.ctrlKey == false && event.shiftKey == false
			&& event.altKey == false) {
		return true;
	}
  

	// Ini, End, Pg Up, Pg Dn
	if (33 <= keyCode && keyCode <= 36) {
		return true;
	}

	// Arrow keys
	if (37 <= keyCode && keyCode <= 40) {
		return true;
	}
	
	// Special keys
	if (BACKSP_KEY == keyCode || TAB_KEY == keyCode || ENTER_KEY == keyCode
			|| CTRL_KEY == keyCode  || ESC_KEY == keyCode
			|| INS_KEY == keyCode || DEL_KEY == keyCode) {
		return true;
	}

	return false;
}

/**
 * Función encargada de validar que se ingrese sólo números en
 * los campos númericos.
 * 
 * @param inputText input a validar.
 * @param event envento onkeydown
 */
function permitirSoloEstricatementePositivos(inputText, event) {
	var BACKSP_KEY = 8, TAB_KEY = 9, ENTER_KEY = 13, CTRL_KEY = 17, ALT_KEY = 18, ESC_KEY = 27, INS_KEY = 45, DEL_KEY = 46;
	var keyCode = event.keyCode ? event.keyCode : event.charCode;

	// Permite pegar
	if (event.ctrlKey && (event.keyCode == 86 || event.keyCode == 118)) {
		return true;
	}		
	
	var caretPos = $input.GetCaretPos(inputText);
	
	// Si el primer caracter es 0
	if ((48 == keyCode || 96 == keyCode) && caretPos == 0) {
		return false;
	}
	
	if (isNumber(keyCode) && event.ctrlKey == false && event.shiftKey == false
			&& event.altKey == false) {
		return true;		
	}

	// Ini, End, Pg Up, Pg Dn
	if (33 <= keyCode && keyCode <= 36) {
		return true;
	}

	// Arrow keys
	if (37 <= keyCode && keyCode <= 40) {
		return true;
	}

	// Numeric keyboard
	if (96 <= keyCode && keyCode <= 105) {
		return true;
	}

	// Special keys
	if (BACKSP_KEY == keyCode || TAB_KEY == keyCode || ENTER_KEY == keyCode
			|| CTRL_KEY == keyCode || ALT_KEY == keyCode || ESC_KEY == keyCode
			|| INS_KEY == keyCode || DEL_KEY == keyCode) {
		return true;
	}
	
	return false;
}

/**
 * Función encargada de validar que se ingrese sólo números y racionales en
 * los campos númericos.
 * 
 * @param inputText input a validar.
 * @param event envento onkeydown
 * @param numberDecimal number of digits after decimal 
 */
function permitirSoloNumerosYRacionales(inputText, event, numberDecimal) {
	var BACKSP_KEY = 8, TAB_KEY = 9, ENTER_KEY = 13, CTRL_KEY = 17, ALT_KEY = 18, ESC_KEY = 27, INS_KEY = 45, DEL_KEY = 46, DOT_KEY = 110, DOT_KEY2 = 190;
	var keyCode = event.keyCode ? event.keyCode : event.charCode;
	var res = inputText.value.split(".");

	if (isNumber(keyCode) && event.ctrlKey == false && event.shiftKey == false
			&& event.altKey == false) {
		return res[1]?(res[1].length > numberDecimal -1 ? false : true):true;
	}

	// Ini, End, Pg Up, Pg Dn
	if (33 <= keyCode && keyCode <= 36) {
		return true;
	}

	// Arrow keys
	if (37 <= keyCode && keyCode <= 40) {
		return true;
	}

	// Numeric keyboard
	if (96 <= keyCode && keyCode <= 105) {
		return res[1]?(res[1].length > numberDecimal -1 ? false : true):true;
	}

	// Special keys
	if (BACKSP_KEY == keyCode || TAB_KEY == keyCode || ENTER_KEY == keyCode
			|| CTRL_KEY == keyCode || ALT_KEY == keyCode || ESC_KEY == keyCode
			|| INS_KEY == keyCode || DEL_KEY == keyCode || DOT_KEY == keyCode || DOT_KEY2 == keyCode) {
		return true;
	}
	
	return false;
}


/**
 * Función encargada de validar que se ingrese letras
 * los campos
 * @author Natalia Marín Cardona <nmarin@heinsohn.com.co>
 * 
 * @param inputText input a validar.
 * @param event envento onkeydown
 */
function permitirSoloLetras(inputText, event) {
	var BACKSP_KEY = 8, TAB_KEY = 9, ENTER_KEY = 13, CTRL_KEY = 17, ALT_KEY = 18, ESC_KEY = 27, INS_KEY = 45, DEL_KEY = 46;
	var keyCode = event.keyCode ? event.keyCode : event.charCode;

	  //Combinación tecla shift + letra
	  if(65<=keyCode &&  keyCode<=90 ){
	   return true;
	  }
	
	// Numeric keyboard
    if (96 <= keyCode && keyCode <= 105) {
        return false;
    }
    
	if (!isNumber(keyCode) && event.ctrlKey == false && event.shiftKey == false
			&& event.altKey == false) {
		return true;
	}
    

	// Ini, End, Pg Up, Pg Dn
	if (33 <= keyCode && keyCode <= 36) {
		return true;
	}

	// Arrow keys
	if (37 <= keyCode && keyCode <= 40) {
		return true;
	}
	
	// Special keys
	if (BACKSP_KEY == keyCode || TAB_KEY == keyCode || ENTER_KEY == keyCode
			|| CTRL_KEY == keyCode || ALT_KEY == keyCode || ESC_KEY == keyCode
			|| INS_KEY == keyCode || DEL_KEY == keyCode) {
		return true;
	}

	return false;
}

/**
 * Función que verifica el código de la tecla oprimida
 * pertenece a un número.
 * 
 * @param keyCode : código de la tecla oprimida.
 */
function isNumber(keyCode) {
	if ('0' <= String.fromCharCode(keyCode)
			&& String.fromCharCode(keyCode) <= '9') {
		return true;
	}
	return false;
}

/**
 * Funcion encargada de definir el foco inicial a un formulario al momento de cargarse.
 */
function focosGeneral()
{
	jQuery("[id*='elementoFoco']").focus();
}

/**
 * Método que permite asignar la acción de un botón
 * al presionar enter.
 * 
 * @param event: Evento del boton.
 * @param foco: elemento al cual se le quiere asignar
 * la tecla enter.
 */
function submitEnter(event, foco) {
    if (!event)
        event = window.event;
    if (event.keyCode == 13) {
        if (document.getElementById(foco) != null) {
            establecerFoco(foco);
            document.getElementById(foco).click();
        }
        return false;
    } else {
        return true;
    }
}

function keyPressNoLimpiarFormulario(event)
{
	if (!event) event = window.event;
	if (event.keyCode == 13) {
		return false;
	}
	
	return true;
}

/**
 * Método que permite asignar la acción de un botón
 * al presionar enter.
 * 
 * @param event : evento cuando se oprime una tecla.
 * @param idElemento : id del elemento que se desea ejecutar
 * con el enter.
 */
function asignarEnter(event, idElemento){
	if (event.keyCode == 13){
		jQuery("[id$='" + idElemento + "']").click();
	}
}

/**
 * Function encargada de seleccionar/deseleccionar los checkbox de los registros
 * de una tabla.
 * 
 * @param checkbox
 *            a seleccionar/deseleciconar
 */
function seleccionarDeseleccionar(checkbox, form) {
    var length = document.getElementById(form).elements.length;
    for(var i = 0; i < length; i++) {
           var type=document.getElementById(form).elements[i].type;
           if(type == "checkbox"){
        	   document.getElementById(form).elements[i].checked = checkbox.checked;
           }
    }
}

/**
 * Crea una variable llamada capturedFieldId con el id del elemento 
 * que tiene el foco en el momento de llamar esta funcion
 * con el fin de restablecer luego el foco llamando a la funcion
 * restoreFocus
 * 
 * ctoro@heinsohn.com.co
 * 
 * @returns
 */
function captureFocus() {
    if (document.activeElement != null) {
        capturedFieldId = document.activeElement.id;
    } else {
        capturedFieldId = null;
    }
}
 
/**
 * Restaura el foco en el elemento con el id que se encuentra
 * en la variable caputedDieldId 
 * 
 * ctoro@heinsohn.com.co
 * 
 * @returns
 */
function restoreFocus() {
    if (capturedFieldId && capturedFieldId != null) {
        var focusedElement = document.getElementById(capturedFieldId);
        if (focusedElement) {
            focusedElement.focus();
        }
    }
}

/**
 * Permite valores numericos positivos o negativos con dos decimales
 * 
 * @param inputText
 * @param event
 * @returns {Boolean}
 */
function permitirSoloNumerosNegativos(inputText, event) {

    var inputMinus = inputText.value.indexOf("-") != -1;
    var pointPos = inputText.value.indexOf(".");
    var inputPoint = inputText.value.indexOf(".") != -1;

	var BACKSP_KEY = 8, TAB_KEY = 9, ENTER_KEY = 13, CTRL_KEY = 17, ALT_KEY = 18, ESC_KEY = 27, INS_KEY = 45, DEL_KEY = 46;
	var keyCode = event.keyCode ? event.keyCode : event.charCode;

    var caretPos = $input.GetCaretPos(inputText);        
    
	if (isNumber(keyCode) && event.ctrlKey == false && event.shiftKey == false
			&& event.altKey == false) {
            
        if(inputPoint) {
            if(caretPos > (pointPos + 2)) {
                return false;
            }
        }            
		return true;
	}

	// Ini, End, Pg Up, Pg Dn
	if (33 <= keyCode && keyCode <= 36) {
		return true;
	}

    // Minus
	if (189 == keyCode || 109 == keyCode) {
        if(caretPos == 0) {
            inputMinus = true;
            return true;
        } else {
            return false;
        }
	}
    
    // Point
	if (190 == keyCode || 110 == keyCode) {
        if(!inputPoint && ((caretPos > 0 && !inputMinus) || (caretPos > 1 && inputMinus))) {
            inputPoint = true;
            return true;
        } else {
            return false;
        }
	}    
    
	// Arrow keys
	if (37 <= keyCode && keyCode <= 40) {
		return true;
	}

	// Numeric keyboard
	if (96 <= keyCode && keyCode <= 105) {
		return true;
	}

	// Special keys
	if (BACKSP_KEY == keyCode || TAB_KEY == keyCode || ENTER_KEY == keyCode
			|| CTRL_KEY == keyCode || ALT_KEY == keyCode || ESC_KEY == keyCode
			|| INS_KEY == keyCode || DEL_KEY == keyCode) {
		return true;
	}
    
	return false;
}

/**
 * funcion que permite solo precionar la tacla DEL sobre un campo de texto
 * @param inputText componente de texto
 * @param event evento 
 * @returns true si se permite la tecla false en caso contrario
 * @author Daniel Eduardo Trujillo <dtrujillo@heinsohn.com.co>
 */
function permitirSoloBorrarTexto(inputText, event) {
	// varaible que identifica la tecla DEL
	var BACKSP_KEY = 8, TAB_KEY = 9, DEL_KEY = 46;
	// se recupera el valor de la tacla ingresada
	var keyCode = event.keyCode ? event.keyCode : event.charCode;
	// se verifica quel valor de la techa sea DEL
	if(BACKSP_KEY == keyCode || 
			TAB_KEY == keyCode ||
			DEL_KEY == keyCode){
		return true;
	}
	return false;
}
function activarLink(){
	linkhabilitado= true;
}
function inactivarLinkVacio(){
	if(linkhabilitado){
		linkhabilitado=false;
	}
}


function close_window() {

	var win = window.open("", "_self");
	win.close();
}

function formatMoneda(input) {
	var num = input.value.replace(/\./g, '');
	if (!isNaN(num)) {
		num = num.toString().split('').reverse().join('').replace(
				/(?=\d*\.?)(\d{3})/g, '$1.');
		num = num.split('').reverse().join('').replace(/^[\.]/, '');
		input.value = num;
	}
}
