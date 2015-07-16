
<div class="table-responsive" style="overflow-x: auto">
<table class="table table-striped " cellspacing="0" cellpadding="0" width="100%" id="activitiesTable">
<thead>
<tr>
<th></th>
<th><a href="#" >Ticket #</a></th>
<th ><a>Summary</a></th>
<th><a>Status</a></th>
<th><a>Priority</a></th>
<th ><a>Actual Start</a></th>
<th ><a>Person name</a></th>
<th style="white-space:nowrap;"><a>Responsible Group</a></th>
<th style="white-space:nowrap;"><a>Owner Group</a></th>
<th style="white-space:nowrap;"><a>Status Date</a></th>
<th style="white-space:nowrap;"><a>Environment</a></th>
<th style="white-space:nowrap;"><a>Customer</a></th>
<th style="white-space:nowrap;"><a>RequestType</a></th>
<th style="white-space:nowrap;"><a>Worklog</a></th>
<th style="white-space:nowrap;"><a>Alerts</a></th>
<th style="white-space:nowrap;"><a>Related Record</a></th>

</tr>
<tr>
<th></th>
<th><a>Ticket #</a></th>
<th><a>Summary</a></th>
<th><a>Status</a></th>
<th><a>Priority</a></th>
<th><a>Actual Start</a></th>
<th><a>Person name</a></th>
<th><a>Responsible Group</a></th>
<th><a>Owner Group</a></th>
<th><a>Status Date</a></th>
<th><a>Environment</a></th>
<th class="col-md-3"><a>Customer</a></th>
<th><a>RequestType</a></th>
<th><a>Worklog</a></th>
<th><a>Alerts</a></th>
<th><a>Related Record</a></th>

</tr>
</thead>
<tbody>


            <g:each in="${activities}"  var="activity">
        <tr>
            <td class="details-control" onClick="showWorklog(${activity.worklogs.toList() as grails.converters.JSON});"></td>
            <td data-toggle="modal" data-target="#myModal">${activity.ticketID}</td>
            <td >${activity.summary}</td>
            <td>${activity.status}</td>
            <td>${activity.priority}</td>
            <td>${activity.actualStart}</td>
            <td>${activity.personName}</td>
            <td>${activity.responsibleGroup}</td>
            <td>${activity.ownerGroup}</td>
            <td>${activity.statusDate}</td>
            <td>${activity.env}</td>
            <td>${activity.customer.name}</td>
            <td>${activity.requestType.name}</td>
            <td>${activity.worklogs.isEmpty()? "No" : "Yes"}</td>
            <td title="Alert Reason">${(new Date() - activity.statusDate) > 60? "Alert" : "" } <span class="glyphicon glyphicon-exclamation-sign"></span></td>

            <td><g:if test="${activity.relatedIncident != null}">
                <g:link id="${activity.relatedIncident.relatedPRB}" controller="ODProblems" action="getTicket">${activity.relatedIncident.relatedPRB}</g:link></g:if></td>
            </tr>
            </g:each>
        </tbody>

    </table>

</div>
