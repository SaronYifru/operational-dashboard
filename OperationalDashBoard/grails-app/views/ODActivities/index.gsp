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
    <asset:stylesheet src="operationaldashboard/homeStyle2.css"/>
    <asset:stylesheet src="operationaldashboard/homeStyle.css"/>
    <asset:stylesheet src="operationaldashboard/dataTable.css"/>
    <asset:stylesheet src="operationaldashboard/columnReorder.css"/>
    %{--<asset:javascript src="expandableTable.js"/>--}%
    <asset:stylesheet src="operationaldashboard/expandableTable.css"/>
    <asset:stylesheet src="operationaldashboard/jquery-ui.css"/>
    <asset:javascript src="columnFilter.js"/>
    <asset:javascript src="columnFilter.js"/>
    <asset:javascript src="dataTablesResponsive.js"/>
    <asset:javascript src="columnResize.js"/>
    <asset:javascript src="columnVis.js"/>
    <asset:javascript src="datatables-bootstrap.js"/>
    <asset:stylesheet src="operationaldashboard/columnVis.css"/>
    <asset:stylesheet src="operationaldashboard/dataTablesResponsive.css"/>
    <asset:stylesheet src="operationaldashboard/datatables-jquery-ui.css"/>
    <asset:stylesheet src="operationaldashboard/expandableTable.css"/>
    <asset:javascript src="jquery-datatable.js"/>
    <asset:javascript src="jquery-ui.js"/>
    <asset:javascript src="datatableScroller.js"/>
    <asset:stylesheet src="operationaldashboard/dataTablesScroller.css"/>
    <title>OPD - Activities</title>
</head>

<body>
<script>
    function format ( d, ticket ) {
    // `d` is the original data object for the row

    var t =  '<div class="slider">'+
            '<table class="table table-striped " width="100" cellpadding="0" cellspacing="0" border="0" style="padding-left:0px; width:1000px;">'+
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
                     '<td>' + new Date(d[i].createdDate).toLocaleString() + '</td>'+
                     '<td>' + d[i].createdBy + '</td>'+
                     '<td>' + d[i].summary +'</td>'+
                 '</tr>'
            }
           var url = "https://gsm.mastercard.com/maximo/ui/maximo.jsp?event=loadapp&value=activity&additionalevent=useqbe&additionaleventvalue=wonum=" + ticket
            var additionalInfo = '<a href=' + url + '>'
                + 'View Ticket In GSM' + '</a>'
        var tFoot = '<tfoot>' + '<tr>' + '<td>' + additionalInfo + '</td>' + '</tr>' + '</tfoot>'
        t = t.concat('</tbody>' + tFoot +
                '</table>' + '</div>') ;




    return t
}

    var table
    var worklogsData
    var ticket
    function showWorklog(worklogs, ticketId) {
        worklogsData = worklogs
        ticket = ticketId
    }
    $(document).ready(function () {

       table = $('#activitiesTable').dataTable({

           "autoWidth": false,
            "dom": 'Rlfrtip',
            "dom": 'C<"clear">RZlfrtip',
           "sPaginationType" : "full_numbers",
            "colResize": {
            "tableWidthFixed": false
              },
            "order": [[3, "desc"]],
             "paging": true,
            "scrollX": true,
            "scrollY": $(window).height()*58/100,
            "scrollCollapse": true,
           "iDisplayLength" : 10,
            "paging": false,
           "deferRender": true,
           "bAutoWidth" : false,

        }).columnFilter({
            sPlaceHolder: "head:after",
            aoColumns: [  null, { type: "text"  },

                { type: "text"  },
               { type: "select"  },
                { type: "select"  },
               { type: "text"  },
                { type: "text"  },
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
        table.fnAdjustColumnSizing();


        $('#activitiesTable tbody').on('click', 'td.details-control', function () {
            var tr =$(this).closest('tr');
            var row = table.api().row( tr );
            console.log(worklogsData)
            if ( row.child.isShown() ) {
                // This row is already open - close it
                $('div.slider', row.child()).slideUp( function () {
                    row.child.hide();
                    tr.removeClass('shown');
                });
            }
            else {

                row.child( format(worklogsData, ticket), 'no-padding' ).show();
                tr.addClass('shown');

                $('div.slider', row.child()).slideDown();
            }
        })
        $("#activitiesTable").tabs( {
            "activate": function(event, ui) {
                $( $.fn.dataTable.tables( true ) ).DataTable().columns.adjust();
            }
        } );


});


</script>

<g:render template="/sections/header"/>
<div class="container-fluid">
    <h1>Activities</h1>
    <g:if test="${initialFilter != null}">
        <h3>${initialFilter}</h3>
        <span> <g:link controller="ODActivities">View All Activities</g:link></span>
    </g:if>
    <div class="row">
        <g:render template="activitiesDetails" model="[activities: activities]"/>
    </div>
</div>

%{--<g:render template="/sections/footer"/>--}%
%{--</div>--}%


</body>

</html>