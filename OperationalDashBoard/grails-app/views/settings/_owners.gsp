<div class="tab-pane" id="owners">
    <h3>Owners</h3>
    <table class="table table-striped " cellspacing="0" cellpadding="0" width="100%" id="ownersTable">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
        </tr>

        </thead>
        <tbody>

        <g:each in="${owners}" var="owner">
            <tr>
                <td>${owner.eID}</td>
                <td>
                    <a class="ownerName" data-pk="" id="${owner.id}" class="editable editable-click editable-empty"
                       data-type="text" data-placement="right"
                       style="display: inline;" data-url="../ODOwner/setOwnerName">${owner.name}</a></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>