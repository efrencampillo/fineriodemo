package mx.finerio.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movement {
    @JsonProperty("id")
    public String id;
    @JsonProperty("amount")
    public String amount;

    //format ISO 8601
    @JsonProperty("date")
    public String date;

    @JsonProperty("description")
    public String description;

}
