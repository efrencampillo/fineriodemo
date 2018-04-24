package mx.finerio.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovementResponseModel {
    @JsonProperty("data")
    public List<Movement> data;
    @JsonProperty("size")
    public int size;
}
