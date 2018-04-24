package mx.finerio.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseModel {
    @JsonProperty("username")
    public String username;
    @JsonProperty("token_type")
    public String token_type;
    @JsonProperty("access_token")
    public String access_token;
}
