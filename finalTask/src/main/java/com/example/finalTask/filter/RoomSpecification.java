package com.example.finalTask.filter;

import com.example.finalTask.entity.Room;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class RoomSpecification {

    public static Specification<Room> withId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.equal(root.get("id"), id);
    }

    public static Specification<Room> withName(String name) {
        return (root, query, cb) ->
                !StringUtils.hasText(name) ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Room> withHotelId(Long hotelId) {
        return (root, query, cb) ->
                hotelId == null ? null : cb.equal(root.join("hotel").get("id"), hotelId);
    }

    public static Specification<Room> withMinPrice(Double minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Room> withMaxPrice(Double maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Room> withMaxPeople(Integer maxPeople) {
        return (root, query, cb) ->
                maxPeople == null ? null : cb.greaterThanOrEqualTo(root.get("maxPeople"), maxPeople);
    }

    public static Specification<Room> availableBetween(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || checkIn.isAfter(checkOut)) {
            return null;
        }

        return (root, query, cb) -> {
            query.distinct(true);
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Room> subRoot = subquery.from(Room.class);
            Join<Object, Object> bookings = subRoot.join("bookings", JoinType.LEFT);

            subquery.select(subRoot.get("id"))
                    .where(cb.and(
                            cb.equal(subRoot.get("id"), root.get("id")),
                            cb.or(
                                    cb.between(cb.literal(checkIn), bookings.get("checkInDate"), bookings.get("checkOutDate")),
                                    cb.between(cb.literal(checkOut), bookings.get("checkInDate"), bookings.get("checkOutDate")),
                                    cb.and(
                                            cb.lessThanOrEqualTo(bookings.get("checkInDate"), checkOut),
                                            cb.greaterThanOrEqualTo(bookings.get("checkOutDate"), checkIn)
                                    )
                            )
                    ));

            return cb.not(cb.exists(subquery));
        };
    }
}