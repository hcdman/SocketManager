# Ticket Booking System

## Overview
This Ticket Booking System is a user-friendly program that lets you set up and book tickets for events. It's designed for both the event organizers (server side) and attendees (client side).

## Features
- **Server Side**:
  - Set up events for future dates.
  - Configure multiple showtimes throughout the day.
  - Organize seating by zones and rows with different ticket prices.
  - View current seat availability.
  - Allow multiple clients to connect and book tickets simultaneously.

- **Client Side**:
  - Connect to the server to access event data.
  - Display showtimes, seating arrangements, and seat availability.
  - Book available seats and save booking details like name and phone number.

## How to Use
1. **Server Setup**:
   - Run the server application.
   - Set up your event details and seating configuration.

2. **Client Booking**:
   - Open the client application.
   - Connect to the server using the provided IP and port.
   - Choose an event and select your seats.
   - Enter your details and confirm the booking.

## Technical Stack
- Java for core application development.
- Swing for graphical user interface.
- Sockets for client-server communication.
- Object Streams for data transfer.
- Custom data structures for data management without a database.
