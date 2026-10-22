package com.taskorbit.config;

import com.taskorbit.model.Task;
import com.taskorbit.model.TaskStatus;
import com.taskorbit.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(TaskRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                LocalDate today = LocalDate.now();

                Task task1 = new Task();
                task1.setTitle("Calibrate Orbital Thrusters");
                task1.setDescription("Verify sensor array calibration and test thrust vectors before planetary insertion.");
                task1.setDueDate(today.plusDays(1));
                task1.setStatus(TaskStatus.PENDING);
                task1.setCreatedAt(LocalDateTime.now().minusHours(5));
                task1.setUpdatedAt(LocalDateTime.now().minusHours(5));

                Task task2 = new Task();
                task2.setTitle("Design 3D Card Hover Glare Effect");
                task2.setDescription("Implement specular reflection gradient and smooth gyro perspective tilt in TaskOrbit CSS.");
                task2.setDueDate(today);
                task2.setStatus(TaskStatus.COMPLETED);
                task2.setCreatedAt(LocalDateTime.now().minusHours(12));
                task2.setUpdatedAt(LocalDateTime.now().minusMinutes(30));

                Task task3 = new Task();
                task3.setTitle("Review MongoDB Indexing Strategy");
                task3.setDescription("Ensure compound indices on status + dueDate are optimized for high query velocity.");
                task3.setDueDate(today.minusDays(2)); // Deliberately in the past to demo the Overdue badge
                task3.setStatus(TaskStatus.PENDING);
                task3.setCreatedAt(LocalDateTime.now().minusDays(3));
                task3.setUpdatedAt(LocalDateTime.now().minusDays(2));

                Task task4 = new Task();
                task4.setTitle("Plan Stellar Sprint Milestones");
                task4.setDescription("Coordinate engineering roadmap with mission command for lunar telemetry rollout.");
                task4.setDueDate(today.plusDays(4));
                task4.setStatus(TaskStatus.PENDING);
                task4.setCreatedAt(LocalDateTime.now().minusHours(2));
                task4.setUpdatedAt(LocalDateTime.now().minusHours(2));

                repository.save(task1);
                repository.save(task2);
                repository.save(task3);
                repository.save(task4);

                System.out.println("Initialized TaskOrbit database with 4 sample tasks.");
            }
        };
    }
}
