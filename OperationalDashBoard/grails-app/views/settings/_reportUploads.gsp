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
                <iframe id="actUploadTrigger" name="actUploadTrigger" height="30px" frameborder="0"
                        scrolling="no"></iframe>
            </span>
        </div>

        <div class="content scaffold-create" role="main">
            <span class="">
                <h3>Problems Data</h3>
                <g:uploadForm controller="ODProblems" action="doUpload" target="prbUploadTrigger">
                    <input id="prbFile" name="prbFile" type="file" class="file" data-show-preview="false">

                    <div id="#errorPrbBlock" class="help-block"></div>
                </g:uploadForm>
                <iframe id="prbUploadTrigger" name="prbUploadTrigger" height="30px" frameborder="0"
                        scrolling="no"></iframe>
            </span>
        </div>

        <div class="content scaffold-create" role="main">
            <span class="">
                <h3>Incidents Data</h3>
                <g:uploadForm controller="ODIncidents" action="doUpload" target="incUploadTrigger">
                    <input id="incFile" name="incFile" type="file" class="file" data-show-preview="false">

                    <div id="#errorIncBlock" class="help-block"></div>
                </g:uploadForm>

                <iframe id="incUploadTrigger" name="incUploadTrigger" height="30px" frameborder="0"
                        scrolling="no"></iframe>
            </span>
        </div>

        <div class="row">
            <g:link name="applyChanges" id="applyChanges" onclick="showSpinner()" controller="ODHome" class="btn btn-success  col-sm-offset-4"
                    action="updateData">Apply Data Changes</g:link>
            <img id="spinner" style="display:none;" src="${createLinkTo(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/>

        </div>
    </div>
</div>
<script>
    function showSpinner() {
        document.getElementById('spinner').style.display = 'inline';
        $(this).unbind('click');
    }

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
</script>