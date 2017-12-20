package com.librarymanagementsystem.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.librarymanagementsystem.demo.entity.ConfigParameter;
import com.librarymanagementsystem.demo.exception.BadRequestException;
import com.librarymanagementsystem.demo.exception.ResourceNotFoundException;
import com.librarymanagementsystem.demo.service.ConfigParameterService;
import com.librarymanagementsystem.demo.util.Constants;
import com.librarymanagementsystem.demo.util.Util;


@RestController
@RequestMapping(Constants.V1_CONFIGPARAMETERS_RESOURCE_URI)
public class ConfigParametersController {	
	@Autowired
	private ConfigParameterService configParameterService;
	
	private final static Logger LOGGER = Logger.getLogger(ConfigParametersController.class.getName());

	
	/**
	 * This method can be used to get the list of all the ConfigParameter objects.
	 * Optionally, the level parameter can be supplied as input parameter.  In that case, 
	 * a list of ConfigParamater objects are returned filtered based on levels.
	 * 
	 * @param level - The level.  This is an optional input parameter
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value = {"", "{level}"})
	@ResponseBody
	public List<ConfigParameter> getConfigParameters(@PathVariable Optional<String> level) {
    	LOGGER.log(Level.INFO, "\n ConfigParameterController::getConfigParameters(@PathVariable Optional<String> level)");
    	
    	if (!level.isPresent()) {
    		return configParameterService.findAll();
    	} else {
    		String levelString = level.get();
    		if (!((levelString.equals(Constants.LEVEL_GLOBAL)) || (levelString.equals(Constants.LEVEL_LOCAL)))) {
    			throw new BadRequestException("Unsupported value supplied for 'level'.  The supported values for 'level' are [GLOBAL | LOCAL].  Please supply valid input and retry");
    		}
    		
    		return configParameterService.findByLevel(level.get());
    	}
   	}
	
	/**
	 * This method is used to get the specific ConfigParameter object in a given level.  If the ConfigParameter does not exist in the local level, the method would get the ConfigParamter from the global level
	 * if present. 
	 * 
	 * @param level - The level.  The supported values are [GLOBAL | LOCAL]
	 * @param name - The specific ConfigParameter name
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value = {"{level}/{name}"})
	@ResponseBody
	public List<ConfigParameter> getConfigParameters(@PathVariable String level, @PathVariable String name) {
    	LOGGER.log(Level.INFO, "\n ConfigParameterController::getConfigParameters(@PathVariable String level, @PathVariable String name)");

    	List<ConfigParameter> configParameterList = new ArrayList<ConfigParameter>();
    	
    	if (level != null && level.length() > 0) {
    		if (!((level.equals(Constants.LEVEL_GLOBAL)) || (level.equals(Constants.LEVEL_LOCAL)))) {
    			throw new BadRequestException("Unsupported value supplied for 'level'.  The supported values for 'level' are [GLOBAL | LOCAL].  Please supply valid input and retry");
    		}
    	}

    	if (name != null && name.length() > 0) {
    	    configParameterList = configParameterService.findByLevelAndName(level, name);
    		
    		if (configParameterList != null && configParameterList.isEmpty()) {
    			if (Util.isLocalLevel(level)) {
    				configParameterList = configParameterService.findByLevelAndName(Constants.LEVEL_GLOBAL, name);
    			}
    		}    		
    	}

    	return configParameterList;
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ConfigParameter create(@RequestBody ConfigParameter configParameter) {
    	LOGGER.log(Level.INFO, "\n ConfigParameterController::create(@RequestBody ConfigParameter configParameter)");

		if (configParameter == null) {
			throw new BadRequestException("Null configParameter supplied.  Please provide valid input and retry");
		}
		
		if (configParameter.getId() != null && configParameter.getId() > 0) {
			throw new BadRequestException("'id' field should not be populated in the supplied configParameter object, as it is a system generated field.  Please provide valid input and retry");
		}
		
		if (configParameter.getName() == null || configParameter.getName().length() <= 0) {
			throw new BadRequestException("'name' is a not-nullable field in the ConfigParameter object.  Please provide valid input and retry");
		}

		String datatype = configParameter.getDatatype();
    	if (datatype != null && datatype.length() > 0) {
    		if (! ( (datatype.equals(Constants.CONFIGPARAMETER_DATATYPE_STRING)) || 
    				(datatype.equals(Constants.CONFIGPARAMETER_DATATYPE_INT)) ||
    				(datatype.equals(Constants.CONFIGPARAMETER_DATATYPE_DOUBLE)) ||
    				(datatype.equals(Constants.CONFIGPARAMETER_DATATYPE_BOOLEAN)) 
    			  ) 
    		   ) {
    			throw new BadRequestException("Unsupported value supplied for 'datatype'.  The supported values for 'datatype' are [String | Int | Double | Boolean].  Please supply valid input and retry");
    		}
    	}

		String level = configParameter.getLevel();
    	if (level != null && level.length() > 0) {
    		if (!((level.equals(Constants.LEVEL_GLOBAL)) || (level.equals(Constants.LEVEL_LOCAL)))) {
    			throw new BadRequestException("Unsupported value supplied for 'level'.  The supported values for 'level' are [GLOBAL | LOCAL].  Please supply valid input and retry");
    		}
    	}		
    	
		configParameterService.save(configParameter);		
		return configParameter;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable Long id) {
    	LOGGER.log(Level.INFO, "\n ConfigParameterController::delete(@PathVariable Long id)");
    	
    	if (id == null || id <= 0) {
			throw new BadRequestException("Unsupported value supplied for 'id'.  Please supply a value greater than zero and retry");
    	}
    	    	
		ConfigParameter configParameter = configParameterService.findById(id);
		if (configParameter == null) {
			throw new ResourceNotFoundException("There does not exist any ConfigParameter resource for id: " + id + " and so it can not be deleted."
					                            + "  Please provide the id of an existing ConfigParameter resource and retry");
		}
		
		configParameterService.delete(configParameter);
	}	
	
	@RequestMapping(method=RequestMethod.PUT, value="{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ConfigParameter update(@RequestBody ConfigParameter newConfigParameter, @PathVariable Long id) {
    	LOGGER.log(Level.INFO, "\n ConfigParameterController::update(@RequestBody ConfigParameter newConfigParameter, @PathVariable Long id)");

		if (newConfigParameter == null) {
			throw new BadRequestException("Null newConfigParameter supplied.  Please provide valid input and retry");
		}

		if (newConfigParameter.getId() != null && newConfigParameter.getId() > 0) {
			throw new BadRequestException("'id' field should not be populated in the supplied newConfigParameter object, as it is a system generated field.  Please provide valid input and retry");
		}
		
		if (newConfigParameter.getName() == null || newConfigParameter.getName().length() <= 0) {
			throw new BadRequestException("'name' is a not-nullable field in the newConfigParameter object.  Please provide valid input and retry");
		}

		String datatype = newConfigParameter.getDatatype();
    	if (datatype != null && datatype.length() > 0) {
    		if (! ( (datatype.equals(Constants.CONFIGPARAMETER_DATATYPE_STRING)) || 
    				(datatype.equals(Constants.CONFIGPARAMETER_DATATYPE_INT)) ||
    				(datatype.equals(Constants.CONFIGPARAMETER_DATATYPE_DOUBLE)) ||
    				(datatype.equals(Constants.CONFIGPARAMETER_DATATYPE_BOOLEAN)) 
    			  ) 
    		   ) {
    			throw new BadRequestException("Unsupported value supplied for 'datatype'.  The supported values for 'datatype' are [String | Int | Double | Boolean].  Please supply valid input and retry");
    		}
    	}

		String level = newConfigParameter.getLevel();
    	if (level != null && level.length() > 0) {
    		if (!((level.equals(Constants.LEVEL_GLOBAL)) || (level.equals(Constants.LEVEL_LOCAL)))) {
    			throw new BadRequestException("Unsupported value supplied for 'level'.  The supported values for 'level' are [GLOBAL | LOCAL].  Please supply valid input and retry");
    		}
    	}		
		
    	if (id == null || id <= 0) {
			throw new BadRequestException("Unsupported value supplied for 'id'.  Please supply a value greater than zero and retry");
    	}
    	    	
		ConfigParameter existingConfigParameter = configParameterService.findById(id);
		if (existingConfigParameter == null) {
			throw new ResourceNotFoundException("There does not exist any ConfigParameter resource for id: " + id + " and so it can not be updated."
					                            + "  Please provide the id of an existing ConfigParameter resource and retry");
		}
    	
		existingConfigParameter.setName(newConfigParameter.getName());
		existingConfigParameter.setCategory(newConfigParameter.getCategory());
		existingConfigParameter.setDatatype(newConfigParameter.getDatatype());
		existingConfigParameter.setValue(newConfigParameter.getValue());
		existingConfigParameter.setLevel(newConfigParameter.getLevel());
		existingConfigParameter.setBranch(newConfigParameter.getBranch());
	    		
		return configParameterService.save(existingConfigParameter);
	}
}