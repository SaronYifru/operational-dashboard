///**
// * Created by E055756 on 7/14/2015.
// */
//module.controller('CustomerListCtrl', function($scope, $rootScope, $http, $location) {
//    var load = function () {
//        console.log('call load()...');
//        $http.get($rootScope.appUrl + '/books.json')
//            .success(function (data, status, headers, config) {
//                $scope.books = data;
//                angular.copy($scope.books, $scope.copy);
//            });
//    }
//
//    load();
//
//    $scope.addBook = function () {
//        console.log('call addBook');
//        $location.path("/new");
//    }
//
//    $scope.editBook = function (index) {
//        console.log('call editBook');
//        $location.path('/edit/' + $scope.books[index].id);
//    }
//}