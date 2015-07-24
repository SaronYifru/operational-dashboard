<%--
  Created by IntelliJ IDEA.
  User: E055756
  Date: 7/14/2015
  Time: 3:50 PM
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
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    %{--<!-- Latest compiled and minified JavaScript -->--}%


    <script
            src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.1/js/bootstrap.js"></script>

    <script type="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.13/"></script>
    <asset:stylesheet src="/OperationalDashboard/homeStyle2.css"/>
    <asset:stylesheet src="/OperationalDashboard/homeStyle.css"/>
    <asset:stylesheet src="/OperationalDashboard/dataTable.css"/>
    <asset:stylesheet src="/OperationalDashboard/columnReorder.css"/>
    <asset:stylesheet src="/OperationalDashboard/jquery-ui.css"/>

    %{--<asset:javascript src="expandableTable.js"/>--}%
    <asset:stylesheet src="/OperationalDashboard/expandableTable.css"/>
    <asset:javascript src="columnFilter.js"/>
    <asset:javascript src="columnFilter.js"/>
    <asset:javascript src="dataTablesResponsive.js"/>
    <asset:javascript src="columnResize.js"/>
    <asset:javascript src="columnVis.js"/>
    <asset:javascript src="datatables-bootstrap.js"/>
    <asset:stylesheet src="/OperationalDashboard/columnVis.css"/>
    <asset:stylesheet src="/OperationalDashboard/dataTablesResponsive.css"/>
    <asset:stylesheet src="/OperationalDashboard/datatables-jquery-ui.css"/>
    <asset:stylesheet src="/OperationalDashboard/datatables-bootstrap.css"/>
    <asset:javascript src="jquery-datatable.js"/>
    <asset:javascript src="jquery-ui.js"/>

    <title>OPD - Activities</title>
</head>

<body>
<script>
    function format ( d ) {
        // `d` is the original data object for the row

        var t = '<div class="slider">'+
                '<table class="table table-striped">'+
                '<thead>' +
                '<tr>' +
                '<th>Date</th>' +
                '<th>Created By</th>'  +
                '<th>Summary</th>' +
                '</tr>' +
                '</thead>' +
                '<tbody>'
        for (var i = 0; i < d.length; i++) {

            t = t + '<tr>'+
                    '<td>' + d[i].createdDate + '</td>'+
                    '<td>' + d[i].createdBy + '</td>'+

                    '<td>' + d[i].summary +'</td>'+
                    '</tr>'
        }
        t = t.concat('</tbody>' +
                '</table>' + '</div>') ;

        return t
    }

    var table
    var worklogsData
    function showWorklog(worklogs) {
        worklogsData = worklogs
    }
    $(document).ready(function () {
        table = $('#problemsTable').dataTable({

                "jQueryUI": true,
            "autoWidth": true,
            "dom": 'Rlfrtip',
            "dom": 'C<"clear">RZlfrtip',
            "colResize": {
                "tableWidthFixed": false
            },
            "order": [[3, "desc"]],
            "scrollX": true,
            "scrollY": $(window).height()*58/100,
            "scrollCollapse": true,
            "paging": true

        }).columnFilter({
            sPlaceHolder: "head:after",
            aoColumns: [  null, { type: "text"  },

                { type: "text"  },
                { type: "select"  },
                { type: "select"  },
                { type: "text"  },
                {type: "text"},
                { type: "text"},
                { type: "select"},
                { type: "select"},
                { type: "text"  },
                { type: "select"},
                { type: "select"},
                { type: "select"},
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
            }



        });

        $('#problemsTable tbody').on('click', 'td.details-control', function () {
            var tr =$(this).closest('tr');
            var row = table.api().row( tr );
            console.log(worklogsData)
            if ( row.child.isShown() ) {
                // This row is already open - close it
                $('div.slider', row.child()).slideUp( function () {
                    row.child.hide();
                    tr.removeClass('shown');
                })
            }
            else {

                row.child( format(worklogsData), 'no-padding' ).show();
                tr.addClass('shown');

                $('div.slider', row.child()).slideDown();
            }
        })
        $("#problemsTable").tabs( {
            "activate": function(event, ui) {
                $( $.fn.dataTable.tables( true ) ).DataTable().columns.adjust();
            }
        } );

    });


</script>

<g:render template="/sections/header"/>
<div class="container-fluid">
    <h1>Problems</h1>
    <g:if test="${customerName != null}">
        <h3>${customerName}</h3>
        <span> <g:link controller="ODProblems">All Problems</g:link></span>
    </g:if>
    <g:render template="problemsDetails" model="[problems: problems]"/>
</div>

%{--<g:render template="/sections/footer"/>--}%
%{--</div>--}%


</body>

</html>