package com.augmentis.ayp.criminalintention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chayanit on 7/18/2016.
 */
public class CrimeListFragment extends Fragment {


    private static final int REQUEST_UPDATE_CRIME = 100;
    private static final java.lang.String SUBTITLE_VISIBLE_STATE = "SUBTITLE_VISIBLE_STATE";
    private RecyclerView _crimeRecycleView;
    private CrimeAdapter _adapter;

    protected static final String TAG = "CRIME_LIST";
    private Integer[] crimePos;

    private boolean _subtitle;
    private boolean _subtitleVisible;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.crime_list_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_show_subtitle);

        if (_subtitle) {
            menuItem.setTitle(R.string.hide_subtitle);
        } else {
            menuItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:

                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntend(getActivity(), crime.getId());
                startActivity(intent);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        _crimeRecycleView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        _crimeRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            _subtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBLE_STATE);
        }

        updateUI();
        return v;
    }

    /**
     * Update UI
     */
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (_adapter == null) {
            _adapter = new CrimeAdapter(crimes);
            _crimeRecycleView.setAdapter(_adapter);
        } else {
            _adapter.notifyDataSetChanged();
//            if (crimePos != null) {
//                for (int pos : crimePos) {
//                    _adapter.notifyItemChanged(pos);
//                }
//            }
        }
        updateSubtitle();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Resume list");
        updateUI();
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_UPDATE_CRIME) {
//            if (resultCode == Activity.RESULT_OK) {
//                crimePos = (Integer[]) data.getExtras().get("position");
//                Log.d(TAG, "get crimePos =" + crimePos);
//            }
//            Log.d(TAG, "Return form crime fragment");
//        }
//    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleTextView;
        public TextView dateTextView;
        public CheckBox solvedCheckBox;

        Crime _crime;
        int _position;

        public CrimeHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            dateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            solvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);

            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime, int position) {
            _crime = crime;
            _position = position;
            if(crime.getId() != null){
                    titleTextView.setText(_crime.getTitle());
                    solvedCheckBox.setChecked(_crime.isSolved());
                    dateTextView.setText(_crime.getCrimeDate().toString());
                }
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimePagerActivity.newIntend(getActivity(), _crime.getId());
            startActivityForResult(intent, REQUEST_UPDATE_CRIME);
            /*Toast.makeText(getActivity(), "Press!" + titleTextView.getText(),
                    Toast.LENGTH_SHORT)
                    .show();*/
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> crimes;
        private int _viewCreatingCount;

        public CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            _viewCreatingCount++;
            Log.d(TAG, "Create view holder for CrimeList: creating view time = " + _viewCreatingCount);

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(v);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Log.d(TAG, "Bind view holder for crimList : position = " + position);

            Crime crime = crimes.get(position);
            holder.bind(crime, position);
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }

    public void updateSubtitle() {

        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (!_subtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SUBTITLE_VISIBLE_STATE, _subtitleVisible);
    }
}