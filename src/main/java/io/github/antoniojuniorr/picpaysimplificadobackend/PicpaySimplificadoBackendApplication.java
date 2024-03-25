package io.github.antoniojuniorr.picpaysimplificadobackend;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.kafka.config.TopicBuilder;

@EnableJdbcAuditing
@SpringBootApplication
public class PicpaySimplificadoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PicpaySimplificadoBackendApplication.class, args);
    }

    @Bean
    NewTopic notificacaoTopico() {
        return TopicBuilder.name("notificacao-transacao").build();
    }
}
