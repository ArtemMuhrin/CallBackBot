package com.example.callbackbot.service;

import com.example.callbackbot.aspect.LogMethodCallCount;
import com.example.callbackbot.model.Group;
import com.example.callbackbot.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @LogMethodCallCount
    public Group saveGroup(Group group) {
        Group newGroup = null;
        Group currentGroup = findByGroupId(group.getGroupId());
        if (currentGroup == null) {
            newGroup = groupRepository.save(group);
        } else if (!currentGroup.getActive()) {
            currentGroup.setActive(true);
            newGroup = groupRepository.save(currentGroup);
        }
        return newGroup;
    }

    @LogMethodCallCount
    public Group findByGroupId(Long groupId) {
        return groupRepository.findByGroupId(groupId);
    }

    @LogMethodCallCount
    public void deleteGroup(Group group) {
        group.setActive(false);
        groupRepository.save(group);
    }
}
