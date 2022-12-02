/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.controller;

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

import id.co.aio.procure_to_pay.aio_ptp.TlMsVe;
import id.co.aio.procure_to_pay.aio_ptp.service.TlMsVeService;


/**
 * Controller object for domain model class TlMsVe.
 * @see TlMsVe
 */
@RestController("aio_ptp.TlMsVeController")
@Api(value = "TlMsVeController", description = "Exposes APIs to work with TlMsVe resource.")
@RequestMapping("/aio_ptp/TlMsVe")
public class TlMsVeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsVeController.class);

    @Autowired
	@Qualifier("aio_ptp.TlMsVeService")
	private TlMsVeService tlMsVeService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsVe instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsVe createTlMsVe(@RequestBody TlMsVe tlMsVe) {
		LOGGER.debug("Create TlMsVe with information: {}" , tlMsVe);

		tlMsVe = tlMsVeService.create(tlMsVe);
		LOGGER.debug("Created TlMsVe with information: {}" , tlMsVe);

	    return tlMsVe;
	}

    @ApiOperation(value = "Returns the TlMsVe instance associated with the given id.")
    @RequestMapping(value = "/{abIdor:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsVe getTlMsVe(@PathVariable("abIdor") Integer abIdor) {
        LOGGER.debug("Getting TlMsVe with id: {}" , abIdor);

        TlMsVe foundTlMsVe = tlMsVeService.getById(abIdor);
        LOGGER.debug("TlMsVe details with id: {}" , foundTlMsVe);

        return foundTlMsVe;
    }

    @ApiOperation(value = "Updates the TlMsVe instance associated with the given id.")
    @RequestMapping(value = "/{abIdor:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsVe editTlMsVe(@PathVariable("abIdor") Integer abIdor, @RequestBody TlMsVe tlMsVe) {
        LOGGER.debug("Editing TlMsVe with id: {}" , tlMsVe.getAbIdor());

        tlMsVe.setAbIdor(abIdor);
        tlMsVe = tlMsVeService.update(tlMsVe);
        LOGGER.debug("TlMsVe details with id: {}" , tlMsVe);

        return tlMsVe;
    }
    
    @ApiOperation(value = "Partially updates the TlMsVe instance associated with the given id.")
    @RequestMapping(value = "/{abIdor:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsVe patchTlMsVe(@PathVariable("abIdor") Integer abIdor, @RequestBody @MapTo(TlMsVe.class) Map<String, Object> tlMsVePatch) {
        LOGGER.debug("Partially updating TlMsVe with id: {}" , abIdor);

        TlMsVe tlMsVe = tlMsVeService.partialUpdate(abIdor, tlMsVePatch);
        LOGGER.debug("TlMsVe details after partial update: {}" , tlMsVe);

        return tlMsVe;
    }

    @ApiOperation(value = "Deletes the TlMsVe instance associated with the given id.")
    @RequestMapping(value = "/{abIdor:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsVe(@PathVariable("abIdor") Integer abIdor) {
        LOGGER.debug("Deleting TlMsVe with id: {}" , abIdor);

        TlMsVe deletedTlMsVe = tlMsVeService.delete(abIdor);

        return deletedTlMsVe != null;
    }

    /**
     * @deprecated Use {@link #findTlMsVes(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsVe instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsVe> searchTlMsVesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsVes list by query filter:{}", (Object) queryFilters);
        return tlMsVeService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsVe instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsVe> findTlMsVes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsVes list by filter:", query);
        return tlMsVeService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsVe instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsVe> filterTlMsVes(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsVes list by filter", query);
        return tlMsVeService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsVes(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsVeService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsVesAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsVe.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsVeService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsVe instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsVes( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsVes");
		return tlMsVeService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsVeAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsVeService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsVeService instance
	 */
	protected void setTlMsVeService(TlMsVeService service) {
		this.tlMsVeService = service;
	}

}