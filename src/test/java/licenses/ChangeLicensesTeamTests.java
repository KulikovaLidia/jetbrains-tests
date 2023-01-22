package licenses;

import confg.TestConfg;
import exceptions.TestException;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.body.ChangeLicensesTeamRequestBody;

import java.util.*;
import java.util.stream.Stream;

import static data.enums.Headers.COMPANY;
import static data.enums.Headers.TOKEN;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static utils.TestUtils.createJsonStringFromPojo;
import static utils.TestUtils.readFileToString;

public class ChangeLicensesTeamTests {
    private static final String METHOD = "customer/changeLicensesTeam";
    private static final String NO_LICENSE_MOVED = createExpectedResponseBody("");
    private static final Long INACTIVE_TEAM = 2046529L;
    private static final Long ACTIVE_TEAM002 = 2281034L;
    private static final Long ACTIVE_TEAM003 = 2275473L;
    private static final Long ACTIVE_TEAM001 = 2220010L;

    private static String adminToken;
    private static String viewerToken;
    private static String teamAdminToken;
    private static String company;
    private static String apiUrl;

    @BeforeAll
    static void setUp() {
        /**
         * With JUnit5 assumptions assume that licenses are set up in the following way:
         * QZBDFF880H is expired and is under team id = 2220010, product WS
         * RIQIET072L is active and assigned is under team id = 2220010, product WS
         * 321LX0RHB5 is active and is under team id = 2220010, product WS
         * 2NDKEQZ1ZS is active and is under team id = 2220010, product PC
         * T5JTU1RVR6 is Space On-Premises Beta license and is under team id = 2281034L
         * OTN0X7CSQ0 is active and under team id = 2220010, product WS
         * IRXNYY173X is active and under team id = 2220010, product WS
         * 0RNZ1QPXVH is active and under team id = 2220010, product AR
         * U886DF2U2D is expired and is under team id = 2046529, product DG
         */
        TestConfg cfg = ConfigFactory.create(TestConfg.class);
        adminToken = cfg.adminToken();
        viewerToken = cfg.viewerToken();
        teamAdminToken = cfg.teamAdminToken();
        company = String.valueOf(cfg.company());
        apiUrl = cfg.apiUrl();
    }

