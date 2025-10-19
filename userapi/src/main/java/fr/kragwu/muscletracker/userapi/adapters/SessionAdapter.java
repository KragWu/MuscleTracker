package fr.kragwu.muscletracker.userapi.adapters;

import fr.kragwu.muscletracker.userapi.controllers.dto.SessionDTO;
import fr.kragwu.muscletracker.userapi.repositories.entities.Session;
import fr.kragwu.muscletracker.userapi.services.bo.SessionBO;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class SessionAdapter {

    public static Session transformBOtoDAO(SessionBO sessionBO) {
        if (sessionBO == null) {
            return null;
        }
        return new Session(sessionBO.getId(),
                sessionBO.getIdUser(),
                sessionBO.getLoginDateTime(),
                sessionBO.getLogoutDateTime(),
                sessionBO.getToken());
    }

    public static SessionBO transformDAOtoBO(Session session) {
        return new SessionBO(session.getId(),
                session.getIdUser(),
                session.getLoginDateTime(),
                session.getLogoutDateTime(),
                session.getToken());
    }

    public static SessionDTO transformBOtoDTO(SessionBO sessionBO) {
        if (sessionBO == null) {
            return new SessionDTO();
        }
        String idSecret = "";
        if (sessionBO.getId() != null) {
            idSecret = sessionBO.getId().toString();
        }
        String tokenSecret = "";
        if (!idSecret.isEmpty()) {
            tokenSecret = sessionBO.getToken();
        }
        return new SessionDTO(idSecret,
                tokenSecret);
    }

    public static SessionBO transformDTOtoBO(SessionDTO sessionDTO) {
        if (sessionDTO == null) {
            return new SessionBO();
        }
        UUID idSecret = null;
        if (!StringUtils.isBlank(sessionDTO.getId())) {
            try {
                idSecret = UUID.fromString(sessionDTO.getId());
            } catch (IllegalArgumentException iae) {
                log.error("Error session secret");
            }
        }
        String tokenSecret = null;
        if (idSecret != null) {
            tokenSecret = sessionDTO.getToken();
        }
        return new SessionBO(idSecret,
                null,
                null,
                null,
                tokenSecret);
    }
}
