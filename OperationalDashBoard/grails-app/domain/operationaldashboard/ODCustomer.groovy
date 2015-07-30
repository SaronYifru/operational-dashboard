package operationaldashboard

class ODCustomer {
    String name
    boolean focusFlag
    ODThreshold threshold
    int thresholdValue = 0
    static hasMany = [activities:ODActivities, problems:ODProblems]

    static constraints = {
        name blank: false, unique: true
        focusFlag nullable: false

    }
}
