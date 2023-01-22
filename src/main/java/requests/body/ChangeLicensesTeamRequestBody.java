package requests.body;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "targetTeamId",
        "licenseIds"
})
public class ChangeLicensesTeamRequestBody implements RequestBody{
    @JsonProperty("targetTeamId")
    private Long targetTeamId;
    @JsonProperty("licenseIds")
    private List<String> licenseIds = null;

    @JsonProperty("targetTeamId")
    public Long getTargetTeamId() {
        return targetTeamId;
    }

    @JsonProperty("targetTeamId")
    public void setTargetTeamId(Long targetTeamId) {
        this.targetTeamId = targetTeamId;
    }

    public ChangeLicensesTeamRequestBody withTargetTeamId(Long targetTeamId) {
        this.targetTeamId = targetTeamId;
        return this;
    }

    @JsonProperty("licenseIds")
    public List<String> getLicenseIds() {
        return licenseIds;
    }

    @JsonProperty("licenseIds")
    public void setLicenseIds(List<String> licenseIds) {
        this.licenseIds = licenseIds;
    }

    public ChangeLicensesTeamRequestBody withLicenseIds(List<String> licenseIds) {
        this.licenseIds = licenseIds;
        return this;
    }

    public ChangeLicensesTeamRequestBody withLicenseIds(String... licenseIds) {
        this.licenseIds = Arrays.asList(licenseIds);

        return this;
    }
    private ChangeLicensesTeamRequestBody(){}

    public static ChangeLicensesTeamRequestBody create(){
        return new ChangeLicensesTeamRequestBody();
    }
}
