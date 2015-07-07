import operationaldashboard.ODCustomer
import operationaldashboard.ODRequestType
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

import operationaldashboard.ODActivities
import operationaldashboard.ODWorklog

import java.nio.charset.Charset

class BootStrap {
    public static String FORMAT1 = "MM-dd-yyyy hh:mm:ss"
    public static String FORMAT2 = "MM/dd/yyyy"
    public static String FORMAT3 = "dd MMM yyyy"
    public static String FORMAT4 = "dd MMM yyyy hh:mm"
    public static String FORMAT5 = "yyyy-MM-dd hh:mm:ss"
    File actCSV
    File requestTypesCSV
    File customersCSV
    File components
    List<CSVRecord> recordsAct
    List<CSVRecord> recordsRequestType
    List<CSVRecord> customers
    def init = { servletContext ->
        requestTypesCSV = new File("data/requestType.csv")
        recordsRequestType = CSVParser.parse(requestTypesCSV,  Charset.defaultCharset(),CSVFormat.DEFAULT ).getRecords()
        parseRequestType()
        customersCSV = new File("data/focusCustomers.csv")
        customers = CSVParser.parse(customersCSV,  Charset.defaultCharset(),CSVFormat.DEFAULT ).getRecords()
        parseCustomers()
        actCSV = new File("data/activites.csv")
        recordsAct = CSVParser.parse(actCSV, Charset.defaultCharset(),
                CSVFormat.DEFAULT).getRecords()

        parseACT()
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
                new ODCustomer(name: csvRecord.get(0).trim()).save(failOnError: true)
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
            new ODRequestType(name: csvRecord.get(0)).save(failOnError: true)
        }


    }
    /*This function determines whether the list of recordsAct returned from the CSV reader is empty
     ** Returns Boolean
     */
    def isRecordsEmpty(List<CSVRecord> records) {
        return (records.size() > 0) ? false : true
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
                Date actualStart =  new Date().parse(FORMAT2, csvRecord.get(8))
                Date statusDate = new Date().parse(FORMAT2, csvRecord.get(11))
                String owner = csvRecord.get(14)
                String ownerGroup = csvRecord.get(15)
                String responsibleGroup = csvRecord.get(16)
                String environment = csvRecord.get(19);
                String relatedRecord = csvRecord.get(21);
                HashMap<String, String> spec = parseACTSpec(index + 4)
                List<ODWorklog>logs = parseACTWorklog(index + 8)
                //Create a new instance of 'Activity'

               def activity = new ODActivities(ticketID:
                        id, summary: summary, status: status, priority: priority, actualStart: actualStart, statusDate: statusDate, personName: owner, ownerGroup: ownerGroup, responsibleGroup: responsibleGroup, env: environment,
                        customer: spec.get("customer"), requestType: spec.get("request"), relatedRecord: relatedRecord)
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
    private HashMap<String, String> parseACTSpec(final int index) {
        HashMap<String, String> specs = new HashMap<String, String>();
        String customer = recordsAct.get(index).get(12);
        String component = recordsAct.get(index + 1).get(12);
        String request = recordsAct.get(index + 2).get(12);
        specs.put("customer", customer);
        specs.put("component", component);
        specs.put("request", request);

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
