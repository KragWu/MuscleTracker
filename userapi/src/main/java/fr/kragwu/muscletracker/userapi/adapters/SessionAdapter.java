package fr.kragwu.muscletracker.userapi.adapters;

import fr.kragwu.muscletracker.userapi.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.entities.Session;

public class SessionAdapter {

    public static Session transformDTOtoDAO(SessionDTO sessionDTO) {
        return new Session(sessionDTO.getId(), sessionDTO.getIdUser(), sessionDTO.getLoginDateTime(), sessionDTO.getLogoutDateTime(), sessionDTO.getToken());
    }

    public static SessionDTO transformDAOtoDTO(Session session) {
        return new SessionDTO(session.getId(), session.getIdUser(), session.getLoginDateTime(), session.getLogoutDateTime(), session.getToken());
    }
}
