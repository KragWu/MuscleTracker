package fr.kragwu.muscletracker.userapi.adapters;

import fr.kragwu.muscletracker.userapi.controllers.dto.UserDTO;
import fr.kragwu.muscletracker.userapi.repositories.entities.User;

public class UserAdapter {

    public static User transformDTOtoDAO(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getLogin(), userDTO.getPassword(), userDTO.getRegistrationDate());
    }

    public static UserDTO transformDAOtoDTO(User user) {
        return new UserDTO(user.getId(), user.getLogin(), user.getPassword(), user.getRegistrationDate());
    }
}
