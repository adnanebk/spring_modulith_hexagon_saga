package com.example.demo.common.events;

import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class FailedEventsManagement {

    private final IncompleteEventPublications incompleteEventPublications;
    private final Map<UUID,Integer> attempts = new ConcurrentHashMap<>();
    private final int maxAttempts = 3;
    private final long maxDurationInSeconds = 20;

    public FailedEventsManagement(IncompleteEventPublications incompleteEventPublications) {
        this.incompleteEventPublications = incompleteEventPublications;
    }


    @Scheduled(fixedRate = 5000)
    public void retry() {
        incompleteEventPublications.resubmitIncompletePublications(filter->{
           UUID id =  filter.getIdentifier();
          attempts.put(id,attempts.getOrDefault(id,0)+1);
            Instant publicationDate = filter.getPublicationDate();
            long durationInSeconds = publicationDate.until(Instant.now(), ChronoUnit.SECONDS);
            return attempts.get(id)<=maxAttempts && durationInSeconds<=maxDurationInSeconds;
        });
    }
}
