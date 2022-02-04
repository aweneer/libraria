package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.config.PersistenceConfig;
import cz.cvut.fel.ear.libraria.config.ServiceConfig;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class) // Run tests using Spring runner, which is able to start the Spring container
@ContextConfiguration(classes = {ServiceConfig.class, PersistenceConfig.class}) // Configuration classes for the tests
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)    // Reset the Spring container after each test method
// Transaction manager for the tests. This means that a transaction spans each test (and is rolled back after it)
@Transactional(transactionManager = "txManager")
@EnableAspectJAutoProxy(proxyTargetClass = true)    // AspectJ proxies for beans without interfaces
@EnableTransactionManagement
@Ignore
public abstract class BaseServiceTestRunner {
}
