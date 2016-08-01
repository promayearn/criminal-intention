package com.augmentis.ayp.criminalintention;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Chayanit on 7/18/2016.
 */
public class CrimePagerActivity extends SingleFragmentActivity {

    private UUID _crimeId;
    protected static final String CRIME_ID = "crimeActivity.crimeID";

    @Override
    protected Fragment onCreateFragment() {
        _crimeId = (UUID) getIntent().getSerializableExtra(CRIME_ID);
        return CrimeFragment.newInstance(_crimeId);
    }

    public static Intent newIntend(Context context, UUID id) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(CRIME_ID, id);
        return intent;
    }
}