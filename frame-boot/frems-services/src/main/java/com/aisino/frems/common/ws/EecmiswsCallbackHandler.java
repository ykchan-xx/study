
/**
 * EecmiswsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.aisino.frems.common.ws;

/**
 *  EecmiswsCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class EecmiswsCallbackHandler{



    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public EecmiswsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public EecmiswsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

    public Object getClientData() {
        return clientData;
    }


    /**
     * auto generated Axis2 call back method for login method
     * override this method for handling normal response from login operation
     */
    public void receiveResultlogin(
            EecmiswsStub.LoginResponse result
    ) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from login operation
     */
    public void receiveErrorlogin(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for unimethod method
     * override this method for handling normal response from unimethod operation
     */
    public void receiveResultunimethod(
            EecmiswsStub.UnimethodResponse result
    ) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from unimethod operation
     */
    public void receiveErrorunimethod(Exception e) {
    }

    /**
     * auto generated Axis2 call back method for loginNew method
     * override this method for handling normal response from loginNew operation
     */
    public void receiveResultloginNew(
            EecmiswsStub.LoginNewResponse result
    ) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from loginNew operation
     */
    public void receiveErrorloginNew(Exception e) {
    }



}