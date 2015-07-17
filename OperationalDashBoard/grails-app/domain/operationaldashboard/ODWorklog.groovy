package operationaldashboard

class ODWorklog {
    Date createdDate
    String createdBy
    String summary
    static belongsTo = ODActivities
    static constraints = {
        summary blank:false, nullable: true
    }
}
