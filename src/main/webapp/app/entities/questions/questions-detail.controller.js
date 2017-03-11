(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('QuestionsDetailController', QuestionsDetailController);

    QuestionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Questions', 'Subjects', 'User'];

    function QuestionsDetailController($scope, $rootScope, $stateParams, previousState, entity, Questions, Subjects, User) {
        var vm = this;

        vm.questions = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omitApp:questionsUpdate', function(event, result) {
            vm.questions = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
