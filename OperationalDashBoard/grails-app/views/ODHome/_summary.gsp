
        <div class="panel-group" id="accordion">
            <g:render template="/ODHome/activitiesPanel" model="[summary: actSummary, panelHeading: 'Activities']"/>
            <g:render template="/ODHome/activitiesPanel" model="[summary: prbSummary, panelHeading: 'Problems']"/>
        </div>