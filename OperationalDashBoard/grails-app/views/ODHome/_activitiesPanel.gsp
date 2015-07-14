
            <div class="panel panel-default" id="panel1">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-target="#collapseAct"
                           href="#collapseOne"> ${panelHeading} </a>
                    </h4>

                </div>
                <div class="panel-body panel-collapse collapse" id="collapseAct">
                    <h4 class="panel-title">
                        <g:link controller="ODActivities">All ${panelHeading}</g:link>
                    </h4>
                    <g:render template="/ODHome/env" model="[summary: actSummary.prodSummary, envName:'Production']"/>
                    <g:render template="/ODHome/env" model="[summary: actSummary.mtfSummary, envName:'MTF']"/>
                    <g:render template="/ODHome/env" model="[summary: actSummary.unknownSummary, envName:'Unknown']"/>
                </div>
            </div>
