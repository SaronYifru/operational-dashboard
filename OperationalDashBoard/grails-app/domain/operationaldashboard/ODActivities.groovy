package operationaldashboard

/**
 * Created by E055756 on 6/30/2015.
 */
class ODActivities {
    String ticketID
    String summary
    String status
    String priority
    Date actualStart
    String personName
    String responsibleGroup
    String ownerGroup
    Date statusDate
    String env
    String customer
    String relatedRecord
    ODRequestType requestType
    static hasMany = [worklogs:ODWorklog]
    static constraints = {
        ticketID unique:true
        relatedRecord blank:true
        customer blank: true, nullable: true
        personName blank: true
        responsibleGroup blank: true, nullable:true
        ownerGroup blank: true, nullable: true
        requestType nullable: true
        env blank:true, nullable:true
        priority nullable: true
    }



}
