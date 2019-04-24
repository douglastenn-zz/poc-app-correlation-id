package br.com.dafiti.correlationid.business;

import br.com.dafiti.correlationid.domain.TrackingWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrackingUseCase {

    public void execute(final TrackingWrapper trackingWrapper) {
        log.info("{}", trackingWrapper);
    }
}
