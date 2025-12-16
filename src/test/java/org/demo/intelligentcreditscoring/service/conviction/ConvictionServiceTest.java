package org.demo.intelligentcreditscoring.service.conviction;

import org.demo.intelligentcreditscoring.model.Address;
import org.demo.intelligentcreditscoring.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ConvictionServiceTest {

    private final static Address BELFAST = new Address("123 Main Street", "", "Belfast", "BT7 2DE");
    private final static User USER = new User("Richard", "Martin", LocalDate.of(1985, 12, 12), BELFAST);
    private final IConvictionService conviction = new ConvictionService();

    @Test
    void shouldReturnZeroWhenUserHasConvictions() {

        conviction.setHasConvictions(true);

        assertThat(conviction.calculateConvictionsScore(USER)).isEqualTo(new Conviction(true, 0));
    }

    @Test
    void shouldReturnOneHundredWhenUserDoesNotHaveConvictions() {

        conviction.setHasConvictions(false);

        assertThat(conviction.calculateConvictionsScore(USER)).isEqualTo(new Conviction(false, 100));
    }
}