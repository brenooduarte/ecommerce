package com.ecommerce.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.exceptions.CityNotFoundException;
import com.ecommerce.domain.exceptions.EntityInUseException;
import com.ecommerce.domain.models.City;
import com.ecommerce.domain.models.State;
import com.ecommerce.domain.repository.CityRepository;

@Service
public class CityService {

    private static final String MSG_CITY_IN_USE = "Code city %d cannot be removed as it is in use";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateService stateService;

    public City save(City city) {
        Long stateId = city.getState().getId();

        State state = stateService.searchOrFail(stateId);

        city.setState(state);

        return cityRepository.save(city);
    }

    public void delete(Long cityId) {
        try {
            cityRepository.deleteById(cityId);

        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(cityId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_CITY_IN_USE, cityId));
        }
    }

    public City searchOrFail(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
    }
}
