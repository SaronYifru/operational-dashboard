package operationaldashboard

import grails.converters.JSON

class ODActivitiesController {
    def index() {
        def activities = ODActivities.list()
        def requestType = ODRequestType.list()
        log.info(activities.get(2).worklogs.toList())

        [activities:activities, requestType:requestType, customerName:null]


    }
    def getCustomersActivities() {
        ODCustomer customer = ODCustomer.findById(params.id)
        def activities = ODActivities.findAllByCustomer(customer)
        render(view: "index", model: [activities:activities, customerName:customer.name])
    }

}
