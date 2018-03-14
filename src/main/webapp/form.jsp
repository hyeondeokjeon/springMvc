<%--
  Created by IntelliJ IDEA.
  User: hyeondeok
  Date: 2018. 3. 13.
  Time: 오전 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>form test</title>
    <style>
        td { max-width: 400px;;}
    </style>

    <%--<script src="https://cdn.jsdelivr.net/npm/vue"></script>--%>
    <%--<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js"></script>--%>
    <%--<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.5/angular.min.js"></script>--%>

    <link rel="stylesheet" href="/static/bootstrap-3.3.7/css/bootstrap.min.css"/>
    <script src="/static/bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script src="/static/vue.js"></script>
    <script src="/static/axios.min.js"></script>
    <script src="/static/angular.min.js"></script>

</head>
<body>
<div id="vueApp">
    vue.js Form
    <br/>
    {{ message }}
    <form @submit.prevent="handleSubmit">
        <input type="text" @input="typing" v-model="deal_srl" placeholder="deal_srl field">
        <input type="text" @input="typing" v-model="keyword" placeholder="keyword field">
        <input type="text" @input="typing" v-model="keyword_org" placeholder="keyword_org field">
        <button type="submit">Submit</button>
    </form>
</div>
<div ng-app="angularApp" ng-controller="submitController">
    angular.js Form
    <br/>
    {{ angular_message }}
    <form ng-submit="angular_handleSubmit($element.action)">
        <input type="text" ng-model="deal_srl" placeholder="deal_srl field">
        <input type="text" ng-model="keyword" placeholder="keyword field">
        <input type="text" ng-model="keyword_org" placeholder="keyword_org field">
        <button type="submit">Submit</button>
    </form>
</div>



<table border="1">
    <thead>
    <tr>
        <th>deal_srl</th>
        <th>keyword</th>
        <th>keyword_org</th>
        <th>regdate</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${dealList}" var="deal">
        <tr>
            <td>${deal.deal_srl}</td>
            <td>${deal.keyword}</td>
            <td>${deal.keyword_org}</td>
            <td>${deal.regdate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%-- 처음--%>
<c:if test="${currentPage > 1}">
    <a href="/form/read?page=1&count=${count}">[처음]</a>
</c:if>
<%--이전--%>
<c:if test="${currentPage > 1}">
    <a href="/form/read?page=${currentPage - 1}&count=${count}">[이전]</a>
</c:if>
<%--범위 표시--%>
<c:forEach begin="${startPage}" end="${endPage}" varStatus="pageNum">
    <span>
    <c:choose>
        <c:when test="${currentPage == pageNum.index}">
            ${pageNum.index}
        </c:when>

        <c:when test="${pageNum.index > totalPage}"/>

        <c:otherwise>
            <a href="/form/read?page=${pageNum.index}&count=${count}">${pageNum.index}</a>
        </c:otherwise>
    </c:choose>
    </span>
</c:forEach>
<%--다음--%>
<c:if test="${currentPage < totalPage}">
    <a href="/form/read?page=${currentPage + 1}&count=${count}">[다음]</a>
</c:if>
<%--끝--%>
<c:if test="${currentPage < totalPage}">
    <a href="/form/read?page=${totalPage}&count=${count}">[ 끝 ]</a>
</c:if>



<script type="text/javascript">

    Vue.prototype.$http = axios;

    var app = new Vue({
        el: '#vueApp',
        data: {
            message: 'result',
            deal_srl: '123',
            keyword: 'aa',
            keyword_org: 'bb',
            results: []
        },
        methods:{
            typing: function(e){
                this.text = e.target.value
            },
            handleSubmit: function(){
                this.$http.post('/form/create',
                    'deal_srl=' + this.$data.deal_srl +
                    '&keyword=' + this.$data.keyword +
                    '&keyword_org=' + this.$data.keyword_org,
                    {
                        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' }
                    }
                )
                .then(function(response){
                    app.$data.message = response.data;
                })
                .catch(function(error) {
                    if (error.response) {
                        app.$data.message = 'error code : ' + error.response.status;
                    } else if (error.request) {
                        app.$data.message = error.request;
                    }
                })
            }
        }
    });
</script>



<script type="text/javascript">
    var angular_app = angular.module('angularApp', []);
    angular_app.controller('submitController', function($scope, $http) {

        $scope.angular_handleSubmit = function(form){
            var url = '/form/create';

            $http
                    .post(url,
                            'deal_srl=' + $scope.deal_srl +
                            '&keyword=' + $scope.keyword +
                            '&keyword_org=' + $scope.keyword_org,
                            {
                                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
                                transformResponse: undefined
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
</script>


</body>
</html>
