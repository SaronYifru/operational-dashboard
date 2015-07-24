<asset:javascript src="uploadr.manifest.js"/>
<asset:stylesheet href="uploadr.manifest.css"/>
<div class="row">
<ul class="nav nav-pills nav-stacked col-sm-2">
    <li class="active"><a href="#customerSettings" data-toggle="pill">Customers</a></li>
    <li><a href="#thresholdSettings" data-toggle="pill">Thresholds</a></li>
    <li><a href="#upload" data-toggle="pill">Report Uploads</a></li>

</ul>

<div class="tab-content col-sm-8 col-sm-offset-1">
    <div class="tab-pane active" id="customerSettings">
        <h3>Customers</h3>
        <table class="table table-striped " cellspacing="0" cellpadding="0" width="100%" id="customersTable">
            <thead>

            <tr>
                <g:formRemote name="customerForm"
                              url="[controller: 'ODCustomer', action: 'addCustomer',
                                    ]" onSuccess="updateCustomersTable(data)">


                <div class="row">

                    <td class="form-group col-sm-1">
                        <label><input type="checkbox" name="focusFlag">Focus</label>
                    </td>
                    <td class="form-group col-sm-9">
                         <input class="form-control" name="customerName" placeholder="Enter New Customer">
                    </td>
                    <td class="form-group col-sm-1">
                    <button type="submit" class="btn btn-success">Add</button>
                </td>
            </div>
                </g:formRemote>
          </tr>
            <tr>
                <th>Focus Flag</th>
                <th>Customer</th>
            </tr>
            </thead>
            <tbody>
            <div class="row">
            <g:render template="../ODCustomer/customerTableRows" model="[customers:customers]" />
            </div>
            </tbody>
            </table>

    </div>
    <div class="tab-pane" id="thresholdSettings">
        <h3>Thresholds</h3>
        <table class="table table-striped " cellspacing="0" cellpadding="0" width="100%" id="thresholdsTable">
            <thead>
            <tr>
                <th>Focus Flag</th>
                <th>Request Type</th>
                <th>Attribute</th>
                <th>Value</th>
            </tr>

            </thead>
            <tbody>

            <g:each in="${thresholds}" var="threshold">
                <tr>

                    <g:if test="${threshold.type == null}">
                        <td></td>
                        <td>All Request Types</td>
                    </g:if>
                    <g:else> <td><g:checkBox name="requestTypeFocusFlag" value="${threshold.type?.focusFlag}" checked="${threshold.type?.focusFlag}" onclick="${remoteFunction(controller:'ODRequestType', action:'setFocusFlag', id:"${threshold.type.id}",
                            params:'\'requestTypeFocusFlag=\' + this.checked')}"/></td>
                        <td>${threshold.type.name}</td></g:else>

                    <td>${threshold.attribute}</td>
                    <td>
                        <a  class="thresholdValue" data-pk="" id="${threshold.id}" class="editable editable-click editable-empty" data-type="text"  data-placement="right"
                            style="display: inline;" data-url="../ODThreshold/setThreshold">${threshold.thresholdValue}</a> </td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="tab-pane" id="upload">
        <h3 class="">Report Uploads</h3>
        <div id="upload-data" class="content scaffold-create" role="main">
            <div class="content scaffold-create" role="main">
                <span class="">
                <h3>Activities Data</h3>
                <g:uploadForm controller="ODActivities" action="doUpload" target="actUploadTrigger">
                            <input id="actFile" name="actFile" type="file" class="file" data-show-preview="false">
                             <div id="#errorActBlock" class="help-block"></div>
                </g:uploadForm>
                <iframe id="actUploadTrigger" name="actUploadTrigger" height="30px" frameborder="0" scrolling="no"></iframe>
                </span>
            </div>
            <div class="content scaffold-create" role="main">
                <span class="">
                <h3>Problems Data</h3>
                <g:uploadForm  controller="ODProblems" action="doUpload" target="prbUploadTrigger">
                            <input id="prbFile" name="prbFile" type="file" class="file" data-show-preview="false">
                            <div id="#errorPrbBlock" class="help-block"></div>
                </g:uploadForm>
                <iframe id="prbUploadTrigger" name="prbUploadTrigger" height="30px" frameborder="0" scrolling="no"></iframe>
                </span>
            </div>
            <div class="content scaffold-create" role="main">
                <span class="">
                    <h3>Incidents Data</h3>
                    <g:uploadForm  controller="ODIncidents" action="doUpload" target="incUploadTrigger">
                        <input id="incFile" name="incFile" type="file" class="file" data-show-preview="false">
                        <div id="#errorIncBlock" class="help-block"></div>
                    </g:uploadForm>

                    <iframe id="incUploadTrigger" name="incUploadTrigger" height="30px" frameborder="0" scrolling="no"></iframe>
                </span>
            </div>
           <div class="row">
               <g:link name="applyChanges" controller="ODHome" class="btn btn-success  col-sm-offset-4" action="updateData">Apply Data Changes</g:link>
               <g:jprogress class="col-sm-8 col-sm-offset-1" progressId="reportUpdateProgress" trigger="applyChanges"/>
           </div>
        </div>
        </div>

</div><!-- tab content -->
</div>
<script encodeAs="none">
    $.fn.editable.defaults.mode = 'inline';
    $(document).ready(function() {
        $('.thresholdValue').editable({

        });
    });
    $("#incFile").fileinput({
        showPreview: false,
        allowedFileExtensions: ["xls", "xlsx", "csv"],
        elErrorContainer: "#errorIncBlock",
        msgInvalidFileExtension: 'Invalid extension for file {name}. Only "{extensions} files are supported.'

        // you can configure `msgErrorClass` and `msgInvalidFileExtension` as well
    });
    $("#prbFile").fileinput({
        showPreview: false,
        allowedFileExtensions: ["xls", "xlsx", "csv"],
        elErrorContainer: "#errorPrbBlock",
        msgInvalidFileExtension: 'Invalid extension for file {name}. Only "{extensions} files are supported.'

        // you can configure `msgErrorClass` and `msgInvalidFileExtension` as well
    });
    $("#actFile").fileinput({
        showPreview: false,
        allowedFileExtensions: ["xls", "xlsx", "csv"],
        elErrorContainer: "#errorActBlock",
        msgErrorClass: "help-block",
        msgInvalidFileExtension: 'Invalid extension for file {name}. Only "{extensions} files are supported.'

        // you can configure `msgErrorClass` and `msgInvalidFileExtension` as well
    });
    function updateCustomersTable(html) {
        $("#customersTable>tbody").append(html);
        var window = $(window);
        var rowCount = $('#customersTable>tbody >tr').length
        var row = $('#customersTable').find('tr').eq( rowCount );
        if (row.length){
            $('html,body').animate({scrollTop: row.offset().top - (window.height()/2)}, 1000 );
        }
    }

</script>