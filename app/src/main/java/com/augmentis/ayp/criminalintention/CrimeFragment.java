package com.augmentis.ayp.criminalintention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Chayanit on 7/18/2016.
 */
public class CrimeFragment extends Fragment {

    private static final String CRIME_ID = "CrimeFragment.CRIME_ID";
    private static final String CRIME_POSITION = "CrimeFragment.CRIME_POSITION";

    private Crime crime;
    private int position;

    private EditText editText;
    private Button crimeDateButton;
    private CheckBox crimeSolvedCheckBox;

    public static CrimeFragment newInstance(UUID crimeId, int position) {
        Bundle args = new Bundle();
        args.putSerializable(CRIME_ID, crimeId);
        args.putInt(CRIME_POSITION, position);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(CRIME_ID);
        position = getArguments().getInt(CRIME_POSITION);
        crime = CrimeLab.getInstance().getCrimeById(crimeId);
        Log.d(CrimeListFragment.TAG, "crime.getTitle() = " + crime.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        editText = (EditText) v.findViewById(R.id.crime_title);
        editText.setText(crime.getTitle());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                crime.setTitle(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        crimeDateButton = (Button) v.findViewById(R.id.crime_date);
        DateFormat dm = new DateFormat();
        String date = dm.format("dd MMMM yyyy", crime.getCrimeDate()).toString();
        crimeDateButton.setText(date);
        crimeDateButton.setEnabled(false);

        crimeSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        crimeSolvedCheckBox.setChecked(crime.isSolved());
        crimeSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                crime.setSolved(isChecked);
                Log.d(CrimeActivity.TAG, "Crime :" + crime.toString());
            }
        });

        Intent intent = new Intent();
        intent.putExtra("position", position);
        getActivity().setResult(Activity.RESULT_OK, intent);
        return v;
    }

    private String getFormattedDate(Date date){
        return new SimpleDateFormat("dd MMMM yyyy").format(date);
    }
}
