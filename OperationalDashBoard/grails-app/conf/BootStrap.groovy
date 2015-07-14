import groovy.time.TimeDuration
import operationaldashboard.ODCustomer
import operationaldashboard.ODIncidents
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
    File components
    List<CSVRecord> recordsAct
    List<CSVRecord> recordsPrb
    List<CSVRecord> recordsRequestType
    List<CSVRecord> customers
    int actIndex
    int prbIndex
    def emailReaderService
    def init = { servletContext ->
        requestTypesCSV = new File("data/requestType.csv")
        recordsRequestType = CSVParser.parse(requestTypesCSV,  Charset.defaultCharset(),CSVFormat.DEFAULT ).getRecords()
//        parseRequestType()
        customersCSV = new File("data/focusCustomers.csv")
        customers = CSVParser.parse(customersCSV,  Charset.defaultCharset(),CSVFormat.DEFAULT ).getRecords()
//        parseCustomers()
        actCSV = new File("data/activites.csv")
        recordsAct = CSVParser.parse(actCSV, Charset.defaultCharset(),
                CSVFormat.DEFAULT).getRecords()

        parseACT()
        prbCSV = new File("data/problems.csv")
        recordsPrb = CSVParser.parse(prbCSV, Charset.defaultCharset(),
                CSVFormat.DEFAULT).getRecords()
        parsePRB()
//        emailReaderService.serviceMethod()
        initializeThresholds()


    }
    def destroy = {
    }
    def parseCustomers() {

        if (isRecordsEmpty(customers)) {
            return null;
        }
        long numberOfRecords = customers.size();
        for (int index = 1; index<numberOfRecords;index++) {

            CSVRecord csvRecord = customers.get(index)
            if(isValidRecord(csvRecord)) {
                log.info(csvRecord)
                def name = csvRecord.get(0).trim().replaceAll("^\\s+", "")
                new ODCustomer(name: name).save(failOnError: true)
            }
        }

    }
    def parseRequestType() {
        if (isRecordsEmpty(recordsRequestType)) {
            return null;
        }
        long numberOfRecords = recordsRequestType.size();
        for (int index = 1; index<numberOfRecords;index++) {
            CSVRecord csvRecord = recordsRequestType.get(index)
            new ODRequestType(name: csvRecord.get(0).trim()).save(failOnError: true)
        }


    }
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
    def parsePRB() {
        if (isRecordsEmpty(recordsPrb)) {
            return null;
        }
        long numberOfRecords = recordsPrb.size();
        String createdDate = recordsPrb.get((int) numberOfRecords - 1).get(0);

        for (prbIndex = 4; prbIndex < numberOfRecords; prbIndex++) {
            CSVRecord csvRecord = recordsPrb.get(prbIndex);
            //Parse the record if the record is not empty

            if (isValidRecord(csvRecord) && csvRecord.get(0).matches("PRB(.*)")) {

                String id = csvRecord.get(0)
                log.info(id + "problem ids")
                String summary = csvRecord.get(1)
                String status = csvRecord.get(5)
                Date reportedDate =  Date.parse(FORMAT2, csvRecord.get(6), TimeZone.getTimeZone("CDT"))
                Date targetFinish
                int numberOfDaysOpen
                if  (csvRecord.get(9).equals("")) {
                    targetFinish = null
                    use(groovy.time.TimeCategory) {
                        numberOfDaysOpen = (new Date() - reportedDate).days
                    }
                }
                else {
                    targetFinish = Date.parse(FORMAT2, csvRecord.get(9), TimeZone.getTimeZone("CDT"))
                    use(groovy.time.TimeCategory) {
                        numberOfDaysOpen = (targetFinish - reportedDate).days
                    }
                }
                String priority = csvRecord.get(11)


                log.info(numberOfDaysOpen)


//                Date statusDate = Date.parse(FORMAT2, csvRecord.get(11))
                String owner = csvRecord.get(13)
                String ownerGroup = csvRecord.get(15)
                String responsibleGroup = csvRecord.get(18)
                String environment = csvRecord.get(20);
//                String relatedRecord = csvRecord.get(21);
                List<ODWorklog>logs = parsePRBWorklog()
                HashMap<String, String> spec = parsePRBSpec()

                //Create a new instance of 'Activity'
                ODRequestType  type
                String requestTypeName
                if (spec.get("MRSREQUEST").equals(null)) {
                    requestTypeName = "No Attribute"
                }
                else if (spec.get("MRSREQUEST").equals("")){
                    requestTypeName = "No Value"
                }
                else {
                    requestTypeName = spec.get("MRSREQUEST").trim()

                }
                type  = ODRequestType.findOrSaveByName(requestTypeName)
                String customerName
                if (spec.get("MRSCUSTOMER").equals(null)) {
                    customerName = "No Attribute"
                }
                else if (spec.get("MRSCUSTOMER").equals("")){
                    customerName = "No Value"
                }
                else {
                    customerName = spec.get("MRSCUSTOMER").trim()

                }
                ODCustomer customer = ODCustomer.findOrSaveByName(customerName)
                parseRelatedRecord()
                def problem = new ODProblems(ticketID:
                        id, summary: summary, status: status, priority: priority, reportedDate: reportedDate, owner: owner, ownerGroup: ownerGroup, responsibleGroup: responsibleGroup, env: environment,
                        customer: customer, requestType: type, numberOfDaysOpen: numberOfDaysOpen)
                problem.save(failOnError: true)

                for (log in logs) {
                    problem.addToWorklogs(log)

                }
                problem.save(failOnError: true)


            } else {
                continue;
            }
        }
    }
    def parseACT()  {
        if (isRecordsEmpty(recordsAct)) {
            return null;
        }
        long numberOfRecords = recordsAct.size();
        String createdDate = recordsAct.get((int) numberOfRecords - 1).get(0);

        for (int index = 4; index < numberOfRecords; index++) {
            CSVRecord csvRecord = recordsAct.get(index);
            //Parse the record if the record is not empty

            if (isValidRecord(csvRecord) && csvRecord.get(0).matches("ACT(.*)")) {

                String id = csvRecord.get(0)
                String summary = csvRecord.get(1)
                String status = csvRecord.get(5)

                String priority = csvRecord.get(7)
                Date actualStart =  Date.parse(FORMAT2, csvRecord.get(8), TimeZone.getTimeZone("CDT"))
                int numberOfDaysOpen

                    DateFormat dateFormat =  DateFormat.getDateTimeInstance()
                            dateFormat.setTimeZone(TimeZone.getTimeZone("CDT"))
                     numberOfDaysOpen =  new Date() - actualStart
                    log.info(numberOfDaysOpen)


                Date statusDate = Date.parse(FORMAT2, csvRecord.get(11))
                String owner = csvRecord.get(14)
                String ownerGroup = csvRecord.get(15)
                String responsibleGroup = csvRecord.get(16)
                String environment = csvRecord.get(19);
                String relatedRecord = csvRecord.get(21);
                HashMap<String, String> spec = parseSpec(recordsAct, index + 4)
                List<ODWorklog>logs = parseACTWorklog(index + 8)
                //Create a new instance of 'Activity'
                ODRequestType  type
                String requestTypeName
                if (spec.get("MRSREQUEST").equals(null)) {
                    requestTypeName = "No Attribute"
                }
                else if (spec.get("MRSREQUEST").equals("")){
                    requestTypeName = "No Value"
                }
                else {
                    requestTypeName = spec.get("MRSREQUEST").trim()

                }
                type  = ODRequestType.findOrSaveByName(requestTypeName)
                String customerName
                if (spec.get("MRSCUSTOMER").equals(null)) {
                    customerName = "No Attribute"
                }
                else if (spec.get("MRSCUSTOMER").equals("")){
                    customerName = "No Value"
                }
                else {
                    customerName = spec.get("MRSCUSTOMER").trim()

                }
                ODCustomer customer = ODCustomer.findOrSaveByName(customerName)
               def activity = new ODActivities(ticketID:
                        id, summary: summary, status: status, priority: priority, actualStart: actualStart, statusDate: statusDate, personName: owner, ownerGroup: ownerGroup, responsibleGroup: responsibleGroup, env: environment,
                        customer: customer, requestType: type, relatedRecord: relatedRecord, numberOfDaysOpen: numberOfDaysOpen)
                activity.save(failOnError: true)

                for (log in logs) {
                    activity.addToWorklogs(log)

                }
                activity.save(failOnError: true)


            } else {
                continue;
            }
        }

    }
    private List<ODWorklog> parseACTWorklog(int index) {
        List<ODWorklog> logs = new ArrayList<ODWorklog> ();

        if (!recordsAct.get(index).get(0).matches("(.*)worklog(.*)")) {
            return logs;
        }
        index = index + 2;
        CSVRecord record = recordsAct.get(index);
        while (isValidRecord(record)) {

            Date date = new Date().parse(FORMAT2, record.get(0));
            String createdBy = record.get(3);
            String summary = record.get(6);

            logs.add(new ODWorklog(createdDate: date, createdBy: createdBy, summary:summary));
            record = recordsAct.get(++index);
        }
        return logs;

    }
    private List<ODWorklog> parsePRBWorklog() {
        List<ODWorklog> logs = new ArrayList<ODWorklog> ();
        prbIndex = prbIndex + 2
        if (!recordsPrb.get(prbIndex).get(0).matches("(.*)work log(.*)")) {
            return logs;
        }
        prbIndex = prbIndex + 2;
        CSVRecord record = recordsPrb.get(prbIndex);
        while (isValidRecord(record)) {

            Date date = new Date().parse(FORMAT2, record.get(0));
            String createdBy = record.get(3);
            String summary = record.get(7);

            logs.add(new ODWorklog(createdDate: date, createdBy: createdBy, summary:summary));
            record = recordsPrb.get(++prbIndex);
        }
        return logs;

    }
    private HashMap<String, String> parsePRBSpec() {
        prbIndex = prbIndex + 3
        HashMap<String, String> specs = new HashMap<String, String>();
        while (isValidRecord(recordsPrb.get( prbIndex))) {
            String attribute = recordsPrb.get( prbIndex).get(0)
            String value = recordsPrb.get( prbIndex).get(12)

            specs.put(attribute, value)
            prbIndex++
        }
//        String customer = recordsAct.get(index).get(12);
//        String component = recordsAct.get(index + 1).get(12);
//        String request = recordsAct.get(index + 2).get(12);
//        specs.put("customer", customer);
//        specs.put("component", component);
//        specs.put("request", request);

        return specs
    }
    private def parseRelatedRecord() {
       ODActivities activity
        ODIncidents  incident
        prbIndex++
        if (!recordsPrb.get(prbIndex).get(0).matches("(.*)related records(.*)")) {
            return null;
        }
        prbIndex=prbIndex+2
        while (isValidRecord(recordsPrb.get( prbIndex))) {
            String record = recordsPrb.get( prbIndex).get(0)
            if (record.matches("ACT(.*)")) {
               activity = ODActivities.findByTicketID(record)

            }
            else if (record.matches("INC(.*)")) {
                incident = ODIncidents.findByTicketID(record)

            }

            prbIndex++
        }

        return [activity: activity, incident:incident]

    }
    private HashMap<String, String> parseSpec(List<CSVRecord> records, index) {
        HashMap<String, String> specs = new HashMap<String, String>();
        while (isValidRecord(records.get(index))) {
            String attribute = records.get(index).get(0)
            String value = records.get(index).get(12)

                specs.put(attribute, value)
            index++
        }
//        String customer = recordsAct.get(index).get(12);
//        String component = recordsAct.get(index + 1).get(12);
//        String request = recordsAct.get(index + 2).get(12);
//        specs.put("customer", customer);
//        specs.put("component", component);
//        specs.put("request", request);

        return specs
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
