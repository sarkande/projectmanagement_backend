package com.codesolution.projectmanagement.dtos;

import com.codesolution.projectmanagement.models.User;

public record UserWithRoleDTO(
        User user,  // Objet User complet ✅
        String role
) {}

