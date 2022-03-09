angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    const contextPathCore = 'http://localhost:8189/winter';
    const contextPathCart = 'http://localhost:8190/winter-carts';

    $scope.tryToAuth = function () {
        $http.post(contextPathCore + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.winterMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
    };

    $scope.clearUser = function () {
        delete $localStorage.winterMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.winterMarketUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.authCheck = function () {
        $http.get(contextPathCore + '/auth_check').then(function (response) {
            alert(response.data.value);
        });
    };

    if ($localStorage.winterMarketUser) {
        try {
            let jwt = $localStorage.winterMarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!");
                delete $localStorage.winterMarketUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {
        }

        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.winterMarketUser.token;
    }


    $scope.loadProducts = function () {
        $http.get(contextPathCore + '/api/v1/products').then(function (response) {
            $scope.productsList = response.data;
        });
    }

    $scope.showProductInfo = function (productId) {
        $http.get(contextPathCore + '/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.deleteProductById = function (productId) {
        $http.delete(contextPathCore + '/api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.loadCart = function () {
        $http.get(contextPathCart + '/api/v1/cart').then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.addToCart = function (productId) {
        $http.get(contextPathCart + '/api/v1/cart/add/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.removeFromCart = function (productId) {
        $http.delete(contextPathCart + '/api/v1/cart/remove/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.excludeFromCart = function (productId) {
        $http.delete(contextPathCart + '/api/v1/cart/exclude/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.clearCart = function () {
        $http.get(contextPathCart + '/api/v1/cart/clear/').then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.createOrder = function () {
        $http.get(contextPathCore + "/api/v1/orders/create").then(function (response) {
            $scope.clearCart();
        });
    }

    $scope.loadProducts();
    $scope.loadCart();

});