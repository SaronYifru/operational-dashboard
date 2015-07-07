
            <div class="panel panel-default" id="panel1">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-target="#collapseAct"
                           href="#collapseOne"> Activities </a>
                    </h4>

                </div>
                <div class="panel-body panel-collapse collapse" id="collapseAct">
                    <h4 class="panel-title">
                        <a href="./activitiesDetailed.html">All Activities</a>
                    </h4>
                    <g:render template="/ODHome/env/prod" model="[summary: actSummary.prodSummary]"/>
                    <g:render template="/ODHome/env/mtf" model="[summary: actSummary.mtfSummary]"/>
                    <g:render template="/ODHome/env/unknown" model="[summary: actSummary.unknownSummary]"/>
                </div>
            </div>
