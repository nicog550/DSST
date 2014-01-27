
var self;
$(document).ready(function() {
    self = this;
    Funcions.reescriureReveal();
    Funcions.prepararLogin();
    Funcions.prepararLogout();
    Funcions.prepararRegistre();
});

//Cream un namespace per les funcions genèriques
var Funcions = {
    
    // <editor-fold defaultstate="collapsed" desc="Funció que recol·loca el listener als disparadors de reveal.">
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
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Funció que prepara el formulari de login.">
    
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
                Funcions.peticioAjax(params, loginOk, Funcions.revealError);
            }
        });
    
        /**
         * Mostra el nom de l'usuari i prepara el botó de logout. També col·loca totes les dades de l'usuari en camps ocults
         * @param {XML String} response Resposta XML amb les dades de l'usuari
         * @returns {void}
         */
        function loginOk(response) {
            var elems = response.getElementsByTagName("resposta")[0];
            try {
                var nom = new XMLSerializer().serializeToString(elems.getElementsByTagName("nom")[0].firstChild);
                var nac = new XMLSerializer().serializeToString(elems.getElementsByTagName("nac")[0].firstChild);
                var dni = new XMLSerializer().serializeToString(elems.getElementsByTagName("dni")[0].firstChild);
                var tip = new XMLSerializer().serializeToString(elems.getElementsByTagName("tip")[0].firstChild);
                $('#loginModal').trigger('reveal:close');
                Funcions.formUsuari(nom, $("#loginMail").val(), nac, dni, tip);
            } catch (e) {
                console.log(e);
                $("#credencialsKoModal").reveal();
            }
        }
    },
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Funció que prepara el formulari de registre.">
    
    /**
     * Comprova que el formulari de registre està ben emplenat i, només en aquest cas, l'envia
     * @returns {void}
     */
    prepararRegistre: function() {
        $("#signupForm").on('submit', function(e) {
            e.preventDefault(); //Evitam que s'enviï el formulari
            Funcions.llevarErrors();
            var errors = false;
            if ($("#signupNom").val() === '') {
                Funcions.mostrarError($("#signupNom"), 'El nom és obligatori'); errors = true;
            }
            if ($("#signupMail").val() === '') {
                Funcions.mostrarError($("#signupMail"), 'L\'email és obligatori'); errors = true;
            }
            if ($("#signupDni").val() === '') {
                Funcions.mostrarError($("#signupDni"), 'El número de DNI és obligatori'); errors = true;
            }
            if ($("#signupPass").val() === '') {
                Funcions.mostrarError($("#signupPass"), 'La contrasenya és obligatòria'); errors = true;
            }
            if ($("#signupPassRep").val() === '') {
                Funcions.mostrarError($("#signupPassRep"), 'Repetiu la contrasenya'); errors = true;
            } else if ($("#signupPassRep").val() !== $("#signupPass").val()) {
                Funcions.mostrarError($("#signupPassRep"), 'Les contrasenyes no coincideixen'); errors = true;
            }
            if ($("#signupNac").val() === '0') {
                Funcions.mostrarError($("#signupNac"), 'La nacionalitat és obligatòria'); errors = true;
            }
            if (!errors) {
                var params = '../registre?nom=' + $("#signupNom").val() + '&mail=' + $("#signupMail").val() + '&pass=' +
                            $("#signupPass").val() + '&dni=' + $("#signupDni").val() + '&nac=' + $("#signupNac").val();
                Funcions.peticioAjax(params, signupOk, Funcions.revealError);
            }
        });
    
        /**
         * Mostra el nom de l'usuari i prepara el botó de logout. També col·loca totes les dades de l'usuari en camps ocults
         * @param {XML String} response Resposta XML amb les dades de l'usuari
         * @returns {void}
         */
        function signupOk(response) {
            var elems = response.getElementsByTagName("resposta")[0];
            var estat = new XMLSerializer().serializeToString(elems.getElementsByTagName("estat")[0].firstChild);
            if (estat == 'ok') {
                var tip = new XMLSerializer().serializeToString(elems.getElementsByTagName("tipus")[0].firstChild);
                $('#signupModal').trigger('reveal:close');
                Funcions.formUsuari($("#signupNom").val(), $("#signupMail").val(), $("#signupNac").val(), $("#signupDni").val(), tip);
            } else {
                Funcions.revealError();
            }
        }
    },
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="Funció que col·loca el formulari ocult d'usuari.">
    /**
     * Crea un formulari ocult on hi col·loca les dades de l'usuari per tal de poder enviar-les a la pàgina de reserves
     * sense necessitat d'haver de passar per la bbdd. També mostra el nom de l'usuari i prepara el botó de logout
     * @param {String} nom Nom de l'usuari
     * @param {String} email Email de l'usuari
     * @param {String} nac Nacionalitat de l'usuari
     * @param {String} dni DNI de l'usuari
     * @param {Number} tip Tipus d'usuari de l'usuari
     * @returns {void}
     */
    formUsuari: function(nom, email, nac, dni, tip) {
        $("#login-signup").fadeOut(600, function() {
            //Mostram el nom de l'usuari i afegim els altres valors com a camps ocults
            var html = '\
                <a href="#" id="nomVal" class="gris right">' + nom + '<div class="arrow arrowDown"></div></a><br />\
                <a href="../logout" id="logoutBtn" class="gris">Tancar sessió</a>\
                <input type="hidden" id="mailVal" value="' + email + '" />\
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
    },
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funció que mostra el popup d'error.">
    
    /**
     * Mostra el popup que indica que s'ha produit un error
     * @returns {void}
     */
    revealError: function() {
        $("#errorModal").reveal();
    },
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Funció que preparar el botó de logout.">
    
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
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Funció que lleva els missatges d'error.">
    
    /**
     * Lleva tots els missatges d'error
     * @return {void} 
     */
    llevarErrors: function() {
        $(".errorText").remove();
    },
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Funció que col·loca els missatges d'error.">
    
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
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Funció que realitza una petició AJAX.">
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
    // </editor-fold>  
};