package operationaldashboard

class ODActivitiesController {
    def index() {
        def activities = ODActivities.list()
        def requestType = ODRequestType.list()
        log.info(activities.get(2).worklogs.toList())

        [activities:activities, requestType:requestType, customerName:null ]


    }
    def getCustomersActivities() {
        String name = params.id

        def activities = ODActivities.findAllByCustomer("6687 - KEYBANK NATIONAL ASSOCIATION - RPS")
        render(view: "index", model: [activities:activities, customerName:name])
    }
}
