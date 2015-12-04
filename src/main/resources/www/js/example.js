angular.module('app', [
  'ngResource',
  'ngRoute'
])
.config(function($routeProvider, $locationProvider){
  $routeProvider
    .when('/page1', {
      templateUrl:'ui/page1.html'
    })
    .otherwise({
      redirectTo:'/'
    });

  $locationProvider
    .html5Mode(true);
})
