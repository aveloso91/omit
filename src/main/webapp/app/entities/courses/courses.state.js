(function() {
    'use strict';

    angular
        .module('omitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('courses', {
            parent: 'entity',
            url: '/courses',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omitApp.courses.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/courses/courses.html',
                    controller: 'CoursesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('courses');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('courses-detail', {
            parent: 'courses',
            url: '/courses/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omitApp.courses.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/courses/courses-detail.html',
                    controller: 'CoursesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('courses');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Courses', function($stateParams, Courses) {
                    return Courses.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'courses',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('courses-detail.edit', {
            parent: 'courses-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/courses/courses-dialog.html',
                    controller: 'CoursesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Courses', function(Courses) {
                            return Courses.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('courses.new', {
            parent: 'courses',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/courses/courses-dialog.html',
                    controller: 'CoursesDialogController',
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
                    $state.go('courses', null, { reload: 'courses' });
                }, function() {
                    $state.go('courses');
                });
            }]
        })
        .state('courses.edit', {
            parent: 'courses',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/courses/courses-dialog.html',
                    controller: 'CoursesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Courses', function(Courses) {
                            return Courses.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('courses', null, { reload: 'courses' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('courses.delete', {
            parent: 'courses',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/courses/courses-delete-dialog.html',
                    controller: 'CoursesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Courses', function(Courses) {
                            return Courses.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('courses', null, { reload: 'courses' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
