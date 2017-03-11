(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('CoursesDetailController', CoursesDetailController);

    CoursesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Courses', 'Degrees', 'Subjects'];

    function CoursesDetailController($scope, $rootScope, $stateParams, previousState, entity, Courses, Degrees, Subjects) {
        var vm = this;

        vm.courses = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omitApp:coursesUpdate', function(event, result) {
            vm.courses = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
