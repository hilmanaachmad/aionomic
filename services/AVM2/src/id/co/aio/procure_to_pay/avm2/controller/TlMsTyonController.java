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

import id.co.aio.procure_to_pay.avm2.TlMsTyon;
import id.co.aio.procure_to_pay.avm2.service.TlMsTyonService;


/**
 * Controller object for domain model class TlMsTyon.
 * @see TlMsTyon
 */
@RestController("AVM2.TlMsTyonController")
@Api(value = "TlMsTyonController", description = "Exposes APIs to work with TlMsTyon resource.")
@RequestMapping("/AVM2/TlMsTyon")
public class TlMsTyonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsTyonController.class);

    @Autowired
	@Qualifier("AVM2.TlMsTyonService")
	private TlMsTyonService tlMsTyonService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsTyon instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsTyon createTlMsTyon(@RequestBody TlMsTyon tlMsTyon) {
		LOGGER.debug("Create TlMsTyon with information: {}" , tlMsTyon);

		tlMsTyon = tlMsTyonService.create(tlMsTyon);
		LOGGER.debug("Created TlMsTyon with information: {}" , tlMsTyon);

	    return tlMsTyon;
	}

    @ApiOperation(value = "Returns the TlMsTyon instance associated with the given id.")
    @RequestMapping(value = "/{abIdpetr:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsTyon getTlMsTyon(@PathVariable("abIdpetr") Integer abIdpetr) {
        LOGGER.debug("Getting TlMsTyon with id: {}" , abIdpetr);

        TlMsTyon foundTlMsTyon = tlMsTyonService.getById(abIdpetr);
        LOGGER.debug("TlMsTyon details with id: {}" , foundTlMsTyon);

        return foundTlMsTyon;
    }

    @ApiOperation(value = "Updates the TlMsTyon instance associated with the given id.")
    @RequestMapping(value = "/{abIdpetr:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsTyon editTlMsTyon(@PathVariable("abIdpetr") Integer abIdpetr, @RequestBody TlMsTyon tlMsTyon) {
        LOGGER.debug("Editing TlMsTyon with id: {}" , tlMsTyon.getAbIdpetr());

        tlMsTyon.setAbIdpetr(abIdpetr);
        tlMsTyon = tlMsTyonService.update(tlMsTyon);
        LOGGER.debug("TlMsTyon details with id: {}" , tlMsTyon);

        return tlMsTyon;
    }
    
    @ApiOperation(value = "Partially updates the TlMsTyon instance associated with the given id.")
    @RequestMapping(value = "/{abIdpetr:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsTyon patchTlMsTyon(@PathVariable("abIdpetr") Integer abIdpetr, @RequestBody @MapTo(TlMsTyon.class) Map<String, Object> tlMsTyonPatch) {
        LOGGER.debug("Partially updating TlMsTyon with id: {}" , abIdpetr);

        TlMsTyon tlMsTyon = tlMsTyonService.partialUpdate(abIdpetr, tlMsTyonPatch);
        LOGGER.debug("TlMsTyon details after partial update: {}" , tlMsTyon);

        return tlMsTyon;
    }

    @ApiOperation(value = "Deletes the TlMsTyon instance associated with the given id.")
    @RequestMapping(value = "/{abIdpetr:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsTyon(@PathVariable("abIdpetr") Integer abIdpetr) {
        LOGGER.debug("Deleting TlMsTyon with id: {}" , abIdpetr);

        TlMsTyon deletedTlMsTyon = tlMsTyonService.delete(abIdpetr);

        return deletedTlMsTyon != null;
    }

    /**
     * @deprecated Use {@link #findTlMsTyons(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsTyon instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsTyon> searchTlMsTyonsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsTyons list by query filter:{}", (Object) queryFilters);
        return tlMsTyonService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsTyon instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsTyon> findTlMsTyons(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsTyons list by filter:", query);
        return tlMsTyonService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsTyon instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsTyon> filterTlMsTyons(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsTyons list by filter", query);
        return tlMsTyonService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsTyons(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsTyonService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsTyonsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsTyon.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsTyonService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsTyon instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsTyons( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsTyons");
		return tlMsTyonService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsTyonAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsTyonService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsTyonService instance
	 */
	protected void setTlMsTyonService(TlMsTyonService service) {
		this.tlMsTyonService = service;
	}

}