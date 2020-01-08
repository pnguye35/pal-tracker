package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository    =   timeEntryRepository;
    }
    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry =   timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.CREATED);
    }
    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry returnTimeEntry   =   timeEntryRepository.find(timeEntryId);
        HttpStatus returnStatus     =   (returnTimeEntry == null) ? HttpStatus.NOT_FOUND: HttpStatus.OK ;
        return new ResponseEntity<TimeEntry>(returnTimeEntry, returnStatus);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);
    }
    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry newTimeEntry) {
        TimeEntry returnTimeEntry = timeEntryRepository.update(timeEntryId, newTimeEntry);
        HttpStatus returnStatus     =   (returnTimeEntry == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity( returnTimeEntry, returnStatus);
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        HttpStatus returnStatus;
        try {
            timeEntryRepository.delete(timeEntryId);
            returnStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            returnStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(returnStatus);
    }
}
