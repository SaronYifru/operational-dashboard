package operationaldashboard

class ODHomeController {
    def customerService
    def reportDataUpdaterService
    def progressService
    def index() {

       def actSummary = loadACTSummary()
        def prbSummary = loadPRBSummary()
        def requestType = ODRequestType.list()


      [actSummary: actSummary, prbSummary: prbSummary, actAlertsByRequest: getAlerts(ODActivities), prbAlertsByRequest:getAlerts(ODProblems), requestType:requestType, customers:ODCustomer.list(), thresholds:ODThreshold.list()]

    }
    def updateData() {
        reportDataUpdaterService.deleteCurrentAndupdateReports()
       redirect(action: 'index')
    }
    def getJson() {
        def actSummary = loadACTSummary()

        render (actSummary)  as grails.converters.JSON
    }
    def loadACTSummary() {
        //Finish this
        def prodCustomerRequestSummary = getCustomersSummary("Production Servers", false, ODActivities)
        def prodCustomerRequestedIssueSummary = getCustomersSummary("Production Servers", true, ODActivities)
        def mtfCustomerRequestedIssueSummary = getCustomersSummary("MTF", true, ODActivities)
        def mtfCustomerRequestSummary = getCustomersSummary("MTF", false, ODActivities)
        def unknownCustomerRequestedIssueSummary = getCustomersSummary(null, true, ODActivities)
        def unknownCustomerRequestSummary = getCustomersSummary(null, false, ODActivities)
        return [prodSummary: [env: "Production Servers", customerRequestSummary: prodCustomerRequestSummary,
         customerRequestedIssueSummary: prodCustomerRequestedIssueSummary], mtfSummary:
         [env: "MTF", customerRequestedIssueSummary: mtfCustomerRequestedIssueSummary,
         customerRequestSummary: mtfCustomerRequestSummary], unknownSummary:[env:"Unknown",
         customerRequestedIssueSummary: unknownCustomerRequestedIssueSummary,
         customerRequestSummary: unknownCustomerRequestSummary]]

    }
    def loadPRBSummary() {
        def prodCustomerRequestSummary = getCustomersSummary("Production Servers", false, ODProblems)
        def prodCustomerRequestedIssueSummary = getCustomersSummary("Production Servers", true, ODProblems)
        def mtfCustomerRequestedIssueSummary = getCustomersSummary("MTF", true, ODProblems)
        def mtfCustomerRequestSummary = getCustomersSummary("MTF", false, ODProblems)
        def unknownCustomerRequestedIssueSummary = getCustomersSummary(null, true, ODProblems)
        def unknownCustomerRequestSummary = getCustomersSummary(null, false, ODProblems)
        return [prodSummary: [env: "Production Servers", customerRequestSummary: prodCustomerRequestSummary,
                              customerRequestedIssueSummary: prodCustomerRequestedIssueSummary], mtfSummary:
                        [env: "MTF", customerRequestedIssueSummary: mtfCustomerRequestedIssueSummary,
                         customerRequestSummary: mtfCustomerRequestSummary], unknownSummary:[env: "Unknown",
                customerRequestedIssueSummary: unknownCustomerRequestedIssueSummary,
                customerRequestSummary: unknownCustomerRequestSummary]]
    }
    def getAlerts(Class className) {
        def requestTypes = ODRequestType.findAllByFocusFlag(true)
        if (requestTypes.size() == 0) {
            requestTypes = ODRequestType.list()
        }
        def alerts= []
        //These will be uploadable by user
        for (type in requestTypes) {
            def thresholdValue = type.thresholdValue
            def typeAlertWorklog =className.countByRequestTypeAndWorklogsIsNotNullAndNumberOfDaysOpenGreaterThanEquals(type.id, thresholdValue)
            def typeAlertNoWorklog = className.countByRequestTypeAndWorklogsIsNullAndNumberOfDaysOpenGreaterThanEquals(type.id, thresholdValue)
            if (typeAlertWorklog > 0 || typeAlertNoWorklog > 0) {
                alerts.add([freq: [worklog: typeAlertWorklog, noWorklog: typeAlertNoWorklog], State: type.name, Id: type.id, reportType: className.name])
            }

        }
       alerts as grails.converters.JSON
    }
    def getCustomersSummary(String environment, boolean relatedRecord, Class className) {
        def customers = ODCustomer.findAllByFocusFlag(true)
        if(customers.size() == 0) {
            customers = ODCustomer.list()
        }
        def customerToTickets = new HashMap<String, Integer>()
        def highestNumber = 0
        def highestCustomer = customers.get(0)
        List customersAboveThreshold = []
        for (customer in customers) {
            def thresholdValue = customer.thresholdValue
            int numberOfTickets
            if (relatedRecord) {
                numberOfTickets = className.countByCustomerAndEnvAndRelatedIncidentIsNotNull(customer, environment)
            }
            else {
                numberOfTickets = className.countByCustomerAndEnvAndRelatedIncidentIsNull(customer, environment)

            }
            if (numberOfTickets > highestNumber) {
                highestNumber = numberOfTickets
                highestCustomer = customer
            }
           if (numberOfTickets >= thresholdValue) {
                customersAboveThreshold.add([id: customer.id, name:customer.name,tickets: numberOfTickets] )
            }

            customerToTickets.put(customer.name, numberOfTickets)

        }
        return [customers:customers, customerToTickets:customerToTickets, highestTickets:highestNumber, highestCustomer:highestCustomer, customersAboveThreshold:customersAboveThreshold]
    }
}
