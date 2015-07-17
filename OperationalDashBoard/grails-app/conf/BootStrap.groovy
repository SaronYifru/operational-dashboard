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

class BootStrap {
    public static String FORMAT1 = "MM-dd-yyyy hh:mm:ss"
    public static String FORMAT2 = "MM/dd/yyyy"
    public static String FORMAT3 = "dd MMM yyyy"
    public static String FORMAT4 = "dd MMM yyyy hh:mm"
    public static String FORMAT5 = "yyyy-MM-dd hh:mm:ss"
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
        requestTypesCSV = new File("data/requestType.csv")
        recordsRequestType = CSVParser.parse(requestTypesCSV,  Charset.defaultCharset(),CSVFormat.DEFAULT ).getRecords()
//        parseRequestType()
        customersCSV = new File("data/focusCustomers.csv")
        customers = CSVParser.parse(customersCSV,  Charset.defaultCharset(),CSVFormat.DEFAULT ).getRecords()
//        parseCustomers()
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
//        emailReaderService.serviceMethod()
        initializeThresholds()

        runAsync {
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
        }
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
    def parseINC() {
        if (isRecordsEmpty(recordsInc)) {
            return null;
        }
        long numberOfRecords = recordsInc.size()
        for (int index = 0; index<numberOfRecords;index++) {
            CSVRecord csvRecord = recordsInc.get(index)
            if(isValidRecord(csvRecord) && csvRecord.get(0).matches("INC(.*)") ) {
                ODIncidents incident = ODIncidents.findOrCreateByTicketIDAndSummary(csvRecord.get(0), csvRecord.get(1)).save(failOnError: true)
                String relatedRecord = csvRecord.get(4)
                if(relatedRecord.matches("ACT(.*)")) {
                    incident.setRelatedACT(relatedRecord)
                }
                else if (relatedRecord.matches("PRB(.*)")) {
                    incident.setRelatedPRB(relatedRecord)
                }
                incident.save(failOnError: true)
            }
        }
    }
//    def parseRequestType() {
//        if (isRecordsEmpty(recordsRequestType)) {
//            return null;
//        }
//        long numberOfRecords = recordsRequestType.size();
//        for (int index = 1; index<numberOfRecords;index++) {
//            CSVRecord csvRecord = recordsRequestType.get(index)
//            new ODRequestType(name: csvRecord.get(0).trim()).save(failOnError: true)
//        }
//
//
//    }
    def initializeThresholds() {
        String thresholdAttribute1 = "Number of Days Open"
        String thresholdAttribute2 = "Number of Tickets"
        def types = ODRequestType.list()
        for (type in types) {
            new ODThreshold(type:  type, attribute:thresholdAttribute1, thresholdValue:0).save(failOnError: true)
        }
        new ODThreshold(type:  null, attribute:  thresholdAttribute2, thresholdValue:0).save(failOnError: true)
    }
    /*This function determines whether the list of recordsAct returned from the CSV reader is empty
     ** Returns Boolean
     */
    def isRecordsEmpty(List<CSVRecord> records) {
        return (records.size() > 0) ? false : true
    }
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

                String id = csvRecord.get(prbColumnIndexes.get("Problem"))
                log.info(id + "problem ids")
                String summary = csvRecord.get(prbColumnIndexes.get("Summary"))
                String status = csvRecord.get(prbColumnIndexes.get("Status"))
                Date reportedDate =  Date.parse(FORMAT2, csvRecord.get(prbColumnIndexes.get("Reported Date")), TimeZone.getTimeZone("CDT"))
                Date targetFinish
                int numberOfDaysOpen
                if  (csvRecord.get(prbColumnIndexes.get("Target Finish")).equals("")) {
                    targetFinish = null
                    use(groovy.time.TimeCategory) {
                        numberOfDaysOpen = (new Date() - reportedDate).days
                    }
                }
                else {
                    targetFinish = Date.parse(FORMAT2, csvRecord.get(prbColumnIndexes.get("Target Finish")), TimeZone.getTimeZone("CDT"))
                    use(groovy.time.TimeCategory) {
                        numberOfDaysOpen = (targetFinish - reportedDate).days
                    }
                }
                String priority = csvRecord.get(prbColumnIndexes.get("Priority"))


                log.info(numberOfDaysOpen)


//                Date statusDate = Date.parse(FORMAT2, csvRecord.get(11))
                String owner = csvRecord.get(prbColumnIndexes.get("Owner"))
                String ownerGroup = csvRecord.get(prbColumnIndexes.get("Owner Group"))
                String responsibleGroup = csvRecord.get(prbColumnIndexes.get("Responsible Group"))
                String environment = csvRecord.get(prbColumnIndexes.get("Environment"));
//
                List<ODWorklog>logs = parseWorklog(recordsPrb, "work log")
                HashMap<String, String> spec = parseSpec(recordsPrb)


                ODRequestType  requestType  = ODRequestType.findOrSaveByName( getAttributeValue(spec.get("MRSREQUEST")))
                ODCustomer customer = ODCustomer.findOrSaveByName(getAttributeValue(spec.get("MRSCUSTOMER")))
                ODComponent component = ODComponent.findOrSaveByValue(getAttributeValue(spec.get("MRSCOMPONENT")))
                ODAccess access = ODAccess.findOrSaveByValue(getAttributeValue(spec.get("MRSACCESS")))
                ODOther other = ODOther.findOrSaveByValue(getAttributeValue(spec.get("MRSOTHER")))
                ODBreakFix breakFix = ODBreakFix.findorSaveByValue(getAttributeValue(spec.get("MRSBREAKFIX")))


