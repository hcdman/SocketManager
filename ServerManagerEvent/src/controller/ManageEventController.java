package controller;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JOptionPane;

import model.Event;
import model.Schedule;
import model.Seat;
import model.Zone;
import utils.EventWriter;
import view.AddEventView;
import view.DetailEventView;
import view.HomeView;
import view.ServerManageView;

public class ManageEventController implements ActionListener, MouseListener {

	private HomeView view;
	private AddEventView addEventView;
	private DetailEventView detailView;
	private ServerManageView server;

	public ManageEventController(HomeView homeView) {
		this.view = homeView;
	}

	public ManageEventController(AddEventView view) {
		this.addEventView = view;
	}

	public ManageEventController(DetailEventView view) {
		this.detailView = view;
	}
	public ManageEventController(ServerManageView view) {
		this.server = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// event combo box
		int index = -1;
		if (e.getSource() instanceof JComboBox) {
			JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
			index = comboBox.getSelectedIndex();
		}
		if (index != -1 && this.addEventView != null) {
			this.addEventView.zones = this.addEventView.schedules.get(index).getZones();
			this.addEventView.updateDataZone();
		}
		if (index != -1 && this.detailView != null) {
			Schedule selectedSchedule = this.detailView.event.getSchedules().get(index);
			this.detailView.displaySeatingChart(selectedSchedule);
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
			int columns = Integer.parseInt(this.addEventView.seats.getText());
			String zoneId = "Z" + this.addEventView.zones.size();
			List<Seat> seats = new ArrayList<>();
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					seats.add(new Seat(nameZone + i + j, i, j, false,zoneId));
				}
			}
			this.addEventView.zones
					.add(new Zone(zoneId, nameZone, price, rows, columns, seats));
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
			LocalDate dateEvent = this.addEventView.dateEvent.getDate().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			Event event = new Event("E" + this.addEventView.events.size(), nameEvent, discription,
					dateEvent, this.addEventView.schedules);
			this.addEventView.events.add(event);
			try {
				EventWriter.writeEventsToFile(this.addEventView.events, "src/data/events.json");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (command.equals("Open server")) {
			this.server = new ServerManageView();
			this.view.setEnabled(false);
			this.server.setLocationRelativeTo(null);
			this.server.setVisible(true);
			this.server.events = this.view.events;
			this.server.initServer();
			this.server.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					view.setEnabled(true);
					view.events = server.events;
					view.setVisible(true);
					view.showEvents();
					server.endSocket();
				}
			});
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Check if a row is clicked
		if (e.getClickCount() == 1) {
			// Get the selected row
			int selectedRow = this.view.table.getSelectedRow();
			if (selectedRow != -1) {
				// Retrieve data from the selected row
				this.detailView = new DetailEventView();
				this.detailView.event = this.view.events.get(selectedRow);
				this.view.setEnabled(false);
				this.detailView.setLocationRelativeTo(null);
				this.detailView.setVisible(true);
				this.detailView.showDataEvent();
				this.detailView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						view.setEnabled(true);
						view.setVisible(true);
						view.showEvents();
					}
				});
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
