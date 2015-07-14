<div class="panel panel-primary">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-target="#${envName}"
               href="#collapseOne"> ${envName}</a>
        </h4>

    </div>
    <div class="panel-body panel-collapse collapse" id="${envName}"
        <g:render template="/ODHome/request" model="[requestSummary: summary.customerRequestSummary, requestType: 'Customer Request']"/>
        <g:render template="/ODHome/request" model="[requestSummary: summary.customerRequestedIssueSummary, requestType: 'Customer Requested Issue']"/>
    </div>
</div>