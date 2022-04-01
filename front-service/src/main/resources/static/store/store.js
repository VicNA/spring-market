angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const cartContextPath = 'http://localhost:5555/cart';
    const coreContextPath = 'http://localhost:5555/core';

    $scope.loadProducts = function (pageIndex = 1) {
        let path = coreContextPath + '/api/v1/products';
        $http({
            url: path,
            method: 'GET',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.productsList = response.data.content;
        });
    };

    $scope.showProductInfo = function (productId) {
        let path = coreContextPath + '/api/v1/products/' + productId;
        $http.get(path).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.addToCart = function (productId) {
        let path = cartContextPath + '/api/v1/cart/add/' + productId;
        $http.get(path).then(function (response) {
        });
    }

    $scope.loadProducts();
})