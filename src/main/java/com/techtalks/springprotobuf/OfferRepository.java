package com.techtalks.springprotobuf;

import java.util.Map;

import com.techtalks.springprotobuf.OfferProtobuf.Envelope;

public class OfferRepository {
    private final Map<Integer, Envelope> envelopes;

    public OfferRepository(Map<Integer, Envelope> envelopes) {
        this.envelopes = envelopes;
    }

    public Envelope getEnvelope(int id) {
        return envelopes.get(id);
    }
}
