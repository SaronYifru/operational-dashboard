package operationaldashboard

import com.mongodb.util.JSON

class ODHomeController {
    def activitiesService
    def index() {

       def actSummary = loadACTSummary()
        def requestType = ODRequestType.list()


      [actSummary: actSummary, alertsByRequest: getAlerts(), requestType:requestType, customers:ODCustomer.list()]

    }
    def getJson() {
        def actSummary = loadACTSummary()

        render (actSummary)  as grails.converters.JSON
    }
    def loadACTSummary() {
        //Finish this
        def prodCustomerRequestSummary = getCustomersACTSummary("Production Servers", false)
        def prodCustomerRequestedIssueSummary = getCustomersACTSummary("Production Servers", true)
        def mtfCustomerRequestedIssueSummary = getCustomersACTSummary("MTF", true)
        def mtfCustomerRequestSummary = getCustomersACTSummary("MTF", false)
        def unknownCustomerRequestedIssueSummary = getCustomersACTSummary(null, true)
        def unknownCustomerRequestSummary = getCustomersACTSummary(null, false)
        return [prodSummary: [customerRequestSummary: prodCustomerRequestSummary,
         customerRequestedIssueSummary: prodCustomerRequestedIssueSummary], mtfSummary:
         [customerRequestedIssueSummary: mtfCustomerRequestedIssueSummary,
         customerRequestSummary: mtfCustomerRequestSummary], unknownSummary:[
         customerRequestedIssueSummary: unknownCustomerRequestedIssueSummary,
         customerRequestSummary: unknownCustomerRequestSummary]]

    }
    def getAlerts() {
        def requestTypes = ODRequestType.list()
        def alerts= []
        //These will be uploadable by user
        for (type in requestTypes) {
            log.info(type.name)
            def typeAlertWorklog = ODActivities.countByRequestTypeAndWorklogsIsNotNull(type.id, 7)
            def typeAlertNoWorklog = ODActivities.countByRequestType(type.id, 7)
            alerts.add([freq: [worklog: typeAlertWorklog, noWorklog: typeAlertNoWorklog], State: type.name])
            log.info(" tickets by request type: " + typeAlertNoWorklog)
        }
       alerts as grails.converters.JSON
    }
    def getCustomersACTSummary(String environment, boolean relatedRecord) {
        def customers = ODCustomer.list()

        def customerToTickets = new HashMap<String, Integer>()
        def highestNumber = 0
        def highestCustomer = customers.get(0)
//        this is going to be a database uploaded value
        def threshold = 10
        List customersAboveThreshold = []
        for (customer in customers) {


//            def act = ODActivities.findAllByCustomer("6687 - KEYBANK NATIONAL ASSOCIATION - RPS")
//            String c= "6687 - KEYBANK NATIONAL ASSOCIATION - RPS"
            int numberOfTickets
            if (relatedRecord) {
                numberOfTickets = ODActivities.countByCustomerAndEnvAndRelatedRecordIsNull(customer, environment)
                //activitiesService.getCustomerAct(customer.name)
            }
            else {
                numberOfTickets = ODActivities.countByCustomerAndEnvAndRelatedRecordIsNotNull(customer, environment)

            }
//            log.info(ODActivities.countByCustomer("6687 - KEYBANK NATIONAL ASSOCIATION - RPS"))
            log.info(customer.name + " customer")
            log.info(numberOfTickets)
            if (numberOfTickets > highestNumber) {
                highestNumber = numberOfTickets
                highestCustomer = customer
            }
           if (numberOfTickets > threshold) {
                log.info("Customer tickets greater than threshold")
                customersAboveThreshold.add([name:customer.name,tickets: numberOfTickets] )
            }

            customerToTickets.put(customer.name, numberOfTickets)

        }
        return [customers:customers, customerToTickets:customerToTickets, highestTickets:highestNumber, highestCustomer:highestCustomer, customersAboveThreshold:customersAboveThreshold]
    }
}
