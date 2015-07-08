<g:javascript>

    google.load('visualization', '1', {packages: ['corechart', 'bar']});
    google.setOnLoadCallback(drawBasic);
    function drawBasic() {
    $.ajax({
    url:"${g.createLink(controller:"ODHome",action:"getJson")}",
    dataType: 'json',

    success: function(data) {
        var cData = data.prodSummary.customerRequest.customers
        alert(cData)
    },
    error: function(request, status, error) {

        alert(error)
    },
    complete: function() {
    }
});
%{--var customerData = [['Focus Customer', 'Number of Tickets']];--}%
%{--//var data = "${actSummary.prodSummary.prodCustomerRequestSummary.customers}".split("/");--}%

%{--//var customersToTickets = "${actSummary.prodSummary.prodCustomerRequestSummary.customerToTickets}".split("/");--}%
%{--var i = 0--}%
%{--for (customer in data) {--}%

%{--customerData.concat([customer.name, customersToTickets[0]])--}%
%{--i++--}%
%{--}--}%


%{--var options = {--}%
%{--title: 'Focus Customers and their Tickets ',--}%
%{--chartArea: {width: '50%'},--}%
%{--hAxis: {--}%
%{--title: 'Tickets',--}%
%{--minValue: 0--}%
%{--},--}%
%{--vAxis: {--}%
%{--title: 'Focus Customer'--}%

%{--}--}%
%{--};--}%

%{--var chart = new google.visualization.BarChart(document.getElementById('chart_div'));--}%

%{--chart.draw(data, options);--}%
    }
</g:javascript>