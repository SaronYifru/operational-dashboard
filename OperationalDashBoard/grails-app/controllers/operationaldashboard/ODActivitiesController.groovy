package operationaldashboard

class ODActivitiesController {
    def index() {
        def activities = ODActivities.list()
        def requestType = ODRequestType.list()
        log.info(activities.get(2).worklogs.toList())

        [activities:activities, requestType:requestType ]


    }
}
