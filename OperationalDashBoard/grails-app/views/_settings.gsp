<%--
  Created by IntelliJ IDEA.
  User: E055756
  Date: 6/29/2015
  Time: 9:50 AM
--%>

<!-- Modal -->
<div id="settingsModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Settings</h4>
            </div>
            <div class="modal-body">
                <h5>Upload Focus Customers List</h5>
                <g:formRemote name="myForm" enctype="multipart/form-data" url="[controller: 'ODCustomer', action: 'uploadFile']">

                        <input type="file" name="customerFile" />
                        <input type="text" name="id"/>


                        <g:submitButton name="uploadFile" value="Upload" />

                </g:formRemote>
                <h5>Upload Person to Owner Group List</h5>
                <g:formRemote name="myForm" url="[controller: 'ODPerson', action: 'uploadFile']" onSuccess="createUserTable(data)">
                    <fieldset class="form">
                        <input type="file" name="ownerFile" />
                    </fieldset>
                    <fieldset class="buttons">
                        <g:submitButton name="uploadFile" value="Upload" />
                    </fieldset>
                </g:formRemote>
            </span>
                <h5>Thresholds</h5>
                <h6>Number of days open with no worklog:</h6>
                <input min="1" max="100" id="daysWithNoWorkLog" type="number">
                <h6>Number of days open with worklog:</h6>
                <input min="1" max="100" id="daysWithWorkLog" type="number">
                <h6>Highest Number of tickets:</h6>
                <input min="1" max="100" id="ticketsThreshold" type="number">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default submit" data-dismiss="modal">Save</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>
