<script>

    google.load('visualization', '1', {packages: ['corechart', 'bar']});
    google.setOnLoadCallback(drawBasic);
    function drawBasic() {
        var customerData = [['Focus Customer', 'Number of Tickets']];
        var data = ${summary.customers};
        var customersToTickets = ${summary.customerToTickets}
        for (customer in data) {
            customerData.add([customer.name, customersToTickets.get(customer.name)])
        }


        var options = {
            title: 'Focus Customers and their Tickets ',
            chartArea: {width: '50%'},
            hAxis: {
                title: 'Tickets',
                minValue: 0
            },
            vAxis: {
                title: 'Focus Customer'

            }
        };

        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));

        chart.draw(data, options);
    }
</script>