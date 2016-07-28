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
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

/**
 * Created by Chayanit on 7/28/2016.
 */
public class TimePickerFragment extends DialogFragment implements DialogInterface.OnClickListener {

    protected static final String EXTRA_TIME = "TimePickerFragment";

    public static TimePickerFragment newInstance(Date date) {
        TimePickerFragment tf = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("ARG_TIME", date);
        tf.setArguments(args);
        return tf;
    }

    TimePicker _timePicker;
    private Date tempDate;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        tempDate = (Date) getArguments().getSerializable("ARG_TIME");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tempDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        _timePicker = (TimePicker) v.findViewById(R.id.time_picker_in_dialog);
        _timePicker.setMinute(minute);
        _timePicker.setHour(hour);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v);
        builder.setTitle(R.string.time_picker_title);
        builder.setPositiveButton(android.R.string.ok, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        int minute = _timePicker.getMinute();
        int hour = _timePicker.getHour();

        Calendar c = Calendar.getInstance();
        c.setTime(tempDate);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        Date date = c.getTime();
        sendResult(Activity.RESULT_OK, date);
    }

    private void sendResult(int resultOk, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, intent);
    }
}
