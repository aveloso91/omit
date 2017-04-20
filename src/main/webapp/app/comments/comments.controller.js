/**
 * Created by Alejandro on 24/3/17.
 */
(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('CommentsController', CommentsController);

    CommentsController.$inject = ['Users_Subjects','Principal', 'ParseLinks','AlertService', 'paginationConstants', 'User','CommentsService','$translate'];

    function CommentsController(Users_Subjects, Principal, ParseLinks, AlertService, paginationConstants, User, CommentsService, $translate) {


        $translate(['comments.errors.notsubject','comments.errors.notteacher','comments.errors.notcomment','comments.successSent']).then(function (translations) {
            vm.MSG_ERROR_SUBJECT = translations['comments.errors.notsubject'];
            vm.MSG_ERROR_TEACHER =translations['comments.errors.notteacher'];
            vm.MSG_ERROR_COMMENT =translations['comments.errors.notcomment'];
            vm.MSG_SUCCESS_SENT =translations['comments.successSent'];
        });

        var vm = this;

        vm.teachers = [];
        vm.users_subjects = [];
        vm.subjects_of_current_user = [];
        vm.page = 0;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.predicate = 'id';
        vm.reverse = true;
        vm.links = {
            last: 0
        };

        vm.subject = undefined;

        vm.potencialTeacher = {};

        vm.currentAccount = null;

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        loadAll_U_S();

        function loadAll_U_S () {
            Users_Subjects.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.users_subjects.push(data[i]);
                    if(data[i].user.id == vm.currentAccount.id){
                        vm.subjects_of_current_user.push(data[i].subject);
                    }
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        };

        vm.getTeachers = function (sub) {
            CommentsService.getTeachersBySubject(sub.id).then(function (data) {
                vm.teachers = data;
            }, function (error) {
            })
        };

        vm.sendComment = function (subj, teach, comment) {
            if(subj != undefined){
                if(teach != undefined){
                    if(comment != undefined && comment != ""){

                        AlertService.success(vm.MSG_SUCCESS_SENT);

                    }else AlertService.error(vm.MSG_ERROR_COMMENT);
                }else AlertService.error(vm.MSG_ERROR_TEACHER);
            }else AlertService.error(vm.MSG_ERROR_SUBJECT);
        };
    }
})();
