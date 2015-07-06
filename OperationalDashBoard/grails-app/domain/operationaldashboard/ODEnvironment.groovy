package operationaldashboard

class ODEnvironment {
    String name
    static hasMany = [activities: ODActivities]
    static constraints = {
        name blank: false, unique: true
    }
}
