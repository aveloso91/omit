(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('Questions_DefaultDeleteController',Questions_DefaultDeleteController);

    Questions_DefaultDeleteController.$inject = ['$uibModalInstance', 'entity', 'Questions_Default'];

    function Questions_DefaultDeleteController($uibModalInstance, entity, Questions_Default) {
        var vm = this;

        vm.questions_Default = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Questions_Default.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
