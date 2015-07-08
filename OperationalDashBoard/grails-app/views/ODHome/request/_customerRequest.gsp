<div class="row placeholders">
    <h4>Customer Request</h4>
    <div class="col-xs-6 col-sm-4 placeholder">
        <div id="chart_div"></div>

        <h4>Focus Customers</h4>
        <ul class="list-group">
            <g:each in="${customerRequestSummary.customers}" var="customer">
            <li class="list-group-item"><span class="badge"> ${customerRequestSummary.customerToTickets.get(customer.name)}</span><g:link controller="ODActivities" action="getCustomersActivities" id="${customer.name}"> ${customer.name}</g:link></li>
            </g:each>
        </ul>

        <span class="text-muted">Something else</span>
    </div>
    <div class="col-xs-6 col-sm-4 placeholder">
        <h4>Highest If 1</h4>
        <ul class="list-group">
            <li class="list-group-item"><span class="badge">${customerRequestSummary.highestTickets}</span> <a>${customerRequestSummary.highestCustomer.name}</a></li>
        </ul>
        <span class="text-muted">Something else</span>
    </div>
    <div class="col-xs-6 col-sm-4 placeholder">
        <h4>Threshold (X Tickets)</h4>
        <ul class="list-group">
            <g:each in="${customerRequestSummary.customersAboveThreshold}" var="customer">
            <li class="list-group-item"><span class="badge"> ${customer.getAt("tickets")}</span><a>${customer.getAt("name")}</a></li>
            </g:each>
        </ul>

        <span class="text-muted">Something else</span>
    </div>
</div>