<g:each in="${requestType}" var="type">
<div class="row placeholders">
    <div class="col-xs-6 col-sm-6 placeholder text-center">
        <img
                src="C:/Users/e055756/Documents/project 1/test_images/bar_graph.gif"
                class="center-block img-responsive img-circle"
                alt="Generic placeholder thumbnail">
        <h4>${alerts.get(type).typeAlertWorklog}</h4>
        <h5>Number of Tickets Open for X days</h5>
        <span class="text-muted">Tickets With Worklog</span>
    </div>
    <div class="col-xs-6 col-sm-6 placeholder text-center">
        <h4>${alerts.get(type).typeAlertNoWorklog}</h4>
        <h5>Number of Tickets open for Y days</h5>
        <span class="text-muted">Tickets With No Worklog</span>
    </div>
</div>
</g:each>