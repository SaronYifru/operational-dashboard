
<nav class="navbar navbar-default navbar-fixed-top row" role="banner">
    <div class="container-fluid">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse"
                    data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <g:link controller="ODHome">   <span class="navbar-brand"><span
                    class="glyphicon glyphicon-home"></span> Operational Dashboard</span> </g:link>


        </div>
        <nav class="collapse navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <li class="dropdown"><a id="settingsMenu" data-toggle="dropdown" > <span
                        class="glyphicon glyphicon-cog"></span>Settings</a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="settingsMenu">
                        <li role="presentation"><a data-toggle="modal" data-target="#settingsModal" role="menuitem" tabindex="-1" href="#">Upload Focus Customers</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Upload Request Types</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Upload Person to Owner Group</a></li>

                    </ul>
                </li>

            </ul>
        </nav>
        %{--<g:img uri="/images/operationaldashboard/mastercardLogo.png" width="40" height="40"/>--}%
    </div>
</nav>
<g:render template="/settings"/>

