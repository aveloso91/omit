'use strict';

describe('Controller Tests', function() {

    describe('Users_Subjects Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUsers_Subjects, MockSubjects, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUsers_Subjects = jasmine.createSpy('MockUsers_Subjects');
            MockSubjects = jasmine.createSpy('MockSubjects');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Users_Subjects': MockUsers_Subjects,
                'Subjects': MockSubjects,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Users_SubjectsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omitApp:users_SubjectsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
