(function() {
    'use strict';

    angular
        .module('omitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subjects', {
            parent: 'entity',
            url: '/subjects',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'omitApp.subjects.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subjects/subjects.html',
                    controller: 'SubjectsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subjects');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subjects-detail', {
            parent: 'subjects',
            url: '/subjects/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'omitApp.subjects.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subjects/subjects-detail.html',
                    controller: 'SubjectsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subjects');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Subjects', function($stateParams, Subjects) {
                    return Subjects.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'subjects',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('subjects-detail.edit', {
            parent: 'subjects-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subjects/subjects-dialog.html',
                    controller: 'SubjectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subjects', function(Subjects) {
                            return Subjects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subjects.new', {
            parent: 'subjects',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subjects/subjects-dialog.html',
                    controller: 'SubjectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subjects', null, { reload: 'subjects' });
                }, function() {
                    $state.go('subjects');
                });
            }]
        })
        .state('subjects.edit', {
            parent: 'subjects',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subjects/subjects-dialog.html',
                    controller: 'SubjectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subjects', function(Subjects) {
                            return Subjects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subjects', null, { reload: 'subjects' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subjects.delete', {
            parent: 'subjects',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subjects/subjects-delete-dialog.html',
                    controller: 'SubjectsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Subjects', function(Subjects) {
                            return Subjects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subjects', null, { reload: 'subjects' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
