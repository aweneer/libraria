package cz.cvut.fel.ear.libraria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({PersistenceConfig.class, ServiceConfig.class, WebAppConfig.class})
public class AppConfig {
}
