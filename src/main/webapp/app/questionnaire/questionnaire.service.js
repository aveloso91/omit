/**
 * Created by Alejandro on 7/4/17.
 */

(function() {
    'use strict';
    angular
        .module('omitApp')
        .factory('QuestService', QuestService);

    QuestService.$inject = ['$http','$q'];

    function QuestService ($http, $q) {
        return{
            getQuestionsSubjTeacher : function (id1, id2) {
                var deferred = $q.defer();
                $http.get("/api/questions/questionsBySubjectTeacher/"+id1+"/"+id2)
                    .success(function (data) {
                        deferred.resolve(data);}).error(function (error) {
                    deferred.reject(error);
                });
                return deferred.promise;
            },

            getQuestionsDefault : function () {
                var deferred = $q.defer();
                $http.get("/api/questions-defaults")
                    .success(function (data) {
                        deferred.resolve(data);}).error(function (error) {
                    deferred.reject(error);
                });
                return deferred.promise;
            },
            // guarda una questionsdefault en una questions con profe y asignatura
            saveQuestionsDefaultTS : function (text, idTeacher, idSubject) {
                var deferred = $q.defer();
                $http.get("/api/questions/saveQuestionsDefaultTS/"+text+"/"+idTeacher+"/"+idSubject)
                    .success(function (data) {
                        deferred.resolve(data);}).error(function (error) {
                    deferred.reject(error);
                });
                return deferred.promise;
            }
        };
    }
})();

