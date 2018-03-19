<%--
  Created by IntelliJ IDEA.
  User: hyeondeok
  Date: 2018. 3. 13.
  Time: 오전 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page trimDirectiveWhitespaces="true" %>--%>

<html>
<head>
    <title>form test</title>
    <link rel="stylesheet" href="/static/table.css"/>
    <link rel="stylesheet" href="/static/bootstrap-3.3.7/css/bootstrap.min.css"/>
    <script src="/static/jquery-3.3.1.min.js"></script>
    <script src="/static/bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script src="/static/vue.js"></script>
    <script src="/static/axios.min.js"></script>
    <script src="/static/angular.min.js"></script>
</head>
<body class="container">
<div id="vueApp">

    <table class="table table-bordered">
        <thead>
        <tr>
            <th class="col-md-1">deal_srl</th>
            <th class="col-md-4">keyword</th>
            <th class="col-md-4">keyword_org</th>
            <th class="col-md-2">regdate</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in result.dealList">
            <td>{{ item.deal_srl }}</td>
            <td>{{ item.keyword }}</td>
            <td>{{ item.keyword_org }}</td>
            <td>{{ item.regdate }}</td>
        </tr>
        </tbody>
    </table>

    <div class="text-center">
        <template v-if="result.currentPage > 1">
            <a @click="getDealItems(1, result.count)">[처음]</a>
            <a @click="getDealItems(result.currentPage - 1, result.count)">[이전]</a>
        </template>
        <template v-for="pageNum in 9">
            <template v-if="getIndex(pageNum) > 0 && getIndex(pageNum) == result.currentPage"> {{getIndex(pageNum)}} </template>
            <a v-else-if="getIndex(pageNum) > 0 && getIndex(pageNum) <= result.totalPage" @click="getDealItems(getIndex(pageNum), result.count)"> {{getIndex(pageNum)}}</a>
        </template>
        <template v-if="result.currentPage < result.totalPage">
            <a @click="getDealItems(result.currentPage + 1, result.count)">[다음]</a>
            <a @click="getDealItems(result.totalPage, result.count)">[ 끝 ]</a>
        </template>
    </div>

    <div id="vueForm">
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".vue-form">
            vue.js Form
        </button>

        <div class="modal fade vue-form" tabindex="-1" role="dialog" aria-labelledby="vue.js form" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        {{ message }}
                        <form class="form-inline" @submit.prevent="handleSubmit">
                            <input class="form-control" type="text" @input="typing" v-model="deal_srl" placeholder="deal_srl field">
                            <input class="form-control" type="text" @input="typing" v-model="keyword" placeholder="keyword field">
                            <input class="form-control" type="text" @input="typing" v-model="keyword_org" placeholder="keyword_org field">
                            <button type="submit" class="btn btn-primary">Submit</button>
                            <button type="submit" class="btn btn-default" data-dismiss="modal">Close</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <br/>
    </div>
</div>
<div ng-app="angularApp" ng-controller="submitController">
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".angular-form">
        angular.js Form
    </button>
    <div class="modal fade angular-form" tabindex="-1" role="dialog" aria-labelledby="angular.js form" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    {{ angular_message }}
                        <form class="form-inline" ng-submit="angular_handleSubmit($element.action)">
                            <input class="form-control" type="text" ng-model="deal_srl" placeholder="deal_srl field">
                            <input class="form-control" type="text" ng-model="keyword" placeholder="keyword field">
                            <input class="form-control" type="text" ng-model="keyword_org" placeholder="keyword_org field">
                            <button type="submit" class="btn btn-primary">Submit</button>
                            <button type="submit" class="btn btn-default" data-dismiss="modal">Close</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/scripts/myVue.js"></script>


<script type="text/javascript" src="/static/scripts/myAngular.js"></script>


</body>
</html>
