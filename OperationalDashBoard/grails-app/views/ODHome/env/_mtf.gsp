<div class="panel panel-primary">
<div class="panel-heading">
    <h4 class="panel-title">
        <a data-toggle="collapse" data-target="#collapseActMtf"
           href="#collapseOne"> MTF </a>
    </h4>

</div>
<div class="panel-body panel-collapse collapse" id="collapseActMtf">
    <g:render template="/ODHome/request/customerRequest" model="[customerRequestSummary: summary.mtfCustomerRequestSummary]"/>
    <g:render template="/ODHome/request/customerRequestedIssue" model="[customerRequestSummary: summary.mtfCustomerRequestSummary]"/>
</div>
</div>