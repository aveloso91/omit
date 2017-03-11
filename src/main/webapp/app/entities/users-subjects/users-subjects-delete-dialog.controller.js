(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('Users_SubjectsDeleteController',Users_SubjectsDeleteController);

    Users_SubjectsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Users_Subjects'];

    function Users_SubjectsDeleteController($uibModalInstance, entity, Users_Subjects) {
        var vm = this;

        vm.users_Subjects = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Users_Subjects.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
