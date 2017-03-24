/**
 * Created by Alejandro on 24/3/17.
 */
(function() {
    'use strict';

    angular
        .module('omitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('students', {
                parent: 'app',
                url: '/students',
                data: {
                    authorities: ['ROLE_TEACHER'],
                    pageTitle: 'students.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/students/students.html',
                        controller: 'CommentsController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('students');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    }
})();
