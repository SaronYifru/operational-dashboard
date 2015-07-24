
<nav class=" row navbar navbar-default navbar-fixed-top row" role="banner">
    <div class="col-sm-12">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse"
                    data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <g:link controller="ODHome"><div class="navbar-brand"><span class="glyphicon glyphicon-home"></span> Operational Dashboard</div></g:link>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li><g:link controller="Settings"><i class="glyphicon glyphicon-wrench"></i>Admin Settings</g:link></li>
        </ul>

        %{--<g:img uri="/images/operationaldashboard/mastercardLogo.png" width="40" height="40"/>--}%
    </div>
</nav>
<g:render template="/settings"/>

