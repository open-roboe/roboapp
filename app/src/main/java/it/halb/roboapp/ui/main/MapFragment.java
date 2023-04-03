package it.halb.roboapp.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.databinding.FragmentMapBinding;
import it.halb.roboapp.util.BuoyFactory;
import it.halb.roboapp.util.Constants;
import it.halb.roboapp.util.RegattaController;


public class MapFragment extends Fragment{

    private FragmentMapBinding binding;
    private SupportMapFragment supportmapfragment;
    Context c;
    private MapViewModel model;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

            MutableLiveData<Regatta> race = new MutableLiveData<>();
            MutableLiveData<List<Buoy>> buoy = new MutableLiveData<>();

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
        model = new ViewModelProvider(requireActivity()).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);
        //TEST
        //TODO: remove
        Log.d("VIEWMODEL_SCOPING_TEST", " " + model.TEST);

        //model listeners
        model.getMapFocusLocation().observe(getViewLifecycleOwner(), location -> {
            if(location != null && !(location.getLongitude() == 0.0 && location.getLatitude() == 0.0) ){
                supportmapfragment.getMapAsync(googleMap1 -> {
                    googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                            location.getLatitude(),
                            location.getLongitude()),15));
                });
            }
        });
        model.getNavigationTargetReadableName().observe(getViewLifecycleOwner(), name -> {
            Log.d("OBS_", "target changed " + name);
            if(name == null){
                // There is no navigation target
            }else{
                // Set navigation target UI
            }
        });
    }


}