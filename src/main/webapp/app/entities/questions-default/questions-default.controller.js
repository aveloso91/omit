(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('Questions_DefaultController', Questions_DefaultController);

    Questions_DefaultController.$inject = ['Questions_Default'];

    function Questions_DefaultController(Questions_Default) {

        var vm = this;

        vm.questions_Defaults = [];

        loadAll();

        function loadAll() {
            Questions_Default.query(function(result) {
                vm.questions_Defaults = result;
                vm.searchQuery = null;
            });
        }
    }
})();
