package com.aps.chatapp.controller;

import com.aps.chatapp.model.Message;
import com.aps.chatapp.repository.MessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "Message management endpoints for chat functionality")
@SecurityRequirement(name = "JWT")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    @Operation(
        summary = "Get all messages",
        description = "Retrieve all chat messages with optional pagination"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Messages retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Message.class)
            )
        ),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<Page<Message>> getAllMessages(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort direction", example = "DESC")
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), "timestamp");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Message> messages = messageRepository.findAll(pageable);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get message by ID",
        description = "Retrieve a specific message by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Message found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Message.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Message not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<Message> getMessageById(
            @Parameter(description = "Message ID", required = true)
            @PathVariable String id
    ) {
        Optional<Message> message = messageRepository.findById(id);
        return message.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Create new message",
        description = "Create a new chat message"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Message created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Message.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid message data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    @GetMapping("/author/{autor}")
    @Operation(
        summary = "Get messages by author",
        description = "Retrieve all messages from a specific author"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Messages retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Message.class)
            )
        ),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<List<Message>> getMessagesByAuthor(
            @Parameter(description = "Author username", required = true)
            @PathVariable String author
    ) {
        List<Message> messages = messageRepository.findByAuthorOrderByTimestampDesc(author);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete message",
        description = "Delete a message by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Message deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Message not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<Void> deleteMessage(
            @Parameter(description = "Message ID", required = true)
            @PathVariable String id
    ) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
