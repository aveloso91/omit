(function() {
    'use strict';

    angular
        .module('omitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('questions-default', {
            parent: 'entity',
            url: '/questions-default',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omitApp.questions_Default.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/questions-default/questions-defaults.html',
                    controller: 'Questions_DefaultController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questions_Default');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('questions-default-detail', {
            parent: 'questions-default',
            url: '/questions-default/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omitApp.questions_Default.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/questions-default/questions-default-detail.html',
                    controller: 'Questions_DefaultDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questions_Default');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Questions_Default', function($stateParams, Questions_Default) {
                    return Questions_Default.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'questions-default',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('questions-default-detail.edit', {
            parent: 'questions-default-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/questions-default/questions-default-dialog.html',
                    controller: 'Questions_DefaultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Questions_Default', function(Questions_Default) {
                            return Questions_Default.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('questions-default.new', {
            parent: 'questions-default',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/questions-default/questions-default-dialog.html',
                    controller: 'Questions_DefaultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                text: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('questions-default', null, { reload: 'questions-default' });
                }, function() {
                    $state.go('questions-default');
                });
            }]
        })
        .state('questions-default.edit', {
            parent: 'questions-default',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/questions-default/questions-default-dialog.html',
                    controller: 'Questions_DefaultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Questions_Default', function(Questions_Default) {
                            return Questions_Default.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('questions-default', null, { reload: 'questions-default' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('questions-default.delete', {
            parent: 'questions-default',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/questions-default/questions-default-delete-dialog.html',
                    controller: 'Questions_DefaultDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Questions_Default', function(Questions_Default) {
                            return Questions_Default.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('questions-default', null, { reload: 'questions-default' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
