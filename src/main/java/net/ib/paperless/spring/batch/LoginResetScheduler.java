package net.ib.paperless.spring.batch;

import lombok.RequiredArgsConstructor;
import net.ib.paperless.spring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class LoginResetScheduler {
    private final Logger logger = LoggerFactory.getLogger(LoginResetScheduler.class);

    private final UserRepository userRepository;

    @Scheduled(cron = "0 */10 * * * *")
    @ConditionalOnProperty(name = "was.name", havingValue = "was01")
    public void resetLoginFailCount() {
        logger.info("##### BATCH : resetLoginFailCount");
        userRepository.resetLoginFailCount();
    }
}