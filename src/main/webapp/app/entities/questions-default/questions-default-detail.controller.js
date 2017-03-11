(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('Questions_DefaultDetailController', Questions_DefaultDetailController);

    Questions_DefaultDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Questions_Default'];

    function Questions_DefaultDetailController($scope, $rootScope, $stateParams, previousState, entity, Questions_Default) {
        var vm = this;

        vm.questions_Default = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omitApp:questions_DefaultUpdate', function(event, result) {
            vm.questions_Default = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
