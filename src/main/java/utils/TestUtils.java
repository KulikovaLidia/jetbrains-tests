package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.TestException;
import org.apache.commons.io.FileUtils;
import requests.body.RequestBody;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestUtils {

    public static String createJsonStringFromPojo(RequestBody requestBody) throws TestException {
        try {
            return new ObjectMapper().writeValueAsString(requestBody);
        } catch (IOException e) {
            throw new TestException("incorrect request body !");
        }

    }

    public static String readFileToString(String name) throws TestException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        String path  = classLoader.getResource(name).getPath();
        try {
            return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TestException("Incorrect file!");
        }
    }


}
