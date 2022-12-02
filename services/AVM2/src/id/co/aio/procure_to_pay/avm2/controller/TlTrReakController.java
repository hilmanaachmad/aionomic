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

import id.co.aio.procure_to_pay.avm2.TlTrReak;
import id.co.aio.procure_to_pay.avm2.TlTrReakId;
import id.co.aio.procure_to_pay.avm2.service.TlTrReakService;


/**
 * Controller object for domain model class TlTrReak.
 * @see TlTrReak
 */
@RestController("AVM2.TlTrReakController")
@Api(value = "TlTrReakController", description = "Exposes APIs to work with TlTrReak resource.")
@RequestMapping("/AVM2/TlTrReak")
public class TlTrReakController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlTrReakController.class);

    @Autowired
	@Qualifier("AVM2.TlTrReakService")
	private TlTrReakService tlTrReakService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlTrReak instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrReak createTlTrReak(@RequestBody TlTrReak tlTrReak) {
		LOGGER.debug("Create TlTrReak with information: {}" , tlTrReak);

		tlTrReak = tlTrReakService.create(tlTrReak);
		LOGGER.debug("Created TlTrReak with information: {}" , tlTrReak);

	    return tlTrReak;
	}

    @ApiOperation(value = "Returns the TlTrReak instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrReak getTlTrReak(@RequestParam("abIdak") Long abIdak, @RequestParam("abIdsi") Integer abIdsi) {

        TlTrReakId tltrreakId = new TlTrReakId();
        tltrreakId.setAbIdak(abIdak);
        tltrreakId.setAbIdsi(abIdsi);

        LOGGER.debug("Getting TlTrReak with id: {}" , tltrreakId);
        TlTrReak tlTrReak = tlTrReakService.getById(tltrreakId);
        LOGGER.debug("TlTrReak details with id: {}" , tlTrReak);

        return tlTrReak;
    }



    @ApiOperation(value = "Updates the TlTrReak instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrReak editTlTrReak(@RequestParam("abIdak") Long abIdak, @RequestParam("abIdsi") Integer abIdsi, @RequestBody TlTrReak tlTrReak) {

        tlTrReak.setAbIdak(abIdak);
        tlTrReak.setAbIdsi(abIdsi);

        LOGGER.debug("TlTrReak details with id is updated with: {}" , tlTrReak);

        return tlTrReakService.update(tlTrReak);
    }

	@ApiOperation(value = "Partially updates the  TlTrReak instance associated with the given composite-id.")
	@RequestMapping(value = "/composite-id", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrReak patchTlTrReak(@RequestParam("abIdak") Long abIdak, @RequestParam("abIdsi") Integer abIdsi, @RequestBody @MapTo(TlTrReak.class) Map<String, Object> tlTrReakPatch) {

        TlTrReakId tltrreakId = new TlTrReakId();
        tltrreakId.setAbIdak(abIdak);
        tltrreakId.setAbIdsi(abIdsi);
        LOGGER.debug("Partially updating TlTrReak with id: {}" , tltrreakId);

        TlTrReak tlTrReak = tlTrReakService.partialUpdate(tltrreakId, tlTrReakPatch);
        LOGGER.debug("TlTrReak details after partial update: {}" , tlTrReak);

        return tlTrReak;
    }


    @ApiOperation(value = "Deletes the TlTrReak instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlTrReak(@RequestParam("abIdak") Long abIdak, @RequestParam("abIdsi") Integer abIdsi) {

        TlTrReakId tltrreakId = new TlTrReakId();
        tltrreakId.setAbIdak(abIdak);
        tltrreakId.setAbIdsi(abIdsi);

        LOGGER.debug("Deleting TlTrReak with id: {}" , tltrreakId);
        TlTrReak tlTrReak = tlTrReakService.delete(tltrreakId);

        return tlTrReak != null;
    }


    /**
     * @deprecated Use {@link #findTlTrReaks(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlTrReak instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlTrReak> searchTlTrReaksByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlTrReaks list by query filter:{}", (Object) queryFilters);
        return tlTrReakService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlTrReak instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlTrReak> findTlTrReaks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlTrReaks list by filter:", query);
        return tlTrReakService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlTrReak instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlTrReak> filterTlTrReaks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlTrReaks list by filter", query);
        return tlTrReakService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlTrReaks(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlTrReakService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlTrReaksAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlTrReak.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlTrReakService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlTrReak instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlTrReaks( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlTrReaks");
		return tlTrReakService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlTrReakAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlTrReakService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlTrReakService instance
	 */
	protected void setTlTrReakService(TlTrReakService service) {
		this.tlTrReakService = service;
	}

}