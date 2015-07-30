import com.mongodb.util.Hash
import groovy.time.TimeDuration
import operationaldashboard.ODAccess
import operationaldashboard.ODBreakFix
import operationaldashboard.ODComponent
import operationaldashboard.ODCustomer
import operationaldashboard.ODIncidents
import operationaldashboard.ODOther
import operationaldashboard.ODOwner
import operationaldashboard.ODOwner
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
    public static String DATE_FORMAT1 = "MM/dd/yy"
    public static String DATE_FORMAT2 = "MM/dd/yyyy"
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
    File ownersCsv
    File prbCSV
    File incCsv
    File components
    List<CSVRecord> recordsAct
    List<CSVRecord> recordsPrb
    List<CSVRecord> recordsInc
    List<CSVRecord> recordsRequestType
    List<CSVRecord> recordsOwners
    int columnIndex
    int rowIndex
    int prbIndex
    def emailReaderService
    HashMap<String, Integer> actColumnIndexes
    HashMap<String, Integer> prbColumnIndexes
    HashMap<String, Integer> worklogColumnIndexes
    HashMap<String, Integer> specColumnIndexes
    def grailsApplication
    def reportDataUpdaterService
    def init = { servletContext ->
          reportDataUpdaterService.initializeReports()
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

}
