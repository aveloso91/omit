(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('Questions_DefaultDialogController', Questions_DefaultDialogController);

    Questions_DefaultDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Questions_Default'];

    function Questions_DefaultDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Questions_Default) {
        var vm = this;

        vm.questions_Default = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.questions_Default.id !== null) {
                Questions_Default.update(vm.questions_Default, onSaveSuccess, onSaveError);
            } else {
                Questions_Default.save(vm.questions_Default, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omitApp:questions_DefaultUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
