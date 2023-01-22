package requests.body;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"licenseId", "license", "contact", "sendEmail", "includeOfflineActivationCode" })

public class AssignLicenseRequestBody implements RequestBody{
    /**
     * (Required)
     */
    @JsonProperty("licenseId")
    private String licenseId;
    /**
     * License
     * <p>
     * <p>
     * (Required)
     */
    @JsonProperty("license")
    private License license;
    /**
     * Contact
     * <p>
     * <p>
     * (Required)
     */
    @JsonProperty("contact")
    private Contact contact;
    /**
     * (Required)
     */
    @JsonProperty("sendEmail")
    private Boolean sendEmail;
    /**
     * (Required)
     */
    @JsonProperty("includeOfflineActivationCode")
    private Boolean includeOfflineActivationCode;

    /**
     * (Required)
     */
    @JsonProperty("licenseId")
    public String getLicenseId() {
        return licenseId;
    }

    /**
     * (Required)
     */
    @JsonProperty("licenseId")
    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public AssignLicenseRequestBody withLicenseId(String licenseId) {
        this.licenseId = licenseId;
        return this;
    }

    /**
     * License
     * <p>
     * <p>
     * (Required)
     */
    @JsonProperty("license")
    public License getLicense() {
        return license;
    }

    /**
     * License
     * <p>
     * <p>
     * (Required)
     */
    @JsonProperty("license")
    public void setLicense(License license) {
        this.license = license;
    }

    public AssignLicenseRequestBody withLicense(License license) {
        this.license = license;
        return this;
    }

    /**
     * Contact
     * <p>
     * <p>
     * (Required)
     */
    @JsonProperty("contact")
    public Contact getContact() {
        return contact;
    }

    /**
     * Contact
     * <p>
     * <p>
     * (Required)
     */
    @JsonProperty("contact")
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public AssignLicenseRequestBody withContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    /**
     * (Required)
     */
    @JsonProperty("sendEmail")
    public Boolean getSendEmail() {
        return sendEmail;
    }

    /**
     * (Required)
     */
    @JsonProperty("sendEmail")
    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public AssignLicenseRequestBody withSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
        return this;
    }

    /**
     * (Required)
     */
    @JsonProperty("includeOfflineActivationCode")
    public Boolean getIncludeOfflineActivationCode() {
        return includeOfflineActivationCode;
    }

    /**
     * (Required)
     */
    @JsonProperty("includeOfflineActivationCode")
    public void setIncludeOfflineActivationCode(Boolean includeOfflineActivationCode) {
        this.includeOfflineActivationCode = includeOfflineActivationCode;
    }

    public AssignLicenseRequestBody withIncludeOfflineActivationCode(Boolean includeOfflineActivationCode) {
        this.includeOfflineActivationCode = includeOfflineActivationCode;
        return this;
    }

    private AssignLicenseRequestBody(){}

    public static AssignLicenseRequestBody create(){
        return new AssignLicenseRequestBody();
    }
}