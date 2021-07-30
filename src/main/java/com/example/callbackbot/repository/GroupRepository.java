package com.example.callbackbot.repository;

import com.example.callbackbot.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByGroupId(Long groupId);
}
