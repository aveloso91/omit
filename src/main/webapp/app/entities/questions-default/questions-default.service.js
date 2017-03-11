(function() {
    'use strict';
    angular
        .module('omitApp')
        .factory('Questions_Default', Questions_Default);

    Questions_Default.$inject = ['$resource'];

    function Questions_Default ($resource) {
        var resourceUrl =  'api/questions-defaults/:id';

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