    static Stream<Arguments> testDataProvider() throws TestException {
        return Stream.of(
                arguments("Move empty licenses to inactive team", adminToken, INACTIVE_TEAM, Arrays.asList("", ""), 400, NO_LICENSE_MOVED),
                arguments("Move no existed licenses to inactive team", adminToken, INACTIVE_TEAM, Arrays.asList("BLABLA1", "BLABLA2"), 400, NO_LICENSE_MOVED),
                arguments("Move 'null' licenses to inactive team", adminToken, INACTIVE_TEAM, Arrays.asList(null, null), 400, NO_LICENSE_MOVED),
                arguments("Move no licenseIds node to inactive team", adminToken, INACTIVE_TEAM, null, 400, readFileToString("errors/BadRequestError.html")),
                arguments("Move no licenses to inactive team", adminToken, INACTIVE_TEAM, new ArrayList<>(), 400, NO_LICENSE_MOVED),
                arguments("Move one expired license to inactive team", adminToken, INACTIVE_TEAM, Arrays.asList("QZBDFF880H"), 200, createExpectedResponseBody("QZBDFF880H")),
                arguments("Move assigned, unassigned, different product licenses to active team", adminToken, ACTIVE_TEAM002, Arrays.asList("RIQIET072L", "321LX0RHB5", "2NDKEQZ1ZS"), 200, createExpectedResponseBody("2NDKEQZ1ZS\",\"321LX0RHB5\",\"RIQIET072L")),
                arguments("Move different product licenses to invalid team", adminToken, 666L, Arrays.asList("RIQIET072L", "321LX0RHB5", "2NDKEQZ1ZS"), 404, readFileToString("errors/TeamNotFoundError.json")),
                arguments("Move beta license to an active team", adminToken, ACTIVE_TEAM002, Arrays.asList("T5JTU1RVR6"), 200, createExpectedResponseBody("T5JTU1RVR6")),
                arguments("Move same licenses to an active team", adminToken, ACTIVE_TEAM002, Arrays.asList("OTN0X7CSQ0", "OTN0X7CSQ0"), 200, createExpectedResponseBody("OTN0X7CSQ0")),
                arguments("Move license to the same team", adminToken, ACTIVE_TEAM001, Arrays.asList("IRXNYY173X"), 200, NO_LICENSE_MOVED),
                arguments("Move license from inactive team", adminToken, ACTIVE_TEAM001, Arrays.asList("285J9BOJ06"), 200, createExpectedResponseBody("285J9BOJ06")),
                arguments("Move license under viewer", viewerToken, ACTIVE_TEAM001, Arrays.asList("IRXNYY173X"), 403, readFileToString("errors/TokenTypeMismatchError.json")),
                arguments("Move license under invalid token", "BAD_TOKEN", ACTIVE_TEAM001, Arrays.asList("IRXNYY173X"), 401, readFileToString("errors/InvalidTokenError.json")),
                arguments("Move license to inactive team under who has access to active team", teamAdminToken, INACTIVE_TEAM, Arrays.asList("0RNZ1QPXVH"), 403, readFileToString("errors/TokenTypeMismatchError.json")),
                arguments("Move license from inactive to active team under team admin who has access to active team", teamAdminToken, ACTIVE_TEAM001, Arrays.asList("U886DF2U2D"), 403, readFileToString("errors/TokenTypeMismatchError.json")),
                arguments("Move license from active to another team under team admin who has access to both teams", teamAdminToken, ACTIVE_TEAM003, Arrays.asList("47BBHIY5CH"), 200, createExpectedResponseBody("47BBHIY5CH")), //BUG works using UI
                arguments("Move Toolbox Enterprise EAP license (non transferable license?)", adminToken, ACTIVE_TEAM002, Arrays.asList("HEKZ83CJQM"), 403, readFileToString("errors/TokenTypeMismatchError.json"))
                // TODO: Test Case: Move to team from another organisation
                // TODO: Test Case: Move revoked license
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("testDataProvider")
    void changeEmptyLicensesTeamTest(String description, String token, Long teamId, List<String> licenseIds, int code, String responseBody) throws TestException {
        given().headers(TOKEN.getHttpHeader(), token, COMPANY.getHttpHeader(), company)
                .body(createJsonStringFromPojo(createExpectedRequestBody(teamId, licenseIds)))
                .post(apiUrl + METHOD)
                .then().assertThat()
                .statusCode(code)
                .body(equalToCompressingWhiteSpace(responseBody));

        //TODO: add validation of license,license's attributes and team in database

    }

    @AfterAll
    static void rollback() throws TestException {
        //TODO: instead should be rollback using DB requests!!!
        given().headers(TOKEN.getHttpHeader(), adminToken, COMPANY.getHttpHeader(), company)
                .body(createJsonStringFromPojo(createExpectedRequestBody(ACTIVE_TEAM001, List.of("RIQIET072L", "321LX0RHB5", "RIQIET072L", "2NDKEQZ1ZS",
                        "QZBDFF880H", "OTN0X7CSQ0", "IRXNYY173X", "0RNZ1QPXVH", "47BBHIY5CH", "HEKZ83CJQM"))))
                .post(apiUrl + METHOD);
        given().headers(TOKEN.getHttpHeader(), adminToken, COMPANY.getHttpHeader(), company)
                .body(createJsonStringFromPojo(createExpectedRequestBody(ACTIVE_TEAM003, List.of("T5JTU1RVR6"))))
                .post(apiUrl + METHOD);
        given().headers(TOKEN.getHttpHeader(), adminToken, COMPANY.getHttpHeader(), company)
                .body(createJsonStringFromPojo(createExpectedRequestBody(INACTIVE_TEAM, List.of("285J9BOJ06"))))
                .post(apiUrl + METHOD);

    }

     private static ChangeLicensesTeamRequestBody createExpectedRequestBody(Long teamId, List<String> licenses) {
        return ChangeLicensesTeamRequestBody.create()
                .withTargetTeamId(teamId)
                .withLicenseIds(licenses);
    }

    private static String createExpectedResponseBody(String license){
        if (license == null || license.isEmpty()){
            return "{\"licenseIds\":[]}";
        }
        return "{\"licenseIds\":[\""+license+"\"]}";
    }
}
