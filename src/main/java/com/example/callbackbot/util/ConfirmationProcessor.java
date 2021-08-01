package com.example.callbackbot.util;

import com.example.callbackbot.model.CallbackEvent;
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
    public String process(CallbackEvent callbackEvent) {
        String confirmation = "";
        Group group = groupService.findByGroupId(callbackEvent.getGroupId());
        if (group == null) {
            logger.warn("group not found");
        } else {
            confirmation = group.getConfirmation();
        }
        return confirmation;
    }
}
