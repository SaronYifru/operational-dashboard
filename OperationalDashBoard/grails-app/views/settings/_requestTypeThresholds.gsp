<div class="tab-pane" id="thresholdSettings">
    <h3>Thresholds By Request Type</h3>
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

        <g:each in="${requestTypes}" var="type">
            <tr>
                <td><g:checkBox name="requestTypeFocusFlag" value="${type.focusFlag}"
                                        checked="${type?.focusFlag}"
                                        onclick="${remoteFunction(controller: 'ODRequestType', action: 'setFocusFlag', id: "${type.id}",
                                                params: '\'requestTypeFocusFlag=\' + this.checked')}"/></td>
                    <td>${type.name}</td>

                <td>${type.threshold.attribute}</td>
                <td>
                    <a class="thresholdValue" data-pk="" id="${type.id}"
                       class="editable editable-click editable-empty" data-type="text" data-placement="right"
                       style="display: inline;" data-url="../ODRequestType/setThreshold">${type.thresholdValue}</a>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>
</div>