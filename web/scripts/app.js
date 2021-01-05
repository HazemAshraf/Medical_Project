'use strict';

var app = angular.module('angNewsApp', ['ngImageCompress']);

app.controller('demoCtrl', function($scope){
	$scope.$watch('image1.compressed.dataURL', function(nVal, oVal) {
    if (nVal) {
      console.log('image1.compressed.dataURL', nVal);
      $scope.watchedImgData = nVal;
    }
  });
});