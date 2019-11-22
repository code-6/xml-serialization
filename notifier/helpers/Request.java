package com.softwaregroup.digiwave.eip.components.communication.connectors.custom.kicb.notifier.helpers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;

/**
 * @author Stanislav Tun
 * @version 0.0.0
 * @since 22.11.2019
 * <p>
 * Class will be used for XML request serialization for partners/merchants. Request sample is described in package-info
 */
@JsonRootName(value = "NotifyPendingPayments")
public class Request {

    private String Token;

    @JacksonXmlProperty(localName = "MSISDNPayee")
    private String MSISDNPayee;

    private Date BatchDate;

    @JacksonXmlProperty(localName = "DataList")
    private List<Data> DataList;

    // temporary partners storage. todo : replace with real storage. For now unknown how it'll be stored in DGW
    @JsonIgnore
    private List<Partner> partners;

    public Request(Partner partner, Date batchDate, ArrayList<Data> transactions) {
        partners = new ArrayList<>();
        DataList = transactions;
        MSISDNPayee = partner.getMsisdn();
        BatchDate = batchDate;
        // todo : how and where from get partner password?
        Token = TokenGenerator.getToken(MSISDNPayee, transactions.size(), getTotalAmount(), partner.getPassword());
    }

    /**
     * Get request for partners systems as string XML. Used Jackson for serialize Request object
     * */
    public static String getRequest(Partner partner, Date batchDate, ArrayList<Data> transactions){
        XmlMapper mapper = new XmlMapper();
        try {
            //mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
            return mapper.writeValueAsString(new Request(partner,batchDate,transactions));
        } catch (JsonProcessingException e) {
            throw new CompletionException(e);
        }
    }

    /**
     * Iterates over transactions list and counts amount of each transaction;
     * Returns total sum of all transactions as integer. Logic was taken from current system writen on C#
     * string TokenFormat = MSISDNPayee + Rows.Count.ToString() + string.Format("{0:0.00}", Rows.Sum(row => row.Amount)).Replace(",", ".") + Password;
     * */
    private double getTotalAmount() {
        var amount = DataList.stream().mapToDouble(transaction -> transaction.getAmount()).sum();
        System.out.println("toatl amount = "+amount);
        return amount;
    }

    public String getMSISDNPayee() {
        return MSISDNPayee;
    }

    public Date getBatchDate() {
        return BatchDate;
    }

    public List<Data> getDataList() {
        return DataList;
    }

    public String getToken() {
        return Token;
    }

    private static class TokenGenerator {

        protected static String getToken(String payeeMSISDN, int totalRowsCount, double totalAmount, String password) {

            MessageDigest md5 = null;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                // ignored
            }
            var bytes = assembleString(payeeMSISDN,totalRowsCount,totalAmount,password).getBytes();
            return String.valueOf(md5.digest(bytes));
        }

        private static String assembleString(String payeeMSISDN, int totalRowsCount, double totalAmount, String password){
            return payeeMSISDN + totalRowsCount + totalAmount + password;
        }

    }

    @JsonRootName(value = "Data")
    public static class Data {
        int RowNo; // row counter
        String MSISDNPayer; // wallet number of partner ex: 0707696409
        int Amount;
        String Ccy; // currency
        String TxnID; // Transaction ID sample : PP191121.0055.C32531

        public Data(int rowNo, String MSISDNPayer, int amount, String ccy, String txnID) {
            RowNo = rowNo;
            this.MSISDNPayer = MSISDNPayer;
            Amount = amount;
            Ccy = ccy;
            TxnID = txnID;
        }

        @Override
        public String toString() {
            return getRowNo()+" "+getTxnID()+" "+getMSISDNPayer()+" "+getAmount()+" "+getCcy();
        }

        public int getRowNo() {
            return RowNo;
        }

        public String getMSISDNPayer() {
            return MSISDNPayer;
        }

        public long getAmount() {
            return Amount;
        }

        public String getCcy() {
            return Ccy;
        }

        public String getTxnID() {
            return TxnID;
        }
    }


}
