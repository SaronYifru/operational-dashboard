
        <div class="panel-group" id="accordion">
            <g:render template="/ODHome/summary/reportPanel" model="[summary: actSummary, panelHeading: 'Activities']"/>
            <g:render template="/ODHome/summary/reportPanel" model="[summary: prbSummary, panelHeading: 'Problems']"/>
        </div>