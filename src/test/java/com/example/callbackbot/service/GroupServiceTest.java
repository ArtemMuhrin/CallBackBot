package com.example.callbackbot.service;

import com.example.callbackbot.model.Group;
import com.example.callbackbot.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private GroupRepository groupRepository;
    @InjectMocks
    private GroupService groupService;
    private Group testGroup;

    @BeforeEach
    void createTestGroup() {
        testGroup = new Group(1L, "token", "confirmation", 1.01, true);
    }

    @Test
    void saveGroup() {
        when(groupRepository.save(any(Group.class))).thenReturn(testGroup);
        Group savedGroup = groupService.saveGroup(testGroup);
        assertNotNull(savedGroup);
    }

    @Test
    void findByGroupId() {
        when(groupRepository.findByGroupId(testGroup.getId())).thenReturn(testGroup);
        assertEquals(groupService.findByGroupId(testGroup.getId()), testGroup);
    }
}