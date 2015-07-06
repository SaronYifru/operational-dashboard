package operationaldashboard

class ODProblems {
    String ticketID
    String summary
    String status
    String priority
    Date actualStart
    Date targetDate
    String personName
    String responsibleGroup
    String ownerGroup
    Date statusDate
    String env
    String customer
    ODWorklog worklog
    String relatedRecord

    static constraints = {
        ticketID unique:true
        relatedRecord blank:true
        customer blank: true
        personName blank: true
        responsibleGroup blank: true
        ownerGroup blank: true
    }
}
