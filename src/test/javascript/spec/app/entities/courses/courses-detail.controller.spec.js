'use strict';

describe('Controller Tests', function() {

    describe('Courses Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCourses, MockDegrees, MockSubjects;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCourses = jasmine.createSpy('MockCourses');
            MockDegrees = jasmine.createSpy('MockDegrees');
            MockSubjects = jasmine.createSpy('MockSubjects');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Courses': MockCourses,
                'Degrees': MockDegrees,
                'Subjects': MockSubjects
            };
            createController = function() {
                $injector.get('$controller')("CoursesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omitApp:coursesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
