(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('Users_SubjectsDialogController', Users_SubjectsDialogController);

    Users_SubjectsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Users_Subjects', 'Subjects', 'User'];

    function Users_SubjectsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Users_Subjects, Subjects, User) {
        var vm = this;

        vm.users_Subjects = entity;
        vm.clear = clear;
        vm.save = save;
        vm.subjects = Subjects.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.users_Subjects.id !== null) {
                Users_Subjects.update(vm.users_Subjects, onSaveSuccess, onSaveError);
            } else {
                Users_Subjects.save(vm.users_Subjects, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omitApp:users_SubjectsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
