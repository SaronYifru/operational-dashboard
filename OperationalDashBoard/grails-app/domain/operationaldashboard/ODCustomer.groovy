package operationaldashboard

class ODCustomer {
    String name
    boolean focusFlag
    static hasMany = [activities:ODActivities, problems:ODProblems]

    static constraints = {
        name blank: false, unique: true
        focusFlag nullable: false

    }
}
