angular.module('dleague').factory('persistence', function($resource, $http, $q, config, notifier) {
    var apiRoot = config.client.apiRoot;
    var restMappings = {
        'get': {
            method: 'GET'
        },
        'create': {
            method: 'POST'
        },
        'update': {
            method: 'PUT'
        },
        'query': {
            method: 'GET',
            isArray: true
        },
        'bulkUpdate': {
            method: 'PUT',
            isArray: true
        }
    };

    var restMappingsWithDelete = _.extend(restMappings, {
        'delete': {
            method: 'DELETE'
        }
    });

    var constructRoute = function(route, withId) {
        return apiRoot + '/' + route + ((withId) ? '/:_id' : '');
    };

    var standardIdMapping = {
        _id: '@_id'
    };

    var constructResource = function(route, withId, alternateMethodMappings) {
        var resource = $resource(
            constructRoute(route, withId), ((withId) ? standardIdMapping : {}),
            alternateMethodMappings || restMappings);

        resource.baseRouteName = constructRoute(route, false);
        resource.routeName = constructRoute(route, withId);

        var queryWithString = function(queryString, successFn, errorFn) {
            $http.get(apiRoot + '/' + route + '?query=' + queryString)
                .success(successFn)
                .error(errorFn);
        };

        var loadFailureHandler = function(message, deferred, response) {
            notifier.error(message);
            deferred.reject();
        };

        var deferredQuery = function(queryArgs, successFn, failMessage) {
            var deferred = $q.defer();

            resource.query(
                queryArgs,
                function(data) {
                    successFn(data);
                    deferred.resolve(data);
                },
                _.partial(loadFailureHandler, failMessage, deferred));

            return deferred.promise;
        };

        return _.extend(resource, {
            queryWithString: queryWithString,
            deferredQuery: deferredQuery
        });
    };

    var service = {
        CollectionKeys: constructResource('collectionKeys', false, {
            'query': {
                method: 'GET',
                isArray: false
            }
        }),
        Config: constructResource('config', true),
        Connectivity: constructResource('connectivity', true),
        File: constructResource('file', false),
        Notification: constructResource('notification', true),
        Metadata: constructResource('metadata', true),
    };

    // See http://stackoverflow.com/questions/13813673/one-dimensional-array-of-strings-being-parsed-to-2d-by-angular-resource
    service.Collection = {
        query: function(params, successFn, errorFn) {
            // Note: params not used, but adding it in keeps API design consistent with angular.
            $http.get(apiRoot + '/collection').success(successFn).error(errorFn);
        }
    };

    return service;
});
