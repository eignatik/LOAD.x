package org.loadx.application.controllers;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.loadx.application.constants.JsonBodyConstants;
import org.loadx.application.processor.TaskProcessor;
import org.loadx.application.processor.tasks.TaskCreator;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class NGLoadControllerTest {

    @Mock private NGLoadController controller;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(controller.generateHashForOwnership(Mockito.any())).thenCallRealMethod();
    }

    @Test
    public void testHashGenReturnsSuccessAndGeneratedHash() {
        String expectedHash = "f01034c5969e9e72069d5f7501939cf6";
        Map<String, String> payload = ImmutableMap.of(JsonBodyConstants.URL, "www.example.com");

        ResponseEntity response = controller.generateHashForOwnership(payload);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), expectedHash);
    }

    @Test
    public void testHashGenReturnsBadRequestAndContainsErrorMessage() {
        Map<String, String> payload = ImmutableMap.of(JsonBodyConstants.URL, "");

        ResponseEntity response = controller.generateHashForOwnership(payload);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertNotNull(response.getBody());
    }

}