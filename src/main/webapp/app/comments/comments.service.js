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
            },
            saveComment : function (idOwner,idSubject,idTeacher,textCo,score) {
                var deferred = $q.defer();
                $http.get("/api/solrcomments/saveComment/"+idOwner+"/"+idSubject+"/"+idTeacher+"/"+textCo+"/"+score)
                    .success(function (data) {
                        deferred.resolve(data);}).error(function (error) {
                    deferred.reject(error);
                });
                return deferred.promise;
            },
            findCommentsBySubjectTeacher : function (idSubject,idTeacher) {
                var deferred = $q.defer();
                $http.get("/api/solrcomments/findCommentsByTS/"+idSubject+"/"+idTeacher)
                    .success(function (data) {
                        deferred.resolve(data);}).error(function (error) {
                    deferred.reject(error);
                });
                return deferred.promise;
            }
        };
    }
})();

