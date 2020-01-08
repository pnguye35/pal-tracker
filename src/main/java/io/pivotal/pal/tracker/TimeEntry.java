package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {
    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    public TimeEntry(long projectId, long userId, LocalDate parse, int i) {
        this.projectId      =   projectId;
        this.userId         =   userId;
        this.date           =   parse;
        this.hours          =   i;
    }
    public TimeEntry(long id, long projectId, long userId, LocalDate parse, int i) {
        this.id             =   id;
        this.projectId      =   projectId;
        this.userId         =   userId;
        this.date           =   parse;
        this.hours          =   i;
    }

    public TimeEntry() {

    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        TimeEntry objTE =   (TimeEntry) obj;

        if (this.projectId != objTE.getProjectId()) {
            System.out.println("Project ID's don't match : " + this.projectId + ":" + objTE.projectId);
            return false;
        }
        if (this.userId != objTE.getUserId()) {
            System.out.println("userId ID's don't match : " + this.userId + ":" + objTE.userId);
            return false;
        }
        if (!this.date.equals(objTE.getDate())) {
            System.out.println("Date's don't match : " + this.date + ":" + objTE.date);
            return false;
        }
        if (this.hours != objTE.getHours()) {
            System.out.println("Hours's don't match : " + this.hours + ":" + objTE.hours);
            return false;
        }
        System.out.println("looks like they're equal!");
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (projectId ^ (projectId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + hours;
        return result;
    }
}
