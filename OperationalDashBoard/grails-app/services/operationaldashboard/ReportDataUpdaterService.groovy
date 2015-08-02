package operationaldashboard

import com.mongodb.BasicDBObject
import grails.transaction.Transactional
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

import java.nio.charset.Charset
import java.text.DateFormat

@Transactional
class ReportDataUpdaterService {
    public static String DATE_FORMAT1 = "MM/dd/yy"
    public static String DATE_FORMAT2 = "MM/dd/yyyy"
    public static String FORMAT3 = "dd MMM yyyy"
    public static String FORMAT4 = "dd MMM yyyy hh:mm"
    public static String FORMAT5 = "yyyy-MM-dd hh:mm:ss"
    static String ACT_TICKET_ID = "Activity"
    static String PRB_TICKET_ID = "Problem"
    static String INC_TICKET_ID = "Incident"
    static String RELATED_RECORD_KEY = "Related Record Key"
    static String SUMMARY = "Summary"
    static String PRIORITY = "Priority"
    static String STATUS = "Status"
    static String ACTUAL_START = "Actual Start"
    static String OWNER = "Owner"
    static String OWNER_GROUP = "Owner Group"
    static String RESPONSIBLE_GROUP = "Responsible Group"
    static String STATUS_DATE = "Status Date"
    static String REPORTED_DATE = "Reported Date"
    static String TARGET_FINISH = "Target Finish"
    static String ENVIRONMENT = "Environment"
    static String WORKLOG_DATE = "Date"
    static String WORKLOG_CREATED_BY = "Created By"
    static String WORKLOG_SUMMARY = "Summary"
    static String SPEC_ATTRIBUTE = "Attribute"
    static String SPEC_VALUE = "Alphanumeric Value"
    static String ACT_WORKLOGS_MATCHER = "All the worklog entries"
    static String PRB_WORKLOGS_MATCHER = "All the work log entries"
    static String ACT_RELATED_RECORDS_MATCHER = "All the related workorders"
    static String PRB_RELATED_RECORDS_MATCHER = "All the related records"
    static String SPECS_TITLE = "All the specifications"
    static String ACT_TICKET_MATCHER = "ACT(.*)"
    static String PRB_TICKET_MATCHER = "PRB(.*)"
    static String INC_TICKET_MATCHER = "INC(.*)"
    static String REQUEST_TYPE = "MRSREQUEST"
    static String COMPONENT = "MRSCOMPONENT"
    static String ACCESS = "MRSACCESS"
    static String BREAK_FIX = "MRSBREAKFIX"
    static String OTHER = "MRSOTHER"
    static String CUSTOMER = "MRSCUSTOMER"
    // Threshold attributes
    static String NUMBER_OF_DAYS_OPEN_THRESHOLD = "Number of days open"
    static String NUMBER_OF_TICKETS_THRESHOLD = "Number of tickets"


    List<CSVRecord> recordsAct
    List<CSVRecord> recordsPrb
    List<CSVRecord> recordsInc
    List<CSVRecord> recordsOwners
    List<CSVRecord> customers
    int columnIndex
    int rowIndex
    HashMap<String, Integer> actColumnIndexes
    HashMap<String, Integer> prbColumnIndexes
    HashMap<String, Integer> incColumnIndexes
    HashMap<String, Integer> worklogColumnIndexes
    HashMap<String, Integer> specColumnIndexes
    File actCSV
    File prbCSV
    File incCsv
    File ownersCsv
    def grailsApplication
    def serviceMethod() {

    }
    def deleteCurrentAndupdateReports() {
        ODActivities.collection.remove(new BasicDBObject());
        ODIncidents.collection.remove(new BasicDBObject())
        ODProblems.collection.remove(new BasicDBObject());
        updateReports()


    }
    def updateReports (){
        incCsv = grailsApplication.mainContext.getResource("reports/lsps_incidents.csv").file
        recordsInc = CSVParser.parse(incCsv, Charset.defaultCharset(), CSVFormat.DEFAULT).getRecords()
        incColumnIndexes = parseColumns(recordsInc.get(3))
        parseINC()
        actCSV = grailsApplication.mainContext.getResource("reports/lsps_activities.csv").file
        recordsAct = CSVParser.parse(actCSV, Charset.defaultCharset(),
                CSVFormat.DEFAULT).getRecords()
        actColumnIndexes = parseColumns(recordsAct.get(4))

        parseACT()
        prbCSV = grailsApplication.mainContext.getResource("reports/lsps_problems.csv").file
        recordsPrb = CSVParser.parse(prbCSV, Charset.defaultCharset(),
                CSVFormat.DEFAULT).getRecords()
        prbColumnIndexes = parseColumns(recordsPrb.get(4))
        parsePRB()
    }

