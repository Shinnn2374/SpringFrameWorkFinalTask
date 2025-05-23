package com.example.finalTask.filter;

import com.example.finalTask.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class HotelSpecification {

    public static Specification<Hotel> withId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.equal(root.get("id"), id);
    }

    public static Specification<Hotel> withName(String name) {
        return (root, query, cb) ->
                !StringUtils.hasText(name) ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Hotel> withCity(String city) {
        return (root, query, cb) ->
                !StringUtils.hasText(city) ? null : cb.equal(cb.lower(root.get("city")), city.toLowerCase());
    }

    public static Specification<Hotel> withAddress(String address) {
        return (root, query, cb) ->
                !StringUtils.hasText(address) ? null : cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%");
    }

    public static Specification<Hotel> withDistanceFromCenter(Double distance) {
        return (root, query, cb) ->
                distance == null ? null : cb.lessThanOrEqualTo(root.get("distanceFromCityCenter"), distance);
    }

    public static Specification<Hotel> withMinRating(Double minRating) {
        return (root, query, cb) ->
                minRating == null ? null : cb.greaterThanOrEqualTo(root.get("rating"), minRating);
    }

    public static Specification<Hotel> withMaxRating(Double maxRating) {
        return (root, query, cb) ->
                maxRating == null ? null : cb.lessThanOrEqualTo(root.get("rating"), maxRating);
    }
}