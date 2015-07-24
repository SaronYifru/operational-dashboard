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
        reportDataUpdaterService.updateReports()
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
        return [prodSummary: [customerRequestSummary: prodCustomerRequestSummary,
         customerRequestedIssueSummary: prodCustomerRequestedIssueSummary], mtfSummary:
         [customerRequestedIssueSummary: mtfCustomerRequestedIssueSummary,
         customerRequestSummary: mtfCustomerRequestSummary], unknownSummary:[
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
        return [prodSummary: [customerRequestSummary: prodCustomerRequestSummary,
                              customerRequestedIssueSummary: prodCustomerRequestedIssueSummary], mtfSummary:
                        [customerRequestedIssueSummary: mtfCustomerRequestedIssueSummary,
                         customerRequestSummary: mtfCustomerRequestSummary], unknownSummary:[
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
            log.info(type.name)
            def threshold = ODThreshold.findByType(type).thresholdValue
            def typeAlertWorklog =className.countByRequestTypeAndWorklogsIsNotNullAndNumberOfDaysOpenGreaterThanEquals(type.id, threshold)
            def typeAlertNoWorklog = className.countByRequestTypeAndWorklogsIsNullAndNumberOfDaysOpenGreaterThanEquals(type.id, threshold)
            if (typeAlertWorklog > 0 || typeAlertNoWorklog > 0) {
                alerts.add([freq: [worklog: typeAlertWorklog, noWorklog: typeAlertNoWorklog], State: type.name, Id: type.id])
                log.info(" tickets by request type: " + typeAlertNoWorklog)
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
//        this is going to be a database uploaded value
        def threshold = ODThreshold.findByType(null).thresholdValue
        List customersAboveThreshold = []
        for (customer in customers) {


//            def act = ODActivities.findAllByCustomer("6687 - KEYBANK NATIONAL ASSOCIATION - RPS")
//            String c= "6687 - KEYBANK NATIONAL ASSOCIATION - RPS"
            int numberOfTickets
            if (relatedRecord) {
                numberOfTickets = className.countByCustomerAndEnvAndRelatedIncidentNotIsNull(customer, environment)
                //activitiesService.getCustomerAct(customer.name)
            }
            else {
                numberOfTickets = className.countByCustomerAndEnvAndRelatedIncidentIsNull(customer, environment)

            }
//            log.info(ODActivities.countByCustomer("6687 - KEYBANK NATIONAL ASSOCIATION - RPS"))
            log.info(customer.name + " customer")
            log.info(numberOfTickets)
            if (numberOfTickets > highestNumber) {
                highestNumber = numberOfTickets
                highestCustomer = customer
            }
           if (numberOfTickets >= threshold) {
                log.info("Customer tickets greater than threshold")
                customersAboveThreshold.add([name:customer.name,tickets: numberOfTickets] )
            }

            customerToTickets.put(customer.name, numberOfTickets)

        }
        return [customers:customers, customerToTickets:customerToTickets, highestTickets:highestNumber, highestCustomer:highestCustomer, customersAboveThreshold:customersAboveThreshold, threshold:threshold]
    }
}
