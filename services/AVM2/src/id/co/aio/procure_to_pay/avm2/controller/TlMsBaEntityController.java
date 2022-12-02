/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.manager.ExportedFileManager;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.MapTo;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import id.co.aio.procure_to_pay.avm2.TlMsBaEntity;
import id.co.aio.procure_to_pay.avm2.service.TlMsBaEntityService;


/**
 * Controller object for domain model class TlMsBaEntity.
 * @see TlMsBaEntity
 */
@RestController("AVM2.TlMsBaEntityController")
@Api(value = "TlMsBaEntityController", description = "Exposes APIs to work with TlMsBaEntity resource.")
@RequestMapping("/AVM2/TlMsBaEntity")
public class TlMsBaEntityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsBaEntityController.class);

    @Autowired
	@Qualifier("AVM2.TlMsBaEntityService")
	private TlMsBaEntityService tlMsBaEntityService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsBaEntity instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsBaEntity createTlMsBaEntity(@RequestBody TlMsBaEntity tlMsBaEntity) {
		LOGGER.debug("Create TlMsBaEntity with information: {}" , tlMsBaEntity);

		tlMsBaEntity = tlMsBaEntityService.create(tlMsBaEntity);
		LOGGER.debug("Created TlMsBaEntity with information: {}" , tlMsBaEntity);

	    return tlMsBaEntity;
	}

    @ApiOperation(value = "Returns the TlMsBaEntity instance associated with the given id.")
    @RequestMapping(value = "/{abCoey:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsBaEntity getTlMsBaEntity(@PathVariable("abCoey") String abCoey) {
        LOGGER.debug("Getting TlMsBaEntity with id: {}" , abCoey);

        TlMsBaEntity foundTlMsBaEntity = tlMsBaEntityService.getById(abCoey);
        LOGGER.debug("TlMsBaEntity details with id: {}" , foundTlMsBaEntity);

        return foundTlMsBaEntity;
    }

    @ApiOperation(value = "Updates the TlMsBaEntity instance associated with the given id.")
    @RequestMapping(value = "/{abCoey:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsBaEntity editTlMsBaEntity(@PathVariable("abCoey") String abCoey, @RequestBody TlMsBaEntity tlMsBaEntity) {
        LOGGER.debug("Editing TlMsBaEntity with id: {}" , tlMsBaEntity.getAbCoey());

        tlMsBaEntity.setAbCoey(abCoey);
        tlMsBaEntity = tlMsBaEntityService.update(tlMsBaEntity);
        LOGGER.debug("TlMsBaEntity details with id: {}" , tlMsBaEntity);

        return tlMsBaEntity;
    }
    
    @ApiOperation(value = "Partially updates the TlMsBaEntity instance associated with the given id.")
    @RequestMapping(value = "/{abCoey:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsBaEntity patchTlMsBaEntity(@PathVariable("abCoey") String abCoey, @RequestBody @MapTo(TlMsBaEntity.class) Map<String, Object> tlMsBaEntityPatch) {
        LOGGER.debug("Partially updating TlMsBaEntity with id: {}" , abCoey);

        TlMsBaEntity tlMsBaEntity = tlMsBaEntityService.partialUpdate(abCoey, tlMsBaEntityPatch);
        LOGGER.debug("TlMsBaEntity details after partial update: {}" , tlMsBaEntity);

        return tlMsBaEntity;
    }

    @ApiOperation(value = "Deletes the TlMsBaEntity instance associated with the given id.")
    @RequestMapping(value = "/{abCoey:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsBaEntity(@PathVariable("abCoey") String abCoey) {
        LOGGER.debug("Deleting TlMsBaEntity with id: {}" , abCoey);

        TlMsBaEntity deletedTlMsBaEntity = tlMsBaEntityService.delete(abCoey);

        return deletedTlMsBaEntity != null;
    }

    /**
     * @deprecated Use {@link #findTlMsBaEntities(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsBaEntity instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsBaEntity> searchTlMsBaEntitiesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsBaEntities list by query filter:{}", (Object) queryFilters);
        return tlMsBaEntityService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsBaEntity instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsBaEntity> findTlMsBaEntities(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsBaEntities list by filter:", query);
        return tlMsBaEntityService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsBaEntity instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsBaEntity> filterTlMsBaEntities(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsBaEntities list by filter", query);
        return tlMsBaEntityService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsBaEntities(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsBaEntityService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsBaEntitiesAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsBaEntity.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsBaEntityService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsBaEntity instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsBaEntities( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsBaEntities");
		return tlMsBaEntityService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsBaEntityAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsBaEntityService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsBaEntityService instance
	 */
	protected void setTlMsBaEntityService(TlMsBaEntityService service) {
		this.tlMsBaEntityService = service;
	}

}