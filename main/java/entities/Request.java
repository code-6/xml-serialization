package entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.Map;

@JsonRootName(value = "request")
public class Request {
    private Auth auth;
    // used instead of separate class. Solution for dynamic request creation
    private HashMap<String, IRequestType> providers;

    public Request(IRequestType type) {
        // todo : change hardcode to get values from config file
        auth = new Auth("stt16gua", "qwerty123","MD5");
        providers = new HashMap<>();
        providers.put(type.getRequestName(),type);

    }

    public Auth getAuth() {
        return auth;
    }

    public HashMap<String, IRequestType> getProviders() {
        return providers;
    }

    public static class Auth {
        @JacksonXmlProperty(isAttribute = true)
        private String login, sign, signAlg;

        public Auth(String login, String sign, String signAlg) {
            this.login = login;
            this.sign = sign;
            this.signAlg = signAlg;
        }

        public String getLogin() {
            return login;
        }

        public String getSign() {
            return sign;
        }

        public String getSignAlg() {
            return signAlg;
        }
    }

    /*public static class Providers {
        private Map<String, IRequestType> providers;
        @JsonIgnore
        private String name;
        public Providers(IRequestType type) {
            name = type.getRequestName();
            providers = new HashMap<String, IRequestType>();
            providers.put(name,type);
        }

        public Map<String, IRequestType> getProviders() {
            return providers;
        }
    }*/

}
