
            <div class="panel panel-default" id="panel1">
                <div class="panel-heading">
                    <h4 class="panel-primary">
                        <a data-toggle="collapse" data-target="#${panelHeading}"
                           href="#collapseOne"> ${panelHeading} </a>
                    </h4>

                </div>
                <div class="panel-body panel-collapse collapse" id="${panelHeading}">
                    <h4 class="panel-primary row">
                        <div class="col-sm-12"><g:link controller="OD${panelHeading}">View All ${panelHeading}</g:link></div>
                    </h4>
                    <g:render template="/ODHome/summary/env" model="[summary: summary.prodSummary, envName:summary.prodSummary.env, panelHeading:panelHeading]"/>
                    <g:render template="/ODHome/summary/env" model="[summary: summary.mtfSummary, envName:summary.mtfSummary.env]"/>
                    <g:render template="/ODHome/summary/env" model="[summary: summary.unknownSummary, envName:summary.unknownSummary.env]"/>
                </div>
            </div>