    def initializeReports() {
        ownersCsv = grailsApplication.mainContext.getResource("reports/owners.csv").file
        recordsOwners = CSVParser.parse(ownersCsv, Charset.defaultCharset(), CSVFormat.DEFAULT).getRecords()
        parseOwners()
        updateReports()
//        initializeThresholds()
    }
    def parseOwners() {

        if (isRecordsEmpty(recordsOwners)) {
            return null;
        }
        long numberOfRecords = recordsOwners.size();
        HashMap ownerColumns = parseColumns(recordsOwners.get(0))
        for (int index = 1; index<numberOfRecords;index++) {

            CSVRecord csvRecord = recordsOwners.get(index)
            if(isValidRecord(csvRecord)) {
                def eID = csvRecord.get(ownerColumns.get("Person"))
                def name = csvRecord.get(ownerColumns.get("Name")).trim().replaceAll("^\\s+", "")
                new ODOwner(eID: eID, name: name).save(failOnError: true)
            }
        }

    }
//    This function parses an incident report (Particularly extracts the related records for each incident ticket)
    def parseINC() {
        if (isRecordsEmpty(recordsInc)) {
            return null;
        }
        long numberOfRecords = recordsInc.size()
        for (int index = 0; index<numberOfRecords;index++) {
            CSVRecord csvRecord = recordsInc.get(index)
            if(isValidRecord(csvRecord) && csvRecord.get(0).matches(INC_TICKET_MATCHER) ) {
                ODIncidents incident = ODIncidents.findByTicketID(csvRecord.get(incColumnIndexes.get(INC_TICKET_ID)).trim())
                if (incident == null) {
                    String ticketID = csvRecord.get(incColumnIndexes.get(INC_TICKET_ID)).trim()
                    incident = new ODIncidents(ticketID: ticketID)
                }
                String relatedRecord = csvRecord.get(incColumnIndexes.get(RELATED_RECORD_KEY))
                if(relatedRecord.matches(ACT_TICKET_MATCHER)) {
                    incident.setRelatedACT(relatedRecord)
                }
                else if (relatedRecord.matches(PRB_TICKET_MATCHER)) {
                    incident.setRelatedPRB(relatedRecord)
                }
                incident.save(failOnError: true)
            }
        }
    }

//    This function initializes the thresholds for each request type with default values
    def initializeThresholds() {
        def types = ODRequestType.list()
        def customers = ODCustomer.list()
        // Initialize thresholds for request type
        for (type in types) {
            ODThreshold threshold = new ODThreshold(type: type, thresholdValue : 0, attribute: NUMBER_OF_DAYS_OPEN_THRESHOLD)
            threshold.save(failOnError: true)
        }
        new ODThreshold(type: null, attribute: NUMBER_OF_TICKETS_THRESHOLD, thresholdValue: 0).save(failOnError: true)


    }
    /*This function determines whether the list of CSVRecords returned from the CSV reader is empty
     ** Returns Boolean
     */
    def isRecordsEmpty(List<CSVRecord> records) {
        return (records.size() > 0) ? false : true
    }
//    This function given a String column value identifies the characterstic of the value related to the column
    def getAttributeValue(String attributeValue){
        String value
        if (attributeValue.equals(null)) {
            attributeValue = "No Attribute"
        }
        else if (attributeValue.equals("")){
            attributeValue = "No Value"
        }
        else {
            attributeValue = attributeValue.trim()

        }
        return attributeValue
    }
//    This function parses a problem report and adds every ticket with parsed data to the ODProblems table
    def parsePRB() {
        if (isRecordsEmpty(recordsPrb)) {
            return null;
        }
        long numberOfRecords = recordsPrb.size();
        String createdDate = recordsPrb.get((int) numberOfRecords - 1).get(0);

        for (rowIndex = 4; rowIndex < numberOfRecords; rowIndex++) {
            CSVRecord csvRecord = recordsPrb.get(rowIndex);
            //Parse the record if the record is not empty

            if (isValidRecord(csvRecord) && csvRecord.get(0).matches("PRB(.*)")) {

                String id = csvRecord.get(prbColumnIndexes.get(PRB_TICKET_ID))
                String summary = csvRecord.get(prbColumnIndexes.get(SUMMARY))
                String status = csvRecord.get(prbColumnIndexes.get(STATUS))

                Date reportedDate =  Date.parse(DATE_FORMAT1, csvRecord.get(prbColumnIndexes.get(REPORTED_DATE)))
                Date targetFinish
                int numberOfDaysOpen
                if  (csvRecord.get(prbColumnIndexes.get(TARGET_FINISH)).equals("")) {
                    targetFinish = null
                    use(groovy.time.TimeCategory) {
                        def duration = new Date() - reportedDate
                        numberOfDaysOpen = duration.days

                    }
                }
                else {
                    targetFinish = Date.parse(DATE_FORMAT1, csvRecord.get(prbColumnIndexes.get(TARGET_FINISH)))
                    use(groovy.time.TimeCategory) {
                        numberOfDaysOpen = (targetFinish - reportedDate).days
                    }
                }
                String priority = csvRecord.get(prbColumnIndexes.get(PRIORITY))

                String ownerID = csvRecord.get(prbColumnIndexes.get(OWNER))
                ODOwner owner
                if (!ownerID.equals("") && !ownerID.equals(null)) {
                    owner = ODOwner.findOrSaveByEID(ownerID)
                }
                String ownerGroup = csvRecord.get(prbColumnIndexes.get(OWNER_GROUP))
                String responsibleGroup = csvRecord.get(prbColumnIndexes.get(RESPONSIBLE_GROUP))
                String environment = csvRecord.get(prbColumnIndexes.get(ENVIRONMENT));
                rowIndex++
                List<ODWorklog>logs = parseWorklog(recordsPrb, PRB_WORKLOGS_MATCHER)
                HashMap<String, String> spec = parseSpec(recordsPrb)


                ODRequestType  requestType  = ODRequestType.findOrSaveByName( getAttributeValue(spec.get(REQUEST_TYPE)))
                requestType.setThreshold(ODThreshold.findOrSaveByAttribute(NUMBER_OF_DAYS_OPEN_THRESHOLD))
                requestType.save(failOnError: true)
                ODCustomer customer = ODCustomer.findOrSaveByName(getAttributeValue(spec.get(CUSTOMER)))
                customer.setThreshold(ODThreshold.findOrSaveByAttribute(NUMBER_OF_TICKETS_THRESHOLD))
                customer.save()
                ODComponent component = ODComponent.findOrSaveByValue(getAttributeValue(spec.get(COMPONENT)))
                ODAccess access = ODAccess.findOrSaveByValue(getAttributeValue(spec.get(ACCESS)))
                ODOther other = ODOther.findOrSaveByValue(getAttributeValue(spec.get(OTHER)))
                ODBreakFix breakFix = ODBreakFix.findOrSaveByValue(getAttributeValue(spec.get(BREAK_FIX)))


                parseRelatedRecord(recordsPrb, PRB_RELATED_RECORDS_MATCHER)
                def problem = new ODProblems(ticketID:
                        id, summary: summary, status: status, priority: priority, reportedDate: reportedDate, targetFinish: targetFinish, owner: owner, ownerGroup: ownerGroup, responsibleGroup: responsibleGroup, env: environment,
                        customer: customer, requestType: requestType, numberOfDaysOpen: numberOfDaysOpen, component:component, access:access, other:other, breakFix:breakFix)
                problem.save(failOnError: true)

                for (log in logs) {
                    problem.addToWorklogs(log)

                }
                problem.save(failOnError: true)
                ODIncidents relatedIncident = ODIncidents.findByRelatedPRB(id)
                if (relatedIncident != null) {
                    problem.setRelatedIncident(relatedIncident)
                    problem.save(failOnError: true)
                }



            } else {
                continue;
            }
        }
    }
//    This function parses the columns of a given row (CSVRecord) and returns the header and the index for those Columns
    def parseColumns(CSVRecord csvRecord) {
        Map<String, Integer> columnIndexes = new HashMap<String, Integer>()
        for (int i = 0; i< csvRecord.size(); i++) {

            if (!csvRecord.get(i).equals("") && !csvRecord.get(i).equals(null)) {
                columnIndexes.put(csvRecord.get(i), i)
            }

        }


        return columnIndexes
    }

//    This function parses an activity report and adds each ticket to ODActivities table
    def parseACT()  {
        if (isRecordsEmpty(recordsAct)) {
            return null;
        }
        long numberOfRecords = recordsAct.size();
        String createdDate = recordsAct.get((int) numberOfRecords - 1).get(0);

        for (rowIndex = 4; rowIndex < numberOfRecords; rowIndex++) {
            CSVRecord csvRecord = recordsAct.get(rowIndex);
            //Parse the record if the record is not empty

            if (isValidRecord(csvRecord) && csvRecord.get(0).matches("ACT(.*)")) {
                columnIndex = 0
                String id = csvRecord.get(actColumnIndexes.get(ACT_TICKET_ID))
                String summary = csvRecord.get(actColumnIndexes.get(SUMMARY))
                String status = csvRecord.get(actColumnIndexes.get(STATUS))
                String priority = csvRecord.get(actColumnIndexes.get(PRIORITY))
//                String actualTime = parseColumn(csvRecord)
                String ownerID = csvRecord.get(actColumnIndexes.get(OWNER))
                ODOwner owner
                if (!ownerID.equals("") && !ownerID.equals(null)) {
                    owner = ODOwner.findOrSaveByEID(ownerID)
                }
                String ownerGroup = csvRecord.get(actColumnIndexes.get(OWNER_GROUP))
                String responsibleGroup = csvRecord.get(actColumnIndexes.get(RESPONSIBLE_GROUP))
                String environment = csvRecord.get(actColumnIndexes.get(ENVIRONMENT))
                Date statusDate = Date.parse(DATE_FORMAT2, csvRecord.get(actColumnIndexes.get(STATUS_DATE)))
                Date actualStart =  Date.parse(DATE_FORMAT1,csvRecord.get(actColumnIndexes.get(ACTUAL_START)))
                int numberOfDaysOpen
                use(groovy.time.TimeCategory) {
                    def duration = new Date() - actualStart
                    numberOfDaysOpen = duration.days
                }
                rowIndex++
                parseRelatedRecord(recordsAct, ACT_RELATED_RECORDS_MATCHER)
                HashMap<String, String> spec = parseSpec(recordsAct)
                List<ODWorklog>logs = parseWorklog(recordsAct, ACT_WORKLOGS_MATCHER)
                //Create a new instance of 'Activity'
                ODRequestType  requestType  = ODRequestType.findOrSaveByName( getAttributeValue(spec.get(REQUEST_TYPE)))
                requestType.setThreshold(ODThreshold.findOrSaveByAttribute(NUMBER_OF_DAYS_OPEN_THRESHOLD))
                requestType.save(failOnError: true)
                ODCustomer customer = ODCustomer.findOrSaveByName(getAttributeValue(spec.get(CUSTOMER)))
                customer.setThreshold(ODThreshold.findOrSaveByAttribute(NUMBER_OF_TICKETS_THRESHOLD))
                customer.save(failOnError: true)
                ODComponent component = ODComponent.findOrSaveByValue(getAttributeValue(spec.get(COMPONENT)))
                ODAccess access = ODAccess.findOrSaveByValue(getAttributeValue(spec.get(ACCESS)))
                ODOther other = ODOther.findOrSaveByValue(getAttributeValue(spec.get(OTHER)))
                ODBreakFix breakFix = ODBreakFix.findOrSaveByValue(getAttributeValue(spec.get(BREAK_FIX)))

                def activity = new ODActivities(ticketID:
                        id, summary: summary, status: status, priority: priority, actualStart: actualStart, statusDate: statusDate, owner: owner, ownerGroup: ownerGroup, responsibleGroup: responsibleGroup, env: environment,
                        customer: customer, requestType: requestType, component:component, accessAtr:access,other:other, breakFix:breakFix, numberOfDaysOpen: numberOfDaysOpen)
                activity.save(failOnError: true)

                for (log in logs) {
                    activity.addToWorklogs(log)

                }
                activity.save(failOnError: true)
                ODIncidents relatedIncident = ODIncidents.findByRelatedACT(id)
                if (relatedIncident != null) {
                    activity.setRelatedIncident(relatedIncident)
                    activity.save(failOnError: true)
                }


            } else {
                continue;
            }
        }

    }
//    This function parses a ticket's Worklogs
//    Returns a list of worklogs
    private List<ODWorklog> parseWorklog(List<CSVRecord> records, String worklogTitle) {
        List<ODWorklog> logs = new ArrayList<ODWorklog> ();
        if (!records.get(rowIndex + 1).get(0).matches("(.*)" + worklogTitle + "(.*)")) {
            return logs;
        }
        rowIndex = rowIndex + 2
        worklogColumnIndexes = parseColumns(records.get(rowIndex))
        rowIndex++;
        CSVRecord record = records.get(rowIndex);
        while (isValidRecord(record)) {

            Date date = Date.parse(DATE_FORMAT2, record.get(worklogColumnIndexes.get(WORKLOG_DATE)));
            String createdBy = record.get(worklogColumnIndexes.get(WORKLOG_CREATED_BY));
            ODOwner person = createdBy.equals("") || createdBy.equals(null)? null: ODOwner.findOrSaveByEID(createdBy)
            String owner
            if (person != null) {
                owner = person.name != null && !person.name.equals("")? person.name:person.eID
            }

            String summary = record.get(worklogColumnIndexes.get(WORKLOG_SUMMARY));
            ODWorklog worklog = new ODWorklog(createdDate: date,summary:summary,createdBy:owner)
            logs.add(worklog);
            record = records.get(++rowIndex);
        }
        return logs;

    }

//  This method parses a ticket's specifications
//    Returns a map of specs
    private HashMap<String, String> parseSpec(List<CSVRecord> records) {
        HashMap<String, String> specs = new HashMap<String, String>();
        if (!records.get(rowIndex + 1).get(0).matches("(.*)" + SPECS_TITLE + "(.*)")) {
            return specs;
        }
        rowIndex = rowIndex + 2
        specColumnIndexes = parseColumns(records.get(rowIndex))
        rowIndex++
        String attribute = records.get(rowIndex).get(specColumnIndexes.get(SPEC_ATTRIBUTE))
        while (!attribute.equals("") && !attribute.equals(null)) {
//            String attribute = records.get(rowIndex).get(specColumnIndexes.get(SPEC_ATTRIBUTE))
            String value = records.get(rowIndex).get(specColumnIndexes.get(SPEC_VALUE))
            specs.put(attribute, value)
            rowIndex++
            attribute = records.get(rowIndex).get(specColumnIndexes.get(SPEC_ATTRIBUTE))
        }
        return specs
    }
//    This function parses the related records of a ticket
//    Returns a map of tickets (Activity, incident and problem)
    private def parseRelatedRecord(List<CSVRecord> records, String relatedRecordTitle) {
        ODActivities activity
        ODIncidents  incident
        ODProblems problem
        if (!records.get(rowIndex + 1).get(0).matches(relatedRecordTitle)) {
            return null;
        }
        rowIndex=rowIndex + 3
        while (isValidRecord(records.get( rowIndex))) {
            String record = records.get( rowIndex).get(0)
            if (record.matches(ACT_TICKET_MATCHER)) {
                activity = ODActivities.findByTicketID(record)

            }
            else if (record.matches(INC_TICKET_MATCHER)) {
                incident = ODIncidents.findByTicketID(record)

            }
            else if (record.matches(PRB_TICKET_MATCHER)) {
                problem = ODProblems.findByTicketID(record)
            }


            rowIndex++
        }

        return [activity: activity, incident:incident, problem:problem]

    }

    /* This function determines if a record (row) is empty or null
    * Returns Boolean
    * */
    private boolean isValidRecord(CSVRecord record) {
        if (record.get(0).equals("") || record.get(0).equals(null)) {
            return false;
        }
        return true;
    }


}