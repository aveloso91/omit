(function() {
    'use strict';
    angular
        .module('omitApp')
        .factory('Users_Subjects', Users_Subjects);

    Users_Subjects.$inject = ['$resource'];

    function Users_Subjects ($resource) {
        var resourceUrl =  'api/users-subjects/:id';

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
