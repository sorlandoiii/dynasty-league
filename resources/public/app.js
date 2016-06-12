angular.module('dleague', ['ui.bootstrap', 'ui.utils', 'ngRoute', 'ngAnimate', 'ngResource',
    'ui.select2', 'ngCookies', 'cgBusy', 'cgNotify', 'ngTable', 'ngSanitize', 'cgPrompt'
]);

angular.module('dleague').config(function($routeProvider) {
    // Wrapper around routeProvider that automatically adds our default resolves
    var oldRouteProvider = $routeProvider;
    $routeProvider = {
        when: function(path, route) {
            if (!route.resolve) {
                route.resolve = {};
            }
            oldRouteProvider.when(path, route);
            return $routeProvider;
        },
        otherwise: function(arg) {
            oldRouteProvider.otherwise(arg);
            return $routeProvider;
        }
    };

    $routeProvider.
    when('/', {
        templateUrl: 'partial/home/home.html'
    }).
        /* Add New Routes Above */
    otherwise({
        templateUrl: 'partial/four-oh-four/four-oh-four.html'
    });
});

angular.module('dleague').run(function($rootScope, $http, config) {
    $('#ircop-splash').remove();

    // TODO: Can we get this into our route.resolve area above?
    $http.get(config.client.apiRoot + '/user')
        .success(function(data, status, headers, config) {
            $rootScope.userList = data;
        });

    $rootScope.safeApply = function(fn) {
        var phase = $rootScope.$$phase;
        if (phase === '$apply' || phase === '$digest') {
            if (fn && (typeof(fn) === 'function')) {
                fn();
            }
        } else {
            this.$apply(fn);
        }
    };
});

angular.module('cgBusy').run(['$templateCache', function($templateCache) {
    $templateCache.put('angular-busy.html',
        '<div class="cg-busy-default-wrapper">\n' +
        '\n' +
        '   <div class="cg-busy-default-sign">\n' +
        '\n' +
        '      <div class="cg-busy-default-spinner">\n' +
        '         <img src=\'img/ajax-loader.gif\' />\n' +
        '      </div>\n' +
        '\n' +
        '      <div class="cg-busy-default-text">Please Wait...</div>\n' +
        '\n' +
        '   </div>\n' +
        '\n' +
        '</div>');
}]);

// CGross: 4/4/2013, Hack to overwrite popover template from ui-bootstrap only
// change was to change the ng-bind in the content to ng-bind-html future peeps
// should remove and replace with
// https://github.com/angular-ui/bootstrap/pull/1848 when its done
angular.module('template/popover/popover.html', []).run(['$templateCache', function($templateCache) {
    $templateCache.put('template/popover/popover.html',
        '<div class="popover {{placement}}" ng-class="{ in: isOpen(), fade: animation() }">\n' +
        '  <div class="arrow"></div>\n' +
        '\n' +
        '  <div class="popover-inner">\n' +
        '      <h3 class="popover-title" ng-bind="title" ng-show="title"></h3>\n' +
        '      <div class="popover-content" ng-bind-html="content"></div>\n' +
        '  </div>\n' +
        '</div>\n' +
        '');
}]);
