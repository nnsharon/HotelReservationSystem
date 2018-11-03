import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

public class HotelReservationSystem {
	private static HashMap<String, Guest> guests = new HashMap<String, Guest>(); // hold existing guest info from guests.txt
	private static ArrayList<Reservation> all_reservations = new ArrayList<Reservation>(); // hold all reservations from reservations.txt
    private static ArrayList<Room> rooms = new ArrayList<Room>(); // hold all general rooms info from rooms.txt
    
	public static void main(String[] args) throws FileNotFoundException{
		loadData("guests.txt", 1);
		loadData("rooms.txt", 2);
		loadData("reservations.txt", 3);
		
		/* TEST CODE FOR LOADING DATA 
        System.out.println(guests.get("ngannguyen2").toString());
		
        for(Room room : rooms)
        	System.out.println(room.toString());
        
        for(Reservation r: all_reservations)
        	System.out.println(r.toString());
        */
		
		
		ReservationsByRoomModel reservationsByRoomModel = new ReservationsByRoomModel(all_reservations);
		ReservationsByRoomPanel reservationsByRoomPanel = new ReservationsByRoomPanel(reservationsByRoomModel, rooms);
		reservationsByRoomModel.addChangeListener(reservationsByRoomPanel);
		
		JFrame frame = new JFrame("Hotel SEN");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 500);
		
		frame.add(reservationsByRoomPanel);
		
		frame.setVisible(true);
    }
	
	/**
	 * Load data from a text file to data structure in Java
	 * @param fileName
	 * @param option 1: guests.txt, 2: rooms.txt, 3: reservations.txt
	 * @throws FileNotFoundException
	 */
	public static void loadData(String fileName, int option) throws FileNotFoundException{
		File file = new File(fileName);
		Scanner fin = new Scanner(file);
		
		switch(option){
		case 1:
			/* LOAD GUESTS INFO */
			while(fin.hasNextLine()){
				String name = fin.nextLine(); 
				String[] logIn = fin.nextLine().split(" ");
				guests.put(logIn[0], new Guest(name, logIn[0], logIn[1]));
			}
			break;
		case 2:
			/* LOAD ROOMS INFO */
			while(fin.hasNextLine()){
				String[] roomInfo = fin.nextLine().split(" ");
				int room_number = Integer.parseInt(roomInfo[0]);
				int price = Integer.parseInt(roomInfo[1]);
				Room room = new Room(room_number, price);
				rooms.add(room);
				if(price == 100)
					type1Rooms.add(room);
				else if(price == 200)
					type2Rooms.add(room);
				else
					type3Rooms.add(room);
			}
			break;
		case 3:
			/* LOAD RESERVATIONS INFO */
			while(fin.hasNextLine()){
				String[] str = fin.nextLine().split(" ");
				
				String[] date = str[2].split("/");
				LocalDate start_date = LocalDate.of(Integer.parseInt(date[2]), 
						Integer.parseInt(date[0]), Integer.parseInt(date[1]));
				
				date = str[3].split("/");
				LocalDate end_date = LocalDate.of(Integer.parseInt(date[2]), 
						Integer.parseInt(date[0]), Integer.parseInt(date[1]));
				
				date = str[5].split("/");
				LocalDate bookingDate = LocalDate.of(Integer.parseInt(date[2]), 
						Integer.parseInt(date[0]), Integer.parseInt(date[1]));
				DateInterval dateInterval = new DateInterval(start_date, end_date);
				
				all_reservations.add(new Reservation(str[0], Integer.parseInt(str[1]), 
						dateInterval, Integer.parseInt(str[4]), bookingDate));
			} 
			break;
		}
		
		fin.close();
	}
	
	
}
