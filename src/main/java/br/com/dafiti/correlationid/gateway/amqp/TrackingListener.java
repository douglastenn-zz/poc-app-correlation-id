package br.com.dafiti.correlationid.gateway.amqp;

import br.com.dafiti.correlationid.business.TrackingUseCase;
import br.com.dafiti.correlationid.domain.TrackingWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrackingListener {

    private final TrackingUseCase trackingUseCase;

    @RabbitListener(queues = "${spring.rabbitmq.queues.tracking}", containerFactory = "trackingContainerFactory")
    public void read(final TrackingWrapper trackingWrapper) {
        try {
            log.info("{}", trackingWrapper);
            trackingUseCase.execute(trackingWrapper);
        } catch (final Exception e) {
            log.error("{}", trackingWrapper);
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        }
    }

}
