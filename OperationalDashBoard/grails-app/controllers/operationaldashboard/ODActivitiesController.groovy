package operationaldashboard

class ODActivitiesController {
    def index() {
        def activities = ODActivities.list()
        def requestType = ODRequestType.list()
        [activities:activities, requestType:requestType ]


    }
}
