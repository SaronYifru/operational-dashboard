<div class="table-responsive" style="overflow-x: auto">
    <table class="table table-striped " cellspacing="0" cellpadding="0" width="100%" id="problemsTable">
        <thead>
        <tr>
            <th></th>
            <th><a href="#" >Ticket #</a></th>
            <th ><a>Summary</a></th>
            <th><a>Status</a></th>
            <th><a>Priority</a></th>
            <th ><a>Reported Date</a></th>
            <th ><a>Owner Name</a></th>
            <th style="white-space:nowrap;"><a>Responsible Group</a></th>
            <th style="white-space:nowrap;"><a>Owner Group</a></th>
            <th style="white-space:nowrap;"><a>Target Finish</a></th>
            <th style="white-space:nowrap;"><a>Environment</a></th>
            <th style="white-space:nowrap;"><a>Customer</a></th>
            <th style="white-space:nowrap;"><a>RequestType</a></th>
            <th style="white-space:nowrap;"><a>Worklog</a></th>
            <th style="white-space:nowrap;"><a>Alerts</a></th>
            %{--<th style="white-space:nowrap;"><a>Related Record</a></th>--}%

        </tr>
        <tr>
            <th></th>
            <th><a>Ticket #</a></th>
            <th><a>Summary</a></th>
            <th><a>Status</a></th>
            <th><a>Priority</a></th>
            <th><a>Reported Date</a></th>
            <th><a>Owner</a></th>
            <th><a>Responsible Group</a></th>
            <th><a>Owner Group</a></th>
            <th><a>Target Finish</a></th>
            <th><a>Environment</a></th>
            <th class="col-md-3"><a>Customer</a></th>
            <th><a>RequestType</a></th>
            <th><a>Worklog</a></th>
            <th><a>Alerts</a></th>
            <th><a>Related Record</a></th>

        </tr>
        </thead>
        <tbody>


        <g:each in="${problems}"  var="problem">
            <tr>
                <td class="details-control" onClick="showWorklog(${problem.worklogs.toList() as grails.converters.JSON});"></td>
                <td data-toggle="modal" data-target="#myModal">${problem.ticketID}</td>
                <td >${problem.summary}</td>
                <td>${problem.status}</td>
                <td>${problem.priority}</td>
                <td>${problem.reportedDate}</td>
                <td>${problem.owner}</td>
                <td>${problem.responsibleGroup}</td>
                <td>${problem.ownerGroup}</td>
                <td>${problem.targetFinish}</td>
                <td>${problem.env}</td>
                <td>${problem.customer.name}</td>
                <td>${problem.requestType.name}</td>
                <td>${problem.worklogs.isEmpty()? "No" : "Yes"}</td>
                <td title="Alert Reason">${problem.numberOfDaysOpen > 60? "Alert" : "" } <span class="glyphicon glyphicon-exclamation-sign"></span></td>
                <td><g:if test="${problem.relatedIncident != null}">
                    <g:link id="${problem.relatedIncident.relatedACT}" controller="ODActivities" action="getTicket">${problem.relatedIncident.relatedACT}</g:link></g:if></td>
            </tr>
            </tr>
        </g:each>
        </tbody>

    </table>

</div>
