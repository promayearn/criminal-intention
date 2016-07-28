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
import java.util.GregorianCalendar;

/**
 * Created by Chayanit on 7/28/2016.
 */
public class DatePickerFragment extends DialogFragment implements DialogInterface.OnClickListener {

    protected static final String EXTRA_DATE = "DatePickerFragment";

    public static DatePickerFragment newInstance(Date date) {
        DatePickerFragment df = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("ARG_DATE", date);
        df.setArguments(args);
        return df;
    }

    DatePicker _datePicker;
    private Date tempDate;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        tempDate = (Date) getArguments().getSerializable("ARG_DATE");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tempDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

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
    public void onClick(DialogInterface dialogInterface, int i) {
        int dayOfMonth = _datePicker.getDayOfMonth();
        int month = _datePicker.getMonth();
        int year = _datePicker.getYear();

        Calendar c = Calendar.getInstance();
        c.setTime(tempDate);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);
        Date date = c.getTime();

        sendResult(Activity.RESULT_OK, date);
    }

    private void sendResult(int resultOk, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, intent);
    }
}
