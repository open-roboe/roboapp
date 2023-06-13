package it.halb.roboapp.ui.main;

import static it.halb.roboapp.util.Constants.SENSOR_SAMPLING_RATE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.databinding.FragmentMapBinding;
import it.halb.roboapp.util.BuoyFactory;
import it.halb.roboapp.util.CompassUtil;
import it.halb.roboapp.util.Constants;
import it.halb.roboapp.util.RegattaController;


public class MapFragment extends Fragment implements SensorEventListener {
    private FragmentMapBinding binding;
    private SupportMapFragment supportmapfragment;
    Context c;
    private MapViewModel model;
    private SensorManager sensorManager;
    private boolean firstRegatta = false; //andra tolto una volta risolto il problema nella tabella current regatta
    private boolean firstBuoys = false; //andra tolto una volta risolto il problema nella tabella current regatta

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
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
            RegattaController.getInstance().setMap(googleMap);

            model.getRegatta().observe(getViewLifecycleOwner(), regatta -> {
                if (firstRegatta) {
                    return;
                }
                if (regatta != null) {
                    RegattaController.getInstance().setRegatta(regatta);
                    firstRegatta=true;
                    Log.d("Buoys", " model.getRegatta().observe execute");
                }
            });

            model.getBuoys().observe(getViewLifecycleOwner(), buoys -> {
                if (firstBuoys) {
                    return;
                }
                if (buoys != null) {
                    RegattaController.getInstance().setBuoys(buoys);
                    RegattaController.getInstance().setCourse();
                    Log.d("Buoys", " model.getBuoys().observe execute");
                    firstBuoys=true;
                }
            });

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {

                    for (String s:Constants.StringArray
                    ) {
                        if(s.equals(marker.getTitle()))
                        {
                            model.setTarget(marker.getTitle());

                            return true;
                        }
                    }
                    return false;
                }
            });
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ViewModel initialization.
        // It is scoped to the navigation graph, so that it will be shared between all
        // the runningRegatta fragments and it will be cleared when navigating back to the regatta lists fragment.
        NavBackStackEntry store = NavHostFragment.findNavController(this)
                .getBackStackEntry(R.id.main_navigation);
        model = new ViewModelProvider(store).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);

        //sensors initialization
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            // Success! There's a magnetometer.
            model.hasSensors = true;
        }

        //model listeners
        model.getMapFocusLocation().observe(getViewLifecycleOwner(), location -> {
            if (location != null && !(location.getLongitude() == 0.0 && location.getLatitude() == 0.0)) {
                supportmapfragment.getMapAsync(googleMap1 -> {
                    googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                            location.getLatitude(),
                            location.getLongitude()), 15));
                    Log.d("Buoys", " model.getMapFocusLocation().observe execute");
                });
            }
        });
        model.getCurrentLocation().observe(getViewLifecycleOwner(), location -> {
            //show a grey compass when the location is not available
            if (location == null || (location.getLatitude() == 0.0 && location.getLongitude() == 0.0)) {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);  //0 means grayscale
                ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
                binding.compass.setColorFilter(cf);
                binding.compass.setImageAlpha(160);
            } else {
                binding.compass.setColorFilter(null);
                binding.compass.setImageAlpha(255);
            }

            if(!(model.getTargetLocation() == null))
            {
                float dist = location.distanceTo(model.getTargetLocation());
                model.setDistanceToTarget(dist);
                binding.dustanceToTarget.setVisibility(View.VISIBLE);

            }else{

                binding.dustanceToTarget.setVisibility(View.INVISIBLE);

            }
        });

        model.getNavigationTargetReadableName().observe(getViewLifecycleOwner(), name -> {
            Log.d("OBS_", "target changed " + name);
            if (name == null) {
                // hide navigation target UI
                binding.topAppBarCard.setVisibility(View.INVISIBLE);
                binding.compass.setVisibility(View.INVISIBLE);
                //hide the compass. An animation is required to cancel out the previous rotation animation
                binding.compass.animate()
                        .rotation(100)
                        .scaleY(0f)
                        .scaleX(0f)
                        .setInterpolator(new AccelerateInterpolator())
                        .setDuration(100);
            } else {
                // Set navigation target UI
                binding.topAppBarCard.setVisibility(View.VISIBLE);
                binding.compass.setVisibility(View.VISIBLE);
            }
            //reset the angle filter
            model.angleFilterData = 0;
        });


        //view listeners
        binding.topAppBar.setNavigationOnClickListener(l -> {
            model.clearTarget();
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SENSOR_SAMPLING_RATE, SENSOR_SAMPLING_RATE);
        }

        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SENSOR_SAMPLING_RATE, SENSOR_SAMPLING_RATE);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //make sensor readings smoother using a low pass filter
            CompassUtil.lowPassFilter(event.values.clone(), model.accelerometerReading);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            //make sensor readings smoother using a low pass filter
            CompassUtil.lowPassFilter(event.values.clone(), model.magnetometerReading);
        }
        updateCompass();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // method required by the interface
    }

    public void updateCompass() {
        //compass smoothness configuration.
        //change these values to affect the way the compass moves.
        float ANGLE_CAP = 2.5f;
        long ANIMATION_DURATION = 100;
        //get repository data
        Location targetLocation = model.getTargetLocation();
        Location currentLocation = model.getCurrentLocationValue();

        //don't show the compass if there is no target or current location
        if (targetLocation == null) {
            Log.d("COMPASS", "no target location");
            return;
        }
        if (currentLocation == null) {
            Log.d("COMPASS", "no current location");
            return;
        }
        if (targetLocation.getLatitude() == 0.0 && targetLocation.getLongitude() == 0.0) {
            Log.d("COMPASS", "invalid target location");
            return;
        }
        if (currentLocation.getLatitude() == 0.0 && currentLocation.getLongitude() == 0.0) {
            Log.d("COMPASS", "invalid current location");
            return;
        }

        //calculate heading
        float heading = CompassUtil.calculateHeading(model.accelerometerReading, model.magnetometerReading);
        heading = CompassUtil.convertRadtoDeg(heading);
        heading = CompassUtil.map180to360(heading);

        //calculate magnetic declination
        //TODO: debug, may be useless. we are keeping it disabled for now
        /*
        float currentLatitude = (float) currentLocation.getLatitude();
        float currentLongitude = (float) currentLocation.getLongitude();
        float currentAltitude = (float) currentLocation.getAltitude();
        float magneticDeclination = CompassUtil.calculateMagneticDeclination(currentLatitude, currentLongitude, currentAltitude);
        heading = heading + magneticDeclination;
         */

        //calculate target angle
        //TODO
        // heading * heading + targetAngle


        if (heading > 360) heading = heading - 360;

        float bearing = currentLocation.bearingTo(targetLocation);


        float angle = heading + bearing;

        //to avoid flickering, skip the animation if the angle is not significantly different from the current angle
        if (model.angleFilterData != 0 && Math.abs(angle - model.angleFilterData) < ANGLE_CAP) {
            return;
        } else {
            model.angleFilterData = angle;
        }

        //compensate device rotation, as explained here
        // https://android-developers.googleblog.com/2010/09/one-screen-turn-deserves-another.html
        if (binding.getRoot().getDisplay() != null) {
            int rotationMode = binding.getRoot().getDisplay().getRotation();
            if (rotationMode == Surface.ROTATION_90)
                angle += 90;
            if (rotationMode == Surface.ROTATION_270)
                angle += 270;
        }

        //rotate the compass with an animation
        Animation rotate = new RotateAnimation(
                model.initialCompassDegree,
                -angle,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(ANIMATION_DURATION);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new LinearInterpolator());
        binding.compass.startAnimation(rotate);
        model.initialCompassDegree = -angle;
    }

}