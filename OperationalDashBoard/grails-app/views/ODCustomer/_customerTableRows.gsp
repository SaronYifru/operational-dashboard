<g:each in="${customers}" var="customer">
    <tr>
        <td>
            <g:checkBox name="customerFocusFlag" value="${customer?.focusFlag}" checked="${customer?.focusFlag}" onclick="${remoteFunction(controller:'ODCustomer', action:'setFocusFlag', id:"${customer.id}",
                    params:'\'customerFocusFlag=\' + this.checked')}"/>

        </td>
        <td>${customer.name}</td>
        <td>${customer.threshold.attribute}</td>
        <td>
            <a class="thresholdValue" data-pk="" id="${customer.id}"
               class="editable editable-click editable-empty" data-type="text" data-placement="right"
               style="display: inline;" data-url="../ODCustomer/setThreshold">${customer.thresholdValue}</a>
        </td>


    </tr>
</g:each>
