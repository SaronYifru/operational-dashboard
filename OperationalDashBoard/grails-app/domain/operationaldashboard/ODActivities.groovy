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
    ODCustomer customer
    String relatedRecord
    ODRequestType requestType
    static hasMany = [worklogs:ODWorklog]
    int numberOfDaysOpen
    ODIncidents relatedIncident
    static constraints = {
        ticketID unique:true
        summary blank: true, nullable: true
        status blank: true, nullable: true
        actualStart nullable: true
        statusDate nullable: true
        relatedRecord blank:true
        customer nullable: true
        personName blank: true
        responsibleGroup blank: true, nullable:true
        ownerGroup blank: true, nullable: true
        requestType blank: true, nullable: true
        env blank:true, nullable:true
        priority nullable: true
        relatedIncident nullable: true
    }
    static mapping = {
        customer index:true
        env index:true
        relatedRecord index: true

    }




}
