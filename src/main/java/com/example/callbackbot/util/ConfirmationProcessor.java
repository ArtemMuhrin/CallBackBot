package com.example.callbackbot.util;

import com.example.callbackbot.dto.CallbackEventDto;
import com.example.callbackbot.model.Group;
import com.example.callbackbot.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationProcessor implements CallbackEventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(NewCallbackMessageProcessor.class);
    private final GroupRepository groupService;

    public ConfirmationProcessor(GroupRepository groupService) {
        this.groupService = groupService;
    }

    @Override
    public String process(CallbackEventDto callbackEventDto) {
        String confirmation = "";
        Group group = groupService.findByGroupId(callbackEventDto.getGroupId());
        if (group == null) {
            logger.warn("group not found");
        } else {
            confirmation = group.getConfirmation();
        }
        return confirmation;
    }
}
