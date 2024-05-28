package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import view.AddEventView;
import view.AddScheduleView;
import view.AddSeatView;
import view.HomeView;

public class ManageEventController implements ActionListener {

	private HomeView view;
	private AddEventView addEventView;
	private AddScheduleView schedule;
	private AddSeatView seat;
	public ManageEventController(HomeView homeView)
	{
		this.view = homeView;
	}
	public ManageEventController(AddEventView view)
	{
		this.addEventView = view;
	}
	public ManageEventController(AddScheduleView view)
	{
		this.schedule = view;
	}
	public ManageEventController(AddSeatView view)
	{
		this.seat = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// event button
		String command = e.getActionCommand();
		if (command.equals("Add new event")) {
		    AddEventView frame = new AddEventView();
			this.view.setEnabled(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					view.setEnabled(true);
					view.setVisible(true);

				}
			});
		}
		
	}

}
