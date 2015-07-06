/**
 * Created by E055756 on 7/6/2015.
 */
function fnFormatDetails(table_id, html) {
    var sOut = "<table id=\"exampleTable_" + table_id + "\">";
    sOut += html;
    sOut += "</table>";
    return sOut;
}



////////////////////////////////////////////////////////////

var iTableCounter = 1;
var oTable;
var oInnerTable;
var detailsTableHtml;

//Run On HTML Build
$(document).ready(function () {

    // you would probably be using templates here
    detailsTableHtml = $("#detailsTable").html();

    //Insert a 'details' column to the table
    var nCloneTh = document.createElement('th');
    var nCloneTd = document.createElement('td');
    nCloneTd.innerHTML = '<img src="http://i.imgur.com/SD7Dz.png">';
    nCloneTd.className = "center";

    $('#activitiesTable thead tr').each(function () {
        this.insertBefore(nCloneTh, this.childNodes[0]);
    });

    $('#activitiesTable tbody tr').each(function () {
        this.insertBefore(nCloneTd.cloneNode(true), this.childNodes[0]);
    });



    /* Add event listener for opening and closing details
     * Note that the indicator for showing which row is open is not controlled by DataTables,
     * rather it is done here
     */
    $('#activitiesTable tbody td img').live('click', function () {
        var nTr = $(this).parents('tr')[0];
        var nTds = this;

        if (oTable.fnIsOpen(nTr)) {
            /* This row is already open - close it */
            this.src = "http://i.imgur.com/SD7Dz.png";
            oTable.fnClose(nTr);
        }
        else {
            /* Open this row */
            var rowIndex = oTable.fnGetPosition( $(nTds).closest('tr')[0] );
            var detailsRowData = newRowData[rowIndex].details;

            this.src = "http://i.imgur.com/d4ICC.png";
            oTable.fnOpen(nTr, fnFormatDetails(iTableCounter, detailsTableHtml), 'details');
            oInnerTable = $("#detailsTable").dataTable({
                "bJQueryUI": true,
                "bFilter": false,
                "aaData": detailsRowData,


                "bPaginate": false,

                "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
                    var imgLink = aData['pic'];
                    var imgTag = '<img width="100px" src="' + imgLink + '"/>';
                    $('td:eq(0)', nRow).html(imgTag);
                    return nRow;
                }
            });
            iTableCounter = iTableCounter + 1;
        }
    });


});
