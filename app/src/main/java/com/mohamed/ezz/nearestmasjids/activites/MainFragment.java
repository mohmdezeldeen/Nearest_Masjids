package com.mohamed.ezz.nearestmasjids.activites;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.mohamed.ezz.nearestmasjids.CommonUtils;
import com.mohamed.ezz.nearestmasjids.R;
import com.mohamed.ezz.nearestmasjids.adapters.MasjidAdapter;
import com.mohamed.ezz.nearestmasjids.database.AppDatabase;
import com.mohamed.ezz.nearestmasjids.models.Masjid;
import com.mohamed.ezz.nearestmasjids.services.ApiService;
import com.mohamed.ezz.nearestmasjids.viewmodels.MasjidViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.LinearLayout.VERTICAL;

public class MainFragment extends Fragment implements MasjidAdapter.ItemClickListener {

    private static final String TAG = MainFragment.class.getSimpleName();
    private AppDatabase db;
    private MasjidAdapter masjidAdapter;

    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tvSBCurrentNo)
    TextView tvSBCurrentNo;

    @BindView(R.id.rvMasjids)
    RecyclerView rvMasjids;

    @BindView(R.id.llNoMasjid)
    LinearLayout llNoMasjid;

    private int radius = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, rootView);
        final FragmentActivity fragmentBelongActivity = getActivity();
        masjidAdapter = new MasjidAdapter(fragmentBelongActivity, this);
        rvMasjids.setLayoutManager(new LinearLayoutManager(fragmentBelongActivity));
        rvMasjids.setAdapter(masjidAdapter);
        seekBar.setProgress(radius);

        tvSBCurrentNo.setText(radius + " KM");
        if (CommonUtils.checkConnection(fragmentBelongActivity))
            new FetchNearestMasjidsTask(fragmentBelongActivity).execute();
        else
            Snackbar.make(fragmentBelongActivity.findViewById(android.R.id.content), getString(R.string.error_internet_connection), Snackbar.LENGTH_LONG).show();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSBCurrentNo.setText(progress + " KM");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (CommonUtils.checkConnection(fragmentBelongActivity))
                    new FetchNearestMasjidsTask(fragmentBelongActivity).execute();
                else
                    Snackbar.make(fragmentBelongActivity.findViewById(android.R.id.content), getString(R.string.error_internet_connection), Snackbar.LENGTH_LONG).show();
            }
        });
        DividerItemDecoration decoration = new DividerItemDecoration(fragmentBelongActivity, VERTICAL);
        rvMasjids.addItemDecoration(decoration);
        db = AppDatabase.getInstance(fragmentBelongActivity);
        setupViewModel();


//        if (rootView != null) {
//
//
//            // Click this button will show the text in right fragment.
//            Button androidButton = (Button) rootView.findViewById(R.id.fragmentLeftButtonAndroid);
//            androidButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    // Do not use fragmentBelongActivity.getFragmentManager() method which is not compatible with older android os version. .
//                    FragmentManager fragmentManager = fragmentBelongActivity.getSupportFragmentManager();
//
//                    // Get right Fragment object.
//                    Fragment detailsFragment = fragmentManager.findFragmentById(R.id.detailsFragment);
//
//                    // Get the TextView object in right Fragment.
//                    final TextView rightFragmentTextView = (TextView) detailsFragment.getView().findViewById(R.id.fragmentRightTextView);
//
//                    // Set text in right Fragment TextView.
//                    rightFragmentTextView.setText("You click Android button.");
//                }
//            });
//
//
//            // Click this button will show a Toast popup.
//            Button iosButton = (Button) rootView.findViewById(R.id.fragmentLeftButtonIos);
//            iosButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(fragmentBelongActivity, "You click IOS button.", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//            // Click this button will show an alert dialog.
//            Button windowsButton = (Button) rootView.findViewById(R.id.fragmentLeftButtonWindows);
//            windowsButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(fragmentBelongActivity).create();
//                    alertDialog.setMessage("You click Windows button.");
//                    alertDialog.show();
//                }
//            });
//        }

        return rootView;
    }

    private void setupViewModel() {
        MasjidViewModel viewModel = ViewModelProviders.of(this).get(MasjidViewModel.class);
        viewModel.getMasjids().observe(this, new Observer<List<Masjid>>() {
            @Override
            public void onChanged(List<Masjid> masjids) {
                Log.d(TAG, "Updating list of masjids from LiveData in ViewModel");
                if (masjids.size() <= 0) {
                    llNoMasjid.setVisibility(View.VISIBLE);
                    rvMasjids.setVisibility(View.GONE);
                } else {
                    llNoMasjid.setVisibility(View.GONE);
                    rvMasjids.setVisibility(View.VISIBLE);
                    masjidAdapter.setMasjidsList(masjids);
                }
            }
        });
    }


    @Override
    public void onItemClickListener(int itemId) {
        db.masjidDao().loadMasjidById(itemId).observe(this, new Observer<Masjid>() {
            @Override
            public void onChanged(Masjid masjid) {
                boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
                if (tabletSize) {
                    MainActivity.fragmentManager.beginTransaction().add(R.id.fragmentContainer2, new DetailsFragment(), null).commit();
                } else {
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new DetailsFragment(), null).commit();
                }

            }
        });

    }

    public class FetchNearestMasjidsTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        FragmentActivity fragmentActivity;

        public FetchNearestMasjidsTask(FragmentActivity fragmentActivity) {
            this.fragmentActivity = fragmentActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(fragmentActivity);
            dialog.setTitle("برجاء الإنتظار ..");
            dialog.setMessage("جاري تحميل البيانات ..");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            ApiService.fetchDataFromServer(db, 29.97162, 31.11374, radius);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }

}
