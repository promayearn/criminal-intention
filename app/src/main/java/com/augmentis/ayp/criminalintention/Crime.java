package com.augmentis.ayp.criminalintention;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Chayanit on 7/18/2016.
 */
public class Crime {

    private UUID uuid;
    private String title;
    private Date crimeDate;
    private boolean solved;

    public Crime() {
        uuid = UUID.randomUUID();
        crimeDate = new Date();
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCrimeDate() {
        return crimeDate;
    }

    public void setCrimeDate(Date crimeDate) {
        this.crimeDate = crimeDate;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UUID=").append(uuid);
        builder.append(", Title=").append(title);
        builder.append(", Crime Date=").append(crimeDate);
        builder.append(", Solved=").append(solved);
        return builder.toString();
    }
}
