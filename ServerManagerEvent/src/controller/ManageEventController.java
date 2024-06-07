package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

public class ManageEventController implements ActionListener, MouseListener, ListSelectionListener {

	private HomeView view;
	private AddEventView addEventView;
	private DetailEventView detailView;
	private ServerManageView server;
	private UserBookedView booked;
	private String PATH_EVENT_FILE = "src/data/events.json";
	private String PATH_DATA_BOOKED_FILE = "src/data/databooked.json";

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

		// Event combo box
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

		// Event button
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
				this.booked.users = ObjectReader.readObjectsFromFile(PATH_DATA_BOOKED_FILE, UserBooked.class);
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

			int indexSch = this.addEventView.table.getSelectedRow();
			// check range time is existed
			if (this.addEventView.checkTimeExisted(indexSch)) {
				this.addEventView.ShowError("Exist schedule in this time period !");
				return;
			}
			if (indexSch < 0) {
				this.addEventView.schedules.add(new Schedule("SCH" + this.addEventView.schedules.size(),
						this.addEventView.startTime.getTime(), this.addEventView.endTime.getTime(), new ArrayList<>()));
			} else {
				this.addEventView.schedules.get(indexSch).setStartTime(this.addEventView.startTime.getTime());
				this.addEventView.schedules.get(indexSch).setEndTime(this.addEventView.endTime.getTime());
			}

			this.addEventView.updateDataSchedule();
			this.addEventView.makeEmptyScheduleForm();
		}
		if (command.equals("Add zone")) {
			if (this.addEventView.comboBox.getSelectedIndex() == -1) {
				this.addEventView.ShowError("There haven't any shedule is selected !");
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
					seats.add(new Seat(Character.toString((char) ascii) + i + j, i, j, false, zoneId));
				}
			}
			int indexZone = this.addEventView.tableSeat.getSelectedRow();
			if (indexZone < 0) {
				this.addEventView.zones.add(new Zone(zoneId, nameZone, price, rows, columns, seats));
			} else {
				this.addEventView.zones.get(indexZone).setName(nameZone);
				this.addEventView.zones.get(indexZone).setTicketPrice(price);
				this.addEventView.zones.get(indexZone).setRows(rows);
				this.addEventView.zones.get(indexZone).setColumn(columns);
				this.addEventView.zones.get(indexZone).setSeats(seats);
			}
			int indexSelect = this.addEventView.comboBox.getSelectedIndex();
			this.addEventView.schedules.get(indexSelect).setZones(new ArrayList<>(this.addEventView.zones));
			this.addEventView.updateDataZone();
			this.addEventView.makeEmptyZoneForm();
		}
		if (command.equals("Save event")) {
			// Check fill data
			if (this.addEventView.nameEvent.getText().isBlank() || this.addEventView.Description.getText().isBlank()
					|| this.addEventView.dateEvent.getDate() == null) {
				this.addEventView.ShowError("You have to fill completely information of your event !");
				return;
			}
			String nameEvent = this.addEventView.nameEvent.getText();
			String description = this.addEventView.Description.getText();
			LocalDate dateEvent = this.addEventView.dateEvent.getDate().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			// Check valid date event
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
			this.addEventView.ShowSuccess("Add new event successfully !");
			this.addEventView.makeEmptyEventForm();
			this.addEventView.makeEmptyScheduleForm();
			this.addEventView.makeEmptyZoneForm();
			this.addEventView.schedules.clear();
			this.addEventView.zones.clear();
			this.addEventView.updateDataSchedule();
			this.addEventView.updateDataZone();
			try {
				ObjectWriter.writeObjectsToFile(this.addEventView.events, PATH_EVENT_FILE);
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
			int selectedRow = this.view.table.getSelectedRow();
			if (selectedRow != -1) {
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

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int selectedIndex = this.addEventView.tableSeat.getSelectedRow();
		int anotherIndex = this.addEventView.table.getSelectedRow();
		if (selectedIndex >= 0) {
			Zone valueRow = this.addEventView.zones.get(selectedIndex);
			this.addEventView.nameZone.setText(valueRow.getName());
			this.addEventView.price.setText(valueRow.getTicketPrice() + "");
			this.addEventView.rows.setText(valueRow.getRows() + "");
			this.addEventView.seats.setText(valueRow.getColumn() + "");
		}
		if (anotherIndex >= 0) {
			Schedule schedule = this.addEventView.schedules.get(anotherIndex);
			this.addEventView.startTime.setTime(schedule.getStartTime());
			this.addEventView.endTime.setTime(schedule.getEndTime());
		}

	}

}
