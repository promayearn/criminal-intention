package com.augmentis.ayp.criminalintention;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.Date;

/**
 * Created by Chayanit on 8/4/2016.
 */
public class ImageFragment extends DialogFragment implements DialogInterface.OnClickListener {

    protected static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    protected static final String ARGUMENT_IMAGE = "ARG_IMAGE";

    private ImageView imageView;
    private Bitmap _bitmap;

    public ImageFragment(Bitmap bitmap) {
        _bitmap = bitmap;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_image, null);
        imageView= (ImageView) v.findViewById(R.id.crime_image_dialog);
        imageView.setImageBitmap(_bitmap);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setPositiveButton(android.R.string.ok, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
    }

}
