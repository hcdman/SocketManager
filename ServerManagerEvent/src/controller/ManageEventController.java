package controller;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Schedule;
import view.AddEventView;
import view.HomeView;

public class ManageEventController implements ActionListener {

	private HomeView view;
	private AddEventView addEventView;

	public ManageEventController(HomeView homeView) {
		this.view = homeView;
	}

	public ManageEventController(AddEventView view) {
		this.addEventView = view;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// event button
		String command = e.getActionCommand();
		if (command.equals("Add new event")) {
			this.addEventView = new AddEventView();
			this.view.setEnabled(false);
			this.addEventView.setLocationRelativeTo(null);
			this.addEventView.setVisible(true);
			this.addEventView.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					view.setEnabled(true);
					view.setVisible(true);
				}
			});
		}
		if (command.equals("Add schedule")) {
			this.addEventView.schedules.add(new Schedule("1", LocalTime.now(),LocalTime.now(), null));
			System.out.println(1);
			this.addEventView.updateDataSchedule();
		}
		if (command.equals("Save event")) {
		
		}

	}

}
