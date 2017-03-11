(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('DegreesDetailController', DegreesDetailController);

    DegreesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Degrees', 'Courses'];

    function DegreesDetailController($scope, $rootScope, $stateParams, previousState, entity, Degrees, Courses) {
        var vm = this;

        vm.degrees = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omitApp:degreesUpdate', function(event, result) {
            vm.degrees = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
