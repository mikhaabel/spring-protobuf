package com.techtalks.springprotobuf;

import com.googlecode.protobuf.format.JsonFormat;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.techtalks.springprotobuf.OfferProtobuf.Envelope;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringProtobufApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringProtobufApplicationIT {

    private static final String URL = "http://localhost:8080/envelopes/1";

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void when_using_rest_template_then_succeed() {
        ResponseEntity<Envelope> envelope = restTemplate.getForEntity(URL, Envelope.class);
        assertResponse(envelope.toString());
    }

    @Test
    public void when_using_http_client_then_succeed() throws IOException {
        InputStream responseStream = executeHttpRequest(URL);
        String jsonOutput = convertProtobufMessageStreamToJsonString(responseStream);
        assertResponse(jsonOutput);
    }

    private InputStream executeHttpRequest(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(request);
        return httpResponse.getEntity().getContent();
    }

    private String convertProtobufMessageStreamToJsonString(InputStream protobufStream) throws IOException {
        JsonFormat jsonFormat = new JsonFormat();
        Envelope offerEnvelope = Envelope.parseFrom(protobufStream);
        return jsonFormat.printToString(offerEnvelope);
    }

    private void assertResponse(String response) {
        assertThat(response, containsString("id"));
        assertThat(response, containsString("description"));
        assertThat(response, containsString("Offer envelope full"));
        assertThat(response, containsString("offer"));
        assertThat(response, containsString("small_description"));
        assertThat(response, containsString("large_description"));
        assertThat(response, containsString("reply"));
        assertThat(response, containsString("Call large desc"));
        assertThat(response, containsString("Contact me large desc"));
        assertThat(response, containsString("cta"));
        assertThat(response, containsString("text"));
        assertThat(response, containsString("type"));
    }
}
