(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('DegreesDialogController', DegreesDialogController);

    DegreesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Degrees', 'Courses'];

    function DegreesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Degrees, Courses) {
        var vm = this;

        vm.degrees = entity;
        vm.clear = clear;
        vm.save = save;
        vm.courses = Courses.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.degrees.id !== null) {
                Degrees.update(vm.degrees, onSaveSuccess, onSaveError);
            } else {
                Degrees.save(vm.degrees, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omitApp:degreesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
