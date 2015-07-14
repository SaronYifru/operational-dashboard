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
        String name = params.id
        log.info(name)
        def activities = ODActivities.findAllByCustomer(ODCustomer.findByName(name))
        render(view: "index", model: [activities:activities, customerName:name])
    }

}
