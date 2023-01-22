package confg;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:TestConfg.properties")
public interface TestConfg extends Config {
    @Key("test.admin.token")
    String adminToken();

    @Key("test.viewer.token")
    String viewerToken();

    @Key("test.team_admin.token")
    String teamAdminToken();

    @Key("test.company")
    @DefaultValue("6703615")
    Integer company();

    @Key("test.api")
    @DefaultValue("https://account.jetbrains.com/api/v1/")
    String apiUrl();

    @Key("test.admin.email")
    String adminEmail();

    @Key("test.viewer.email")
    String viewerEmail();

    @Key("test.team_admin.email")
    String teamAdminEmail();
}
