angular.module('dleague').factory('notifier', function(notify) {
    // Global toastr configurations to dictate the behavior of different toast types.
    toastr.config = {};

    toastr.config.info = {
        'timeOut': 3000,
        'extendedTimeOut': 3000,
        'positionClass': 'toast-top-center'
    };

    toastr.config.success = {
        'timeOut': 3000,
        'extendedTimeOut': 3000,
        'positionClass': 'toast-top-center'
    };

    toastr.config.warning = {
        'timeOut': 3000,
        'extendedTimeOut': 3000,
        'positionClass': 'toast-top-left'
    };

    toastr.config.error = {
        'timeOut': 0,
        'extendedTimeOut': 0,
        'tapToDismiss': false,
        'closeButton': true,
        'positionClass': 'toast-top-left'
    };

    var service = {};
    var messageMap = {
        'info': function(message) {
            toastr.info(message);
        },
        'success': function(message) {
            toastr.success(message);
        },
        'warning': function(message) {
            toastr.warning(message);
        },
        'error': function(message) {
            toastr.error(message);
        }
    };

    service.notify = function(args) {
        var newargs;

        if (_.isString(args)) {
            newargs = {
                type: 'info',
                text: args
            };
        } else {
            newargs = {
                type: args.type ? args.type : 'info',
                text: args.text
            };
        }

        messageMap[newargs.type](newargs.text);
    };

    service.error = function(message) {
        messageMap.error(message);
    };

    service.info = function(message) {
        messageMap.info(message);
    };

    service.success = function(message) {
        messageMap.success(message);
    };

    return service;
});
