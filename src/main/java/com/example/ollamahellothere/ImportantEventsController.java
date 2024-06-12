package com.example.ollamahellothere;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ImportantEventsController {

    private final ImportantEventsService importantEventsService;

    @PostMapping("/api/events")
    public String getEvents(@RequestBody EventBody eventBody) {
        return importantEventsService.getImportantEventsByDate(eventBody.eventDate);
    }

    @Data
    public static final class EventBody {
        private String eventDate;
    }
}
