package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import java.util.Calendar;

public class TrainUserImpl implements TrainUser {

	private TrainController controller;
	private int joystickPosition;
	private String name;
	//shows whether thread for reference speed change has been started yet
	private boolean threadStarted=false;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
		name = "Name";
		System.out.printf("Created UserImpl: %s at %s\n", name, java.time.LocalDate.now());
	}

	@Override
	public boolean getAlarmFlag() {
		return false;
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
		if(!threadStarted){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						controller.followSpeed();
						try
						{
							Thread.sleep(5000);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
			}).start();
			threadStarted=true;
		}
	}

}
