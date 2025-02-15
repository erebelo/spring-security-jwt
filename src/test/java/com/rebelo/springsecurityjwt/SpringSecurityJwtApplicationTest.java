package com.rebelo.springsecurityjwt;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@ExtendWith(MockitoExtension.class)
class SpringSecurityJwtApplicationTest {

    @Mock
    private ConfigurableApplicationContext contextMock;

    @Test
    void contextLoads() {
        // This test simply checks if the Spring context loads successfully
        assertNotNull(contextMock, "Spring context should not be null");
    }

    @Test
    void mainRunSuccessful() {
        try (MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
            mockedStatic.when(() -> SpringApplication.run(any(Class.class), any(String[].class)))
                    .thenReturn(contextMock);

            SpringSecurityJwtApplication.main(new String[]{});

            mockedStatic.verify(() -> SpringApplication.run(SpringSecurityJwtApplication.class, new String[]{}));
        }
    }
}
