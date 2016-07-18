package com.augmentis.ayp.criminalintention;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        return new CrimeFragment();
    }
}