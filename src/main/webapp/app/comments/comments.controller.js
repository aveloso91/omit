/**
 * Created by Alejandro on 24/3/17.
 */
(function() {
    'use strict';

    angular
        .module('omitApp')
        .controller('CommentsController', CommentsController);

    CommentsController.$inject = [];

    function CommentsController() {
        var vm = this;
    }
})();
