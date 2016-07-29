package com.augmentis.ayp.criminalintention;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Chayanit on 7/18/2016.
 */
public class CrimeLab {

    private static final String TAG = "CrimeLab";
    List<Crime> crimeList;

    private static CrimeLab instance;

    public static CrimeLab getInstance(Context context) {
        if (instance == null) {
            instance = new CrimeLab();
        }
        return instance;
    }

    private CrimeLab() {
        crimeList = new ArrayList<>();
    }

    public Crime getCrimeById(UUID uuid) {
        for (Crime crime : crimeList) {
            if (crime.getId().equals(uuid)) {
                return crime;
            }
        }
        return null;
    }

    public int getCrimePositionById(UUID uuid) {
        int size = crimeList.size();
        for (int i = 0; i < size; i++) {
            if (crimeList.get(i).getId().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public List<Crime> getCrimes() {
        return this.crimeList;
    }

    public static void main(String[] args) {
        CrimeLab crimeLab = CrimeLab.getInstance(null);
        List<Crime> crimeList = crimeLab.getCrimes();
        int size = crimeList.size();
        for (int i = 0; i < size; i++) {
            System.out.println(crimeList.get(i));
        }
        System.out.println(crimeLab.toString());
    }

    public void addCrime(Crime crime) {
        crimeList.add(crime);
    }

    public void deleteCrime(Crime crime) {
        for (int i = 0; i < crimeList.size(); i++) {
            if (crimeList.get(i).getId().equals(crime.getId())) {
                Log.d(TAG, "Crime Delete " + crimeList.get(i).getTitle());
                crimeList.remove(i);
                break;
            }
        }
    }
}
