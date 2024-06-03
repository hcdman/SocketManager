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

import model.Event;
import model.Schedule;
import model.Seat;
import model.UserBooked;
import model.Zone;
import utils.ObjectReader;
import utils.ObjectWriter;
import view.AddEventView;
import view.DetailEventView;
import view.HomeView;
import view.ServerManageView;
import view.UserBookedView;

public class ManageEventController implements ActionListener, MouseListener {

	private HomeView view;
	private AddEventView addEventView;
	private DetailEventView detailView;
	private ServerManageView server;
	private UserBookedView booked;

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
		if (command.equals("Seat booked")) {
			this.booked = new UserBookedView();
			this.view.setEnabled(false);
			this.booked.setLocationRelativeTo(null);
			this.booked.setVisible(true);
			try {
				this.booked.users = ObjectReader.readObjectsFromFile("src/data/databooked.json",UserBooked.class);
				this.booked.showUsers();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.booked.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					view.setEnabled(true);
					view.setVisible(true);
					view.showEvents();
				}
			});
		}
		if (command.equals("Add schedule")) {
			// check error not have any data
			if (this.addEventView.startTime.getTime() == null || this.addEventView.endTime.getTime() == null) {
				this.addEventView.ShowError("You have to set value of time !");
				return;
			}
			// check range time
			if (this.addEventView.endTime.getTime().compareTo(this.addEventView.startTime.getTime()) <= 0) {
				this.addEventView.ShowError("Value of end time and start time is not valid!");
				return;
			}
			// check range time is existed
			if (this.addEventView.checkTimeExisted()) {
				this.addEventView.ShowError("Exist schedule in this time period !");
				return;
			}

			this.addEventView.schedules.add(new Schedule("SCH" + this.addEventView.schedules.size(),
					this.addEventView.startTime.getTime(), this.addEventView.endTime.getTime(), new ArrayList<>()));
			this.addEventView.updateDataSchedule();
			this.addEventView.makeEmptyScheduleForm();
		}
		if (command.equals("Add zone")) {
			if (this.addEventView.comboBox.getSelectedIndex() == -1) {
				this.addEventView.ShowError("Don't have any shedule is selected !");
				return;
			}
			// check is empty field
			if (this.addEventView.checkIsBlankFormZone()) {
				this.addEventView.ShowError("You have to fill completely information of a zone!");
				return;
			}
			String nameZone = this.addEventView.nameZone.getText();
			double price = Double.parseDouble(this.addEventView.price.getText());
			int rows = Integer.parseInt(this.addEventView.rows.getText());
			int ascii = 65 + this.addEventView.zones.size();
			int columns = Integer.parseInt(this.addEventView.seats.getText());
			String zoneId = "Z" + this.addEventView.zones.size();
			List<Seat> seats = new ArrayList<>();
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					seats.add(new Seat(Character.toString((char)ascii) + i + j, i, j, false, zoneId));
				}
			}
			this.addEventView.zones.add(new Zone(zoneId, nameZone, price, rows, columns, seats));
			int indexSelect = this.addEventView.comboBox.getSelectedIndex();
			this.addEventView.schedules.get(indexSelect).setZones(new ArrayList<>(this.addEventView.zones));
			// update show zone
			this.addEventView.updateDataZone();
			this.addEventView.makeEmptyZoneForm();
		}
		if (command.equals("Save event")) {
			// check fill data
			if (this.addEventView.nameEvent.getText().isBlank() || this.addEventView.Description.getText().isBlank()
					|| this.addEventView.dateEvent.getDate() == null) {
				this.addEventView.ShowError("You have to fill completely information of your event !");
				return;
			}
			// get data event
			String nameEvent = this.addEventView.nameEvent.getText();
			String description = this.addEventView.Description.getText();
			LocalDate dateEvent = this.addEventView.dateEvent.getDate().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();

			// check valid date event
			if (dateEvent.compareTo(LocalDate.now()) <= 0) {
				this.addEventView.ShowError("Date of event is not valid !");
				return;
			}
			if (this.addEventView.schedules.size() == 0) {
				this.addEventView.ShowError("Event have to have at least one schedules !");
				return;
			}
			// Check have data of schedule and zone
			if (this.addEventView.checkLoseData()) {
				this.addEventView.ShowError("All schedules have to set data of seat !");
				return;
			}
			Event event = new Event("E" + this.addEventView.events.size(), nameEvent, description, dateEvent,
					new ArrayList<>(this.addEventView.schedules));
			this.addEventView.events.add(event);
			this.addEventView.makeEmptyEventForm();
			this.addEventView.ShowSuccess("Add new event successfully !");
			this.addEventView.schedules.clear();
			this.addEventView.zones.clear();
			this.addEventView.updateDataSchedule();
			this.addEventView.updateDataZone();
			try {
				ObjectWriter.writeObjectsToFile(this.addEventView.events, "src/data/events.json");
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
		if (command.equals("Delete")) {
			int indexSchedule = this.addEventView.table.getSelectedRow();
			int indexZone = this.addEventView.tableSeat.getSelectedRow();
			if (indexSchedule >= 0) {
				int indexCombobox = this.addEventView.comboBox.getSelectedIndex();
				this.addEventView.schedules.remove(indexSchedule);
				if (indexSchedule == indexCombobox) {
					this.addEventView.zones.clear();
					this.addEventView.updateDataZone();
				}
				this.addEventView.updateDataSchedule();
			}
			if (indexZone >= 0) {
				this.addEventView.zones.remove(indexZone);
				this.addEventView.updateDataZone();
			}

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
//		if (SwingUtilities.isRightMouseButton(e)) {
//				int index = this.addEventView.table.locationToIndex(e.getPoint());
//				this.listView.listFavorite.setSelectedIndex(index);
//				this.listView.showPopupMenu(e);
//			
//		}

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
