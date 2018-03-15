package de.virtual7.reactivelabclient.controller;

import de.virtual7.reactivelabclient.events.TrackingEvent;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * Created by mihai.dobrescu
 */
@RestController
@RequestMapping("/client")
public class ClientEventController {

    Logger logger = LoggerFactory.getLogger(ClientEventController.class);


    @GetMapping(value = "/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<TrackingEvent> getTrackingEvents() {

        Flux<TrackingEvent> trackingEventFlux = WebClient.create("http://localhost:8999")
                .get()
                .uri("/events/latest")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(TrackingEvent.class);

        trackingEventFlux.log().subscribe(new BaseSubscriber<TrackingEvent>() {

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(5);
            }

            @Override
            protected void hookOnNext(TrackingEvent value) {
                System.out.print(value);
            }
        });

        return trackingEventFlux;
    }

    @GetMapping("/groupedEvents")
    public String getTrackingEventsGrouped() {

        return "OK"; //TODO: return something meaningful :)
    }


}
