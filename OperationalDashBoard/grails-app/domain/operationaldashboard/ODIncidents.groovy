package operationaldashboard

class ODIncidents {

        String ticketID
        String summary
        String relatedACT
        String relatedPRB
        boolean prbFound
        boolean actFound

        static constraints = {
            ticketID unique:true
            relatedACT blank: true, nullable: true
            relatedPRB blank: true, nullable: true
            summary blank:true


        }



    }
