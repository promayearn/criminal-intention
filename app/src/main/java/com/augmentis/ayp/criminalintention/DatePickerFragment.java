package com.augmentis.ayp.criminalintention;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * DatePickerFragment class for date picker
 * <p/>
 * Created by Chayanit on 28-Jul-16.
 */
public class DatePickerFragment extends DialogFragment implements DialogInterface.OnClickListener {

    protected static final String EXTRA_DATE = "EXTRA_DATE";
    protected static final String ARGUMENT_DATE = "ARG_DATE";

    private Calendar _calendar;

    // 1.
    public static DatePickerFragment newInstance(Date date) {
        DatePickerFragment df = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_DATE, date);
        df.setArguments(args);
        return df;
    }

    DatePicker _datePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 3.
        Date date = (Date) getArguments().getSerializable(ARGUMENT_DATE);

        _calendar = Calendar.getInstance();
        _calendar.setTime(date);
        int year = _calendar.get(Calendar.YEAR);
        int month = _calendar.get(Calendar.MONTH);
        int dayOfMonth = _calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        _datePicker = (DatePicker) v.findViewById(R.id.date_picker_in_dialog);
        _datePicker.init(year, month, dayOfMonth, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setTitle(R.string.date_picker_title);
        builder.setPositiveButton(android.R.string.ok, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // DatePicker ---> Model
        int dayOfMonth = _datePicker.getDayOfMonth();
        int month = _datePicker.getMonth();
        int year = _datePicker.getYear();

        _calendar.set(Calendar.YEAR, year);
        _calendar.set(Calendar.MONTH, month);
        _calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Date date = _calendar.getTime();
        sendResult(Activity.RESULT_OK, date);
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}