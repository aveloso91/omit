(function() {
    'use strict';
    angular
        .module('omitApp')
        .factory('Degrees', Degrees);

    Degrees.$inject = ['$resource'];

    function Degrees ($resource) {
        var resourceUrl =  'api/degrees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
