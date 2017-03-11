(function() {
    'use strict';

    angular
        .module('omitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('degrees', {
            parent: 'entity',
            url: '/degrees',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omitApp.degrees.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/degrees/degrees.html',
                    controller: 'DegreesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('degrees');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('degrees-detail', {
            parent: 'degrees',
            url: '/degrees/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omitApp.degrees.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/degrees/degrees-detail.html',
                    controller: 'DegreesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('degrees');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Degrees', function($stateParams, Degrees) {
                    return Degrees.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'degrees',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('degrees-detail.edit', {
            parent: 'degrees-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/degrees/degrees-dialog.html',
                    controller: 'DegreesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Degrees', function(Degrees) {
                            return Degrees.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('degrees.new', {
            parent: 'degrees',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/degrees/degrees-dialog.html',
                    controller: 'DegreesDialogController',
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
                    $state.go('degrees', null, { reload: 'degrees' });
                }, function() {
                    $state.go('degrees');
                });
            }]
        })
        .state('degrees.edit', {
            parent: 'degrees',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/degrees/degrees-dialog.html',
                    controller: 'DegreesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Degrees', function(Degrees) {
                            return Degrees.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('degrees', null, { reload: 'degrees' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('degrees.delete', {
            parent: 'degrees',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/degrees/degrees-delete-dialog.html',
                    controller: 'DegreesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Degrees', function(Degrees) {
                            return Degrees.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('degrees', null, { reload: 'degrees' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
