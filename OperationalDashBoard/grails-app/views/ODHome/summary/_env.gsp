<div class="panel panel-primary">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-target="#${panelHeading}${envName.substring(0, 3)}"
               href="#collapseOne"> ${envName}</a>
        </h4>

    </div>
    <div class="panel-body panel-collapse collapse" id="${panelHeading}${envName.substring(0, 3)}">
        <g:render template="/ODHome/summary/request" model="[requestSummary: summary.customerRequestSummary, requestType: 'Customer Request', panelHeading:panelHeading, env:envName]"/>
        <g:render template="/ODHome/summary/request" model="[requestSummary: summary.customerRequestedIssueSummary, requestType: 'Customer Requested Issue', panelHeading:panelHeading, env:envName]"/>
    </div>
</div>