/**
 * Created by Alejandro on 24/3/17.
 */
(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('StudentsController', StudentsController);

    StudentsController.$inject = ['Principal','StudentsService'];

    function StudentsController(Principal, StudentsService) {
        var vm = this;
        vm.currentAccount = undefined;

        vm.students = [];
        vm.subjects = [];


        Principal.identity().then(function(account) {
            vm.currentAccount = account;
            loadSubjects();
        });

        function loadSubjects() {
            StudentsService.getSubjectsByUser(vm.currentAccount.id).then(function (data) {
                vm.subjects = data;
            }, function (error) {

            })
        };

        vm.getStudents = function (sub) {
            StudentsService.getStudentsBySubject(sub.id).then(function (data) {
                vm.students = data;
            }, function (error) {

            })
        };

    }
})();
