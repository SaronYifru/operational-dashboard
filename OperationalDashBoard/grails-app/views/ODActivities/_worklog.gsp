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
                    <table class="table table-striped"  id="worklog">
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Created By</th>
                            <th>Summary</th>
                        </tr>
                        </thead>
                        <tbody>>
                        <g:each in="${worklogs}"  var="log">
                        <tr>
                            <td>${log.createdDate}</td>
                            <td>${log.createdBy}</td>
                            <td>${log.summary}</td>
                        </tr>
                           </g:each>
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