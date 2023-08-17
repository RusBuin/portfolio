
var googleUser = {};

var startApp = function () {

    gapi.load('auth2', function () {
        auth2 = gapi.auth2.init({
            client_id: '223621510034-cmjqif40ts7fqmrk4sullr47l6viri0g.apps.googleusercontent.com',
            cookiepolicy: 'single_host_origin',
            plugin_name: 'chat'
        });
        attachSignin(document.getElementById('btnGoogle'));
    });

};

function attachSignin(element) {
    auth2.attachClickHandler(element, {},

        function (googleUser) {
            console.log(googleUser);
            auth2 = gapi.auth2.getAuthInstance();
            if (auth2.isSignedIn.get()) {
                var profile = auth2.currentUser.get().getBasicProfile();
                console.log('ID: ' + profile.getId());
                console.log('Full Name: ' + profile.getName());
                console.log('Given Name: ' + profile.getGivenName());
                console.log('Family Name: ' + profile.getFamilyName());
                console.log('Image URL: ' + profile.getImageUrl());
                console.log('Email: ' + profile.getEmail());
                document.querySelector("#contentsign").innerHTML = "<p>Vitajte " + profile.getName() + "! Kliknite pre <a href='#' onclick='signOut();'>odhlásenie</a></p>";

            }
        }, function (error) {
            alert(JSON.stringify(error, undefined, 2));
        });

}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        alert("Boli ste odhlásený...");
        location.reload();
    });
}

