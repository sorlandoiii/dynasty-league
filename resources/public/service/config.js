angular.module('dleague').factory('config', function() {
    var config = {
        client: {
            apiRoot: 'ws/ircop/v1'
        },
        server: {
            systemSecurityLabel: ''
        }
    };
    return config;
});
