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

import id.co.aio.procure_to_pay.avm2.TlMsCa;
import id.co.aio.procure_to_pay.avm2.service.TlMsCaService;


/**
 * Controller object for domain model class TlMsCa.
 * @see TlMsCa
 */
@RestController("AVM2.TlMsCaController")
@Api(value = "TlMsCaController", description = "Exposes APIs to work with TlMsCa resource.")
@RequestMapping("/AVM2/TlMsCa")
public class TlMsCaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsCaController.class);

    @Autowired
	@Qualifier("AVM2.TlMsCaService")
	private TlMsCaService tlMsCaService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsCa instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsCa createTlMsCa(@RequestBody TlMsCa tlMsCa) {
		LOGGER.debug("Create TlMsCa with information: {}" , tlMsCa);

		tlMsCa = tlMsCaService.create(tlMsCa);
		LOGGER.debug("Created TlMsCa with information: {}" , tlMsCa);

	    return tlMsCa;
	}

    @ApiOperation(value = "Returns the TlMsCa instance associated with the given id.")
    @RequestMapping(value = "/{abIdry:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsCa getTlMsCa(@PathVariable("abIdry") Integer abIdry) {
        LOGGER.debug("Getting TlMsCa with id: {}" , abIdry);

        TlMsCa foundTlMsCa = tlMsCaService.getById(abIdry);
        LOGGER.debug("TlMsCa details with id: {}" , foundTlMsCa);

        return foundTlMsCa;
    }

    @ApiOperation(value = "Updates the TlMsCa instance associated with the given id.")
    @RequestMapping(value = "/{abIdry:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsCa editTlMsCa(@PathVariable("abIdry") Integer abIdry, @RequestBody TlMsCa tlMsCa) {
        LOGGER.debug("Editing TlMsCa with id: {}" , tlMsCa.getAbIdry());

        tlMsCa.setAbIdry(abIdry);
        tlMsCa = tlMsCaService.update(tlMsCa);
        LOGGER.debug("TlMsCa details with id: {}" , tlMsCa);

        return tlMsCa;
    }
    
    @ApiOperation(value = "Partially updates the TlMsCa instance associated with the given id.")
    @RequestMapping(value = "/{abIdry:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsCa patchTlMsCa(@PathVariable("abIdry") Integer abIdry, @RequestBody @MapTo(TlMsCa.class) Map<String, Object> tlMsCaPatch) {
        LOGGER.debug("Partially updating TlMsCa with id: {}" , abIdry);

        TlMsCa tlMsCa = tlMsCaService.partialUpdate(abIdry, tlMsCaPatch);
        LOGGER.debug("TlMsCa details after partial update: {}" , tlMsCa);

        return tlMsCa;
    }

    @ApiOperation(value = "Deletes the TlMsCa instance associated with the given id.")
    @RequestMapping(value = "/{abIdry:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsCa(@PathVariable("abIdry") Integer abIdry) {
        LOGGER.debug("Deleting TlMsCa with id: {}" , abIdry);

        TlMsCa deletedTlMsCa = tlMsCaService.delete(abIdry);

        return deletedTlMsCa != null;
    }

    /**
     * @deprecated Use {@link #findTlMsCas(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsCa instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsCa> searchTlMsCasByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsCas list by query filter:{}", (Object) queryFilters);
        return tlMsCaService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsCa instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsCa> findTlMsCas(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsCas list by filter:", query);
        return tlMsCaService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsCa instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsCa> filterTlMsCas(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsCas list by filter", query);
        return tlMsCaService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsCas(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsCaService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsCasAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsCa.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsCaService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsCa instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsCas( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsCas");
		return tlMsCaService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsCaAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsCaService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsCaService instance
	 */
	protected void setTlMsCaService(TlMsCaService service) {
		this.tlMsCaService = service;
	}

}