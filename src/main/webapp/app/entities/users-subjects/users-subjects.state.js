(function() {
    'use strict';

    angular
        .module('omitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('users-subjects', {
            parent: 'entity',
            url: '/users-subjects',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'omitApp.users_Subjects.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/users-subjects/users-subjects.html',
                    controller: 'Users_SubjectsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('users_Subjects');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('users-subjects-detail', {
            parent: 'users-subjects',
            url: '/users-subjects/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'omitApp.users_Subjects.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/users-subjects/users-subjects-detail.html',
                    controller: 'Users_SubjectsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('users_Subjects');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Users_Subjects', function($stateParams, Users_Subjects) {
                    return Users_Subjects.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'users-subjects',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('users-subjects-detail.edit', {
            parent: 'users-subjects-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/users-subjects/users-subjects-dialog.html',
                    controller: 'Users_SubjectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Users_Subjects', function(Users_Subjects) {
                            return Users_Subjects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('users-subjects.new', {
            parent: 'users-subjects',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/users-subjects/users-subjects-dialog.html',
                    controller: 'Users_SubjectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('users-subjects', null, { reload: 'users-subjects' });
                }, function() {
                    $state.go('users-subjects');
                });
            }]
        })
        .state('users-subjects.edit', {
            parent: 'users-subjects',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/users-subjects/users-subjects-dialog.html',
                    controller: 'Users_SubjectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Users_Subjects', function(Users_Subjects) {
                            return Users_Subjects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('users-subjects', null, { reload: 'users-subjects' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('users-subjects.delete', {
            parent: 'users-subjects',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/users-subjects/users-subjects-delete-dialog.html',
                    controller: 'Users_SubjectsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Users_Subjects', function(Users_Subjects) {
                            return Users_Subjects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('users-subjects', null, { reload: 'users-subjects' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
