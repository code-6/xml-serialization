package com.softwaregroup.digiwave.eip.components.communication.connectors.custom.kicb.notifier;

import com.softwaregroup.digiwave.eip.components.communication.Connector;
import com.softwaregroup.digiwave.eip.components.communication.ConnectorObject;
import com.softwaregroup.digiwave.eip.components.servicebus.data.DataObject;

import java.util.concurrent.CompletableFuture;

@ConnectorObject(connectorID = "NotifyConnector")
public class NotifyConnector extends Connector {
    @Override
    protected CompletableFuture<DataObject> processRequest(String s, DataObject dataObject, String s1) {
        return null;
    }

    public void notifyPartners(){

    }
}
