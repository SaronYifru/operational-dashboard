import com.mongodb.util.Hash
import groovy.time.TimeDuration
import operationaldashboard.ODAccess
import operationaldashboard.ODBreakFix
import operationaldashboard.ODComponent
import operationaldashboard.ODCustomer
import operationaldashboard.ODIncidents
import operationaldashboard.ODOther
import operationaldashboard.ODProblems
import operationaldashboard.ODRequestType
import operationaldashboard.ODThreshold
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

import operationaldashboard.ODActivities
import operationaldashboard.ODWorklog
import org.codehaus.groovy.runtime.ArrayUtil

import java.nio.charset.Charset
import java.text.DateFormat
import java.text.SimpleDateFormat

class BootStrap {
    public static String FORMAT1 = "MM-dd-yyyy hh:mm:ss"
    public static String FORMAT2 = "MM/dd/yyyy"
    public static String FORMAT3 = "dd MMM yyyy"
    public static String FORMAT4 = "dd MMM yyyy hh:mm"
    public static String FORMAT5 = "yyyy-MM-dd hh:mm:ss"
    static String ACT_TICKET_ID = "Activity"
    static String PRB_TICKET_ID = "Problem"
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


    File actCSV
    File requestTypesCSV
    File customersCSV
    File prbCSV
    File incCsv
    File components
    List<CSVRecord> recordsAct
    List<CSVRecord> recordsPrb
    List<CSVRecord> recordsInc
    List<CSVRecord> recordsRequestType
    List<CSVRecord> customers
    int columnIndex
    int rowIndex
    int prbIndex
    def emailReaderService
    HashMap<String, Integer> actColumnIndexes
    HashMap<String, Integer> prbColumnIndexes
    HashMap<String, Integer> worklogColumnIndexes
    HashMap<String, Integer> specColumnIndexes
    def init = { servletContext ->
        incCsv = new File("data/incidents.csv")
        recordsInc = CSVParser.parse(incCsv, Charset.defaultCharset(), CSVFormat.DEFAULT).getRecords()
        parseINC()
        actCSV = new File("data/activities2.csv")
        recordsAct = CSVParser.parse(actCSV, Charset.defaultCharset(),
                CSVFormat.DEFAULT).getRecords()
        actColumnIndexes = parseColumns(recordsAct.get(4))

        parseACT()
        prbCSV = new File("data/problems.csv")
        recordsPrb = CSVParser.parse(prbCSV, Charset.defaultCharset(),
                CSVFormat.DEFAULT).getRecords()
        prbColumnIndexes = parseColumns(recordsPrb.get(4))
        parsePRB()
        initializeThresholds()
//        runAsync {
//            incCsv = new File("data/incidents.csv")
//            recordsInc = CSVParser.parse(incCsv, Charset.defaultCharset(), CSVFormat.DEFAULT).getRecords()
//            parseINC()
//            actCSV = new File("data/activities2.csv")
//            recordsAct = CSVParser.parse(actCSV, Charset.defaultCharset(),
//                    CSVFormat.DEFAULT).getRecords()
//            actColumnIndexes = parseColumns(recordsAct.get(4))
//
//            parseACT()
//            prbCSV = new File("data/problems.csv")
//            recordsPrb = CSVParser.parse(prbCSV, Charset.defaultCharset(),
//                    CSVFormat.DEFAULT).getRecords()
//            prbColumnIndexes = parseColumns(recordsPrb.get(4))
//            parsePRB()
//        }
    }
    def destroy = {
    }
//    def parseCustomers() {
//
//        if (isRecordsEmpty(customers)) {
//            return null;
//        }
//        long numberOfRecords = customers.size();
//        for (int index = 1; index<numberOfRecords;index++) {
//
//            CSVRecord csvRecord = customers.get(index)
//            if(isValidRecord(csvRecord)) {
//                log.info(csvRecord)
//                def name = csvRecord.get(0).trim().replaceAll("^\\s+", "")
//                new ODCustomer(name: name).save(failOnError: true)
//            }
//        }
//
//    }
//    This function parses an incident report (Particularly extracts the related records for each incident ticket)
    def parseINC() {
        if (isRecordsEmpty(recordsInc)) {
            return null;
        }
        long numberOfRecords = recordsInc.size()
        for (int index = 0; index<numberOfRecords;index++) {
            CSVRecord csvRecord = recordsInc.get(index)
            if(isValidRecord(csvRecord) && csvRecord.get(0).matches(INC_TICKET_MATCHER) ) {
                ODIncidents incident = ODIncidents.findOrCreateByTicketIDAndSummary(csvRecord.get(0), csvRecord.get(1)).save(failOnError: true)
                String relatedRecord = csvRecord.get(4)
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
                Date reportedDate =  Date.parse(FORMAT2, csvRecord.get(prbColumnIndexes.get(REPORTED_DATE)))
                Date targetFinish
                int numberOfDaysOpen
                if  (csvRecord.get(prbColumnIndexes.get(TARGET_FINISH)).equals("")) {
                    targetFinish = null
                    use(groovy.time.TimeCategory) {
                        def dateFormat = new SimpleDateFormat(FORMAT2)
                        def duration = Calendar.getInstance(reportedDate.getTimeZone()).time - reportedDate
                        log.info(csvRecord.get(prbColumnIndexes.get(REPORTED_DATE)))
                        log.info(Calendar.getInstance(reportedDate.getTimeZone()).time.)
                        numberOfDaysOpen = duration.days

                    }
                }
                else {
                    targetFinish = new Date().parse(FORMAT2, csvRecord.get(prbColumnIndexes.get(TARGET_FINISH)))
                    use(groovy.time.TimeCategory) {
                        numberOfDaysOpen = (targetFinish - reportedDate).days
                    }
                }
                String priority = csvRecord.get(prbColumnIndexes.get(PRIORITY))


                log.info(numberOfDaysOpen)


//                Date statusDate = Date.parse(FORMAT2, csvRecord.get(11))
                String owner = csvRecord.get(prbColumnIndexes.get(OWNER))
                String ownerGroup = csvRecord.get(prbColumnIndexes.get(OWNER_GROUP))
                String responsibleGroup = csvRecord.get(prbColumnIndexes.get(RESPONSIBLE_GROUP))
                String environment = csvRecord.get(prbColumnIndexes.get(ENVIRONMENT));
//
                List<ODWorklog>logs = parseWorklog(recordsPrb, PRB_WORKLOGS_MATCHER)
                HashMap<String, String> spec = parseSpec(recordsPrb)


                ODRequestType  requestType  = ODRequestType.findOrSaveByName( getAttributeValue(spec.get(REQUEST_TYPE)))
                ODCustomer customer = ODCustomer.findOrSaveByName(getAttributeValue(spec.get(CUSTOMER)))
                ODComponent component = ODComponent.findOrSaveByValue(getAttributeValue(spec.get(COMPONENT)))
                ODAccess access = ODAccess.findOrSaveByValue(getAttributeValue(spec.get(ACCESS)))
                ODOther other = ODOther.findOrSaveByValue(getAttributeValue(spec.get(OTHER)))
                ODBreakFix breakFix = ODBreakFix.findorSaveByValue(getAttributeValue(spec.get(BREAK_FIX)))


                parseRelatedRecord(recordsPrb)
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
               log.info(csvRecord.get(i))
           }

        }


        return columnIndexes
    }

