
$(document).ready(function() {
    
    Funcions.reescriureReveal();
    Funcions.prepararLogin();
    Funcions.prepararLogout();
});

//Cream un namespace per les funcions genèriques
var Funcions = {
    /**
     * Reescribim la funció que col·loca el listener als disparadors de reveal
     * @returns {void}
     */
    reescriureReveal: function() {
        $('a[data-reveal-id]').on('click', function(e) {
            e.preventDefault();
            var modalLocation = $(this).attr('data-reveal-id');
            $('#'+modalLocation).reveal($(this).data());
        });
    },
    
    /**
     * Comprova que el formulari de login està ben emplenat i, només en aquest cas, l'envia
     * @returns {void}
     */
    prepararLogin: function() {
        $("#loginForm").on('submit', function(e) {
            e.preventDefault(); //Evitam que s'enviï el formulari
            Funcions.llevarErrors();
            var errors = false;
            if ($("#loginMail").val() === '') {
                Funcions.mostrarError($("#loginMail"), 'L\'email és obligatori'); errors = true;
            }
            if ($("#loginPass").val() === '') {
                Funcions.mostrarError($("#loginPass"), 'La contrasenya és obligatòria'); errors = true;
            }
            if (!errors) {
                var params = '../login?mail=' + $("#loginMail").val() + '&pass=' + $("#loginPass").val();
                Funcions.peticioAjax(params, loginOk, revealError);
            }
        });
        
        function loginOk(response) {
            var elems = response.getElementsByTagName("resposta")[0];
            try {
                var nom = new XMLSerializer().serializeToString(elems.getElementsByTagName("nom")[0].firstChild);
                var nac = new XMLSerializer().serializeToString(elems.getElementsByTagName("nac")[0].firstChild);
                var dni = new XMLSerializer().serializeToString(elems.getElementsByTagName("dni")[0].firstChild);
                var tip = new XMLSerializer().serializeToString(elems.getElementsByTagName("tip")[0].firstChild);
                $('#loginModal').trigger('reveal:close');
                $("#loginBtn").parent().fadeOut(600, function() {
                    //Mostram el nom de l'usuari i afegim els altres valors com a camps ocults
                    var html = '\
                        <a href="#" id="nomVal" class="gris">' + nom + '<div class="arrow arrowDown"></div></a><br />\
                        <a href="../logout" id="logoutBtn" class="gris">Tancar sessió</a>\
                        <input type="hidden" id="mailVal" value="' + $("#loginMail").val() + '" />\
                        <input type="hidden" id="nacionalitatVal" value="' + nac + '" />\
                        <input type="hidden" id="dniVal" value="' + dni + '" />\
                        <input type="hidden" id="tipusVal" value="' + tip + '" />';
                    $(this).html(html);
                    $(this).removeClass('pointer');
                    $(this).fadeIn();
                    //Simulam un clic en aquest element perquè el listener de reservar.js sàpiga del canvi
                    $("#loginChange").trigger('click');
                    Funcions.prepararLogout();
                });
            } catch (e) {
                console.log(e);
                $("#credencialsKoModal").reveal();
            }
        }
        
        function revealError() {
            $("#errorModal").reveal();
        }
    },
    
    /**
     * Prepara el botó de logout
     * @returns {void}
     */
    prepararLogout: function() {
        $("#logoutBtn").hide();
        $("#nomVal").on('click', function(e) {
            e.preventDefault();
            $("#logoutBtn").slideToggle(600, function() {
                if ($(".arrow").hasClass("arrowDown")) $(".arrow").removeClass("arrowDown").addClass("arrowUp");
                else $(".arrow").removeClass("arrowUp").addClass("arrowDown");
            });
        });
    },
    
    /**
     * Comprova que el formulari de registre està ben emplenat i, només en aquest cas, l'envia
     * @returns {void}
     */
    prepararRegistre: function() {
        
    },
    
    /**
     * Lleva tots els missatges d'error
     * @return {void} 
     */
    llevarErrors: function() {
        $(".errorText").remove();
    },
    
    /**
     * Posa un missatge d'error abans de l'element donat
     * @param {HTML element} elem L'element on mostrar l'error
     * @param {String} text El text d'error
     * @return {void}
     */
    mostrarError: function(elem, text) {
        var errElem = '<span class="errorText">' + text + '</span>';
        $(elem).before(errElem);
        $(elem).focus();
    },
    
    /**
     * Realitza una petició AJAX
     * @param {String} url URL a la que realitzar la consulta (ha d'incloure els paràmetres)
     * @param {String} success Funció a la que cridar en cas de funcionament normal
     * @param {String} error Funció a la que cridar en cas d'error
     * @returns {void}
     */
    peticioAjax: function(url, success, error) {
        var req = prepararRequest();
        req.open("POST", url, true);
        req.onreadystatechange = ajaxCallback;
        req.send(null);
        
        /**
         * Preparar un objecte per realitzar una petició AJAX
         * @returns {XMLHttpRequest|ActiveXObject}
         */
        function prepararRequest() {
            if (window.XMLHttpRequest) {
                if (navigator.userAgent.indexOf('MSIE') != -1) {
                    isIE = true;
                }
                return new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                isIE = true;
                return new ActiveXObject("Microsoft.XMLHTTP");
            }
        }
        
        /**
         * Callback per la resposta HTTP
         */
        function ajaxCallback() {
            if (req.readyState == 4) { //Esperam a l'estat 4 (Petició acabada i resposta llesta)
                if (req.status == 200) success.call(this, req.responseXML);
                else error.call(this);
            }
        }
    }
};