package br.com.dafiti.correlationid.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TrackingWrapper<T extends Message> implements Serializable {

    private String trackingId;

    private T message;
}
