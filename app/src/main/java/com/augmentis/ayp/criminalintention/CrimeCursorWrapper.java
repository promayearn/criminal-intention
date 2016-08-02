package com.augmentis.ayp.criminalintention;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.augmentis.ayp.criminalintention.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Chayanit on 8/1/2016.
 */
public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        int issolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setCrimeDate(new Date(date));
        crime.setSolved(issolved != 0);
        crime.setSuspect(suspect);

        return crime;
    }
}
