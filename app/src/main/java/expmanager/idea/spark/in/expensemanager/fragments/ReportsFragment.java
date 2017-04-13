package expmanager.idea.spark.in.expensemanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import expmanager.idea.spark.in.expensemanager.R;

/**
 * Created by Ramana.Reddy on 4/13/2017.
 */

public class ReportsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.reports_layout,
                container, false);

        return rootView;
    }
}
