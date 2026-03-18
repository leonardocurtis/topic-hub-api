package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.*;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Topics", description = "Endpoints responsible for managing forum topics and their responses")
@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Operation(summary = "Create a new topic", description = """
        Creates a new discussion topic in the forum

        A topic must belong to a category and may be associated with a course
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Topic created successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CreateTopicResponse.class)))
    })
    @PostMapping()
    public ResponseEntity<CreateTopicResponse> createTopic(@RequestBody @Valid CreateTopicRequest dto) {

        var topic = topicService.createTopic(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    @Operation(summary = "Add response to topic", description = "Adds a new response (answer) to an existing topic")
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Response created successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CreateAnswerResponse.class)))
    })
    @PostMapping("/{topicId}/responses")
    public ResponseEntity<CreateAnswerResponse> addResponse(
            @Parameter(description = "Topic identifier") @PathVariable String topicId,
            @RequestBody @Valid CreateAnswerRequest dto) {

        var response = topicService.createResponse(topicId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Delete topic", description = """
        Deactivates a topic

        Only administrators are allowed to perform this action
        """)
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Topic deleted successfully")})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTopic(@Parameter(description = "Topic identifier") @PathVariable String id) {

        topicService.deactivationTopic(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark response as accepted solution", description = """
        Marks a specific response as the accepted solution for a topic

        Usually performed by the topic author
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Response marked as solution",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = AnswerResolvedResponse.class)))
    })
    @PatchMapping("/{topicId}/responses/{responseId}/solve")
    public ResponseEntity<AnswerResolvedResponse> markAsSolved(
            @Parameter(description = "Topic identifier") @PathVariable String topicId,
            @PathVariable String responseId) {

        var response = topicService.markAsSolved(topicId, responseId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deactivate response", description = "Deactivates a response inside a topic")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Response deactivated successfully")})
    @PatchMapping("/{topicId}/responses/{responseId}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<Void> deleteResponse(
            @Parameter(description = "Topic identifier") @PathVariable String topicId,
            @Parameter(description = "Response identifier") @PathVariable String responseId,
            @RequestBody @Valid DeactivateResponseRequest dto) {
        topicService.deleteResponse(topicId, responseId, dto);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List topics", description = """
        Returns a paginated list of topics

        Topics are sorted by creation date by default
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Topics retrieved successfully",
                content =
                        @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping()
    public ResponseEntity<PageResponse<TopicListResponse>> listTopics(
            @Parameter(description = "Pagination parameters")
                    @PageableDefault(
                            sort = {"createdAt"},
                            direction = Sort.Direction.ASC)
                    Pageable pp) {

        var page = topicService.listAllTopic(pp);

        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get topic details", description = "Returns full details of a topic ")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Topic found",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = TopicDetailsResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsResponse> showTopicDetails(
            @Parameter(description = "Topic identifier") @PathVariable String id) {

        var topic = topicService.topicDetails(id);

        return ResponseEntity.ok(topic);
    }

    @Operation(summary = "Update topic", description = "Updates the title, type or content of a topic")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Topic updated successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UpdateTopicResponse.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateTopicResponse> updateTopic(
            @Parameter(description = "Topic identifier") @PathVariable String id,
            @RequestBody @Valid UpdateTopicRequest dto) {

        var updatedTopic = topicService.updateTopic(id, dto);

        return ResponseEntity.ok(updatedTopic);
    }

    @Operation(summary = "Close topic", description = """
        Closes a topic

        Closed topics cannot receive new responses
        """)
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Topic closed successfully",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CloseTopicResponse.class)))
    })
    @PatchMapping("/{id}/close")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<CloseTopicResponse> closeTopic(
            @Parameter(description = "Topic identifier") @PathVariable String id, @RequestBody CloseTopicRequest dto) {

        var response = topicService.closeTopic(id, dto);

        return ResponseEntity.ok(response);
    }
}
