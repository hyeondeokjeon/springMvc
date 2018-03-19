
var angular_app = angular.module('angularApp', []);
angular_app.controller('submitController', function($scope, $http) {

    $scope.angular_handleSubmit = function(form){
        var url = '/form/create';

        var formData = new FormData();
        formData.append("deal_srl", $scope.deal_srl);
        formData.append("keyword", $scope.keyword);
        formData.append("keyword_org", $scope.keyword_org);


        $http
            .post(url, formData,
                {
                    headers: {'Content-Type': undefined },
                    transformResponse: angular_app.id
                }
            )
            .then(function(response){
                $scope.angular_message = response.data;
            })
            .catch(function(error) {
                if (error.data) {
                    $scope.angular_message = 'error code : ' + error.status;
                }
            })
    };
    $scope.angular_message = "result";
    $scope.deal_srl = "123";
    $scope.keyword = "aa";
    $scope.keyword_org = "aa";
});