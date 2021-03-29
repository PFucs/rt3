package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import java.util.Calendar;

public class TrainUserImpl implements TrainUser {

	private TrainController controller;
	private int joystickPosition;
	private String name;
	private boolean alarmState;
	//shows whether thread for reference speed change has been started yet
	private boolean followSpeedStarted=false;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
		alarmState=false;
		name = "Name";
		System.out.printf("Created UserImpl: %s at %s\n", name, java.time.LocalDate.now());
	}

	@Override
	public boolean getAlarmFlag() {
		return false;
	}

	@Override
	public boolean getAlarmState() {
		return alarmState;
	}

	@Override
	public void setAlarmState(boolean a) {
		alarmState=a;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {
		this.joystickPosition = joystickPosition;
		controller.setJoystickPosition(joystickPosition);

		//starts a thread for changing the ref. speed if it hasn't been started yet
		if(!followSpeedStarted){
			new Thread(new Runnable() {
				@Override
				public void run() {
					int j=0;
					while(true) {
						controller.followSpeed();
						try
						{
							Thread.sleep(3000);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
							Thread.currentThread().interrupt();
						}
						j++;
						if (j  == Integer.MIN_VALUE) {  // true at Integer.MAX_VALUE +1
							break;
						}
					}
				}
			}).start();
			followSpeedStarted=true;
		}
	}

}
