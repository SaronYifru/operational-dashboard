<g:each in="${customers}" var="customer">
    <tr>
        <td>
            <g:checkBox name="customerFocusFlag" value="${customer?.focusFlag}" checked="${customer?.focusFlag}" onclick="${remoteFunction(controller:'ODCustomer', action:'setFocusFlag', id:"${customer.id}",
                    params:'\'customerFocusFlag=\' + this.checked')}"/>

        </td>
        <td>${customer.name}</td>

    </tr>
</g:each>
