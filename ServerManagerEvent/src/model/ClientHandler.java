package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import utils.ObjectReader;
import utils.ObjectWriter;
import view.ServerManageView;

public class ClientHandler implements Runnable {

	private final Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ServerManageView view;
	private String PATH_EVENT_FILE = "src/data/events.json";
	private String PATH_DATA_BOOKED_FILE ="src/data/databooked.json";
	private String GET_DATA = "All";
	private String GET_EVENT = "GetEvent";
	private String BOOK = "Book";
	private String SCHEDULE = "GetSchedule";
	public ClientHandler(Socket clientSocket, ServerManageView view) throws IOException {
		this.client = clientSocket;
		this.view = view;
		this.out = new ObjectOutputStream(this.client.getOutputStream());
		this.in = new ObjectInputStream(this.client.getInputStream());
	}

	public Socket getClient() {
		return client;
	}

	public boolean setBooking(List<Zone> zones, String idZone, String idSeat) {
		for (Zone zone : zones) {
			if (zone.getZoneId().equals(idZone)) {
				for (Seat seat : zone.getSeats()) {
					if (seat.getSeatId().equals(idSeat)) {
						if(seat.isBooked())
						{
							return false;
						}
						seat.setBooked(true);
					}
				}
			}
		}
		return true;
	}

	@Override
	public void run() {
		String clientMsg = "";
		try {
			while (true) {
				clientMsg = (String) in.readObject();
				String[] pattern = clientMsg.split("-");
				if (clientMsg.equals(GET_DATA)) {
					out.writeObject(ObjectReader.readObjectsFromFile(PATH_EVENT_FILE,Event.class));
					out.flush();
				}
				if (pattern[0].equals(GET_EVENT)) {
					int index = Integer.parseInt(pattern[1]);
					Event event = ObjectReader.readObjectsFromFile(PATH_EVENT_FILE,Event.class).get(index);
					out.writeObject(event);
					out.flush();
				}
				if (pattern[0].equals(SCHEDULE)) {
					List<Event> events = ObjectReader.readObjectsFromFile(PATH_EVENT_FILE,Event.class);
					Event event = new Event();
					String idEvent = pattern[1];
					int indexSchedule = Integer.parseInt(pattern[2]);
					for (Event value : events) {
						if (value.getEventId().equals(idEvent)) {
							event = value;
						}
					}
					
					out.writeObject(event.getSchedules().get(indexSchedule));
					out.flush();
				}
				if (pattern[0].equals(BOOK)) {
					List<Event> events = ObjectReader.readObjectsFromFile(PATH_EVENT_FILE,Event.class);
					List<UserBooked> users = ObjectReader.readObjectsFromFile(PATH_DATA_BOOKED_FILE,UserBooked.class);
					Map<String,String> dataSeats = new LinkedHashMap<String, String>();
					String phoneNumber = pattern[1];
					String userName = pattern[2];
					String tickets = pattern[3];
					String idEvent = pattern[4];
					String idSeats = "";
					int indexSchedule = Integer.parseInt(pattern[5]);
					List<Zone> zones = new ArrayList<>();
					Schedule schedule = new Schedule();
					for (Event event : events) {
						if (event.getEventId().equals(idEvent)) {
							zones = event.getSchedules().get(indexSchedule).getZones();
						}
					}
					boolean flag = false;
					for (int i = 6; i < pattern.length; i = i + 2) {
						String idZone = pattern[i];
						String idSeat = pattern[i + 1];
						dataSeats.put(idZone,dataSeats.getOrDefault(idZone, "")+" "+idSeat);
						idSeats +=idSeat+" ";
						if (!setBooking(zones, idZone, idSeat)) {
							flag = true;
							break;
						}
					}
					if (flag) {
						out.writeObject(null);
						out.flush();
					} else {
						for (Event event : events) {
							if (event.getEventId().equals(idEvent)) {
								event.getSchedules().get(indexSchedule).setZones(zones);
								schedule = event.getSchedules().get(indexSchedule);
							}
						}
						for(Map.Entry<String, String> value : dataSeats.entrySet())
						{
							users.add(new UserBooked(phoneNumber,userName, idEvent,schedule.getStartTime()+" to "+schedule.getEndTime(),value.getKey(),value.getValue(),LocalDateTime.now()));
						}
						this.view.events = events;
						this.view.histories.add(new ActionClient(this.getClient().getInetAddress().toString(),this.getClient().getPort()+"",LocalDateTime.now(), "Book "+tickets+" tickets for event have id " + idEvent));
						this.view.UpdateHistory();
						ObjectWriter.writeObjectsToFile(events, PATH_EVENT_FILE);
						ObjectWriter.writeObjectsToFile(users, PATH_DATA_BOOKED_FILE);
						out.writeObject(schedule);
						out.flush();
						
					}
				}

			}

		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e);
		} finally {
			try {
				System.out.println("Client " + this.client.getPort() + " disconnected!");
				this.view.RemoveClient(this.client.getPort());
				this.view.UpdateClientConnect();
				this.client.close();
				this.in.close();
				this.out.close();
			} catch (IOException ex) {
				System.out.println(ex);
			}
		}
	}
}
