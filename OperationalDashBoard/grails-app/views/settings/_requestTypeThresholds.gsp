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
                <g:else><td><g:checkBox name="requestTypeFocusFlag" value="${threshold.type?.focusFlag}"
                                        checked="${threshold.type?.focusFlag}"
                                        onclick="${remoteFunction(controller: 'ODRequestType', action: 'setFocusFlag', id: "${threshold.type.id}",
                                                params: '\'requestTypeFocusFlag=\' + this.checked')}"/></td>
                    <td>${threshold.type.name}</td></g:else>

                <td>${threshold.attribute}</td>
                <td>
                    <a class="thresholdValue" data-pk="" id="${threshold.id}"
                       class="editable editable-click editable-empty" data-type="text" data-placement="right"
                       style="display: inline;" data-url="../ODThreshold/setThreshold">${threshold.thresholdValue}</a>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>
</div>