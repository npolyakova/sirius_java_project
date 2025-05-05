package ru.hpclab.hl.module1.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.BookingDto;
import ru.hpclab.hl.module1.dto.MessageDto;
import ru.hpclab.hl.module1.entities.Guest;
import ru.hpclab.hl.module1.entities.HotelRoom;
import ru.hpclab.hl.module1.service.BookingService;
import ru.hpclab.hl.module1.service.GuestService;
import ru.hpclab.hl.module1.service.HotelRoomService;

import java.text.ParseException;

@Service
public class KafkaConsumerService {

    @Autowired
    private GuestService guestService;

    @Autowired
    private HotelRoomService roomService;

    @Autowired
    private BookingService bookingService;

    @KafkaListener(topics = "var14-topic", groupId = "hotel-group", concurrency = "3")
    public void consume(String message) throws ParseException {
        MessageDto kafkaMessage = parseMessage(message);

        switch (kafkaMessage.getEntity()) {
            case "USER":
                handleUserOperation(kafkaMessage);
                break;
            case "ROOM":
                handleRoomOperation(kafkaMessage);
                break;
            case "BOOKING":
                handleBookingOperation(kafkaMessage);
                break;
            default:
                throw new IllegalArgumentException("Unknown entity: " + kafkaMessage.getEntity());
        }
    }

    private MessageDto parseMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            MessageDto messageDto = new MessageDto();

            JsonNode rootNode = mapper.readTree(message);
            if (!rootNode.has("entity") || !rootNode.has("operation") || !rootNode.has("payload")) {
                throw new RuntimeException("Invalid message format - missing required fields");
            }

            messageDto.setEntity(rootNode.get("entity").asText());
            messageDto.setOperation(rootNode.get("operation").asText());
            messageDto.setPayload(rootNode.get("payload"));

            return messageDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Kafka message", e);
        }
    }

    private void handleUserOperation(MessageDto message) {
        switch (message.getOperation()) {
            case "GET":
                guestService.getAllGuests();
                break;
            case "POST":
                Guest guest = parsePayload(message.getPayload(), Guest.class);
                guestService.saveGuest(guest);
                break;
            case "PUT":
                Guest updatedGuest = parsePayload(message.getPayload(), Guest.class);
                guestService.saveGuest(updatedGuest);
                break;
            case "DEL":
                Long userId = parsePayload(message.getPayload(), Long.class);
                guestService.deleteAll();
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + message.getOperation());
        }
    }

    private void handleRoomOperation(MessageDto message) {
        switch (message.getOperation()) {
            case "GET":
                roomService.getAllHotelRooms();
                break;
            case "POST":
                HotelRoom room = parsePayload(message.getPayload(), HotelRoom.class);
                roomService.saveHotelRoom(room);
                break;
            case "PUT":
                HotelRoom updatedRoom = parsePayload(message.getPayload(), HotelRoom.class);
                roomService.saveHotelRoom(updatedRoom);
                break;
            case "DEL":
                roomService.deleteAll();
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + message.getOperation());
        }
    }

    private void handleBookingOperation(MessageDto message) throws ParseException {
            switch (message.getOperation()) {
                case "GET":
                    bookingService.getAllBookings();
                    break;
                case "POST":
                    BookingDto booking = parsePayload(message.getPayload(), BookingDto.class);
                    bookingService.saveBooking(booking);
                    break;
                case "PUT":
                    BookingDto updatedBooking = parsePayload(message.getPayload(), BookingDto.class);
                    bookingService.saveBooking(updatedBooking);
                    break;
                case "DEL":
                    Long userId = parsePayload(message.getPayload(), Long.class);
                    bookingService.deleteAll();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown operation: " + message.getOperation());
            }
    }

    private <T> T parsePayload(JsonNode payload, Class<T> clazz) {
        try {
            return new ObjectMapper().treeToValue(payload, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse payload", e);
        }
    }
}