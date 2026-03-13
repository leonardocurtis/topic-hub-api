package io.github.leo.topichub.dto.response;

import java.util.List;

public record CourseDetailsResponse(String id, String name, List<String> categories, boolean active) {}
