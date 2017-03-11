(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('SubjectsDetailController', SubjectsDetailController);

    SubjectsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Subjects', 'Courses', 'Users_Subjects', 'Questions'];

    function SubjectsDetailController($scope, $rootScope, $stateParams, previousState, entity, Subjects, Courses, Users_Subjects, Questions) {
        var vm = this;

        vm.subjects = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omitApp:subjectsUpdate', function(event, result) {
            vm.subjects = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
