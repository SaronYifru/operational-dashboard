<h4>${requestType}</h4>
<div class="row placeholders">

    <div class="col-xs-6 col-sm-4 placeholder">
        <h4 style="text-align: center">Focus Customers</h4>
        <ul class="list-group">
            <g:each in="${requestSummary.customers}" var="customer">
                <li class="list-group-item"><span class="badge"> ${requestSummary.customerToTickets.get(customer.name)}</span><g:link controller="OD${panelHeading}" action="getCustomers${panelHeading}" id="${customer.id}"> ${customer.name}</g:link></li>
            </g:each>
        </ul>

    </div>
    <div class="col-xs-6 col-sm-4 placeholder">
        <h4 style="text-align: center">Highest If 1</h4>
        <ul class="list-group">
            <li class="list-group-item"><span class="badge">${requestSummary.highestTickets}</span><g:link controller="OD${panelHeading}" action="getCustomers${panelHeading}" id="${requestSummary.highestCustomer.id}">${requestSummary.highestCustomer.name}</g:link></li>
        </ul>

    </div>
    <div class="col-xs-6 col-sm-4 placeholder">
        <h4 style="text-align: center">Threshold (${requestSummary.threshold} Tickets)</h4>
        <ul class="list-group">
            <g:each in="${requestSummary.customersAboveThreshold}" var="customer">
                <li class="list-group-item"><span class="badge">${customer.tickets}</span><g:link controller="OD${panelHeading}" action="getCustomers${panelHeading}" id="${customer.id}"> ${customer.name}</g:link></li>
            </g:each>
        </ul>


    </div>
</div>