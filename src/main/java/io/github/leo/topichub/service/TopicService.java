package io.github.leo.topichub.service;

import io.github.leo.topichub.domain.model.Response;
import io.github.leo.topichub.domain.model.Topic;
import io.github.leo.topichub.domain.model.User;
import io.github.leo.topichub.domain.valueobject.TopicStatus;
import io.github.leo.topichub.dto.request.*;
import io.github.leo.topichub.dto.response.*;
import io.github.leo.topichub.exception.ForbiddenException;
import io.github.leo.topichub.exception.ResourceNotFoundException;
import io.github.leo.topichub.exception.UnprocessableEntityException;
import io.github.leo.topichub.repository.CourseRepository;
import io.github.leo.topichub.repository.ResponseRepository;
import io.github.leo.topichub.repository.TopicRepository;
import io.github.leo.topichub.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ResponseRepository responseRepository;

    public TopicService(
            TopicRepository topicRepository,
            CourseRepository courseRepository,
            UserRepository userRepository,
            ResponseRepository responseRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.responseRepository = responseRepository;
    }

    public CreateTopicResponse createTopic(CreateTopicRequest dto) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String authenticatedUserId = authenticatedUser.getId();

        var user = userRepository
                .findById(authenticatedUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var course = courseRepository
                .findByIdAndActiveTrue(dto.course())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        var topic = new Topic();

        topic.setTitle(dto.title());
        topic.setType(dto.type());
        topic.setMessage(dto.message());
        topic.markAsOpen();
        topic.setAuthorId(user.getId());
        topic.setCourseId(course.getId());

        var savedTopic = topicRepository.save(topic);

        return new CreateTopicResponse(
                savedTopic.getId(),
                savedTopic.getTitle(),
                savedTopic.getType(),
                savedTopic.getMessage(),
                savedTopic.getCreatedAt(),
                savedTopic.getStatus(),
                savedTopic.getAuthorId(),
                savedTopic.getCourseId());
    }

    public void deactivationTopic(String id) {

        var topic = topicRepository
                .findByIdAndStatus(id, TopicStatus.OPEN)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        topic.suspend();
        topicRepository.save(topic);
    }

    public CreateAnswerResponse createResponse(String topicId, CreateAnswerRequest request) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String authenticatedUserId = authenticatedUser.getId();

        var topic =
                topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        if (topic.getStatus() == TopicStatus.CLOSED) {
            throw new ResourceNotFoundException("Topic is inactive");
        }

        var response = new Response();

        response.setTopicId(topicId);
        response.setMessage(request.message());
        response.setAuthorId(authenticatedUserId);

        var savedResponse = responseRepository.save(response);

        return new CreateAnswerResponse(
                savedResponse.getId(),
                savedResponse.getMessage(),
                savedResponse.getAuthorId(),
                savedResponse.getTopicId());
    }

    public AnswerResolvedResponse markAsSolved(String topicId, String responseId) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String authenticatedUserId = authenticatedUser.getId();

        Topic topic =
                topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        boolean isOwner = authenticatedUserId.equals(topic.getAuthorId());

        boolean isPrivileged = authenticatedUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")
                        || auth.getAuthority().equals("ROLE_MODERATOR"));

        if (!isOwner && !isPrivileged) {
            throw new ForbiddenException("Only the topic creator, admins or moderators can mark it as resolved");
        }

        Response response = responseRepository
                .findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response not found"));

        if (!response.getTopicId().equals(topicId)) {
            throw new UnprocessableEntityException("Response does not belong to this topic");
        }

        topic.markAsSolved(responseId);

        var savedTopic = topicRepository.save(topic);

        return new AnswerResolvedResponse(
                savedTopic.getId(),
                savedTopic.getTitle(),
                savedTopic.getType(),
                savedTopic.getMessage(),
                savedTopic.getCreatedAt(),
                savedTopic.getStatus(),
                savedTopic.getLastSolvedResponseId(),
                savedTopic.getCourseId());
    }

    public void deleteResponse(String topicId, String responseId, DeactivateResponseRequest request) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String moderatorId = authenticatedUser.getId();

        var response = responseRepository
                .findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response not found"));

        if (!response.getTopicId().equals(topicId)) {
            throw new UnprocessableEntityException("Response does not belong to this topic");
        }

        response.deactivate(moderatorId, request.reason());
        responseRepository.save(response);

        var topic =
                topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        if (topic.getLastSolvedResponseId() != null) {
            topic.handleSolutionRemoval(responseId);
            topicRepository.save(topic);
        }
    }

    public PageResponse<TopicListResponse> listAllTopic(Pageable pp) {

        var page = topicRepository
                .findAll(pp)
                .map(t -> new TopicListResponse(
                        t.getId(),
                        t.getTitle(),
                        t.getMessage(),
                        t.getType(),
                        t.getCreatedAt(),
                        t.getStatus(),
                        t.getAuthorId(),
                        t.getCourseId()));

        return PageResponse.from(page);
    }

    public TopicDetailsResponse topicDetails(String id) {

        var topic = topicRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        return new TopicDetailsResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getType(),
                topic.getCreatedAt(),
                topic.getStatus(),
                topic.getAuthorId(),
                topic.getCourseId());
    }

    public UpdateTopicResponse updateTopic(String id, UpdateTopicRequest dto) {
        var topic = topicRepository
                .findByIdAndStatus(id, TopicStatus.OPEN)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        if (dto.title() != null && !dto.title().isEmpty()) {
            topic.setTitle(dto.title());
        }

        if (dto.type() != null) {
            topic.setType(dto.type());
        }

        if (dto.message() != null && !dto.message().isEmpty()) {
            topic.setMessage(dto.message());
        }

        var savedTopic = topicRepository.save(topic);

        return new UpdateTopicResponse(
                savedTopic.getId(),
                savedTopic.getTitle(),
                savedTopic.getType(),
                savedTopic.getMessage(),
                savedTopic.getCreatedAt(),
                savedTopic.getAuthorId(),
                savedTopic.getCourseId());
    }

    public CloseTopicResponse closeTopic(String id, CloseTopicRequest request) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String moderatorId = authenticatedUser.getId();

        var topic = topicRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        topic.close(moderatorId, request.reason());
        var savedTopic = topicRepository.save(topic);

        return new CloseTopicResponse(
                savedTopic.getId(),
                savedTopic.getTitle(),
                savedTopic.getType(),
                savedTopic.getMessage(),
                savedTopic.getCreatedAt(),
                savedTopic.getStatus(),
                savedTopic.getAuthorId(),
                savedTopic.getCourseId(),
                savedTopic.getLastSolvedResponseId(),
                savedTopic.getClosedReason(),
                savedTopic.getClosedBy(),
                savedTopic.getClosedAt());
    }
}
