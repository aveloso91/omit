(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('Users_SubjectsDetailController', Users_SubjectsDetailController);

    Users_SubjectsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Users_Subjects', 'Subjects', 'User'];

    function Users_SubjectsDetailController($scope, $rootScope, $stateParams, previousState, entity, Users_Subjects, Subjects, User) {
        var vm = this;

        vm.users_Subjects = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('omitApp:users_SubjectsUpdate', function(event, result) {
            vm.users_Subjects = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
