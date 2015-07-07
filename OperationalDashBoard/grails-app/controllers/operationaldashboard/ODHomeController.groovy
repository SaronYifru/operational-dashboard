package operationaldashboard

import com.mongodb.util.JSON

class ODHomeController {

    def index() {
        //log.info("Rendering Operational Dashboard!")
       // render (view:'main')
        def actSummary = loadACTSummary()
//        log.info(actSummary.prodSummary.prodCustomerRequestSummary.customerToTickets)
        [summary: [actSummary:actSummary, prbSummary: null], alerts: null]

    }
    def loadACTSummary() {
        //Finish this
        def prodCustomerRequestSummary = getCustomersACTSummary("Production Servers", false)
        def prodCustomerRequestedIssueSummary = getCustomersACTSummary("Production Servers", true)
        def mtfCustomerRequestedIssueSummary = getCustomersACTSummary("MTF", true)
        def mtfCustomerRequestSummary = getCustomersACTSummary("MTF", false)
        def unknownCustomerRequestedIssueSummary = getCustomersACTSummary("", true)
        def unknownCustomerRequestSummary = getCustomersACTSummary("", false)
        return [prodSummary: [prodCustomerRequestSummary: prodCustomerRequestSummary,
         prodCustomerRequestedIssueSummary: prodCustomerRequestedIssueSummary], mtfSummary:
         [mtfCustomerRequestedIssueSummary: mtfCustomerRequestedIssueSummary,
         mtfCustomerRequestSummary: mtfCustomerRequestSummary], unknownSummary:[
         unknownCustomerRequestedIssueSummary: unknownCustomerRequestedIssueSummary,
         unknownCustomerRequestSummary: unknownCustomerRequestSummary]]

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


            def act = ODActivities.findAllByCustomer("6687 - KEYBANK NATIONAL ASSOCIATION - RPS")
            int numberOfTickets = act.size()
            log.info(customer.name + " customer")
            log.info(numberOfTickets)
            if (numberOfTickets > highestNumber) {
                highestNumber = numberOfTickets
                highestCustomer = customer
            }
            else if (numberOfTickets > threshold) {
                customersAboveThreshold.add([name:customer.name,tickets: numberOfTickets] )
            }

            customerToTickets.put(customer.name, numberOfTickets)

        }
        return [customers:customers, customerToTickets:customerToTickets, highestTickets:highestNumber, highestCustomer:highestCustomer, customersAboveThreshold:customersAboveThreshold]
    }
}
