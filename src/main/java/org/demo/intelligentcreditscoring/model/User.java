package org.demo.intelligentcreditscoring.model;

import java.time.LocalDate;

public record User(String firstName, String secondName, LocalDate dateOfBirth, Address address) {
}
