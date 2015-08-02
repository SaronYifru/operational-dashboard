<%@ page import="java.text.DateFormat" %>
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
            <th><a>Number of Days Open</a></th>
            <th ><a>Owner Name</a></th>
            <th style="white-space:nowrap;"><a>Responsible Group</a></th>
            <th style="white-space:nowrap;"><a>Owner Group</a></th>
            <th style="white-space:nowrap;"><a>Target Finish</a></th>
            <th style="white-space:nowrap;"><a>Environment</a></th>
            <th style="white-space:nowrap;"><a>Customer</a></th>
            <th style="white-space:nowrap;"><a>RequestType</a></th>
            <th style="white-space:nowrap;"><a>Component</a></th>
            <th style="white-space:nowrap;"><a>Access</a></th>
            <th style="white-space:nowrap;"><a>Other</a></th>
            <th style="white-space:nowrap;"><a>Break Fix</a></th>
            <th style="white-space:nowrap;"><a>Worklog</a></th>
            %{--<th style="white-space:nowrap;"><a>Alerts</a></th>--}%
            <th style="white-space:nowrap;"><a>Related Record</a></th>

        </tr>
        <tr>
            <th></th>
            <th><a>Ticket #</a></th>
            <th><a>Summary</a></th>
            <th><a>Status</a></th>
            <th><a>Priority</a></th>
            <th><a>Reported Date</a></th>
            <th><a>Number of Days Open</a></th>
            <th><a>Owner</a></th>
            <th><a>Responsible Group</a></th>
            <th><a>Owner Group</a></th>
            <th><a>Target Finish</a></th>
            <th><a>Environment</a></th>
            <th class="col-md-3"><a>Customer</a></th>
            <th><a>RequestType</a></th>
            <th><a>Component</a></th>
            <th><a>Access</a></th>
            <th><a>Other</a></th>
            <th><a>Break Fix</a></th>
            <th><a>Worklog</a></th>
            <th><a>Related Record</a></th>

        </tr>
        </thead>
        <tbody>


        <g:each in="${problems}"  var="problem">
            <tr>
                <td class="details-control" onClick="showWorklog(${problem.worklogs.toList() as grails.converters.JSON}, '${problem.ticketID}');"></td>
                <td data-toggle="modal" data-target="#myModal">${problem.ticketID}</td>
                <td >${problem.summary}</td>
                <td>${problem.status}</td>
                <td>${problem.priority}</td>
                <td>${DateFormat.instance.format(problem.reportedDate)}</td>
                <td>${problem.numberOfDaysOpen}</td>
                <td><g:if test="${problem.owner != null}"><g:if test="${problem.owner.name != null}">
                    ${problem.owner.name}</g:if><g:else>${problem.owner.eID}</g:else></g:if></td>
                <td>${problem.responsibleGroup}</td>
                <td>${problem.ownerGroup}</td>
                <td>g><g:if test="${problem.targetFinish != null}">
                    ${DateFormat.instance.format(problem.targetFinish)}</g:if></td>
                <td>${problem.env}</td>
                <td>${problem.customer.name}</td>
                <td>${problem.requestType.name}</td>
                <td><g:if test="${problem.component != null}">${problem.component.value}</g:if></td>
                <td><g:if test="${problem.access != null}">${problem.access.value}</g:if></td>
                <td><g:if test="${problem.other != null}">${problem.other.value}</g:if></td>
                <td><g:if test="${problem.breakFix != null}">${problem.breakFix.value}</g:if></td>
                <td>${problem.worklogs.isEmpty()? "No" : "Yes"}</td>
                %{--<td title="Alert Reason">${problem.numberOfDaysOpen > 60? "Alert" : "" } <span class="glyphicon glyphicon-exclamation-sign"></span></td>--}%
                <td><g:if test="${problem.relatedIncident != null}">
                            <g:link id="${problem.relatedIncident.relatedACT}" controller="ODActivities" action="getTicket">${problem.relatedIncident.relatedACT}</g:link>
                    </g:if>
                </td>
            </tr>

        </g:each>
        </tbody>

    </table>

</div>
