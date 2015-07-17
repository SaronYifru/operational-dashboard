<g:each in="${customers}" var="customer">
    <tr>
        <td>
            <g:checkBox name="focusFlag" value="${customer.focusFlag}" checked="${customer?.focusFlag == true}" onclick="${remoteFunction(controller:'ODCustomer', action:'SetFocusFlag', id:"${customer.id}",
                    params:'\'focusFlag=\' + this.checked')}"/>

        </td>
        <td>${customer.name}</td>
    </tr>
</g:each>