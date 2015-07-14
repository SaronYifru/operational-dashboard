package operationaldashboard

class ODProblems {
    String ticketID
    String summary
    String status
    String priority
    Date reportedDate
    Date targetFinish
    String owner
    String responsibleGroup
    String ownerGroup
    String env
    ODCustomer customer
    ODRequestType requestType
    static hasMany = [worklogs:ODWorklog]
    int numberOfDaysOpen
    ODActivities relatedActivity
    ODIncidents relatedIncident

    static constraints = {
        ticketID unique:true
        customer blank: true
        owner blank: true, nullable: true
        relatedActivity nullable: true
        relatedIncident nullable: true
        responsibleGroup blank: true, nullable: true
        ownerGroup blank: true, nullable: true
        requestType blank: true, nullable: true
        env blank:true, nullable:true
        priority nullable: true
        targetFinish nullable: true
    }


}
