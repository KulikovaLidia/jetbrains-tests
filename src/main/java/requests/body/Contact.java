package requests.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"firstName", "lastName", "email" })
public class Contact {

    /**
     * (Required)
     */
    @JsonProperty("firstName")
    private String firstName;
    /**
     * (Required)
     */
    @JsonProperty("lastName")
    private String lastName;
    /**
     * (Required)
     */
    @JsonProperty("email")
    private String email;

    /**
     * (Required)
     */
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * (Required)
     */
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Contact withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * (Required)
     */
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * (Required)
     */
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Contact withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * (Required)
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * (Required)
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public Contact withEmail(String email) {
        this.email = email;
        return this;
    }

    private Contact(){}

    public static Contact create() {
        return new Contact();
    }

}