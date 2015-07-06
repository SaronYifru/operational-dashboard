package operationaldashboard

class ODHomeController {

    def index() {
        //log.info("Rendering Operational Dashboard!")
       // render (view:'main')
        def activities = ODActivities.list()
        [activities:activities]

    }
}
