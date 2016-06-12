/**
 * This service should be used to manage Deferred objects. Grouping like
 * objects allows them to be easily resolved all at once.
 */
angular.module('dleague').factory('defer', function($q) {
    var defers = {};
    var service = {};

    /**
     * Add an existing Deferred object to the group of the given type.
     *
     * @param {Deferred} deferred
     * @param {String} type
     * @return null
     */
    service.add = function(deferred, type) {
        type = type || 'default';
        defers[type] = defers[type] || [];
        defers[type].push(deferred);
    };

    /**
     * Create a new Deferred object to be added to the group of the given type
     * and return the new object.
     *
     * @param {String} type
     * @return {Deferred} defer
     */
    service.create = function(type) {
        var deferred = $q.defer();
        service.add(deferred, type);
        return deferred;
    };

    /**
     * Resolve all Deferred objects of a given type.  This can be used to
     * cancel any rest call with a 'timeout' attribute set to a promise from a
     * Deferred object of type 'type'.
     *
     * @param {String} type
     * @return null
     */
    service.reject = function(type) {
        _.each(defers[type], function(deferred) {
            deferred.resolve();
        });
        defers[type] = [];
    };

    return service;
});
