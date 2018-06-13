package org.rbaygildin.unit_test.testing;

import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import org.rbaygildin.service.IDataService;
import org.rbaygildin.unit_testing.config.UnitTestingConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestingConfig.class)
public class ClassicLoopLauncherTest extends AbstractLauncherTest {

    @Autowired
    @Qualifier("classicLoop")
    private IStreamLauncher classicLoop_;

    @Autowired
    private IDataService dataService;

    @Test
    public void testFindPrimeNumbers(){
        super.__testFindPrimeNumbers(classicLoop_);
    }

    @Test
    public void testCalculateCorrelation()
    {
        super.__testCalculateCorrelation(dataService, classicLoop_);
    }
}
