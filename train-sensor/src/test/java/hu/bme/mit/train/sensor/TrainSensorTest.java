package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TrainSensorTest {

    private TrainSensorImpl ts;
    private TrainController tc;
    private TrainUser tu;

    @Before
    public void before() {
        tc = mock(TrainController.class);
        tu = mock(TrainUser.class);
        ts= new TrainSensorImpl(tc, tu);
    }

    @Test
    public void SpeedChangedTest() {
        Assert.assertEquals(ts.getSpeedLimit(), 5);
        ts.overrideSpeedLimit(7);
        Assert.assertEquals(ts.getSpeedLimit(), 7);
    }

    @Test
    public void SpeedSetTest(){
        ts.overrideSpeedLimit(100);
        verify(tc).setSpeedLimit(100);
        verify(tu, never()).setAlarmState(true);
    }

    @Test
    public void InvalidSpeedTest(){
        ts.overrideSpeedLimit(-3);
        verify(tc, never()).setSpeedLimit(-3);
        verify(tu).setAlarmState(true);
    }

    @Test
    public void LimitTest(){
        ts.overrideSpeedLimit(500);
        verify(tc).setSpeedLimit(500);
        verify(tu, never()).setAlarmState(true);
    }

    @Test
    public void SlowSpeedTest(){
        ts.overrideSpeedLimit(1);
        verify(tc, never()).setSpeedLimit(1);
        verify(tu).setAlarmState(true);
    }

    @Test
    public void FastSpeedTest(){
        ts.overrideSpeedLimit(600);
        verify(tc, never()).setSpeedLimit(600);
        verify(tu).setAlarmState(true);
    }
}
