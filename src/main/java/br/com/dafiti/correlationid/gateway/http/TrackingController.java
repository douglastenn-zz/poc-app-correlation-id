package br.com.dafiti.correlationid.gateway.http;

import br.com.dafiti.correlationid.business.TrackingUseCase;
import br.com.dafiti.correlationid.domain.Message;
import br.com.dafiti.correlationid.domain.TrackingWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingUseCase trackingUseCase;

    @PostMapping("tracking")
    public ResponseEntity<Message> create(@RequestBody TrackingWrapper trackingWrapper) {
        log.info("{}", trackingWrapper);
        trackingUseCase.execute(trackingWrapper);
        return ResponseEntity.created(URI.create("tracking"))
                .body(trackingWrapper.getMessage());
    }
}
