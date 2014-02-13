
$(document).ready(function() {
    prepararEdicio();
    prepararEnviamentEdicio();
    /**
     * Penja el listener als botons d'edició d'usuaris i mostra el popup d'edició
     * @returns {void}
     */
    function prepararEdicio() {
        $(".editBtn").on('click', function() {
            var id = $(this).attr('id').replace('edit-', '');
            $("#idEdit").val(id);
            $("#preuEdit").val($("#preu-" + id).text());
            $("#editPreu").reveal();
        });
    }
    
    /**
     * Prepara el botó d'enviar de l'edició d'usuari
     * @returns {void}
     */
    function prepararEnviamentEdicio() {
        $("#editPreuSubmit").on('click', function() {
            Funcions.llevarErrors();
            var errors = false;
            if ($("#preuEdit").val() === '') {
                Funcions.mostrarError($("#preuEdit"), 'L\'estat és obligatori'); errors = true;
            } else if (isNaN(parseInt($("#preuEdit").val()))) {
                Funcions.mostrarError($("#preuEdit"), 'El preu ha de ser numèric'); errors = true;
            }
            if (!errors) {
                var url = '../updates?preu=' + $("#preuEdit").val() + '&taula=import_habitacio&id=' + $("#idEdit").val();
                $("#editPreu").trigger('reveal:close');
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
                $("#preu-" + id).slideToggle(400, function() {
                    $(this).text($("#preuEdit").val())
                    $(this).slideToggle(800);
                });
            }
        }
    }
});
