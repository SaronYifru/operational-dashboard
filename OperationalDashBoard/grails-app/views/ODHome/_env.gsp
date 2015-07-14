<div class="panel panel-primary">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-target="#${panelHeading}${envName}"
               href="#collapseOne"> ${envName}</a>
        </h4>

    </div>
    <div class="panel-body panel-collapse collapse" id="${panelHeading}${envName}"
        <g:render template="/ODHome/request" model="[requestSummary: summary.customerRequestSummary, requestType: 'Customer Request', panelHeading:panelHeading]"/>
        <g:render template="/ODHome/request" model="[requestSummary: summary.customerRequestedIssueSummary, requestType: 'Customer Requested Issue']"/>
    </div>
</div>