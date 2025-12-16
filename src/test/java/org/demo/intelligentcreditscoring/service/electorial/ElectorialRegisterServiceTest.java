package org.demo.intelligentcreditscoring.service.electorial;

import org.assertj.core.api.Assertions;
import org.demo.intelligentcreditscoring.model.Address;
import org.demo.intelligentcreditscoring.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ElectorialRegisterServiceTest {

    private final static Address BELFAST = new Address("123 Main Street", "", "Belfast", "BT7 2DE");
    private final static User USER = new User("Richard", "Martin", LocalDate.of(1985, 12, 12), BELFAST);
    IElectorialRegisterService electorialRegister = new ElectorialRegisterService();

    @Test
    void shouldReturnZeroWhenUserIsNotRegisteredToVote() {

        electorialRegister.setRegisteredToVote(false);

        ElectorialRegister expected = new ElectorialRegister(false, 0);
        Assertions.assertThat(electorialRegister.isRegisteredToVoteScore(USER)).isEqualTo(expected);
    }

    @Test
    void shouldReturnOneHundredWhenUserIsNotRegisteredToVote() {

        electorialRegister.setRegisteredToVote(true);

        ElectorialRegister expected = new ElectorialRegister(true, 100);

        Assertions.assertThat(electorialRegister.isRegisteredToVoteScore(USER)).isEqualTo(expected);
    }


}