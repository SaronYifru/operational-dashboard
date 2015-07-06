
<div class="table-responsive">
    <table class="table table-striped display table-expandable" cellspacing="0" width="100%" id="activitiesTable">
        <thead>
        <tr>
            <th><a href="#" >Ticket #</a></th>
            <th><a>Summary</a></th>
            <th><a>Status</a></th>
            <th><a>Priority</a></th>
            <th><a>Actual Start</a></th>
            <th><a>Person name</a></th>
            <th><a>Responsible Group</a></th>
            <th><a>Owner Group</a></th>
            <th><a>Status Date</a></th>
            <th><a>Environment</a></th>
            <th><a>Customer</a></th>
            <th><a>RequestType</a></th>
            <th><a>Worklog</a></th>
            <th><a>Alerts</a></th>
            <th><a>Related Record</a></th>
        </tr>

        </thead>
        <tfoot>
        <tr>
            <th>Ticket #</th>
            <th>Summary</th>
            <th>Status</th>
            <th>Priority</th>
            <th>Actual Start</th>
            <th>Person name</th>
            <th>Responsible Group</th>
            <th>Owner Group</th>
            <th>Status Date</th>
            <th>Environment</th>
            <th>Customer</th>
            <th>RequestType</th>
            <th>Worklog</th>
            <th>Alerts</th>
            <th><a>Related Record</a></th>
        </tr>

        </tfoot>
        <tbody>
            <g:each in="${activities}"  var="activity">
        <tr>
            %{--<td data-toggle="modal" data-target="#myModal" ng-click="selectedTicket = index" ng-bind="activity.ticket"></td>--}%
            <td data-toggle="modal" data-target="#myModal">${activity.ticketID}</td>
            <td data-resizable-column-id="summary">${activity.summary}</td>
            <td>${activity.status}</td>
            <td>${activity.priority}</td>
            <td>${activity.actualStart}</td>
            <td>${activity.personName}</td>
            <td>${activity.responsibleGroup}</td>
            <td>${activity.ownerGroup}</td>
            <td>${activity.statusDate}</td>
            <td>${activity.env}</td>
            <td>${activity.customer}</td>
            <td>${activity.requestType}</td>
            <td>${activity.worklogs.isEmpty()? "No" : "Yes"}</td>
            <td title="Alert Reason">${(new Date() - activity.statusDate) > 60? "Alert" : "" } <span class="glyphicon glyphicon-exclamation-sign"></span></td>
            <td><a href="#openProblemTicket">${activity.relatedRecord}</a></td>


        </tr>

        </g:each>
        </tbody>

    </table>
</div>
