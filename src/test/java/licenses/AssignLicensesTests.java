package licenses;

import confg.TestConfg;
import data.enums.Product;
import exceptions.TestException;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.body.AssignLicenseRequestBody;
import requests.body.Contact;
import requests.body.License;

import java.util.stream.Stream;

import static data.enums.Headers.COMPANY;
import static data.enums.Headers.TOKEN;
import static data.enums.Product.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static utils.TestUtils.createJsonStringFromPojo;
import static utils.TestUtils.readFileToString;

public class AssignLicensesTests {
    public static final String METHOD = "customer/licenses/assign";
    public static final Long INACTIVE_TEAM = 2046529L;
    public static final Long ACTIVE_TEAM002 = 2281034L;
    public static final Long ACTIVE_TEAM001 = 2220010L;

    private static String adminToken;
    private static String viewerToken;
    private static String teamAdminToken;
    private static String company;
    private static String apiUrl;

    private static String email1;

    private static String email2;

    @BeforeAll
    static void setUp() {
        /**
         * With JUnit5 assumptions assume or select required test licenses from DB
         * "RIQIET072L" must be assigned and active
         * "N9IX2NWHK5" must be assigned and expired
         * "QZBDFF880H" must be unassigned and expired
         * "4IKF3UKGD4" must be unassigned and active
         * "AYUP1280JG" must be unassigned and active
         * all rest licenses used in tests must be unassigned.
         *
         * For now before running these tests go to CRM and revoke licenses : "V0IEECXWP2", "SK8TMYX9WU", "APT5835PAX", "AYUP1280JG"
         */

        TestConfg cfg = ConfigFactory.create(TestConfg.class);
        adminToken = cfg.adminToken();
        viewerToken = cfg.viewerToken();
        teamAdminToken = cfg.teamAdminToken();
        company = String.valueOf(cfg.company());
        apiUrl = cfg.apiUrl();
        email1 = cfg.adminEmail();
        email2 = cfg.viewerEmail();
    }

    static Stream<Arguments> testDataProvider() throws TestException {
        return Stream.of(
                arguments("Assign license", adminToken, 2220010L, "V0IEECXWP2", email1, WEB_STORM, 200, ""),
                arguments("Assign license that was assigned before", adminToken, ACTIVE_TEAM001, "RIQIET072L", email2, WEB_STORM, 400, readFileToString("errors/AssignedLicenseError.json")),
                arguments("Assign license that does not exist", adminToken, ACTIVE_TEAM001, "BLABLABLA", email2, WEB_STORM, 404, readFileToString("errors/LicenseNotFoundError.json")),
                arguments("Assign license from another product", adminToken, ACTIVE_TEAM001, "SK8TMYX9WU", email2, CLION, 400, readFileToString("errors/LicenseNotFoundError.json")),
                arguments("Assign license from invalid product", adminToken, ACTIVE_TEAM001, "APT5835PAX", email2, NOT_EXISTED, 400, readFileToString("errors/LicenseNotFoundError.json")),
                arguments("Assign license to invalid email", adminToken, ACTIVE_TEAM001, "FXEHBS168L", "BLABLABLA", WEB_STORM, 400, readFileToString("errors/InvalidEmailError.json")),
                arguments("Assign expired assigned license in inactive team", adminToken, INACTIVE_TEAM, "N9IX2NWHK5", email1, INTELLIJ_IDEA, 400, readFileToString("errors/AssignedLicenseError.json")),
                arguments("Assign expired unassigned license in active team", adminToken, ACTIVE_TEAM001, "QZBDFF880H", email1, INTELLIJ_IDEA, 400, readFileToString("errors/ExpiredLicenseError.json")),
                arguments("Assign license with invalid token", "BAD_TOKEN", ACTIVE_TEAM001, "QZBDFF880H", email1, WEB_STORM, 401, readFileToString("errors/InvalidTokenError.json")),
                arguments("Assign license with viewer token", viewerToken, ACTIVE_TEAM001, "2NDKEQZ1ZS", email1, PY_CHARM, 403, readFileToString("errors/InsufficientPermissionsError.json")),
                arguments("Assign license with token from another team", viewerToken, INACTIVE_TEAM, "B6UH10FYL0", email1, WEB_STORM, 403, readFileToString("errors/TeamMismatchTokenError.json")),
                arguments("Assign license from active team with team admin token", teamAdminToken, ACTIVE_TEAM001, "AYUP1280JG", email1, WEB_STORM, 200, ""),
                arguments("Assign license from another team with team admin token", viewerToken, ACTIVE_TEAM002, "4IKF3UKGD4", email1, WEB_STORM, 403, readFileToString("errors/TeamMismatchTokenError.json"))
                // TODO: test case "Assign previously revoked license to the same user/ to another user"
        );

    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("testDataProvider")
    void assignLicenseTest(String description, String token, Long teamId, String licenseId, String email, Product product, int code, String response) throws TestException {
        given().headers(TOKEN.getHttpHeader(), token, COMPANY.getHttpHeader(), company)
                .body(createJsonStringFromPojo(createExpectedRequestBody(teamId, licenseId, email, product)))
                .post(apiUrl + METHOD)
                .then().assertThat()
                .statusCode(code)
                .body(is(response));

        //TODO: add validation of license and its attributes in DB
    }

    private AssignLicenseRequestBody createExpectedRequestBody(Long teamId, String licenseId, String email, Product product) {
        License license = License.create()
                .withTeam(teamId)
                .withProductCode(product.getCode());

        Contact contact = Contact.create().withFirstName("TestName")
                .withLastName("TestSurname")
                .withEmail(email);

        AssignLicenseRequestBody assignLicense = AssignLicenseRequestBody.create().withLicense(license)
                .withContact(contact)
                .withLicenseId(licenseId)
                .withSendEmail(false)
                .withIncludeOfflineActivationCode(false);

        return assignLicense;
    }
}
