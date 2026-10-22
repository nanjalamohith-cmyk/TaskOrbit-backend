package com.taskorbit.repository;

import com.taskorbit.model.Task;
import com.taskorbit.model.TaskStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findAllByOrderByDueDateAsc();

    List<Task> findByStatusOrderByDueDateAsc(TaskStatus status);

    long countByStatus(TaskStatus status);
}
