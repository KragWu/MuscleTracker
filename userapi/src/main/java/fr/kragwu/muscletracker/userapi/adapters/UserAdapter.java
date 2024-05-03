package fr.kragwu.muscletracker.userapi.adapters;

import fr.kragwu.muscletracker.userapi.services.bo.UserBO;
import fr.kragwu.muscletracker.userapi.repositories.entities.User;

public class UserAdapter {

    public static User transformDTOtoDAO(UserBO userBO) {
        return new User(userBO.getId(), userBO.getLogin(), userBO.getPassword(), userBO.getRegistrationDate());
    }

    public static UserBO transformDAOtoDTO(User user) {
        return new UserBO(user.getId(), user.getLogin(), user.getPassword(), user.getRegistrationDate());
    }
}