    private def parseACTRelatedRecord() {
//        TODO
        rowIndex++
        if (!recordsAct.get(rowIndex).get(0).matches("(.*)All the related workorders(.*)")) {
            return null;
        }
        rowIndex=rowIndex+2
        while (isValidRecord(recordsAct.get( rowIndex))) {
            String record = recordsAct.get( rowIndex).get(0)
            rowIndex++
        }

        return

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
                log.info(id)
                String priority = csvRecord.get(actColumnIndexes.get(PRIORITY))
                Date actualStart =  Date.parse(FORMAT2,csvRecord.get(actColumnIndexes.get(ACTUAL_START)), TimeZone.getTimeZone("CDT"))
//                String actualTime = parseColumn(csvRecord)
                String owner = csvRecord.get(actColumnIndexes.get(OWNER))
                String ownerGroup = csvRecord.get(actColumnIndexes.get(OWNER_GROUP))
                String responsibleGroup = csvRecord.get(actColumnIndexes.get(RESPONSIBLE_GROUP))
                String environment = csvRecord.get(actColumnIndexes.get(ENVIRONMENT))
                Date statusDate = Date.parse(FORMAT2, csvRecord.get(actColumnIndexes.get(STATUS_DATE)))
                int numberOfDaysOpen

                    DateFormat dateFormat =  DateFormat.getDateTimeInstance()
                            dateFormat.setTimeZone(TimeZone.getTimeZone("CDT"))
                     numberOfDaysOpen =  new Date() - actualStart
                    log.info(numberOfDaysOpen)
//                String relatedRecord = csvRecord.get(21);
                parseRelatedRecord(recordsAct)
                HashMap<String, String> spec = parseSpec(recordsAct)
                List<ODWorklog>logs = parseWorklog(recordsAct, ACT_WORKLOGS_MATCHER)
                //Create a new instance of 'Activity'
                ODRequestType  requestType  = ODRequestType.findOrSaveByName( getAttributeValue(spec.get(REQUEST_TYPE)))
                ODCustomer customer = ODCustomer.findOrSaveByName(getAttributeValue(spec.get(CUSTOMER)))
                ODComponent component = ODComponent.findOrSaveByValue(getAttributeValue(spec.get(COMPONENT)))
                ODAccess access = ODAccess.findOrSaveByValue(getAttributeValue(spec.get(ACCESS)))
                ODOther other = ODOther.findOrSaveByValue(getAttributeValue(spec.get(OTHER)))
                ODBreakFix breakFix = ODBreakFix.findorSaveByValue(getAttributeValue(spec.get(BREAK_FIX)))

                def activity = new ODActivities(ticketID:
                        id, summary: summary, status: status, priority: priority, actualStart: actualStart, statusDate: statusDate, personName: owner, ownerGroup: ownerGroup, responsibleGroup: responsibleGroup, env: environment,
                        customer: customer, requestType: requestType, component:component, accessAtr:access,other:other, breakFix:breakFix, numberOfDaysOpen: numberOfDaysOpen)
                activity.save(failOnError: true)

                for (log in logs) {
                    activity.addToWorklogs(log)

                }
                activity.save(failOnError: true)
                ODIncidents relatedIncident = ODIncidents.findByRelatedACT(id)
                if (relatedIncident != null) {
                    activity.setRelatedIncident(relatedIncident)
                    activity.save()
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
        rowIndex = rowIndex + 2
        if (!records.get(rowIndex).get(0).matches("(.*)" + worklogTitle + "(.*)")) {
            return logs;
        }
        worklogColumnIndexes = parseColumns(records.get(rowIndex + 1))
        rowIndex = rowIndex + 2;
        CSVRecord record = records.get(rowIndex);
        while (isValidRecord(record)) {

            Date date = Date.parse(FORMAT2, record.get(worklogColumnIndexes.get(WORKLOG_DATE)));
            String createdBy = record.get(worklogColumnIndexes.get(WORKLOG_CREATED_BY));
            String summary = record.get(worklogColumnIndexes.get(WORKLOG_SUMMARY));
            log.info(summary)
            logs.add(new ODWorklog(createdDate: date, createdBy: createdBy, summary:summary));
            record = records.get(++rowIndex);
        }
        return logs;

    }

//    private HashMap<String, String> parseSpec(List<CSVRecord> records) {
//        HashMap<String, String> specs = new HashMap<String, String>();
//        rowIndex = rowIndex + 1
//        if (!records.get(rowIndex).get(0).matches("(.*)All the specifications(.*)")) {
//            return specs;
//        }
//        rowIndex = rowIndex + 2
//        while (isValidRecord(records.get(rowIndex))) {
//            String attribute = records.get(rowIndex).get(0)
//            String value = records.get(rowIndex).get(12)
//            specs.put(attribute, value)
//            rowIndex++
//        }
//        return specs
//    }
//  This method parses a ticket's specifications
//    Returns a map of specs
    private HashMap<String, String> parseSpec(List<CSVRecord> records) {
        HashMap<String, String> specs = new HashMap<String, String>();
        rowIndex = rowIndex + 1
        if (!records.get(rowIndex).get(0).matches("(.*)" + SPECS_TITLE + "(.*)")) {
            return specs;
        }
        rowIndex++
        specColumnIndexes = parseColumns(records.get(rowIndex))
        rowIndex++
        while (isValidRecord(records.get(rowIndex))) {
            String attribute = records.get(rowIndex).get(specColumnIndexes.get(SPEC_ATTRIBUTE))
            String value = records.get(rowIndex).get(specColumnIndexes.get(SPEC_VALUE))
            specs.put(attribute, value)
            rowIndex++
        }
        return specs
    }
//    This function parses the related records of a ticket
//    Returns a map of tickets (Activity, incident and problem)
    private def parseRelatedRecord(List<CSVRecord> records) {
       ODActivities activity
        ODIncidents  incident
        ODProblems problem
        ++rowIndex
        if (!records.get(rowIndex).get(0).matches("(.*)related records(.*)")) {
            return null;
        }
        rowIndex=rowIndex+2
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
