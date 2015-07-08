package operationaldashboard

class ODRequestType {
    String name
    static hasMany = [activities: ODActivities]
    int worklogThreshold
    int noWorklogThreshold
    static constraints = {
        name blank: false, unique:true
        worklogThreshold blank: true, nullable:true
        noWorklogThreshold blank: true, nullable:true
    }
}
