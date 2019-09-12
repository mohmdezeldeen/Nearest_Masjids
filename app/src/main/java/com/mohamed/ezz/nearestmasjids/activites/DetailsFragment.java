package com.mohamed.ezz.nearestmasjids.activites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamed.ezz.nearestmasjids.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment {

    public static final String EXTRA_MASJID_NAME = "extra_masjid_name";
    public static final String EXTRA_MASJID_DISTANCE = "extra_masjid_distance";


    @BindView(R.id.tvMasjidName)
    TextView tvMasjidName;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.tvDirections)
    TextView tvDirections;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.bind(this, rootView);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String masjidName = arguments.getString(EXTRA_MASJID_NAME);
            String distance = arguments.getString(EXTRA_MASJID_DISTANCE);
            tvMasjidName.setText(masjidName);
            tvDistance.setText(distance);
        }

        tvDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "لا توجد اتجاهات حاليا لهذ المسجد !", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
