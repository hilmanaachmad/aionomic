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

import id.co.aio.procure_to_pay.avm2.TlMsPuonFina;
import id.co.aio.procure_to_pay.avm2.service.TlMsPuonFinaService;


/**
 * Controller object for domain model class TlMsPuonFina.
 * @see TlMsPuonFina
 */
@RestController("AVM2.TlMsPuonFinaController")
@Api(value = "TlMsPuonFinaController", description = "Exposes APIs to work with TlMsPuonFina resource.")
@RequestMapping("/AVM2/TlMsPuonFina")
public class TlMsPuonFinaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsPuonFinaController.class);

    @Autowired
	@Qualifier("AVM2.TlMsPuonFinaService")
	private TlMsPuonFinaService tlMsPuonFinaService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsPuonFina instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPuonFina createTlMsPuonFina(@RequestBody TlMsPuonFina tlMsPuonFina) {
		LOGGER.debug("Create TlMsPuonFina with information: {}" , tlMsPuonFina);

		tlMsPuonFina = tlMsPuonFinaService.create(tlMsPuonFina);
		LOGGER.debug("Created TlMsPuonFina with information: {}" , tlMsPuonFina);

	    return tlMsPuonFina;
	}

    @ApiOperation(value = "Returns the TlMsPuonFina instance associated with the given id.")
    @RequestMapping(value = "/{code:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPuonFina getTlMsPuonFina(@PathVariable("code") Double code) {
        LOGGER.debug("Getting TlMsPuonFina with id: {}" , code);

        TlMsPuonFina foundTlMsPuonFina = tlMsPuonFinaService.getById(code);
        LOGGER.debug("TlMsPuonFina details with id: {}" , foundTlMsPuonFina);

        return foundTlMsPuonFina;
    }

    @ApiOperation(value = "Updates the TlMsPuonFina instance associated with the given id.")
    @RequestMapping(value = "/{code:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPuonFina editTlMsPuonFina(@PathVariable("code") Double code, @RequestBody TlMsPuonFina tlMsPuonFina) {
        LOGGER.debug("Editing TlMsPuonFina with id: {}" , tlMsPuonFina.getCode());

        tlMsPuonFina.setCode(code);
        tlMsPuonFina = tlMsPuonFinaService.update(tlMsPuonFina);
        LOGGER.debug("TlMsPuonFina details with id: {}" , tlMsPuonFina);

        return tlMsPuonFina;
    }
    
    @ApiOperation(value = "Partially updates the TlMsPuonFina instance associated with the given id.")
    @RequestMapping(value = "/{code:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPuonFina patchTlMsPuonFina(@PathVariable("code") Double code, @RequestBody @MapTo(TlMsPuonFina.class) Map<String, Object> tlMsPuonFinaPatch) {
        LOGGER.debug("Partially updating TlMsPuonFina with id: {}" , code);

        TlMsPuonFina tlMsPuonFina = tlMsPuonFinaService.partialUpdate(code, tlMsPuonFinaPatch);
        LOGGER.debug("TlMsPuonFina details after partial update: {}" , tlMsPuonFina);

        return tlMsPuonFina;
    }

    @ApiOperation(value = "Deletes the TlMsPuonFina instance associated with the given id.")
    @RequestMapping(value = "/{code:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsPuonFina(@PathVariable("code") Double code) {
        LOGGER.debug("Deleting TlMsPuonFina with id: {}" , code);

        TlMsPuonFina deletedTlMsPuonFina = tlMsPuonFinaService.delete(code);

        return deletedTlMsPuonFina != null;
    }

    /**
     * @deprecated Use {@link #findTlMsPuonFinas(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsPuonFina instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsPuonFina> searchTlMsPuonFinasByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsPuonFinas list by query filter:{}", (Object) queryFilters);
        return tlMsPuonFinaService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsPuonFina instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsPuonFina> findTlMsPuonFinas(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsPuonFinas list by filter:", query);
        return tlMsPuonFinaService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsPuonFina instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsPuonFina> filterTlMsPuonFinas(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsPuonFinas list by filter", query);
        return tlMsPuonFinaService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsPuonFinas(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsPuonFinaService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsPuonFinasAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsPuonFina.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsPuonFinaService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsPuonFina instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsPuonFinas( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsPuonFinas");
		return tlMsPuonFinaService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsPuonFinaAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsPuonFinaService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsPuonFinaService instance
	 */
	protected void setTlMsPuonFinaService(TlMsPuonFinaService service) {
		this.tlMsPuonFinaService = service;
	}

}