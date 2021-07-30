package com.example.callbackbot.service;

import com.example.callbackbot.model.Group;
import com.example.callbackbot.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void saveGroup(Group group) {
        Group currentGroup = findByGroupId(group.getGroupId());
        if (currentGroup == null) {
            groupRepository.save(group);
        } else if (!currentGroup.getActive()) {
            currentGroup.setActive(true);
            groupRepository.save(currentGroup);
        }
    }

    public Group findByGroupId(Long groupId) {
        return groupRepository.findByGroupId(groupId);
    }

    public void deleteGroup(Group group) {
        group.setActive(false);
        groupRepository.save(group);
    }
}
