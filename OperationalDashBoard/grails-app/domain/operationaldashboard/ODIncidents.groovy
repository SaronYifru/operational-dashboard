package operationaldashboard

class ODIncidents {

        String ticketID
        String relatedACT
        String relatedPRB

        static constraints = {
            ticketID unique:true
            relatedACT blank: true, nullable: true
            relatedPRB blank: true, nullable: true


        }



    }
