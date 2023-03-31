package it.halb.roboapp.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.databinding.FragmentLoginBinding;
import it.halb.roboapp.databinding.FragmentMapBinding;
import it.halb.roboapp.ui.main.MapViewModel;
import it.halb.roboapp.util.BuoyFactory;
import it.halb.roboapp.util.Constants;
import it.halb.roboapp.util.RegattaController;


public class MapFragment extends Fragment{

    private FragmentMapBinding binding;
    private SupportMapFragment supportmapfragment;
    Context c;

    public MapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);
        c = this.getContext();

        supportmapfragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        supportmapfragment.getMapAsync(googleMap -> {

            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);

            Regatta regatta = new Regatta("regatta_name", Constants.triangleRegatta, 0,
                    10, 100.1, 0, 1000, 1500,
                    false, false);
            regatta.setLatLng(new LatLng(45.5141865,9.2109231));

            List<Buoy> buoys = BuoyFactory.buildCourse(regatta);

            MutableLiveData<Regatta> race = new MutableLiveData<Regatta>();
            MutableLiveData<List<Buoy>> buoy = new MutableLiveData<List<Buoy>>();

            race.setValue(regatta);
            buoy.setValue(buoys);
            RegattaController.getInstance().setMap(googleMap);
            RegattaController.getInstance().setRegatta(race);
            RegattaController.getInstance().setBuoys(buoy);
            RegattaController.getInstance().setCourse();

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.5141865,9.2109231),15));
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        MapViewModel model = new ViewModelProvider(this).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);

        model.getTargetLocation().observe(getViewLifecycleOwner(), location -> {
            Log.d("prova location", "" + location.getLatitude() + " " + location.getLongitude());
            //Log.d("prova location 2", "" + model.getTargetBoat().getLatitude() + " " + model.getTargetBoat().getLongitude());
            try {
                supportmapfragment.getMapAsync(googleMap1 -> {
                    googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                            location.getLatitude(),
                            location.getLongitude()),15));
                });

            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }


}