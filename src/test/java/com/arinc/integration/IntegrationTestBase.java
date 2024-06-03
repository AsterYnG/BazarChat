package com.arinc.integration;

import com.arinc.integration.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@Sql(value = {
        "classpath:sql/init.sql"
})
@Sql(value = {
        "classpath:sql/delete.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@IntegrationTest
@Transactional
@ActiveProfiles("test")
public abstract class IntegrationTestBase{
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    static void setUp() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }

}
