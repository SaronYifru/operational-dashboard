/**
 * Created by E055756 on 7/2/2015.
 */
//var app = angular.module('dashboardApp', []);
//
//app.controller('mainCtrl', function ($scope, $http) {
//
//
//    $scope.initializeFilters = function () {
//        $scope.filterCustomer = "All Customers";
//        $scope.filterRequest = "All Requests";
//        $scope.filterEnv = "All Environments";
//        $scope.filterDaysOpen = "All Open";
//        $scope.filterWorklog = "Worklog & W/o worklog";
//
//    };
//    $scope.initializeFilters();
//
//    // create the list of activities
//    $scope.activities = activities
//    $scope.getLogs = function (worklogID) {
//            $http.get('/operationaldashboard/ODWorklog', {params: {_id: worklogID}}).
//                success(function (data) {
//
//                    $scope.logs = data;
//                }).error(function (data) {
//
//                    $scope.logs = data;
//                });
//        };
//
//    }
//    $scope.worklogID = 0;
//    $scope.logs = [];
//    $scope.selectedTicketNumber = $scope.activities[$scope.selectedTicket].ticket;
//});
//
