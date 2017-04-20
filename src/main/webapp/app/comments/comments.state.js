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
        .state('comments', {
            parent: 'app',
            url: '/comments',
            data: {
                authorities: ['ROLE_TEACHER','ROLE_STUDENT'],
                pageTitle: 'comments.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/comments/comments.html',
                    controller: 'CommentsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comments');
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('users_Subjects');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
