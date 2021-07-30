package com.example.callbackbot.controller;

import com.example.callbackbot.model.Group;
import com.example.callbackbot.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    private final GroupService groupService;

    public ApiController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/group")
    public ResponseEntity<?> saveGroup(@RequestParam Long groupId,
                                       @RequestParam String token,
                                       @RequestParam String confirmation,
                                       @RequestParam Double version) {
        Group group = new Group(groupId, token,confirmation, version, true);
        groupService.saveGroup(group);
        return ResponseEntity.ok("Group has been added. Bot started working.");
    }

    @DeleteMapping("/group")
    public ResponseEntity<?> deleteGroup(@RequestParam Long groupId, @RequestParam String token) {
        Group group = groupService.findByGroupId(groupId);
        if (group == null) {
            return ResponseEntity.badRequest().body("Group not found.");
        } else if (!token.equals(group.getToken())) {
            return ResponseEntity.badRequest().body("Invalid token.");
        } else {
            groupService.deleteGroup(group);
            return ResponseEntity.ok("Group has been deleted. Bot is disabled.");
        }
    }
}