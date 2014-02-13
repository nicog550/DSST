
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
            $("#editEst-" + $("#estat-" + id).text()).attr('selected', 'selected');
            $("#editRes").reveal();
        });
    }
    
    /**
     * Prepara el botó d'enviar de l'edició d'usuari
     * @returns {void}
     */
    function prepararEnviamentEdicio() {
        $("#editResSubmit").on('click', function() {
            Funcions.llevarErrors();
            var errors = false;
            if ($("#estEdit").val() === '0') {
                Funcions.mostrarError($("#estEdit"), 'L\'estat és obligatori'); errors = true;
            }
            if (!errors) {
                var url = '../updates?estat=' + $("#estEdit").val() + '&taula=reserva&id=' + $("#idEdit").val();
                $("#editRes").trigger('reveal:close');
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
                $("#estat-" + id).parent().slideToggle(400, function() {
                    var html = '<span id="estat-' + id + '">' + $("#estEdit").val() + '</span>';
                    $(this).html(html)
                    $(this).slideToggle(800);
                });
            }
        }
    }
});

