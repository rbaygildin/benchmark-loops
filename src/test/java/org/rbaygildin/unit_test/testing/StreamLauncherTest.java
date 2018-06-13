package org.rbaygildin.unit_test.testing;

import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.service.IDataService;
import org.rbaygildin.unit_testing.config.UnitTestingConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.service.IDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestingConfig.class)
public class StreamLauncherTest extends AbstractLauncherTest{

    @Autowired
    @Qualifier("streams")
    private IStreamLauncher streams_;

    @Autowired
    private IDataService dataService;

    @Test
    public void testFindPrimeNumbers(){
        super.__testFindPrimeNumbers(streams_);
        super.__testCalculateCorrelation(dataService, streams_);
    }
}
