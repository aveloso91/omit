(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('SubjectsDeleteController',SubjectsDeleteController);

    SubjectsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Subjects'];

    function SubjectsDeleteController($uibModalInstance, entity, Subjects) {
        var vm = this;

        vm.subjects = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Subjects.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
