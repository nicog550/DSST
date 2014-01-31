
$(document).ready(function() {
    prepararAfegir();
    prepararEnviamentInsercio();
    prepararEliminacio();
    /**
     * Penja el listener al botó d'afegir tipus i en mostra el popup
     * @returns {void}
     */
    function prepararAfegir() {
        $("#addTipus").on('click', function(e) {
            e.preventDefault();
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
            if (!errors) {
                var url = '../altes?params=' + $("#nomAdd").val() + '&taula=tipus_usuari';
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
                    <td><button id="delete-' + id + '" class="deleteBtn"></button></td>\
                </tr>';
                $("tbody").append(html);
                $(html).hide().slideToggle(800);
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

