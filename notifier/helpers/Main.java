package com.softwaregroup.digiwave.eip.components.communication.connectors.custom.kicb.notifier.helpers;

import java.sql.Date;
import java.util.ArrayList;

public class Main {
    public Main() {
        Request.Data trn1 = new Request.Data(1,"0905999999",154,"KGS","qugvb874t");
        Request.Data trn2 = new Request.Data(1,"0905999999",300,"KGS","qugvb874tasdasd");
        Partner partner = new Partner("https:google.som","google", "qwerty", "0707696409");
        var list = new ArrayList<Request.Data>();
        list.add(trn1);
        list.add(trn2);

        var xml = Request.getRequest(partner, new Date(10102019L), list);
        System.out.println(xml);
    }

    public static void main(String[] args) {
        new Main();
    }
}
