/**
 * Created by Alejandro on 24/3/17.
 */
(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('QuestionnaireController', QuestionnaireController);

    QuestionnaireController.$inject = ['Principal','StudentsService','CommentsService', 'QuestService','$translate'];

    function QuestionnaireController(Principal, StudentsService, CommentsService, QuestService, $translate) {
        var vm = this;
        vm.currentAccount = undefined;

        vm.teachers = [];
        vm.subjects = [];
        vm.questions = [];
        vm.months = [];

        $translate(['questionnaire.months.jan','questionnaire.months.feb','questionnaire.months.mar','questionnaire.months.may',
            'questionnaire.months.apr','questionnaire.months.jun','questionnaire.months.jul','questionnaire.months.aug',
            'questionnaire.months.sep','questionnaire.months.oct','questionnaire.months.nov','questionnaire.months.dec'])
            .then(function(translations){
                vm.Jan = translations['questionnaire.months.jan'];
                vm.Feb = translations['questionnaire.months.feb'];
                vm.Mar = translations['questionnaire.months.mar'];
                vm.Apr = translations['questionnaire.months.apr'];
                vm.May = translations['questionnaire.months.may'];
                vm.Jun = translations['questionnaire.months.jun'];
                vm.Jul = translations['questionnaire.months.jul'];
                vm.Aug = translations['questionnaire.months.aug'];
                vm.Sep = translations['questionnaire.months.sep'];
                vm.Oct = translations['questionnaire.months.oct'];
                vm.Nov = translations['questionnaire.months.nov'];
                vm.Dec = translations['questionnaire.months.dec'];
                vm.months = [
                    {name:vm.Sep,value:"jan"},
                    {name:vm.Oct,value:"feb"},
                    {name:vm.Nov,value:"mar"},
                    {name:vm.Dec,value:"apr"},
                    {name:vm.Jan,value:"may"},
                    {name:vm.Feb,value:"jun"},
                    {name:vm.Mar,value:"jul"},
                    {name:vm.Apr,value:"aug"},
                    {name:vm.May,value:"sep"},
                    {name:vm.Jun,value:"oct"},
                    {name:vm.Jul,value:"nov"},
                ];
            });


        Principal.identity().then(function(account) {
            vm.currentAccount = account;
            loadSubjects();
        });

        function loadSubjects() {
            StudentsService.getSubjectsByUser(vm.currentAccount.id).then(function (data) {
                vm.subjects = data;
            }, function (error) {

            })
        };

        vm.getTeachers = function (sub) {
            var isteacher = false;
            vm.currentAccount.authorities.forEach(function (item) {
                if(item == "ROLE_TEACHER"){
                    isteacher = true;
                }
            })
            if(isteacher){
                vm.getQuestions(sub, vm.currentAccount);
            }else{
                CommentsService.getTeachersBySubject(sub.id).then(function (data) {
                    vm.teachers = data;
                }, function (error) {

                })
            }
        };

        vm.getQuestions = function (sub, tea) {
            QuestService.getQuestionsSubjTeacher(tea.id,sub.id).then(function (data) {
                vm.questions = data;
                if(vm.questions.length == 0){
                    QuestService.getQuestionsDefault().then(function (data) {
                        vm.questions = data;
                        for(var i = 0; i<vm.questions.length; i++){
                            QuestService.saveQuestionsDefaultTS(vm.questions[i].text, tea.id, sub.id);
                        }
                    }, function (error) {

                    })
                }
            }, function (error) {

            })
        };

        vm.loadInMonth = function (month) {
            //TODO: Pues la carga de las puntuaciones cuando las haya
        }
    }
})();
