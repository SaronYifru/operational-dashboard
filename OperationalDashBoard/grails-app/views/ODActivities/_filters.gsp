<%--
  Created by IntelliJ IDEA.
  User: E055756
  Date: 6/29/2015
  Time: 1:46 PM
--%>
<div class="page-header" ng-init="initializeFilters()">
    <h2 class="inline">Filters:</h2>
    <!--Filter fields-->

    <span class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="menu1" data-toggle="dropdown">{{filterCustomer}}
            <span class="caret"></span></button>
        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
            <li> <a role="menuitem" tabindex="-1" href="#">All Customers</a></li>
            <li> <a role="menuitem" tabindex="-1" href="#">All Critical Care Customers</a></li>
            <li data-ng-repeat="customer in criticalCustomersList"> <a role="menuitem" tabindex="-1" href="#">{{customer.name}}<span data-ng-class="isChecked(company.id)"></span></a>
            </li>
        </ul>
    </span>
    <span class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="menu2" data-toggle="dropdown">{{filterRequest}}
            <span class="caret"></span></button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu">
            <li><a role="menuitem" tabindex="-1" href="#">All Request Types</a></li>
            <li data-ng-repeat="type in requestTypesList"> <a data-ng-click="setSelectedClient()">{{type.name}} Request<span data-ng-class="isChecked(company.id)"></span></a>
            </li>
        </ul>
    </span>
    <span class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="menu3" data-toggle="dropdown">{{filterEnv}}
            <span class="caret"></span></button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu">
            <li><a role="menuitem" tabindex="-1" href="#">All Environments</a></li>
            <li data-ng-repeat="environment in environmentsList">
                <a role="menuitem" tabindex="-1" href="#">{{environment.name}}</a>
            </li>
        </ul>
    </span>
    <span class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="menu4" data-toggle="dropdown">{{filterDaysOpen}}
            <span class="caret"></span></button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu">
            <li> <a role="menuitem" tabindex="-1" href="#">All Open</a></li>
            <li data-ng-repeat="day in daysList"> <a  role="menuitem" tabindex="-2" href="#" data-ng-click="setSelectedClient()">Open for {{day}} days<span data-ng-class="isChecked(company.id)"></span></a>

            </li>
        </ul>
    </span>
    <span class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="menu5" data-toggle="dropdown">{{filterWorklog}}
            <span class="caret"></span></button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu">
            <li> <a role="menuitem" tabindex="-1" href="#">Worklog & W/o worklog</li>
            <li> <a role="menuitem" tabindex="-2" href="#">Worklog</li>
            <li> <a role="menuitem" tabindex="-3" href="#">No Worklog</li>
        </ul>
    </span>
</div>