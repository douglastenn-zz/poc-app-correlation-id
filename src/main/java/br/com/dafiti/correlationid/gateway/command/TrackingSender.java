package br.com.dafiti.correlationid.gateway.command;

import br.com.dafiti.correlationid.config.Publisher;
import br.com.dafiti.correlationid.domain.Bar;
import br.com.dafiti.correlationid.domain.Foo;
import br.com.dafiti.correlationid.domain.TrackingWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class TrackingSender implements CommandLineRunner {

    private final Publisher publisher;

    @Value("${spring.rabbitmq.queues.tracking}")
    private String queueName;

    @Override
    public void run(String... args) throws Exception {
        for (int x = 1; x <= 2; x++) {
            publisher.sendMessage(
                    TrackingWrapper
                            .builder()
                            .trackingId(UUID.randomUUID().toString())
                            .message(Foo.builder().foo("Foo " + x).bar("Bar " + x).build())
                            .build(), queueName);

            publisher.sendMessage(
                    TrackingWrapper
                            .builder()
                            .trackingId(UUID.randomUUID().toString())
                            .message(Bar.builder().foo("Foo " + x).bar("Bar " + x).build())
                            .build(), queueName);
        }
    }
}
