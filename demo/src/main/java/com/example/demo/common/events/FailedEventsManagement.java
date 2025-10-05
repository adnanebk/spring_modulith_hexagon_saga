package com.example.demo.common.events;

import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class FailedEventsManagement {

    private final IncompleteEventPublications incompleteEventPublications;

    public FailedEventsManagement(IncompleteEventPublications incompleteEventPublications) {
        this.incompleteEventPublications = incompleteEventPublications;
    }


    @Scheduled(fixedRate = 3000)
    public void retry() {
        incompleteEventPublications.resubmitIncompletePublications(filter->true);
    }
}
