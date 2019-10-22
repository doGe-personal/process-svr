
package com.process;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//@RunWith(SpringRunner.class)
//@ContextConfiguration(
//        initializers = {ConfigFileApplicationContextInitializer.class}
//)
//@Transactional
//@Rollback
public abstract class PsBaseTest {

}
