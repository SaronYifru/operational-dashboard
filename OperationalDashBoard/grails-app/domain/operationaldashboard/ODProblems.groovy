package operationaldashboard

class ODProblems {
    String ticketID
    String summary
    String status
    String priority
    Date reportedDate
    Date targetFinish
    ODOwner owner
    String responsibleGroup
    String ownerGroup
    String env
    ODCustomer customer
    ODRequestType requestType
    static hasMany = [worklogs:ODWorklog]
    int numberOfDaysOpen
    ODIncidents relatedIncident
    ODAccess access
    ODBreakFix breakFix
    ODOther other
    ODComponent component


    static constraints = {
        ticketID unique:true
        customer blank: true
        owner nullable: true
        relatedIncident nullable: true
        responsibleGroup blank: true, nullable: true
        ownerGroup blank: true, nullable: true
        requestType blank: true, nullable: true
        env blank:true, nullable:true
        priority nullable: true
        targetFinish nullable: true
        access blank:true, nullable: true
        other blank:true, nullable: true
        breakFix blank:true, nullable: true
        component blank:true, nullable: true
    }


}
