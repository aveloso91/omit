(function() {
    'use strict';
    angular
        .module('omitApp')
        .factory('Subjects', Subjects);

    Subjects.$inject = ['$resource'];

    function Subjects ($resource) {
        var resourceUrl =  'api/subjects/:id';

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
