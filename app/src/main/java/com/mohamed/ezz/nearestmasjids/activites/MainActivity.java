package com.mohamed.ezz.nearestmasjids.activites;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mohamed.ezz.nearestmasjids.R;
import com.mohamed.ezz.nearestmasjids.locationutils.PermissionUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.mohamed.ezz.nearestmasjids.CommonUtils.displayPromptForEnablingGPS;
import static com.mohamed.ezz.nearestmasjids.CommonUtils.isLocationEnabled;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<String> permissions = new ArrayList<>();
    private PermissionUtils permissionUtils;
    private FusedLocationProviderClient client;

    private double latitude;
    private double longitude;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        permissionUtils = new PermissionUtils(MainActivity.this);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        client = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragmentContainer) != null) {
            MainFragment mainFragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putDouble(mainFragment.EXTRA_LATITUDE, latitude);
            bundle.putDouble(mainFragment.EXTRA_LONGITUDE, longitude);
            mainFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, mainFragment).commit();
        }
        if (!isLocationEnabled(MainActivity.this)) {
            displayPromptForEnablingGPS(MainActivity.this);
        } else {
            useGoogleServicesToFetchLocation();
        }
    }

    private void useGoogleServicesToFetchLocation() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
        boolean available = permissionUtils.check_permission_ok(permissions, getResources().getString(R.string.txt_app_need_permission), 1);

        if (available) {
            client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            switch (requestCode) {
                case 1:
                    useGoogleServicesToFetchLocation();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
