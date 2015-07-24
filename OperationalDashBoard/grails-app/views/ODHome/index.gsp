<%@ page import="com.mongodb.util.JSON" %>
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

    <asset:stylesheet src="/OperationalDashboard/homeStyle2.css"/>
    <asset:stylesheet src="/OperationalDashboard/homeStyle.css"/>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <title>Operational Dashboard</title>
    <style>
    body{
        width:1060px;
        margin:50px auto;
    }
    path {  stroke: #fff; }
    path:hover {  opacity:0.9; }
    rect:hover {  fill:blue; }
    .axis {  font: 10px sans-serif; }
    .legend tr{    border-bottom:1px solid grey; }
    .legend tr:first-child{    border-top:1px solid grey; }

    .axis path,
    .axis line {
        fill: none;
        stroke: #000;
        shape-rendering: crispEdges;
    }

    .x.axis path {  display: none; }
    .legend{
        margin-bottom:76px;
        display:inline-block;
        border-collapse: collapse;
        border-spacing: 0px;
    }
    .legend td{
        padding:4px 5px;
        vertical-align:bottom;
    }
    .legendFreq, .legendPerc{
        align:right;
        width:50px;
    }
    .d3-tip {
        line-height: 1;
        font-weight: bold;
        padding: 12px;
        background: rgba(0, 0, 0, 0.8);
        color: #fff;
        border-radius: 2px;
    }

    /* Creates a small triangle extender for the tooltip */
    .d3-tip:after {
        box-sizing: border-box;
        display: inline;
        font-size: 10px;
        width: 100%;
        line-height: 1;
        color: rgba(0, 0, 0, 0.8);
        content: "\25BC";
        position: absolute;
        text-align: center;
    }

    /* Style northward tooltips differently */
    .d3-tip.n:after {
        margin: -1px 0 0 0;
        top: 100%;
        left: 0;
    }
    .barLegend2 span {
        margin: 5px;
        width: 25.5%;
        display: inline-block;
        text-align: center;
        cursor: pointer;
        color: white;
    }
    .barLegend tr {
        margin: 5px;

        display: inline-block;
        text-align: center;
        cursor: pointer;
    }
    </style>



</head>

<body>
<g:render template="/sections/header"/>
    <div class="container">
        <g:render template="/ODHome/tabs" model="[actSummary: actSummary, prbSummary: prbSummary, actAlertsByRequest:actAlertsByRequest, prbAlertsByRequest:prbAlertsByRequest, requestType: requestType, customers:customers, thresholds:thresholds]"/>

    </div>


</body>
</html>