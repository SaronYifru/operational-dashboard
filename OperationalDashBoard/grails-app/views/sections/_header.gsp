
<nav class="navbar navbar-default navbar-fixed-top row" role="banner">
    <div class="container-fluid">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse"
                    data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a href="./dashboard2.html" class="navbar-brand"><span
                    class="glyphicon glyphicon-home"></span> Operational Dashboard</a>
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
    </div>
</nav>
<g:render template="/settings"/>

