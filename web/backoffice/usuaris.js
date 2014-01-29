
$(document).ready(function() {
    prepararEdicio();
    prepararEliminacio();
    
    /**
     * Penja el listener als botons d'edició d'usuaris i mostra el popup d'edició
     * @returns {void}
     */
    function prepararEdicio() {
        
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

