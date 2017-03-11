(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('DegreesDeleteController',DegreesDeleteController);

    DegreesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Degrees'];

    function DegreesDeleteController($uibModalInstance, entity, Degrees) {
        var vm = this;

        vm.degrees = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Degrees.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
