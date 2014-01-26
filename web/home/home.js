
$(document).ready(function() {
    $('.bxslider').bxSlider({ //Afegim la funcionalitat de galeria a la llista d'imatges
        auto: true //Li indicam que comenci automàticament
    });
    $(".bx-viewport").css('border', '0'); //Llevam a la galeria el marc autogenerat pel bxSlider
    $("#dataIni").datepicker({
        minDate: +1 //La data mínima d'entrada serà la de demà
    });
    $("#dataFi").datepicker({
        minDate: +2 //La data mínima de sortida serà la d'aquí a dos dies
    });
    //Afegim al funcionalitat de datepicker als inputs de data
    $(".date").on('change', function() { //El datepicker ens retorna la data en format mm-dd-aaaa, la convertim a dd-mm-aaaa
        if ($(this).val() !== '') {
            var data = $(this).val();
            var dataArr = data.toString().split('/');
            if (dataArr.length === 3) {
                $(this).val(dataArr[1] + '/' + dataArr[0] + '/' + dataArr[2]);
                prepararDates(data, $(this).attr('id') === 'dataIni');
            }
        }
    });
    
    prepararValidacioFormulari();
    
    /**
     * Seteja la data mínima del datepicker de data final per tal que aquesta sigui major que la data inicial o, al contrari,
     * seteja la data màxima del datepicker de data inicial per tal que aquesta sigui menor que la data final
     * @param {String} data Data d'entrada
     * @param {boolean} ini Vertader si el datepicker que ha canviat és el de data inicial
     */
    function prepararDates(data, ini) {
        if (ini) {
            var val = $("#dataFi").val();
            //La data final mínima serà un dia després de la data inicial
            $("#dataFi").datepicker("option", "minDate", new Date(new Date(data).getTime() + (24 * 60 * 60 * 1000)));
            $("#dataFi").val(val);
        } else {
            var val = $("#dataIni").val();
            //La data inicial màxima serà un dia abans de la data final
            $("#dataIni").datepicker("option", "maxDate", new Date(new Date(data).getTime() - (24 * 60 * 60 * 1000)));
            $("#dataIni").val(val);
        }
    }
    
    /**
     * Comprova que tots els camps necessaris estan emplenats i, en cas afirmatiu, envia la petició per AJAX. Si algun
     * dels camps està buit, li posa el focus i no envia la petició.
     */
    function prepararValidacioFormulari() {
        $("#submitBtn").on('click', function() {
            var errors = false;
            if ($("#dataIni").val() === '') {
                errors = true;
                $("#dataIni").focus(); //Li posam el focus perquè es desplegui el datepicker
            } else if ($("#dataFi").val() === '') {
                errors = true;
                $("#dataFi").focus(); //Li posam el focus perquè es desplegui el datepicker
            } else if ($("#places").val() === '0') {
                errors = true;
                $("#places").simulate('mousedown'); //Simulam un clic perquè s'obri el selector
            }
            if (!errors) {
                comprovarDisponibilitat();
            }
        });
    }

    function comprovarDisponibilitat() {
        var
            dataIni = $("#dataIni").val(),
            dataFi = $("#dataFi").val(),
            places = $("#places").val();
        var url = "../ComprovarDisponibilitat?dataIni=" + dataIni + "&dataFi=" + dataFi + "&places=" + places;
        Funcions.peticioAjax(url, missatgeRespostaAjax, mostrarError);
    }

    function missatgeRespostaAjax(responseXML) {
        // no matches returned
        if (responseXML == null) {
            mostrarError();
        } else {
            var resposta = responseXML.getElementsByTagName("disponibilitat")[0];
            //Obtenim el valor de la resposta i el convertim a String
            var disponibilitat = new XMLSerializer().serializeToString(resposta.childNodes[0]);
            if (disponibilitat == 'true') prepararRedireccio();
            else mostrarNoDisponibilitat();
        }
    }

    function mostrarError() {
        $("#ajaxErrModal").reveal();
    }

    function mostrarNoDisponibilitat() {
        $("#noDispModal").reveal();
    }
    
    function prepararRedireccio() {
        $("#siDispModal").reveal();
        var secs;
        var interval = setInterval(function() {
            secs = parseInt($("#timeout").text());
            secs--;
            if (secs === 1) {
                $("#segons").text('segon');
            }
            if (secs === 0) {
                $("#timeout").parent().html("Redirigint...");
                clearInterval(interval);
                redirigir();
            } else $("#timeout").text(secs);
        }, 1000);
    }
    
    /**
     * Per evitar enviar els paràmetres per url (per tal d'ocultar-los) i per no repetir la consulta, enviarem els
     * criteris de cerca mitjançant un formulari i per post
     */
    function redirigir() {
        var html = '<form id="homeForm" action="../reservar/reservar.jsp" method="post" class="nds">\
                        <fieldset>\
                            <input type="hidden" name="inici" value="' + $("#dataIni").val() + '" />\
                            <input type="hidden" name="fi" value="' + $("#dataFi").val() + '" />\
                            <input type="hidden" name="num" value="' + $("#places").val() + '" />\
                        <fieldset>\
                    </form>';
        $("body").append(html);
        $("#homeForm").trigger('submit');
    }
});


