
            <div class="panel panel-default" id="panel1">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-target="#${panelHeading}"
                           href="#collapseOne"> ${panelHeading} </a>
                    </h4>

                </div>
                <div class="panel-body panel-collapse collapse" id="${panelHeading}">
                    <h4 class="panel-title">
                        <g:link controller="OD${panelHeading}">All ${panelHeading}</g:link>
                    </h4>
                    <g:render template="/ODHome/env" model="[summary: summary.prodSummary, envName:'Production', panelHeading:panelHeading]"/>
                    <g:render template="/ODHome/env" model="[summary: summary.mtfSummary, envName:'MTF']"/>
                    <g:render template="/ODHome/env" model="[summary: summary.unknownSummary, envName:'Unknown']"/>
                </div>
            </div>
