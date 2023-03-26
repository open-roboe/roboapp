package it.halb.roboapp.dataLayer;


import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.dataLayer.localDataSource.RunningStatus;


/**
 * This repository is used to manage the ongoing regatta and allows you to obtain the list of buoys,
 * boats, roboas, and other information about this regatta.
 *
 * It is a special repository because it can only be used within the screens associated
 * with an ongoing or "running regatta":
 *   MapFragment
 *   BuoyInfoFragment
 *   BoatInfoFragment
 *   RoboaInfoFragment
 *
 * For more information Check out the schema in the RunningRegattaRepositoryMock documentation
 */
public interface RunningRegattaInterface {

    /**
     * @return the regatta currently running
     */
    LiveData<Regatta> getRegatta();

    /**
     *
     * @return all the buoys of the current regatta
     */
    LiveData<List<Buoy>> getBuoys();

    /**
     * Return a list with all the boats currently following the regatta.
     * the position of every boat will update in real time, and so will the lastUpdate timer.
     * When a boat has an old lastUpdate time it should be considered temporarily offline.
     * Boats offline for too long will be automatically removed from this list
     *
     * @return all the boats of the current regatta
     */
    LiveData<List<Boat>> getBoats();

    /**
     * Return a list with all the roboas that are currently online.
     * Note that an online roboa might be connected to another regatta.
     * It's up to you to handle this
     *
     * @return all the online roboas
     */
    LiveData<List<Roboa>> getRoboas();

    /**
     * Return the status data for the current regatta, which includes
     * the time of the last sync with the server, the last error, the current regatta id,
     * the current position.
     *
     * Note: you probably don't need this. All this data is returned separately from other methods
     *       such as getError(), getLocation()
     *
     * @return the status object for the current Regatta
     */
    LiveData<RunningStatus> getStatus();

    /**
     * During its sync process, several things can go wrong.
     * When something goes wrong it will update this error String livedata with an
     * internationalized description of the error. Internationalized means that you can
     * directly print it in the ui
     *
     * This data should be printed to the user
     *
     * @return A string containing a localized description of an error
     */
    LiveData<String> getError();

    /**
     * @return the last time the app successfully synced state with the server
     */
    LiveData<Date> getLastUpdate();

    /**
     * The current location, updated every time it changes.
     * If you need to access the phone location this is the easiest way to get it.
     *
     * @return The current location
     */
    LiveData<Location> getLocation();

    /**
     * The app is in sync if it managed to communicate with the server in the last few seconds.
     * This is an useful information to display to the user
     *
     * @return true if the app managed to sync state with the server in the last few seconds
     */
    LiveData<Boolean> isInSync();


    /**
     * This method MUST be called from the RunRegattaFragment ONLY.
     *
     * Declare that you want to run the provided regatta.
     * This method will set the RunningStatus object, which defines the current regatta.
     *
     * If this operation is successful, RunRegattaFragment MUST start the RunningRegattaService
     * which will manage the polling with the api server
     *
     * For more information Check out the schema in the RunningRegattaRepositoryMock documentation
     *
     * @param name the name of the regatta you want to run
     */
    void declareRegattaToRun(@NonNull String name,
                                    SuccessCallback<Void> success, ErrorCallback error);


    /**
     * This method MUST be called from the RunningRegattaService ONLY.
     *
     * This method is called periodically by the RunningRegattaService.
     * It updates the running regatta information.
     */
    void poll(Double lat, Double lon);

    /**
     * Set the error message that will be displayed in real time in the app.
     *
     * @param error_code an internationalized string describing the error
     */
    void setError(String error_code);

}
