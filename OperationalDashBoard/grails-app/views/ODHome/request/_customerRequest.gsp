<div class="row placeholders">
    <h4>Customer Request</h4>
    <div class="col-xs-6 col-sm-4 placeholder text-center">


        <h4>Focus Customers</h4>
        <ul>
            <g:each in="${customerRequestSummary.customers}" var="customer">
            <li>${customer.name}: ${customerRequestSummary.customerToTickets.get(customer.name)} Tickets</li>
            </g:each>
        </ul>

        <span class="text-muted">Something else</span>
    </div>
    <div class="col-xs-6 col-sm-4 placeholder text-center">
        <h4>Highest If 1</h4>
        <ul>
            <li>${customerRequestSummary.highestCustomer.name}: ${customerRequestSummary.highestTickets} Tickets</li>
        </ul>
        <span class="text-muted">Something else</span>
    </div>
    <div class="col-xs-6 col-sm-4 placeholder text-center">
        <h4>Threshold (X Tickets)</h4>
        <ul>
            <g:each in="${customerRequestSummary.customersAboveThreshold}" var="customer">
            <li> ${customer.getAt("name")}: ${customer.getAt("tickets")} Tickets</li>
            </g:each>
        </ul>

        <span class="text-muted">Something else</span>
    </div>
</div>