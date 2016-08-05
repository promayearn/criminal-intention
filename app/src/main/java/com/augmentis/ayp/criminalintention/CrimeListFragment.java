package com.augmentis.ayp.criminalintention;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.augmentis.ayp.criminalintention.model.Crime;
import com.augmentis.ayp.criminalintention.model.CrimeLab;

import java.util.List;

/**
 * Created by Chayanit on 18-Jul-16.
 */
public class CrimeListFragment extends Fragment {

    private static final String TAG = "CrimeListFragment";
    private static final java.lang.String SUBTITLE_VISIBLE_STATE = "SUBTITLE_VISIBLE";

    private RecyclerView _crimeRecyclerView;
    private View _zeroItemView;

    private CrimeListAdapter _adapter;
    private boolean _subtitleVisible;
    private Callbacks callbacks;

    public interface Callbacks {
        void onCrimeSelected(Crime crime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callbacks = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        _zeroItemView = v.findViewById(R.id.zero_item_view);

        _crimeRecyclerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        _crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            _subtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBLE_STATE);
        } else {
            _subtitleVisible = false;
        }

        Log.d(TAG, "Subtitle show = " + String.valueOf(_subtitleVisible));

        updateUI();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (CrimeLab.getInstance(getActivity()).getCrimes().size() != 0) {
            callbacks.onCrimeSelected(CrimeLab.getInstance(getActivity()).getCrimes().get(0));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.crime_list_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_show_subtitle);

        Log.d(TAG, "Creation of Menu");
        if (_subtitleVisible) {
            menuItem.setIcon(R.drawable.criminal);
            menuItem.setTitle(R.string.hide_subtitle);
        } else {
            menuItem.setIcon(R.drawable.criminal);
            menuItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:

                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime);

                updateUI();
                callbacks.onCrimeSelected(crime);
                return true;

            case R.id.menu_item_show_subtitle:
                _subtitleVisible = !_subtitleVisible;
                getActivity().invalidateOptionsMenu();

                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        int crimeCount = crimeLab.getCrimes().size();

        // plurals
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_format, crimeCount, crimeCount);

        if (!_subtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle whatever) {
        super.onSaveInstanceState(whatever);

        whatever.putBoolean(SUBTITLE_VISIBLE_STATE, _subtitleVisible);
    }

    /**
     * Update UI
     */
    public void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (_adapter == null) {
            _adapter = new CrimeListAdapter(this, crimes);
            _crimeRecyclerView.setAdapter(_adapter);
        } else {
            _adapter.setCrimes(crimeLab.getCrimes());
            _adapter.notifyDataSetChanged();
        }

        updateZeroView(crimes.size() == 0);
        updateSubtitle();
    }

    private void updateZeroView(boolean visible) {
        if (visible) {
            _zeroItemView.setVisibility(View.VISIBLE);
            _crimeRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            _crimeRecyclerView.setVisibility(View.VISIBLE);
            _zeroItemView.setVisibility(View.INVISIBLE);
        }
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d(TAG, "Resume list");
//        updateUI();
//    }
}