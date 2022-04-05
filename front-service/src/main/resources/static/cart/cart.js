angular.module('market').controller('cartController', function($scope, $http, $location, $localStorage) {
    const cartContextPath = 'http://localhost:5555/cart';
    const coreContextPath = 'http://localhost:5555/core';

    $scope.loadCart = function () {
        let path = cartContextPath + '/api/v1/cart/' + $localStorage.winterMarketGuestCartId;
        $http.get(path).then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.addToCart = function (productId) {
        let path = cartContextPath + '/api/v1/cart/' + $localStorage.winterMarketGuestCartId + '/add/' + productId;
        $http.get(path).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.removeFromCart = function (productId) {
        let path = cartContextPath + '/api/v1/cart/' + $localStorage.winterMarketGuestCartId + '/remove/' + productId;
        $http.get(path).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.excludeFromCart = function (productId) {
        let path = cartContextPath + '/api/v1/cart/' + $localStorage.winterMarketGuestCartId + '/exclude/' + productId;
        $http.get(path).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.clearCart = function () {
        let path = cartContextPath + '/api/v1/cart/' + $localStorage.winterMarketGuestCartId + '/clear';
        $http.get(path).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.createOrder = function () {
        let path = coreContextPath + '/api/v1/orders';
        $http.post(path).then(function (response) {
            alert('Заказ оформлен');
            $scope.loadCart();
        });
    }

    $scope.loadCart();
});