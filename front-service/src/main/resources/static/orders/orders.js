angular.module('market').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/core';

    $scope.loadOrders = function () {
        let path = contextPath + '/api/v1/orders'
        $http.get(path).then(function (response) {
            $scope.orders = response.data;
        });
    }

    $scope.loadOrders();
});