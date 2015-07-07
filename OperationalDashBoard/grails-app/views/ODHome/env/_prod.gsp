<div class="panel-heading">
    <h4 class="panel-title">
        <a data-toggle="collapse" data-target="#collapseActProd"
           href="#collapseOne"> Production </a>
    </h4>

</div>
<div class="panel-body panel-collapse collapse" id="collapseActProd">

    <g:render template="/ODHome/request/customerRequest" model="[customerRequestSummary: summary.prodCustomerRequestSummary]"/>
    <g:render template="/ODHome/request/customerRequestedIssue" model="[customerRequestSummary: summary.prodCustomerRequestSummary]"/>
    %{--<g:render template="/ODHome/request/charts" model="[summary:summary.customerRequestSummary]"/>--}%
</div>