                parseRelatedRecord()
                def problem = new ODProblems(ticketID:
                        id, summary: summary, status: status, priority: priority, reportedDate: reportedDate, owner: owner, ownerGroup: ownerGroup, responsibleGroup: responsibleGroup, env: environment,
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
    def parseColumns(CSVRecord csvRecord) {
        Map<String, Integer> columnIndexes = new HashMap<String, Integer>()
        int numberOfColumns
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
                String id = csvRecord.get(actColumnIndexes.get("Activity"))
                String summary = csvRecord.get(actColumnIndexes.get("Summary"))
                String status = csvRecord.get(actColumnIndexes.get("Status"))
                log.info(id)
                String priority = csvRecord.get(actColumnIndexes.get("Priority"))
                Date actualStart =  Date.parse(FORMAT2,csvRecord.get(actColumnIndexes.get("Actual Start")), TimeZone.getTimeZone("CDT"))
//                String actualTime = parseColumn(csvRecord)
                String owner = csvRecord.get(actColumnIndexes.get("Owner"))
                String ownerGroup = csvRecord.get(actColumnIndexes.get("Owner Group"))
                String responsibleGroup = csvRecord.get(actColumnIndexes.get("Responsible Group"))
                String environment = csvRecord.get(actColumnIndexes.get("Environment"))
                Date statusDate = Date.parse(FORMAT2, csvRecord.get(actColumnIndexes.get("Status Date")))
                int numberOfDaysOpen

                    DateFormat dateFormat =  DateFormat.getDateTimeInstance()
                            dateFormat.setTimeZone(TimeZone.getTimeZone("CDT"))
                     numberOfDaysOpen =  new Date() - actualStart
                    log.info(numberOfDaysOpen)
//                String relatedRecord = csvRecord.get(21);
                parseACTRelatedRecord()
                HashMap<String, String> spec = parseACTSpec(recordsAct)
                List<ODWorklog>logs = parseWorklog(recordsAct, "worklog")
                //Create a new instance of 'Activity'
                ODRequestType  requestType  = ODRequestType.findOrSaveByName( getAttributeValue(spec.get("MRSREQUEST")))
                ODCustomer customer = ODCustomer.findOrSaveByName(getAttributeValue(spec.get("MRSCUSTOMER")))
                ODComponent component = ODComponent.findOrSaveByValue(getAttributeValue(spec.get("MRSCOMPONENT")))
                ODAccess access = ODAccess.findOrSaveByValue(getAttributeValue(spec.get("MRSACCESS")))
                ODOther other = ODOther.findOrSaveByValue(getAttributeValue(spec.get("MRSOTHER")))
                ODBreakFix breakFix = ODBreakFix.findorSaveByValue(getAttributeValue(spec.get("MRSBREAKFIX")))

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
    private List<ODWorklog> parseWorklog(List<CSVRecord> records, String regExp) {
        List<ODWorklog> logs = new ArrayList<ODWorklog> ();
        rowIndex = rowIndex + 2
        if (!records.get(rowIndex).get(0).matches("(.*)" + regExp + "(.*)")) {
            return logs;
        }
        worklogColumnIndexes = parseColumns(records.get(rowIndex + 1))
        rowIndex = rowIndex + 2;
        CSVRecord record = records.get(rowIndex);
        while (isValidRecord(record)) {

            Date date = new Date().parse(FORMAT2, record.get(worklogColumnIndexes.get("Date")));
            String createdBy = record.get(worklogColumnIndexes.get("Created By"));
            String summary = record.get(worklogColumnIndexes.get("Summary"));
            log.info(summary)
            logs.add(new ODWorklog(createdDate: date, createdBy: createdBy, summary:summary));
            record = records.get(++rowIndex);
        }
        return logs;

    }

    private HashMap<String, String> parseSpec(List<CSVRecord> records) {
        HashMap<String, String> specs = new HashMap<String, String>();
        rowIndex = rowIndex + 1
        if (!records.get(rowIndex).get(0).matches("(.*)All the specifications(.*)")) {
            return specs;
        }
        rowIndex = rowIndex + 2
        while (isValidRecord(records.get(rowIndex))) {
            String attribute = records.get(rowIndex).get(0)
            String value = records.get(rowIndex).get(12)
            specs.put(attribute, value)
            rowIndex++
        }
        return specs
    }
    //This will be dropped once act report is fixed
    private HashMap<String, String> parseACTSpec(List<CSVRecord> records) {
        HashMap<String, String> specs = new HashMap<String, String>();
        rowIndex = rowIndex + 1
        if (!records.get(rowIndex).get(0).matches("(.*)All the specifications(.*)")) {
            return specs;
        }
        rowIndex++
        specColumnIndexes = parseColumns(records.get(rowIndex))
        rowIndex++
        while (isValidRecord(records.get(rowIndex))) {
            String attribute = records.get(rowIndex).get(specColumnIndexes.get("Attribute"))
            String value = records.get(rowIndex).get(specColumnIndexes.get("Alphanumeric Value"))
            specs.put(attribute, value)
            rowIndex++
        }
        return specs
    }

    private def parseRelatedRecord() {
       ODActivities activity
        ODIncidents  incident
        ++rowIndex
        if (!recordsPrb.get(rowIndex).get(0).matches("(.*)related records(.*)")) {
            return null;
        }
        rowIndex=rowIndex+2
        while (isValidRecord(recordsPrb.get( rowIndex))) {
            String record = recordsPrb.get( rowIndex).get(0)
            if (record.matches("ACT(.*)")) {
               activity = ODActivities.findByTicketID(record)

            }
            else if (record.matches("INC(.*)")) {
                incident = ODIncidents.findByTicketID(record)

            }


            rowIndex++
        }

        return [activity: activity, incident:incident]

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
