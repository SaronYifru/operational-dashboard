<%--
  Created by IntelliJ IDEA.
  User: e055756
  Date: 7/19/2015
  Time: 10:26 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>OPD - Admin Settings</title>
    <g:javascript library="jquery" plugin="jquery"/>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.js"></script>

    <script
            src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.1/js/bootstrap.js"></script>
    <asset:stylesheet src="/OperationalDashboard/homeStyle2.css"/>
    <link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
    <script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
    <asset:stylesheet src="/OperationalDashboard/fileUpload.css"/>
    <asset:javascript src="fileUpload.js"/>


</head>

<body>
<div class="container">
        <g:render template="/sections/header"/>
        <h1 class="page-header">Administrator Settings</h1>
        <g:render template="adminSettings" model="[customers: customers, thresholds:thresholds]"/>
</div>
</body>
</html>