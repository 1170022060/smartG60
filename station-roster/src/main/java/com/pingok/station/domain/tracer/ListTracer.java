package com.pingok.station.domain.tracer;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class ListTracer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String ListType;

    private String Version;
}
