package com.ecommerce.domain.service;

import com.ecommerce.domain.exceptions.EntityInUseException;
import com.ecommerce.domain.exceptions.StateNotFoundException;
import com.ecommerce.domain.models.State;
import com.ecommerce.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    private static final String MSG_ESTADO_EM_USO = "Code state %d cannot be removed as it is in use";

    @Autowired
    private StateRepository stateRepository;

    public State save(State state) {
        return stateRepository.save(state);
    }

    public void delete(Long stateId) {
        try {
            stateRepository.deleteById(stateId);

        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(stateId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_ESTADO_EM_USO, stateId));
        }
    }


    public State searchOrFail(Long stateId) {
        return stateRepository.findById(stateId).orElseThrow(() -> new StateNotFoundException(stateId));
    }
}
