package it.halb.roboapp.dataLayer;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

/**
 * This repository is used to manage Regattas: it contains methods for modifying, creating
 * or deleting a Regatta.
 */
public interface RegattaInterface {


    /**
     * Return a LiveData list of all the regattas. The list is liveData, but id does not
     * sync the content automatically with the api. To trigger an update
     * call loadAllRegattas()
     *
     * Note that a Regatta object does not include the associated buoys, boats, or roboas.
     * If you want to get that data you must use the RunningRegatta repository
     *
     */
    LiveData<List<Regatta>> getAllRegattas();

    /**
     * Perform an api request to get the list of all the regattas.
     * When it succeeds, the Livedata allRegattas list will automatically update.
     * Use the method getAllRegattas() to get the Livedata List.
     */
    void loadAllRegattas(SuccessCallback<List<Regatta>> successCallback, ErrorCallback errorCallback);

    /**
     * Deletes a regatta, both locally and remotely in the api server.
     * In case of error the errorCallback will be executed. It's up to you then to refresh the list
     */
    void deleteRegatta(Regatta regatta, SuccessCallback<Void> successCallback, ErrorCallback errorCallback);

    /**
     * Create a regatta, both locally and remotely in the api server.
     * In case of error the local copy will be automatically deleted, and the ErrorCallback will execute.
     */
    @ParametersAreNonnullByDefault
    void insertRegatta(
            Regatta regatta,
            List<Buoy> buoys,
            SuccessCallback<Void> successCallback,
            ErrorCallback errorCallback
    );
}
