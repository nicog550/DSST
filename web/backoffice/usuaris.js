
$(document).ready(function() {
    prepararEdicio();
    prepararEnviamentEdicio();
    prepararEliminacio();
    /**
     * Penja el listener als botons d'edició d'usuaris i mostra el popup d'edició
     * @returns {void}
     */
    function prepararEdicio() {
        $(".editBtn").on('click', function() {
            var id = $(this).attr('id').replace('edit-', '');
            $("#idEdit").val(id);
            var camps = ["nom", "mail", "nac", "dni", "tip"];
            for (var i = 0; i < camps.length; i++) {
                $("#" + camps[i] + "Edit").val($("#" + camps[i] + "-" + id).text());
            }
            $("#editUsr").reveal();
        });
    }
    
    /**
     * Prepara el botó d'enviar de l'edició d'usuari
     * @returns {void}
     */
    function prepararEnviamentEdicio() {
        $("#editUsrSubmit").on('click', function() {
            Funcions.llevarErrors();
            var errors = false;
            if ($("#nomEdit").val() === '') {
                Funcions.mostrarError($("#nomEdit"), 'El nom és obligatori'); errors = true;
            }
            if ($("#mailEdit").val() === '') {
                Funcions.mostrarError($("#mailEdit"), 'L\'email és obligatori'); errors = true;
            }
            if ($("#nacEdit").val() === '0') {
                Funcions.mostrarError($("#nacEdit"), 'La nacionalitat és obligatòria'); errors = true;
            }
            if ($("#dniEdit").val() === '') {
                Funcions.mostrarError($("#dniEdit"), 'El DNI és obligatori'); errors = true;
            }
            if ($("#tipEdit").val() === '0') {
                Funcions.mostrarError($("#tipEdit"), 'El tipus és obligatori'); errors = true;
            }
            if (!errors) {
                var url = '../updates?nom=' + $("#nomEdit").val() + '&email=' + $("#mailEdit").val() +
                            '&nac=' + $("#nacEdit").val() + '&dni=' + $("#dniEdit").val() + '&tipus=' +
                            $("#tipEdit").val() + '&taula=usuari' + '&id=' + $("#idEdit").val();
                $("#editUsr").trigger('reveal:close');
                Funcions.peticioAjax(url, updateOk, Funcions.revealError);
            }
        });
        
        function updateOk(resposta) {
            resposta = resposta.getElementsByTagName("files")[0];
            //Obtenim el valor de la resposta i el convertim a String
            var updated = new XMLSerializer().serializeToString(resposta.childNodes[0]);
            if (updated == '0') { //Si ha fallat l'actualització
                Funcions.revealError();
            } else {
                var id = $("#idEdit").val();
                var camps = ["nom", "mail", "nac", "dni", "tip"];
                $("#fila-" + id).slideToggle(400, function() {
                    for (var i = 0; i < camps.length; i++) {
                        $("#" + camps[i] + "-" + id).html('<span>' + $("#" + camps[i] + "Edit").val() + '</span>');
                    }
                    $("#nac-" + id).prev().text($("#nacEdit").val());
                    $("#tip-" + id).prev().text($("#tipEdit").val());
                    $(this).slideToggle(800);
                });
            }
        }
    }
    
    /**
     * Penja el listener als botons d'eliminació i elimina l'usuari triat
     * @returns {void}
     */
    function prepararEliminacio() {
        var id;
        $(".deleteBtn").on('click', function() {
           id = $(this).attr('id').replace('delete-', ''); //Prenem l'id de l'usuari
           var url = '../baixes?id=' + id + '&taula=usuari';
           Funcions.peticioAjax(url, respostaEliminacio, Funcions.revealError);
        });
    
        function respostaEliminacio(resposta) {
            resposta = resposta.getElementsByTagName("files")[0];
            //Obtenim el valor de la resposta i el convertim a String
            var disponibilitat = new XMLSerializer().serializeToString(resposta.childNodes[0]);
            if (disponibilitat == '0') {
                Funcions.revealError();
            } else {
                $("#fila-" + id).fadeOut(800, function() {
                    $(this).remove();
                });
            }
        }
    }
});

