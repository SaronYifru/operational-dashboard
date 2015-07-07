<div class="panel-heading">
    <h4 class="panel-title">
        <a data-toggle="collapse" data-target="#collapseActUnknown"
           href="#collapseOne"> Unknown </a>
    </h4>

</div>
<div class="panel-body panel-collapse collapse" id="collapseActUnknown">
    <g:render template="/ODHome/request/customerRequest" model="[customerRequestSummary: summary.unknownCustomerRequestSummary]"/>
    <g:render template="/ODHome/request/customerRequestedIssue" model="[customerRequestSummary: summary.unknownCustomerRequestSummary]"/>
</div>