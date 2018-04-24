package mx.finerio.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Me {
    @JsonProperty("id")
    public String id;
    @JsonProperty("accountExpired")
    public boolean accountExpired;
    @JsonProperty("accountLocked")
    public boolean accountLocked;
    @JsonProperty("birthdate")
    public String birthdate;
    @JsonProperty("customerId")
    public int customerId;
    @JsonProperty("dateCreated")
    public String dateCreated;
    @JsonProperty("email")
    public String email;
    @JsonProperty("enabled")
    public boolean enabled;
    @JsonProperty("gender")
    public String gender;
    @JsonProperty("lastName")
    public String lastName;
    @JsonProperty("name")
    public String name;
}
