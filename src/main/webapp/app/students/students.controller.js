/**
 * Created by Alejandro on 24/3/17.
 */
(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('StudentsController', StudentsController);

    StudentsController.$inject = [];

    function StudentsController() {
        var vm = this;
    }
})();
