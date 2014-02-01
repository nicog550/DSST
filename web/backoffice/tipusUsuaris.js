
$(document).ready(function() {
    prepararAfegir();
    prepararEnviamentInsercio();
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
            $("#descEdit").val($("#desc-" + id).text());
            $("#editTip").reveal();
        });
    }
    
    /**
     * Prepara el botó d'enviar de l'edició d'usuari
     * @returns {void}
     */
    function prepararEnviamentEdicio() {
        $("#editTipSubmit").on('click', function() {
            Funcions.llevarErrors();
            var errors = false;
            if ($("#descEdit").val() === '') {
                Funcions.mostrarError($("#descEdit"), 'El descompte és obligatori'); errors = true;
            } else if (isNaN(parseInt($("#descEdit").val()))) {
                Funcions.mostrarError($("#descEdit"), 'El valor del descompte ha de ser numèric'); errors = true;
            } else if (parseInt($("#descEdit").val()) < 0 || parseInt($("#descEdit").val()) > 100) {
                Funcions.mostrarError($("#descEdit"), 'El valor del descompte ha d\'estar entre 0 i 100'); errors = true;
            }
            if (!errors) {
                var url = '../updates?descompte=' + $("#descEdit").val() + '&taula=tipus_usuari' + '&id=' + $("#idEdit").val();
                $("#editTip").trigger('reveal:close');
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
                $("#desc-" + id).slideToggle(400, function() {
                    $(this).text($("#descEdit").val());
                    $(this).slideToggle(800);
                });
            }
        }
    }
    /**
     * Penja el listener al botó d'afegir tipus i en mostra el popup
     * @returns {void}
     */
    function prepararAfegir() {
        $("#addTipus").on('click', function(e) {
            e.preventDefault();
            $("#descAdd").val(0);
            $("#addTip").reveal();
        });
    }
    
    /**
     * Prepara el botó d'enviar de l'edició d'usuari
     * @returns {void}
     */
    function prepararEnviamentInsercio() {
        $("#addTipSubmit").on('click', function() {
            Funcions.llevarErrors();
            var errors = false;
            if ($("#nomAdd").val() === '') {
                Funcions.mostrarError($("#nomAdd"), 'El nom és obligatori'); errors = true;
            }
            if (parseInt($("#descAdd").val()) < 0 || parseInt($("#descAdd").val()) > 100 || isNaN(parseInt($("#descAdd").val()))) {
                $("#descAdd").val(0);
            }
            if (!errors) {
                var url = '../altes?params=' + $("#nomAdd").val() + '_' + $("#descAdd").val() + '&taula=tipus_usuari';
                $("#addTip").trigger('reveal:close');
                Funcions.peticioAjax(url, addOk, Funcions.revealError);
            }
        });
        
        function addOk(resposta) {
            resposta = resposta.getElementsByTagName("files")[0];
            //Obtenim el valor de la resposta i el convertim a String
            var id = new XMLSerializer().serializeToString(resposta.childNodes[0]);
            if (id == '0') { //Si ha fallat l'inserció
                Funcions.revealError();
            } else {
                var html = '\
                <tr id="fila-' + id + '">\
                    <td id="id-' + id + '"><span>' + id + '</span></td>\
                    <td id="nom-' + id + '"><span>' + $("#nomAdd").val() + '</span></td>\
                    <td><span id="desc-' + id + '">' + $("#descAdd").val() + '</span><span> %</span></td>\
                    <td><button id="delete-' + id + '" class="deleteBtn"></button></td>\
                </tr>';
                $("tbody").append(html);
                $(html).hide().slideToggle(800);
                prepararEliminacio();
            }
        }
    }
    
    /**
     * Penja el listener als botons d'eliminació i elimina l'usuari triat
     * @returns {void}
     */
    function prepararEliminacio() {
        var id;
        $(".deleteBtn").unbind('click');
        $(".deleteBtn").on('click', function() {
           id = $(this).attr('id').replace('delete-', ''); //Prenem l'id de l'usuari
           var url = '../baixes?id=' + id + '&taula=tipus_usuari';
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

