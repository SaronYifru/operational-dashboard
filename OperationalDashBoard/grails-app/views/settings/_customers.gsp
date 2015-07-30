
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
                    <td class="form-group col-sm-7">
                        <input class="form-control" name="customerName" placeholder="Enter New Customer">
                    </td>
                    <td class="col-sm-2"></td>
                    <td class="form-group col-sm-1">
                        <button type="submit" class="btn btn-success">Add</button>
                    </td>
                </div>
            </g:formRemote>
        </tr>
        <tr>
            <th>Focus Flag</th>
            <th>Customer</th>
            <th>Attribute</th>
            <th>Value</th>
        </tr>
        </thead>
        <tbody>
        <div class="row">
            <g:render template="../ODCustomer/customerTableRows" model="[customers: customers]"/>
        </div>
        </tbody>
    </table>

</div>