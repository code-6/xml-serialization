package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Value;
public class CheckPaymentRequisites implements IRequestType {
    @JacksonXmlProperty
    private Payment payment;
    @JacksonXmlProperty
    private To to;

    @JsonIgnore
    private final String requestName = "checkPaymentRequisites";

    public CheckPaymentRequisites(String id, String service, String account, double amount) {
        payment = new Payment(id);
        to = new To(service,account,amount);
    }

    @Override
    public String getRequestName() {
        return requestName;
    }

    // nested classes
    public static class Payment{
        @JacksonXmlProperty(isAttribute = true)
        private String id;

        public Payment(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public static class To{
        @JacksonXmlProperty(isAttribute = true)
        private String service, account;
        @JacksonXmlProperty(isAttribute = true)
        private double amount;

        public To(String service, String account, double amount) {
            this.service = service;
            this.account = account;
            this.amount = amount;
        }

        public double getAmount() {
            return amount;
        }

        public String getAccount() {
            return account;
        }

        public String getService() {
            return service;
        }
    }
}
