<%--
  Created by IntelliJ IDEA.
  User: E055756
  Date: 6/29/2015
  Time: 8:41 AM
--%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.1/jquery.js"></script>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.1/js/bootstrap.js"></script>

    <script type="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.13/"></script>
    <link rel="import" href="./settingsModal.html">
    <asset:stylesheet src="/OperationalDashboard/homeStyle2.css"/>
    <asset:stylesheet src="/OperationalDashboard/homeStyle.css"/>
    <title>Operational Dashboard</title>
</head>

<body>
<g:render template="/sections/header"/>
    <div class="container">
        <g:render template="/ODHome/tabs"/>
    </div>
    %{--<g:render template="/sections/footer"/>--}%
    %{--</div>--}%


</body>
</html>