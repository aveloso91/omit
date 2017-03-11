(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('CoursesDialogController', CoursesDialogController);

    CoursesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Courses', 'Degrees', 'Subjects'];

    function CoursesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Courses, Degrees, Subjects) {
        var vm = this;

        vm.courses = entity;
        vm.clear = clear;
        vm.save = save;
        vm.degrees = Degrees.query();
        vm.subjects = Subjects.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.courses.id !== null) {
                Courses.update(vm.courses, onSaveSuccess, onSaveError);
            } else {
                Courses.save(vm.courses, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omitApp:coursesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
