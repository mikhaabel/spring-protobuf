package com.techtalks.springprotobuf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techtalks.springprotobuf.OfferProtobuf.Envelope;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {
    @Autowired
    OfferRepository envelopeRepo;

    @RequestMapping("/envelopes/{id}")
    Envelope produce(@PathVariable Integer id) {
        return envelopeRepo.getEnvelope(id);
    }
}
