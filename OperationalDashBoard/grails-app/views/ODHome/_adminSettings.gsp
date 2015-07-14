<ul class="nav nav-pills nav-stacked col-md-2">
    <li class="active"><a href="#customerSettings" data-toggle="pill">Customers</a></li>
    <li><a href="#thresholdSettings" data-toggle="pill">Thresholds</a></li>

</ul>
<div class="tab-content col-md-10">
    <div class="tab-pane active" id="customerSettings">
        <h4>Customers</h4>
        <table class="table table-striped " cellspacing="0" cellpadding="0" width="100%" id="customersTable">
            <thead>
            <tr>
                <th>Focus Flag</th>
                <th>Customer</th>
            </tr>
            <tr>
                <g:formRemote name="customerForm"
                              url="[controller: 'ODCustomer', action: 'addCustomer',
                                    ]">


                <div class="row">

                    <div class="form-group col-xs-1">
                        <label><input type="checkbox" name="focusFlag">Focus</label>
                    </div>
                    <div class="form-group col-xs-9">
                         <input class="form-control" name="customerName" placeholder="Enter New Customer" id="pwd">
                    </div>


                <div class="form-group col-xs-1">
                    <button type="submit" class="btn btn-success">Add</button>
                </div>
            </div>
                </g:formRemote>
          </tr>
            </thead>
            <tbody>

            <g:each in="${customers}" var="customer">
                <tr>
                   <td>
                       <g:checkBox name="focusFlag" value="${customer.focusFlag}" checked="${customer?.focusFlag == true}" onclick="${remoteFunction(controller:'ODCustomer', action:'SetFocusFlag', id:"${customer.id}",
                               params:'\'focusFlag=\' + this.checked')}"/>

                   </td>
                    <td>${customer.name}</td>
                </tr>
            </g:each>
            </tbody>
            </table>

    </div>
    <div class="tab-pane" id="thresholdSettings">
        <h4>Thresholds</h4>
        <table class="table table-striped " cellspacing="0" cellpadding="0" width="100%" id="thresholdsTable">
            <thead>
            <tr>
                <th>Request Type</th>
                <th>Attribute</th>
                <th>Value</th>
            </tr>

            </thead>
            <tbody>

            <g:each in="${thresholds}" var="threshold">
                <tr>
                    <g:if test="${threshold.type == null}">
                        <td>All Request Types</td>
                    </g:if>
                    <g:else><td>${threshold.type.name}</td></g:else>

                    <td>${threshold.attribute}</td>
                    <td><a href="#" class="firstname" id="firstname" data-type="text" data-pk="1" data-placement="right"
                           data-title="Enter Threshold" class="editable editable-click editable-empty" style="display: inline;" data-url="../ODThreshold">${threshold.thresholdValue}</a></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

</div><!-- tab content -->
<script>


    $.fn.editable.defaults.mode = 'inline';
    $(document).ready(function() {
        $('.firstname').editable();
    });
</script>