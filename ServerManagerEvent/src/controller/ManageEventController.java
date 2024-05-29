package controller;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JComboBox;

import model.Event;
import model.Schedule;
import model.Zone;
import utils.EventWriter;
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
		// event combo box
		int index = -1;
		if (e.getSource() instanceof JComboBox) {
			JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
			index = comboBox.getSelectedIndex();

		}
		if (index != -1) {
			this.addEventView.zones = this.addEventView.schedules.get(index).getZones();
			this.addEventView.updateDataZone();
		}
		// event button
		String command = e.getActionCommand();
		if (command.equals("Add new event")) {
			this.addEventView = new AddEventView();
			this.addEventView.events = this.view.events;
			this.view.setEnabled(false);
			this.addEventView.setLocationRelativeTo(null);
			this.addEventView.setVisible(true);
			this.addEventView.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					view.setEnabled(true);
					view.setVisible(true);
					view.showEvents();
				}
			});
		}
		if (command.equals("Add schedule")) {
			this.addEventView.schedules.add(new Schedule("SCH" + this.addEventView.schedules.size(),
					this.addEventView.startTime.getTime(), this.addEventView.endTime.getTime(), new ArrayList<>()));
			this.addEventView.updateDataSchedule();
			this.addEventView.makeEmptyScheduleForm();
		}
		if (command.equals("Add zone")) {
			String nameZone = this.addEventView.nameZone.getText();
			double price = Double.parseDouble(this.addEventView.price.getText());
			int rows = Integer.parseInt(this.addEventView.rows.getText());
			int seats = Integer.parseInt(this.addEventView.seats.getText());
			this.addEventView.zones.add(
					new Zone("Z" + this.addEventView.zones.size(), nameZone, price, rows, seats, new ArrayList<>()));
			int indexSelect = this.addEventView.comboBox.getSelectedIndex();
			this.addEventView.schedules.get(indexSelect).setZones(this.addEventView.zones);
			// update show zone
			this.addEventView.updateDataZone();
			this.addEventView.makeEmptyZoneForm();
		}
		if (command.equals("Save event")) {
			// get data event
			String nameEvent = this.addEventView.nameEvent.getText();
			String discription = this.addEventView.Discription.getText();
			LocalDate dateEvent = this.addEventView.dateEvent.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			this.addEventView.events.add(new Event("E" + this.addEventView.events.size(), nameEvent, discription,
					dateEvent, this.addEventView.schedules));
			try {
				EventWriter.writeEventsToFile(this.addEventView.events, "src/data/events.json");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

}
