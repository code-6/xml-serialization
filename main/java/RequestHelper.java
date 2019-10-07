import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import entities.*;

public class RequestHelper {
    private static final XmlMapper mapper = new XmlMapper();

    public RequestHelper() {

    }

    public static String getAuthRequest(String id, String service, String account, double amount) throws JsonProcessingException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return mapper.writeValueAsString(new Request(new CheckPaymentRequisites(id,service,account,amount)));
    }
}
