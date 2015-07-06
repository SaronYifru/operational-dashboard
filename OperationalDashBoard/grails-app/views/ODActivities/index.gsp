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

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.js"></script>

    <asset:javascript src="dataTable.js"/>
    <asset:javascript src="columnReorder.js"/>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    %{--<!-- Latest compiled and minified JavaScript -->--}%


    <script
            src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.1/js/bootstrap.js"></script>

    <script type="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.13/"></script>
    <asset:stylesheet src="/OperationalDashboard/homeStyle2.css"/>
    <asset:stylesheet src="/OperationalDashboard/homeStyle.css"/>
    <asset:stylesheet src="/OperationalDashboard/dataTable.css"/>
    <asset:stylesheet src="/OperationalDashboard/columnReorder.css"/>
    <asset:javascript src="expandableTable.js"/>
    <asset:stylesheet src="/OperationalDashboard/expandableTable.css"/>
    <asset:javascript src="columnFilter.js"/>
    <asset:javascript src="columnFilter.js"/>
    <asset:javascript src="dataTablesResponsive.js"/>
    <asset:stylesheet src="/OperationalDashboard/dataTablesResponsive.css"/>

    <title>OPD - Activities</title>
</head>

<body>
<g:javascript>
    /* Formatting function for row details - modify as you need */
    %{--function format ( d ) {--}%
        %{--// `d` is the original data object for the row--}%
        %{--var data = eval(${activities})--}%
        %{--var table = '<div>'+--}%
                %{--'<table class="table table-striped display">';--}%
            %{--jQuery.each(data, function(i, worklog) {--}%
                %{--table.append(--}%
                        %{--'<tr>'+--}%
                %{--'<td>Full name:</td>'+--}%
                %{--'<td>'+worklog.summary+'</td>'+--}%

                %{--'<td>Extension number:</td>'+--}%
                %{--'<td>'+worklog.createdBy+'</td>'+--}%

                %{--'<td>' + worklog.createdDate + '</td>'+--}%
                %{--'</tr>'--}%
    %{--)});--}%
            %{--table.append('</table>'+ '</div>');--}%
    %{--}--}%
    $(document).ready(function () {
       var table = $('#activitiesTable').dataTable({
            "bJQueryUI": true,
            "order": [[0, "desc"]],
            "scrollX": true,
            "scrollY": $(window).height()*58/100,
            "scrollCollapse": true,
            "paging": false,
            "sDom": 'Rlfrtip'
        }).columnFilter({
            sPlaceHolder: "head:after",
            aoColumns: [  null, { type: "text"  },

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

            ],
            responsive: {
            details: {
                type: 'column',
                target: 'tr'
            }
        },
        columnDefs: [ {
            className: 'control',
            orderable: false,
            targets:   0
        } ],



        });


        %{--// Add event listener for opening and closing details--}%
        %{--$('#activitiesTable tbody').on('click', 'td', function () {--}%
            %{--var table = $('#activitiesTable').DataTable();--}%
            %{--var tr = $(this).closest('tr');--}%
            %{--var row = table.row( tr );--}%

            %{--if ( row.child.isShown() ) {--}%
                %{--// This row is already open - close it--}%
                %{--$('div.slider', row.child()).slideUp( function () {--}%
                    %{--row.child.hide();--}%
                    %{--tr.removeClass('shown');--}%
                %{--} );--}%
            %{--}--}%
            %{--else {--}%
                %{--// Open this row--}%
                %{--row.child( format(row.data()), 'no-padding' ).show();--}%
                %{--alert(row.data());--}%
                %{--tr.addClass('shown');--}%

                %{--$('div.slider', row.child()).slideDown();--}%
            %{--}--}%
        %{--} );--}%

});

</g:javascript>

<g:render template="/sections/header"/>
<div class="container-fluid">
    <h1>Activities</h1>
    <g:render template="activitiesDetails" model="[activities: activities]"/>
</div>
<h2>Testing subversion</h2>
%{--<g:render template="/sections/footer"/>--}%
%{--</div>--}%


</body>

</html>