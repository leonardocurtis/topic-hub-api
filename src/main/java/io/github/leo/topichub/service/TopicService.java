package io.github.leo.topichub.service;

import io.github.leo.topichub.domain.model.Response;
import io.github.leo.topichub.domain.model.Topic;
import io.github.leo.topichub.domain.model.User;
import io.github.leo.topichub.dto.request.CreateAnswerRequest;
import io.github.leo.topichub.dto.request.CreateTopicRequest;
import io.github.leo.topichub.dto.request.DeactivateResponseRequest;
import io.github.leo.topichub.dto.response.CreateAnswerResponse;
import io.github.leo.topichub.dto.response.CreateTopicResponse;
import io.github.leo.topichub.exception.ResourceNotFoundException;
import io.github.leo.topichub.exception.UnprocessableEntityException;
import io.github.leo.topichub.repository.CourseRepository;
import io.github.leo.topichub.repository.ResponseRepository;
import io.github.leo.topichub.repository.TopicRepository;
import io.github.leo.topichub.repository.UserRepository;
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
                .findById(dto.course())
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
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        topic.deactivate();
        topicRepository.save(topic);
    }

    public CreateAnswerResponse createResponse(String topicId, CreateAnswerRequest request) {

        var authenticatedUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String authenticatedUserId = authenticatedUser.getId();

        var topic =
                topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        if (!topic.isActive()) {
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

    public void markAsSolved(String topicId, String responseId) {

        Topic topic =
                topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        Response response = responseRepository
                .findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response not found"));

        if (!response.getTopicId().equals(topicId)) {
            throw new UnprocessableEntityException("Response does not belong to this topic.");
        }

        topic.markAsSolved(responseId);

        topicRepository.save(topic);
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

        topic.handleSolutionRemoval(responseId);
        topicRepository.save(topic);
    }
}
