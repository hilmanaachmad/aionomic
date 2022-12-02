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

import id.co.aio.procure_to_pay.avm2.TlMsLoc;
import id.co.aio.procure_to_pay.avm2.service.TlMsLocService;


/**
 * Controller object for domain model class TlMsLoc.
 * @see TlMsLoc
 */
@RestController("AVM2.TlMsLocController")
@Api(value = "TlMsLocController", description = "Exposes APIs to work with TlMsLoc resource.")
@RequestMapping("/AVM2/TlMsLoc")
public class TlMsLocController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsLocController.class);

    @Autowired
	@Qualifier("AVM2.TlMsLocService")
	private TlMsLocService tlMsLocService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsLoc instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsLoc createTlMsLoc(@RequestBody TlMsLoc tlMsLoc) {
		LOGGER.debug("Create TlMsLoc with information: {}" , tlMsLoc);

		tlMsLoc = tlMsLocService.create(tlMsLoc);
		LOGGER.debug("Created TlMsLoc with information: {}" , tlMsLoc);

	    return tlMsLoc;
	}

    @ApiOperation(value = "Returns the TlMsLoc instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsLoc getTlMsLoc(@PathVariable("id") Long id) {
        LOGGER.debug("Getting TlMsLoc with id: {}" , id);

        TlMsLoc foundTlMsLoc = tlMsLocService.getById(id);
        LOGGER.debug("TlMsLoc details with id: {}" , foundTlMsLoc);

        return foundTlMsLoc;
    }

    @ApiOperation(value = "Updates the TlMsLoc instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsLoc editTlMsLoc(@PathVariable("id") Long id, @RequestBody TlMsLoc tlMsLoc) {
        LOGGER.debug("Editing TlMsLoc with id: {}" , tlMsLoc.getId());

        tlMsLoc.setId(id);
        tlMsLoc = tlMsLocService.update(tlMsLoc);
        LOGGER.debug("TlMsLoc details with id: {}" , tlMsLoc);

        return tlMsLoc;
    }
    
    @ApiOperation(value = "Partially updates the TlMsLoc instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsLoc patchTlMsLoc(@PathVariable("id") Long id, @RequestBody @MapTo(TlMsLoc.class) Map<String, Object> tlMsLocPatch) {
        LOGGER.debug("Partially updating TlMsLoc with id: {}" , id);

        TlMsLoc tlMsLoc = tlMsLocService.partialUpdate(id, tlMsLocPatch);
        LOGGER.debug("TlMsLoc details after partial update: {}" , tlMsLoc);

        return tlMsLoc;
    }

    @ApiOperation(value = "Deletes the TlMsLoc instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsLoc(@PathVariable("id") Long id) {
        LOGGER.debug("Deleting TlMsLoc with id: {}" , id);

        TlMsLoc deletedTlMsLoc = tlMsLocService.delete(id);

        return deletedTlMsLoc != null;
    }

    /**
     * @deprecated Use {@link #findTlMsLocs(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsLoc instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsLoc> searchTlMsLocsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsLocs list by query filter:{}", (Object) queryFilters);
        return tlMsLocService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsLoc instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsLoc> findTlMsLocs(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsLocs list by filter:", query);
        return tlMsLocService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsLoc instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsLoc> filterTlMsLocs(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsLocs list by filter", query);
        return tlMsLocService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsLocs(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsLocService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsLocsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsLoc.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsLocService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsLoc instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsLocs( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsLocs");
		return tlMsLocService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsLocAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsLocService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsLocService instance
	 */
	protected void setTlMsLocService(TlMsLocService service) {
		this.tlMsLocService = service;
	}

}