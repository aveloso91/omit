(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('CoursesDeleteController',CoursesDeleteController);

    CoursesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Courses'];

    function CoursesDeleteController($uibModalInstance, entity, Courses) {
        var vm = this;

        vm.courses = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Courses.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
