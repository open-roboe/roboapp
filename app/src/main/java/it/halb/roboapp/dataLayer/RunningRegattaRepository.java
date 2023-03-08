package it.halb.roboapp.dataLayer;

import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class RunningRegattaRepository {

    /**
     * Return the regatta currently running
     */
    public void getRegatta(){

    }

    public void getAllBuoys(){

    }

    public void getAllBoats(){

    }

    public void getJury(){

    }

    public void getAllRoboas(){

    }

    public void getCurrentPosition(){

    }

    /**
     * define class status:
     * lastupdate, lasterror, offline(deriva da lastupdate)
     */
    public void getStatus(){}

    /**
     * Declare that you want to run the provided regatta.
     * This method will set the Regatta attribute, and delete all the other attributes.
     *
     * If this operation is successful, you can actually run the regatta by launching the RunningRegattaService
     *
     * @param regatta the regatta that you want to run
     */
    public void DeclareRegattaToRun(Regatta regatta){

    }

    /**
     * Completely reset the running regatta, removing every object.
     * called by declareRegattaToRun()
     *
     */
    private void ResetRunningRegatta(){

    }

    /**
     * This method is called periodically by the RunningRegattaService.
     * It updates the running regatta information.
     */
    public void poll(){

    }

}
