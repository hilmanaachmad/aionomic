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

import id.co.aio.procure_to_pay.avm2.TlLg;
import id.co.aio.procure_to_pay.avm2.service.TlLgService;


/**
 * Controller object for domain model class TlLg.
 * @see TlLg
 */
@RestController("AVM2.TlLgController")
@Api(value = "TlLgController", description = "Exposes APIs to work with TlLg resource.")
@RequestMapping("/AVM2/TlLg")
public class TlLgController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlLgController.class);

    @Autowired
	@Qualifier("AVM2.TlLgService")
	private TlLgService tlLgService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlLg instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlLg createTlLg(@RequestBody TlLg tlLg) {
		LOGGER.debug("Create TlLg with information: {}" , tlLg);

		tlLg = tlLgService.create(tlLg);
		LOGGER.debug("Created TlLg with information: {}" , tlLg);

	    return tlLg;
	}

    @ApiOperation(value = "Returns the TlLg instance associated with the given id.")
    @RequestMapping(value = "/{abIdloin:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlLg getTlLg(@PathVariable("abIdloin") Integer abIdloin) {
        LOGGER.debug("Getting TlLg with id: {}" , abIdloin);

        TlLg foundTlLg = tlLgService.getById(abIdloin);
        LOGGER.debug("TlLg details with id: {}" , foundTlLg);

        return foundTlLg;
    }

    @ApiOperation(value = "Updates the TlLg instance associated with the given id.")
    @RequestMapping(value = "/{abIdloin:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlLg editTlLg(@PathVariable("abIdloin") Integer abIdloin, @RequestBody TlLg tlLg) {
        LOGGER.debug("Editing TlLg with id: {}" , tlLg.getAbIdloin());

        tlLg.setAbIdloin(abIdloin);
        tlLg = tlLgService.update(tlLg);
        LOGGER.debug("TlLg details with id: {}" , tlLg);

        return tlLg;
    }
    
    @ApiOperation(value = "Partially updates the TlLg instance associated with the given id.")
    @RequestMapping(value = "/{abIdloin:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlLg patchTlLg(@PathVariable("abIdloin") Integer abIdloin, @RequestBody @MapTo(TlLg.class) Map<String, Object> tlLgPatch) {
        LOGGER.debug("Partially updating TlLg with id: {}" , abIdloin);

        TlLg tlLg = tlLgService.partialUpdate(abIdloin, tlLgPatch);
        LOGGER.debug("TlLg details after partial update: {}" , tlLg);

        return tlLg;
    }

    @ApiOperation(value = "Deletes the TlLg instance associated with the given id.")
    @RequestMapping(value = "/{abIdloin:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlLg(@PathVariable("abIdloin") Integer abIdloin) {
        LOGGER.debug("Deleting TlLg with id: {}" , abIdloin);

        TlLg deletedTlLg = tlLgService.delete(abIdloin);

        return deletedTlLg != null;
    }

    /**
     * @deprecated Use {@link #findTlLgs(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlLg instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlLg> searchTlLgsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlLgs list by query filter:{}", (Object) queryFilters);
        return tlLgService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlLg instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlLg> findTlLgs(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlLgs list by filter:", query);
        return tlLgService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlLg instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlLg> filterTlLgs(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlLgs list by filter", query);
        return tlLgService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlLgs(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlLgService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlLgsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlLg.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlLgService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlLg instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlLgs( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlLgs");
		return tlLgService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlLgAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlLgService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlLgService instance
	 */
	protected void setTlLgService(TlLgService service) {
		this.tlLgService = service;
	}

}