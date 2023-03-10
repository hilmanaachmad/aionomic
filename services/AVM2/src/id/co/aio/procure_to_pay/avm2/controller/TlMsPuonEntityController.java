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

import id.co.aio.procure_to_pay.avm2.TlMsPuonEntity;
import id.co.aio.procure_to_pay.avm2.service.TlMsPuonEntityService;


/**
 * Controller object for domain model class TlMsPuonEntity.
 * @see TlMsPuonEntity
 */
@RestController("AVM2.TlMsPuonEntityController")
@Api(value = "TlMsPuonEntityController", description = "Exposes APIs to work with TlMsPuonEntity resource.")
@RequestMapping("/AVM2/TlMsPuonEntity")
public class TlMsPuonEntityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsPuonEntityController.class);

    @Autowired
	@Qualifier("AVM2.TlMsPuonEntityService")
	private TlMsPuonEntityService tlMsPuonEntityService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsPuonEntity instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPuonEntity createTlMsPuonEntity(@RequestBody TlMsPuonEntity tlMsPuonEntity) {
		LOGGER.debug("Create TlMsPuonEntity with information: {}" , tlMsPuonEntity);

		tlMsPuonEntity = tlMsPuonEntityService.create(tlMsPuonEntity);
		LOGGER.debug("Created TlMsPuonEntity with information: {}" , tlMsPuonEntity);

	    return tlMsPuonEntity;
	}

    @ApiOperation(value = "Returns the TlMsPuonEntity instance associated with the given id.")
    @RequestMapping(value = "/{abIdseon:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPuonEntity getTlMsPuonEntity(@PathVariable("abIdseon") Integer abIdseon) {
        LOGGER.debug("Getting TlMsPuonEntity with id: {}" , abIdseon);

        TlMsPuonEntity foundTlMsPuonEntity = tlMsPuonEntityService.getById(abIdseon);
        LOGGER.debug("TlMsPuonEntity details with id: {}" , foundTlMsPuonEntity);

        return foundTlMsPuonEntity;
    }

    @ApiOperation(value = "Updates the TlMsPuonEntity instance associated with the given id.")
    @RequestMapping(value = "/{abIdseon:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPuonEntity editTlMsPuonEntity(@PathVariable("abIdseon") Integer abIdseon, @RequestBody TlMsPuonEntity tlMsPuonEntity) {
        LOGGER.debug("Editing TlMsPuonEntity with id: {}" , tlMsPuonEntity.getAbIdseon());

        tlMsPuonEntity.setAbIdseon(abIdseon);
        tlMsPuonEntity = tlMsPuonEntityService.update(tlMsPuonEntity);
        LOGGER.debug("TlMsPuonEntity details with id: {}" , tlMsPuonEntity);

        return tlMsPuonEntity;
    }
    
    @ApiOperation(value = "Partially updates the TlMsPuonEntity instance associated with the given id.")
    @RequestMapping(value = "/{abIdseon:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPuonEntity patchTlMsPuonEntity(@PathVariable("abIdseon") Integer abIdseon, @RequestBody @MapTo(TlMsPuonEntity.class) Map<String, Object> tlMsPuonEntityPatch) {
        LOGGER.debug("Partially updating TlMsPuonEntity with id: {}" , abIdseon);

        TlMsPuonEntity tlMsPuonEntity = tlMsPuonEntityService.partialUpdate(abIdseon, tlMsPuonEntityPatch);
        LOGGER.debug("TlMsPuonEntity details after partial update: {}" , tlMsPuonEntity);

        return tlMsPuonEntity;
    }

    @ApiOperation(value = "Deletes the TlMsPuonEntity instance associated with the given id.")
    @RequestMapping(value = "/{abIdseon:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsPuonEntity(@PathVariable("abIdseon") Integer abIdseon) {
        LOGGER.debug("Deleting TlMsPuonEntity with id: {}" , abIdseon);

        TlMsPuonEntity deletedTlMsPuonEntity = tlMsPuonEntityService.delete(abIdseon);

        return deletedTlMsPuonEntity != null;
    }

    /**
     * @deprecated Use {@link #findTlMsPuonEntities(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsPuonEntity instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsPuonEntity> searchTlMsPuonEntitiesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsPuonEntities list by query filter:{}", (Object) queryFilters);
        return tlMsPuonEntityService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsPuonEntity instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsPuonEntity> findTlMsPuonEntities(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsPuonEntities list by filter:", query);
        return tlMsPuonEntityService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsPuonEntity instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsPuonEntity> filterTlMsPuonEntities(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsPuonEntities list by filter", query);
        return tlMsPuonEntityService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsPuonEntities(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsPuonEntityService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsPuonEntitiesAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsPuonEntity.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsPuonEntityService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsPuonEntity instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsPuonEntities( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsPuonEntities");
		return tlMsPuonEntityService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsPuonEntityAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsPuonEntityService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsPuonEntityService instance
	 */
	protected void setTlMsPuonEntityService(TlMsPuonEntityService service) {
		this.tlMsPuonEntityService = service;
	}

}