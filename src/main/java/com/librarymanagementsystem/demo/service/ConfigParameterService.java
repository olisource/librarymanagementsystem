package com.librarymanagementsystem.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarymanagementsystem.demo.entity.ConfigParameter;
import com.librarymanagementsystem.demo.repository.ConfigParameterRepository;

@Service
public class ConfigParameterService {
	@Autowired
    private ConfigParameterRepository configParameterRepository;
	
	public List<ConfigParameter> findAll() {
		List<ConfigParameter> configParameterList = new ArrayList<ConfigParameter>();
		Iterable<ConfigParameter> iterable = configParameterRepository.findAll();
		if (iterable != null) {
			iterable.forEach(configParameterList::add);
		}
		return configParameterList;
	}
	
	public ConfigParameter findById(Long id) {
		return configParameterRepository.findOne(id);
	}
	
	public ConfigParameter save(ConfigParameter configParameter) {
		return configParameterRepository.save(configParameter);
	}
	
	public void delete(ConfigParameter configParameter) {
		configParameterRepository.delete(configParameter);
        return;
	}
	
    public List<ConfigParameter> findByLevel(String level) {    
        return configParameterRepository.findByLevel(level);
    }

    public List<ConfigParameter> findByLevelAndName(String level, String name) {
        return configParameterRepository.findByLevelAndName(level, name);
    }    
}