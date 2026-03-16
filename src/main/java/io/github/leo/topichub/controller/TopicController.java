package io.github.leo.topichub.controller;

import io.github.leo.topichub.dto.request.*;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.service.TopicService;
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

@Tag(name = "Topics")
@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping()
    public ResponseEntity<CreateTopicResponse> createTopic(@RequestBody @Valid CreateTopicRequest dto) {

        var topic = topicService.createTopic(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    @PostMapping("/{topicId}/responses")
    public ResponseEntity<CreateAnswerResponse> addResponse(
            @PathVariable String topicId, @RequestBody @Valid CreateAnswerRequest dto) {

        var response = topicService.createResponse(topicId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTopic(@PathVariable String id) {

        topicService.deactivationTopic(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{topicId}/responses/{responseId}/solve")
    public ResponseEntity<AnswerResolvedResponse> markAsSolved(
            @PathVariable String topicId, @PathVariable String responseId) {

        var response = topicService.markAsSolved(topicId, responseId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{topicId}/responses/{responseId}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<Void> deleteResponse(
            @PathVariable String topicId,
            @PathVariable String responseId,
            @RequestBody @Valid DeactivateResponseRequest dto) {
        topicService.deleteResponse(topicId, responseId, dto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<PageResponse<TopicListResponse>> listTopics(
            @PageableDefault(
                            sort = {"createdAt"},
                            direction = Sort.Direction.ASC)
                    Pageable pp) {

        var page = topicService.listAllTopic(pp);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsResponse> showTopicDetails(@PathVariable String id) {

        var topic = topicService.topicDetails(id);

        return ResponseEntity.ok(topic);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateTopicResponse> updateTopic(
            @PathVariable String id, @RequestBody @Valid UpdateTopicRequest dto) {

        var updatedTopic = topicService.updateTopic(id, dto);

        return ResponseEntity.ok(updatedTopic);
    }

    @PatchMapping("/{id}/close")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<CloseTopicResponse> closeTopic(@PathVariable String id, @RequestBody CloseTopicRequest dto) {

        var response = topicService.closeTopic(id, dto);

        return ResponseEntity.ok(response);
    }
}
