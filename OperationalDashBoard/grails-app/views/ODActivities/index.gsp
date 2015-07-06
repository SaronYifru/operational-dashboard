<%--
  Created by IntelliJ IDEA.
  User: E055756
  Date: 7/2/2015
  Time: 8:42 AM
--%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>

    <asset:javascript src="dataTable.js"/>
    <asset:javascript src="columnReorder.js"/>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    %{--<!-- Latest compiled and minified JavaScript -->--}%


    <script
            src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.1/js/bootstrap.js"></script>

    <script type="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.13/"></script>
    <link rel="import" href="./settingsModal.html">
    <asset:stylesheet src="/OperationalDashboard/homeStyle2.css"/>
    <asset:stylesheet src="/OperationalDashboard/homeStyle.css"/>
    <asset:stylesheet src="/OperationalDashboard/dataTable.css"/>
    <asset:stylesheet src="/OperationalDashboard/columnReorder.css"/>
    <asset:javascript src="expandableTable.js"/>
    <asset:stylesheet src="/OperationalDashboard/expandableTable.css"/>
    <asset:javascript src="columnFilter.js"/>

    <title>OPD - Activities</title>
</head>

<body>
<g:javascript>
    $(document).ready(function () {
        $('#activitiesTable').dataTable({
            "order": [[0, "desc"]],
            "scrollX": true,

            "dom": 'Rlfrtip'
        }).columnFilter({
            aoColumns: [ { type: "text"  },
                null,
                null,
                null,
                null,
                { type: "select"},
                { type: "select"},
                { type: "select"},
               null,
                { type: "select"},
                { type: "select"},
                { type: "select"},
                { type: "select"},
                { type: "select"},
                { type: "text"}

            ]

        });
        //$("#activitiesTable").resizableColumns({
        //    store: window.store
        //});

});

</g:javascript>

<g:render template="/sections/header"/>
<div class="container">
    <h1>Activities</h1>
    <g:render template="filters"/>
    <g:render template="activitiesDetails" model="[activities: activities]"/>
</div>
<h2>Testing subversion</h2>
%{--<g:render template="/sections/footer"/>--}%
%{--</div>--}%


</body>

</html>