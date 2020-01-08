package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{
    private HashMap<Long, TimeEntry> timeEntryHashMap = new HashMap<Long, TimeEntry>();
    private Long currentId  = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        Long id =   currentId++;
        TimeEntry returnEntry   =   new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        timeEntryHashMap.put(id, returnEntry);
        return returnEntry;
    }

    @Override
    public TimeEntry find(Long id) {
        return timeEntryHashMap.get(id);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        if (!timeEntryHashMap.containsKey(id)) {
            return null;
        }
        TimeEntry returnTimeEntry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        timeEntryHashMap.put(id, returnTimeEntry);
        return returnTimeEntry;
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<TimeEntry>(timeEntryHashMap.values());
    }

    @Override
    public void delete(Long id) {
        timeEntryHashMap.remove(id);
    }
}
