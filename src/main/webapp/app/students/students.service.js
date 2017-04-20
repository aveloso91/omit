/**
 * Created by Alejandro on 7/4/17.
 */

(function() {
    'use strict';
    angular
        .module('omitApp')
        .factory('StudentsService', StudentsService);

    StudentsService.$inject = ['$http','$q'];

    function StudentsService ($http, $q) {
        return{

            getSubjectsByUser : function (id) {
                var deferred = $q.defer();
                $http.get("/api/users-subjects/SubjectsByUser/"+id)
                    .success(function (data) {
                        deferred.resolve(data);}).error(function (error) {
                    deferred.reject(error);
                });
                return deferred.promise;
            },

            getStudentsBySubject : function (id) {
                var deferred = $q.defer();
                $http.get("/api/users-subjects/studentsBySubject/"+id)
                    .success(function (data) {
                        deferred.resolve(data);}).error(function (error) {
                    deferred.reject(error);
                });
                return deferred.promise;
            }
        };
    }
})();
