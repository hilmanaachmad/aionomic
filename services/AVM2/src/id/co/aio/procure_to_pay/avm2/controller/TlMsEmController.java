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

import id.co.aio.procure_to_pay.avm2.TlMsEm;
import id.co.aio.procure_to_pay.avm2.service.TlMsEmService;


/**
 * Controller object for domain model class TlMsEm.
 * @see TlMsEm
 */
@RestController("AVM2.TlMsEmController")
@Api(value = "TlMsEmController", description = "Exposes APIs to work with TlMsEm resource.")
@RequestMapping("/AVM2/TlMsEm")
public class TlMsEmController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsEmController.class);

    @Autowired
	@Qualifier("AVM2.TlMsEmService")
	private TlMsEmService tlMsEmService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsEm instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsEm createTlMsEm(@RequestBody TlMsEm tlMsEm) {
		LOGGER.debug("Create TlMsEm with information: {}" , tlMsEm);

		tlMsEm = tlMsEmService.create(tlMsEm);
		LOGGER.debug("Created TlMsEm with information: {}" , tlMsEm);

	    return tlMsEm;
	}

    @ApiOperation(value = "Returns the TlMsEm instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsEm getTlMsEm(@PathVariable("abIdil") Integer abIdil) {
        LOGGER.debug("Getting TlMsEm with id: {}" , abIdil);

        TlMsEm foundTlMsEm = tlMsEmService.getById(abIdil);
        LOGGER.debug("TlMsEm details with id: {}" , foundTlMsEm);

        return foundTlMsEm;
    }

    @ApiOperation(value = "Updates the TlMsEm instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsEm editTlMsEm(@PathVariable("abIdil") Integer abIdil, @RequestBody TlMsEm tlMsEm) {
        LOGGER.debug("Editing TlMsEm with id: {}" , tlMsEm.getAbIdil());

        tlMsEm.setAbIdil(abIdil);
        tlMsEm = tlMsEmService.update(tlMsEm);
        LOGGER.debug("TlMsEm details with id: {}" , tlMsEm);

        return tlMsEm;
    }
    
    @ApiOperation(value = "Partially updates the TlMsEm instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsEm patchTlMsEm(@PathVariable("abIdil") Integer abIdil, @RequestBody @MapTo(TlMsEm.class) Map<String, Object> tlMsEmPatch) {
        LOGGER.debug("Partially updating TlMsEm with id: {}" , abIdil);

        TlMsEm tlMsEm = tlMsEmService.partialUpdate(abIdil, tlMsEmPatch);
        LOGGER.debug("TlMsEm details after partial update: {}" , tlMsEm);

        return tlMsEm;
    }

    @ApiOperation(value = "Deletes the TlMsEm instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsEm(@PathVariable("abIdil") Integer abIdil) {
        LOGGER.debug("Deleting TlMsEm with id: {}" , abIdil);

        TlMsEm deletedTlMsEm = tlMsEmService.delete(abIdil);

        return deletedTlMsEm != null;
    }

    /**
     * @deprecated Use {@link #findTlMsEms(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsEm instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsEm> searchTlMsEmsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsEms list by query filter:{}", (Object) queryFilters);
        return tlMsEmService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsEm instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsEm> findTlMsEms(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsEms list by filter:", query);
        return tlMsEmService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsEm instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsEm> filterTlMsEms(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsEms list by filter", query);
        return tlMsEmService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsEms(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsEmService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsEmsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsEm.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsEmService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsEm instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsEms( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsEms");
		return tlMsEmService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsEmAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsEmService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsEmService instance
	 */
	protected void setTlMsEmService(TlMsEmService service) {
		this.tlMsEmService = service;
	}

}