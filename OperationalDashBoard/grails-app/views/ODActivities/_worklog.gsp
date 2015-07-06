<%--
  Created by IntelliJ IDEA.
  User: E055756
  Date: 6/29/2015
  Time: 1:49 PM
--%>
<!-- Modal -->
%{--<div id="myModal" class="modal fade" role="dialog" >--}%
    %{--<div class="modal-dialog">--}%

        %{--<!-- Modal content-->--}%
        %{--<div class="modal-content">--}%
            %{--<div class="modal-header">--}%
                %{--<button type="button" class="close" data-dismiss="modal">&times;</button>--}%
                %{--<h4 class="modal-title">{{selectedTicketNumber}} Worklog</h4>--}%
            %{--</div>--}%
            %{--<div class="modal-body">--}%
                %{--<div class="table-responsive" id="activitiesWorklog">--}%
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Created By</th>
                            <th>Summary</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${activities}"  var="activity">
                        <tr ng-repeat="log in logs">
                            <td ng-bind="log.date"></td>
                            <td ng-bind="log.createdBy"></td>
                            <td ng-bind="log.summary"></td>
                        </tr>
                        </tbody>
                    </table>
                %{--</div>--}%
            %{--</div>--}%
            %{--<div class="modal-footer">--}%
                %{--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--}%
            %{--</div>--}%
        %{--</div>--}%

    %{--</div>--}%
%{--</div>--}%