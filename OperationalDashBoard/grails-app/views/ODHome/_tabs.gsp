</br>
<div class = "row">
<ul class="nav nav-tabs col-sm-12">
    <li class="active"><a data-toggle="tab" href="#alerts">Alerts</a></li>
    <li><a data-toggle="tab" href="#summary">Summary</a></li>
</ul>
</div>
<div class="tab-content">
    <div class="tab-pane fade in active" id="alerts">
            <div class="row">
                 <h1 class="page-header col-sm-12">Alerts</h1>
                <div class="col-sm-12"><g:render template="alert/alerts" model="[actAlertsByRequest: actAlertsByRequest, prbAlertsByRequest: prbAlertsByRequest, requestType: requestType]"></g:render></div>

             </div>
    </div>
    <div class="tab-pane fade" id="summary">
            <div class="row">
                    <h1 class="page-header col-sm-12">Summary</h1>
                    <div class="col-sm-12"><g:render template="/ODHome/summary/summary" model="[actSummary: actSummary, prbSummary:prbSummary]"></g:render></div>
            </div>
    </div>
</div>