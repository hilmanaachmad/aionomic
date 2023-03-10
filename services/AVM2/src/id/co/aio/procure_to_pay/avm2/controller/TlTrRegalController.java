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

import id.co.aio.procure_to_pay.avm2.TlTrRegal;
import id.co.aio.procure_to_pay.avm2.service.TlTrRegalService;


/**
 * Controller object for domain model class TlTrRegal.
 * @see TlTrRegal
 */
@RestController("AVM2.TlTrRegalController")
@Api(value = "TlTrRegalController", description = "Exposes APIs to work with TlTrRegal resource.")
@RequestMapping("/AVM2/TlTrRegal")
public class TlTrRegalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlTrRegalController.class);

    @Autowired
	@Qualifier("AVM2.TlTrRegalService")
	private TlTrRegalService tlTrRegalService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlTrRegal instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrRegal createTlTrRegal(@RequestBody TlTrRegal tlTrRegal) {
		LOGGER.debug("Create TlTrRegal with information: {}" , tlTrRegal);

		tlTrRegal = tlTrRegalService.create(tlTrRegal);
		LOGGER.debug("Created TlTrRegal with information: {}" , tlTrRegal);

	    return tlTrRegal;
	}

    @ApiOperation(value = "Returns the TlTrRegal instance associated with the given id.")
    @RequestMapping(value = "/{rlId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrRegal getTlTrRegal(@PathVariable("rlId") Integer rlId) {
        LOGGER.debug("Getting TlTrRegal with id: {}" , rlId);

        TlTrRegal foundTlTrRegal = tlTrRegalService.getById(rlId);
        LOGGER.debug("TlTrRegal details with id: {}" , foundTlTrRegal);

        return foundTlTrRegal;
    }

    @ApiOperation(value = "Updates the TlTrRegal instance associated with the given id.")
    @RequestMapping(value = "/{rlId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrRegal editTlTrRegal(@PathVariable("rlId") Integer rlId, @RequestBody TlTrRegal tlTrRegal) {
        LOGGER.debug("Editing TlTrRegal with id: {}" , tlTrRegal.getRlId());

        tlTrRegal.setRlId(rlId);
        tlTrRegal = tlTrRegalService.update(tlTrRegal);
        LOGGER.debug("TlTrRegal details with id: {}" , tlTrRegal);

        return tlTrRegal;
    }
    
    @ApiOperation(value = "Partially updates the TlTrRegal instance associated with the given id.")
    @RequestMapping(value = "/{rlId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrRegal patchTlTrRegal(@PathVariable("rlId") Integer rlId, @RequestBody @MapTo(TlTrRegal.class) Map<String, Object> tlTrRegalPatch) {
        LOGGER.debug("Partially updating TlTrRegal with id: {}" , rlId);

        TlTrRegal tlTrRegal = tlTrRegalService.partialUpdate(rlId, tlTrRegalPatch);
        LOGGER.debug("TlTrRegal details after partial update: {}" , tlTrRegal);

        return tlTrRegal;
    }

    @ApiOperation(value = "Deletes the TlTrRegal instance associated with the given id.")
    @RequestMapping(value = "/{rlId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlTrRegal(@PathVariable("rlId") Integer rlId) {
        LOGGER.debug("Deleting TlTrRegal with id: {}" , rlId);

        TlTrRegal deletedTlTrRegal = tlTrRegalService.delete(rlId);

        return deletedTlTrRegal != null;
    }

    /**
     * @deprecated Use {@link #findTlTrRegals(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlTrRegal instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlTrRegal> searchTlTrRegalsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlTrRegals list by query filter:{}", (Object) queryFilters);
        return tlTrRegalService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlTrRegal instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlTrRegal> findTlTrRegals(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlTrRegals list by filter:", query);
        return tlTrRegalService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlTrRegal instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlTrRegal> filterTlTrRegals(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlTrRegals list by filter", query);
        return tlTrRegalService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlTrRegals(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlTrRegalService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlTrRegalsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlTrRegal.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlTrRegalService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlTrRegal instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlTrRegals( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlTrRegals");
		return tlTrRegalService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlTrRegalAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlTrRegalService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlTrRegalService instance
	 */
	protected void setTlTrRegalService(TlTrRegalService service) {
		this.tlTrRegalService = service;
	}

}