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

import id.co.aio.procure_to_pay.avm2.TlMsCategoryTemp;
import id.co.aio.procure_to_pay.avm2.service.TlMsCategoryTempService;


/**
 * Controller object for domain model class TlMsCategoryTemp.
 * @see TlMsCategoryTemp
 */
@RestController("AVM2.TlMsCategoryTempController")
@Api(value = "TlMsCategoryTempController", description = "Exposes APIs to work with TlMsCategoryTemp resource.")
@RequestMapping("/AVM2/TlMsCategoryTemp")
public class TlMsCategoryTempController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsCategoryTempController.class);

    @Autowired
	@Qualifier("AVM2.TlMsCategoryTempService")
	private TlMsCategoryTempService tlMsCategoryTempService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsCategoryTemp instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsCategoryTemp createTlMsCategoryTemp(@RequestBody TlMsCategoryTemp tlMsCategoryTemp) {
		LOGGER.debug("Create TlMsCategoryTemp with information: {}" , tlMsCategoryTemp);

		tlMsCategoryTemp = tlMsCategoryTempService.create(tlMsCategoryTemp);
		LOGGER.debug("Created TlMsCategoryTemp with information: {}" , tlMsCategoryTemp);

	    return tlMsCategoryTemp;
	}

    @ApiOperation(value = "Returns the TlMsCategoryTemp instance associated with the given id.")
    @RequestMapping(value = "/{caId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsCategoryTemp getTlMsCategoryTemp(@PathVariable("caId") Integer caId) {
        LOGGER.debug("Getting TlMsCategoryTemp with id: {}" , caId);

        TlMsCategoryTemp foundTlMsCategoryTemp = tlMsCategoryTempService.getById(caId);
        LOGGER.debug("TlMsCategoryTemp details with id: {}" , foundTlMsCategoryTemp);

        return foundTlMsCategoryTemp;
    }

    @ApiOperation(value = "Updates the TlMsCategoryTemp instance associated with the given id.")
    @RequestMapping(value = "/{caId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsCategoryTemp editTlMsCategoryTemp(@PathVariable("caId") Integer caId, @RequestBody TlMsCategoryTemp tlMsCategoryTemp) {
        LOGGER.debug("Editing TlMsCategoryTemp with id: {}" , tlMsCategoryTemp.getCaId());

        tlMsCategoryTemp.setCaId(caId);
        tlMsCategoryTemp = tlMsCategoryTempService.update(tlMsCategoryTemp);
        LOGGER.debug("TlMsCategoryTemp details with id: {}" , tlMsCategoryTemp);

        return tlMsCategoryTemp;
    }
    
    @ApiOperation(value = "Partially updates the TlMsCategoryTemp instance associated with the given id.")
    @RequestMapping(value = "/{caId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsCategoryTemp patchTlMsCategoryTemp(@PathVariable("caId") Integer caId, @RequestBody @MapTo(TlMsCategoryTemp.class) Map<String, Object> tlMsCategoryTempPatch) {
        LOGGER.debug("Partially updating TlMsCategoryTemp with id: {}" , caId);

        TlMsCategoryTemp tlMsCategoryTemp = tlMsCategoryTempService.partialUpdate(caId, tlMsCategoryTempPatch);
        LOGGER.debug("TlMsCategoryTemp details after partial update: {}" , tlMsCategoryTemp);

        return tlMsCategoryTemp;
    }

    @ApiOperation(value = "Deletes the TlMsCategoryTemp instance associated with the given id.")
    @RequestMapping(value = "/{caId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsCategoryTemp(@PathVariable("caId") Integer caId) {
        LOGGER.debug("Deleting TlMsCategoryTemp with id: {}" , caId);

        TlMsCategoryTemp deletedTlMsCategoryTemp = tlMsCategoryTempService.delete(caId);

        return deletedTlMsCategoryTemp != null;
    }

    /**
     * @deprecated Use {@link #findTlMsCategoryTemps(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsCategoryTemp instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsCategoryTemp> searchTlMsCategoryTempsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsCategoryTemps list by query filter:{}", (Object) queryFilters);
        return tlMsCategoryTempService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsCategoryTemp instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsCategoryTemp> findTlMsCategoryTemps(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsCategoryTemps list by filter:", query);
        return tlMsCategoryTempService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsCategoryTemp instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsCategoryTemp> filterTlMsCategoryTemps(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsCategoryTemps list by filter", query);
        return tlMsCategoryTempService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsCategoryTemps(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsCategoryTempService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsCategoryTempsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsCategoryTemp.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsCategoryTempService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsCategoryTemp instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsCategoryTemps( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsCategoryTemps");
		return tlMsCategoryTempService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsCategoryTempAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsCategoryTempService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsCategoryTempService instance
	 */
	protected void setTlMsCategoryTempService(TlMsCategoryTempService service) {
		this.tlMsCategoryTempService = service;
	}

}