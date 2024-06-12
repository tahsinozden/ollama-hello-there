package com.example.ollamahellothere;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportantEventsService {

    private static final String MESSAGE_TEMPLATE = "What happened on %s?";
    private static final String VALID_DATE_TEMPLATE = "Is this date %s a valid date according to the Gregorian calendar? Please answer with 'Yes' or 'No'.";
    private final OllamaChatModel chatModel;

    public String getImportantEventsByDate(String date) {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException(String.format("Invalid date %s", date));
        }
        return sendPrompt(MESSAGE_TEMPLATE.formatted(date));
    }

    private boolean isValidDate(String date) {
        String content = sendPrompt(VALID_DATE_TEMPLATE.formatted(date));
        return Strings.isNotBlank(content) && content.trim().startsWith("Yes");
    }

    private String sendPrompt(String prompt) {
        log.info("Sending the prompt: {}", prompt);
        ChatResponse response = chatModel.call(new Prompt(new UserMessage(prompt)));
        String content = response.getResult().getOutput().getContent();
        log.info("Received prompt response: {}", content);
        return content.trim();
    }
}
