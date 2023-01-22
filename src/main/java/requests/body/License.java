package requests.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"team", "productCode" })

public class License {

    /**
     * (Required)
     */
    @JsonProperty("team")
    private Long team;
    /**
     * (Required)
     */
    @JsonProperty("productCode")
    private String productCode;

    /**
     * (Required)
     */
    @JsonProperty("team")
    public Long getTeam() {
        return team;
    }

    /**
     * (Required)
     */
    @JsonProperty("team")
    public void setTeam(Long team) {
        this.team = team;
    }

    public License withTeam(Long team) {
        this.team = team;
        return this;
    }

    /**
     * (Required)
     */
    @JsonProperty("productCode")
    public String getProductCode() {
        return productCode;
    }

    /**
     * (Required)
     */
    @JsonProperty("productCode")
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public License withProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    private License(){

    }

    public static License create() {
        return new License();
    }

}