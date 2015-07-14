<ul class="nav nav-pills nav-stacked col-md-2">
    <li class="active"><a href="#customerSettings" data-toggle="pill">Customers</a></li>
    <li><a href="#thresholdSettings" data-toggle="pill">Thresholds</a></li>

</ul>
<div class="tab-content col-md-10">
    <div class="tab-pane active" id="customerSettings">
        <h4>Customers</h4>
        <ul class="list-group">
            <g:each in="${customers}" var="customer">
                <li class="list-group-item"><input type="checkbox" class="style1" checked="${customer.focusFlag}"/>${customer.name}</li>
            </g:each>
        </ul>
    </div>
    <div class="tab-pane" id="thresholdSettings">
        <h4>Pane B</h4>
        <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames
        ac turpis egestas.</p>
    </div>

</div><!-- tab content -->
<script>
    $('input[type="checkbox"].style1').checkbox({
        buttonStyle: 'btn-link btn-large',
        buttonStyleChecked: 'btn-success',
        checkedClass: 'icon-check',
        uncheckedClass: 'icon-check-empty'
    });
</script>