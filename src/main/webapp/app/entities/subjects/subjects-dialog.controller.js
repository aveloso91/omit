(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('SubjectsDialogController', SubjectsDialogController);

    SubjectsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Subjects', 'Courses', 'Users_Subjects', 'Questions'];

    function SubjectsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Subjects, Courses, Users_Subjects, Questions) {
        var vm = this;

        vm.subjects = entity;
        vm.clear = clear;
        vm.save = save;
        vm.courses = Courses.query();
        vm.users_subjects = Users_Subjects.query();
        vm.questions = Questions.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subjects.id !== null) {
                Subjects.update(vm.subjects, onSaveSuccess, onSaveError);
            } else {
                Subjects.save(vm.subjects, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omitApp:subjectsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
