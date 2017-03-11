'use strict';

describe('Controller Tests', function() {

    describe('Subjects Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSubjects, MockCourses, MockUsers_Subjects, MockQuestions;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSubjects = jasmine.createSpy('MockSubjects');
            MockCourses = jasmine.createSpy('MockCourses');
            MockUsers_Subjects = jasmine.createSpy('MockUsers_Subjects');
            MockQuestions = jasmine.createSpy('MockQuestions');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Subjects': MockSubjects,
                'Courses': MockCourses,
                'Users_Subjects': MockUsers_Subjects,
                'Questions': MockQuestions
            };
            createController = function() {
                $injector.get('$controller')("SubjectsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omitApp:subjectsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
