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
            .state('questionnaire', {
                parent: 'app',
                url: '/questionnaire',
                data: {
                    authorities: ['ROLE_TEACHER','ROLE_STUDENT'],
                    pageTitle: 'questionnaire.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/questionnaire/questionnaire.html',
                        controller: 'CommentsController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('questionnaire');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    }
})();
