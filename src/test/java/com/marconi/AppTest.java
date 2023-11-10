package com.marconi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class AppTest {

    @Test
    void app_happyPath() {
        App.main(new String[0]);
        App.main(new String[]{"1", "2", "3"});
        App.main(new String[] {"src/main/resources/input.txt"});
        App.main(new String[] {"src/main/resources/input.txt", "src/main/resources/output.txt"});
    }
    @Test
    void app_shouldThrowException_fileDoesntExist() {
        Assertions.assertThrows(RuntimeException.class, () -> App.main(new String[] {"nonExistingInputFile.txt"}));
    }

    @Test
    void app_shouldThrowException_nonValidInput() {
        Assertions.assertThrows(NumberFormatException.class, () -> App.main(new String[] {"1", "2", "non-convertible string"}));
    }


}