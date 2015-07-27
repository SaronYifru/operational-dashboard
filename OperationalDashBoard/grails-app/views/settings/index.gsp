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
    <asset:stylesheet src="OperationalDashboard/homeStyle2.css"/>
    <link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
    <script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
    <asset:stylesheet src="OperationalDashboard/fileUpload.css"/>
    <asset:javascript src="fileUpload.js"/>
    <script encodeAs="none">
        $.fn.editable.defaults.mode = 'inline';
        $(document).ready(function() {
            $('.thresholdValue').editable();
            $('.ownerName').editable();
        });

        function updateCustomersTable(html) {
            $("#customersTable>tbody").append(html);
            var window = $(window);
            var rowCount = $('#customersTable>tbody >tr').length
            var row = $('#customersTable').find('tr').eq( rowCount );
            if (row.length){
                $('html,body').animate({scrollTop: row.offset().top - (window.height()/2)}, 1000 );
            }
        }

    </script>

</head>

<body>
<div class="container">
        <g:render template="/sections/header"/>
        <h1 class="page-header">Administrator Settings</h1>
    <div class="row">
        <ul class="nav nav-pills nav-stacked col-sm-2">
            <li class="active"><a href="#customerSettings" data-toggle="pill">Customers</a></li>
            <li><a href="#thresholdSettings" data-toggle="pill">Thresholds</a></li>
            <li><a href="#owners" data-toggle="pill">Owners</a></li>
            <li><a href="#upload" data-toggle="pill">Report Uploads</a></li>

        </ul>
        <div class="tab-content col-sm-8 col-sm-offset-1">
             <g:render template="/settings/customers" model="[customers: customers]"/>
             <g:render template="/settings/requestTypeThresholds" model="[thresholds:thresholds]"/>
             <g:render template="/settings/owners" model="[owners:owners]"/>
            <g:render template="/settings/reportUploads"/>
        </div>
    </div>
</div>
</body>
</html>