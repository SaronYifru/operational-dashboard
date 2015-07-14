</br>
<ul class="nav nav-tabs row">
    <li class="active"><a data-toggle="tab" href="#alerts">Alerts</a></li>
    <li><a data-toggle="tab" href="#summary">Summary</a></li>
    <li><a data-toggle="tab" href="#adminSettings">Admin Settings</a></li>
</ul>
<div class="tab-content">
    <div class="tab-pane fade in active" id="alerts">
            <div class="row">
                 <h1 class="page-header">Alerts</h1>
                <g:render template="alerts" model="[actAlertsByRequest: actAlertsByRequest, prbAlertsByRequest: prbAlertsByRequest, requestType: requestType]"></g:render>

             </div>
    </div>
    <div class="tab-pane fade" id="summary">
            <div class="row">
                    <h1 class="page-header">Summary</h1>
                    <g:render template="/ODHome/summary" model="[actSummary: actSummary, prbSummary:prbSummary]"></g:render>
            </div>
    </div>
    <div class="tab-pane fade" id="adminSettings">
        <div class="row">
            <h1 class="page-header">Administrator Settings</h1>
            <g:render template="/ODHome/adminSettings" model="[customers: customers, thresholds:thresholds]"></g:render>
        </div>
    </div>
</div>