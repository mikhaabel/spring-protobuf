package com.techtalks.springprotobuf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import com.techtalks.springprotobuf.OfferProtobuf.Envelope;
import com.techtalks.springprotobuf.OfferProtobuf.Offer;
import com.techtalks.springprotobuf.OfferProtobuf.Offer.CallToAction;
import com.techtalks.springprotobuf.OfferProtobuf.Offer.CallToActionType;

@SpringBootApplication
public class SpringProtobufApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProtobufApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
        return new RestTemplate(Arrays.asList(hmc));
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    public OfferRepository createTestEnvelopes() {
        Map<Integer, Envelope> envelopes = new HashMap<>();

        Envelope envelope1 = Envelope.newBuilder()
                .setId(1)
                .setDescription("Offer envelope full")
                .addAllOffer(createTestOffers())
                .build();
        Envelope envelope2 = Envelope.newBuilder()
                .setId(2)
                .setDescription("Offer envelope empty")
                .addAllOffer(new ArrayList<>())
                .build();

        envelopes.put(envelope1.getId(), envelope1);
        envelopes.put(envelope2.getId(), envelope2);

        return new OfferRepository(envelopes);
    }

    private List<Offer> createTestOffers() {
        CallToAction cta1 = createCallToAction("Call me", CallToActionType.CALL);
        Offer offer1 = createOffer(1, "Call small desc", "Call large desc", "Reply to call", Arrays.asList(cta1));

        CallToAction cta2 = createCallToAction("Contact me", CallToActionType.CONTACT_US);
        Offer offer2 = createOffer(2, "Contact me small desc", "Contact me large desc", "Reply to contact me", Arrays.asList(cta2));

        return Arrays.asList(offer1, offer2);
    }

    private Offer createOffer(int id, String smallDescription, String largeDescription, String reply, List<CallToAction> ctas) {
        return Offer.newBuilder()
                .setId(id)
                .setSmallDescription(smallDescription)
                .setLargeDescription(largeDescription)
                .setReply(reply)
                .addAllCta(ctas)
                .build();
    }

    private CallToAction createCallToAction(String text, CallToActionType type) {
        return CallToAction.newBuilder()
                .setText(text)
                .setType(type)
                .build();
    }
}
