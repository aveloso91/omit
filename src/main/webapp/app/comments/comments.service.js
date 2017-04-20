/**
 * Created by Alejandro on 31/3/17.
 */
(function() {
    'use strict';
    angular
        .module('omitApp')
        .factory('CommentsService', CommentsService);

    CommentsService.$inject = ['$http','$q'];

    function CommentsService ($http, $q) {
        return{
            getTeachersBySubject : function (id) {
                var deferred = $q.defer();
                $http.get("/api/users-subjects/teachersBySubject/"+id)
                    .success(function (data) {
                        deferred.resolve(data);}).error(function (error) {
                    deferred.reject(error);
                });
                return deferred.promise;
            }
        };
    }
})();

