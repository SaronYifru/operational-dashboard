package operationaldashboard

class ODCustomer {
    String name
    static hasMany = [activities:ODActivities, problems:ODProblems]
    static constraints = {
        name blank: false, unique: true
    }
}
