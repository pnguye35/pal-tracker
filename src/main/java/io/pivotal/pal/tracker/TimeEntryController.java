package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(
            TimeEntryRepository timeEntryRepository,
            MeterRegistry meterRegistry
    ) {
        this.timeEntryRepository = timeEntryRepository;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }
    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry =   timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.CREATED);
    }
    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry returnTimeEntry   =   timeEntryRepository.find(timeEntryId);
        HttpStatus returnStatus     =   (returnTimeEntry == null) ? HttpStatus.NOT_FOUND: HttpStatus.OK ;
        actionCounter.increment();
        return new ResponseEntity<TimeEntry>(returnTimeEntry, returnStatus);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);
    }
    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry newTimeEntry) {
        actionCounter.increment();
        TimeEntry returnTimeEntry = timeEntryRepository.update(timeEntryId, newTimeEntry);
        HttpStatus returnStatus     =   (returnTimeEntry == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity( returnTimeEntry, returnStatus);
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        HttpStatus returnStatus;
        actionCounter.increment();
        try {
            timeEntryRepository.delete(timeEntryId);
            returnStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            returnStatus = HttpStatus.NOT_FOUND;
        }
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(returnStatus);
    }
}
