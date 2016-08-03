package com.augmentis.ayp.criminalintention;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augmentis.ayp.criminalintention.model.Crime;

import java.util.List;

/**
 * Created by Chayanit on 28-Jul-16.
 */
public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListViewHolder> {
    private static final String TAG = "CrimeListAdapter";
    private Fragment _f;
    private List<Crime> _crimes;
    private int _viewCreatingCount;

    public CrimeListAdapter(Fragment f, List<Crime> crimes) {
        _crimes = crimes;
        _f = f;
    }

    protected void setCrimes(List<Crime> crimes) {
        _crimes = crimes;
    }

    @Override
    public CrimeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        _viewCreatingCount++;
        Log.d(TAG, "Create view holder for CrimeList: creating view time= " + _viewCreatingCount);

        LayoutInflater layoutInflater = LayoutInflater.from(_f.getActivity());
        View v = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
        return new CrimeListViewHolder(_f, v);
    }

    @Override
    public void onBindViewHolder(CrimeListViewHolder holder, int position) {
        Log.d(TAG, "Bind view holder for CrimeList : position = " + position);

        Crime crime = _crimes.get(position);
        holder.bind(crime);
    }

    @Override
    public int getItemCount() {
        return _crimes.size();
    }
}