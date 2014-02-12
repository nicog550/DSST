
var hosteActual, places, descomptes, preuPers;
$(document).ready(function() {
    places = parseInt($("#placesVal").val());
    prepararValidacioFormulari();
    ocultarFiles();
    formatDates('dataIni');
    formatDates('dataFi');
    escoltarLogin();
    mostrarPreu();
    prepararDescomptes();
    actualitzarPreu(0);
    
    /**
     * Guarda tots els descomptes segons el seu tipus d'usuari
     * @returns {void}
     */
    function prepararDescomptes() {
        preuPers = parseInt($("#preuPers").val());
        descomptes = new Array();
        $(".hiddenTips").each(function() {
            descomptes[parseInt($(this).attr('id').replace('descTip-', '')) - 1] = parseInt($(this).val());
        });
    }
    
    /**
     * Mostra l'import corresponent a l'usuari i el suma al total
     * @returns {void}
     */
    function mostrarPreu() {
        $(".selectTipus").on('change', function() {
            var id = $(this).attr('id').replace('tip-', '');
            if ($(this).val() === '0') {
                $("#import-" + id).addClass('nds');
                $("#final-" + id).addClass('nds');
            } else {
                actualitzarPreu(id);
            }
        });
    }
    
    function actualitzarPreu(id) {
        var tipus = parseInt($("#tip-" + id).val());
        if (tipus !== 0) {
            var descompte = descomptes[tipus - 1];
            $("#descompte-" + id).text(descompte);
            $("#final-" + id).text(preuPers - (preuPers * descompte / 100));
            $("#final-" + id).removeClass('nds');
            $("#import-" + id).removeClass('nds').hide().slideToggle();
            var total = 0.0;
            $(".importsFinals").each(function() {
                if (!$(this).hasClass('nds')) total += parseFloat($(this).text());
            });
            $("#importTotal").slideToggle(function() {
                $(this).text(total);
                $(this).slideToggle();
            });
            $("#preuFinal").val(total);
        }
    }
    
    /**
     * Pren una data en format dd/mm/aaaa i la converteix a aaaa-mm-dd
     * @param {HTML Element} elem L'element que conté la data
     */
    function formatDates(elem) {
        var dataArr = $("#" + elem).val().split("/");
        if (dataArr.length === 3) $("#" + elem).val(dataArr[2] + '-' + dataArr[1] + '-' + dataArr[0]);
    }
    
    /**
     * Comprova que tots els camps necessaris estan emplenats i, en cas afirmatiu, envia la petició per AJAX. Si algun
     * dels camps està buit, li posa el focus i no envia la petició.
     */
    function prepararValidacioFormulari() {
        $("#reservarForm").on('submit', function() {
            Funcions.llevarErrors();
            var errors = false;
            for (var i = 0; i < places; i++) {
                if ($("#nom-" + i).val() === '') {
                    Funcions.mostrarError($("#nom-" + i), 'El nom és obligatori'); errors = true;
                }
                if ($("#mail-" + i).val() === '') {
                    Funcions.mostrarError($("#mail-" + i), 'L\'email és obligatori'); errors = true;
                }
                if ($("#nac-" + i).val() === '0') {
                    Funcions.mostrarError($("#nac-" + i), 'La nacionalitat és obligatòria'); errors = true;
                }
                if ($("#dni-" + i).val() === '') {
                    Funcions.mostrarError($("#dni-" + i), 'El DNI és obligatori'); errors = true;
                }
                if ($("#tip-" + i).val() === '0') {
                    Funcions.mostrarError($("#tip-" + i), 'El tipus és obligatori'); errors = true;
                }
            }
            return !errors; //Si no hi ha errors, el formulari s'enviarà
        });
    }
    
    /**
     * Oculta les files dels hostes fins que no es pitgi el botó de mostrar següent
     */
    function ocultarFiles() {
        hosteActual = 1;
        //Ocultam els camps de tots els hostes excepte el primer
        for (var i = 1; i < places; i++) {
            $("#hoste-" + i).hide();
        }
        //Afegim la funcionalitat al botó de mostrar hostes
        $("#seguent").on('click', function() {
            $("#hoste-" + hosteActual++).slideToggle();
            //Quan ja es mostrin tots, llevam el botó
            if (hosteActual === places) {
                $("#seguent").fadeOut(500, function() {
                    $(this).remove();
                });
            }
        });
    }
    
    function escoltarLogin() {
        $("#loginChange").on('click', function() {
           $("#nom-0").val($("#nomVal").text());
           $("#mail-0").val($("#mailVal").val());
           $("#nac-0").val($("#nacionalitatVal").val());
           $("#dni-0").val($("#dniVal").val());
           $("#tip-0").val($("#tipusVal").val());
        });
    }
});


