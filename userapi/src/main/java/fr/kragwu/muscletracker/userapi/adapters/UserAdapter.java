package fr.kragwu.muscletracker.userapi.adapters;

import fr.kragwu.muscletracker.userapi.dto.UserDTO;
import fr.kragwu.muscletracker.userapi.entities.User;

public class UserAdapter {

    public static User transformDTOtoDAO(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getLogin(), userDTO.getPassword(), userDTO.getRegistrationDate());
    }

    public static UserDTO transformDAOtoDTO(User user) {
        return new UserDTO(user.getId(), user.getLogin(), user.getPassword(), user.getRegistrationDate());
    }
}
