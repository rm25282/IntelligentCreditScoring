package org.demo.intelligentcreditscoring.service.bank;

public record Bank(boolean pastOverdrawnLimit, double bankCreditScore) {
}
