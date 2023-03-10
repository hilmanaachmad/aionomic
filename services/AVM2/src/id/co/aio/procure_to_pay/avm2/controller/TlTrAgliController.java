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

import id.co.aio.procure_to_pay.avm2.TlTrAgli;
import id.co.aio.procure_to_pay.avm2.TlTrAgliId;
import id.co.aio.procure_to_pay.avm2.service.TlTrAgliService;


/**
 * Controller object for domain model class TlTrAgli.
 * @see TlTrAgli
 */
@RestController("AVM2.TlTrAgliController")
@Api(value = "TlTrAgliController", description = "Exposes APIs to work with TlTrAgli resource.")
@RequestMapping("/AVM2/TlTrAgli")
public class TlTrAgliController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlTrAgliController.class);

    @Autowired
	@Qualifier("AVM2.TlTrAgliService")
	private TlTrAgliService tlTrAgliService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlTrAgli instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrAgli createTlTrAgli(@RequestBody TlTrAgli tlTrAgli) {
		LOGGER.debug("Create TlTrAgli with information: {}" , tlTrAgli);

		tlTrAgli = tlTrAgliService.create(tlTrAgli);
		LOGGER.debug("Created TlTrAgli with information: {}" , tlTrAgli);

	    return tlTrAgli;
	}

    @ApiOperation(value = "Returns the TlTrAgli instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrAgli getTlTrAgli(@RequestParam("abIdagli") Integer abIdagli, @RequestParam("abIdagch") Integer abIdagch, @RequestParam("abIdag") Integer abIdag) {

        TlTrAgliId tltragliId = new TlTrAgliId();
        tltragliId.setAbIdagli(abIdagli);
        tltragliId.setAbIdagch(abIdagch);
        tltragliId.setAbIdag(abIdag);

        LOGGER.debug("Getting TlTrAgli with id: {}" , tltragliId);
        TlTrAgli tlTrAgli = tlTrAgliService.getById(tltragliId);
        LOGGER.debug("TlTrAgli details with id: {}" , tlTrAgli);

        return tlTrAgli;
    }



    @ApiOperation(value = "Updates the TlTrAgli instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrAgli editTlTrAgli(@RequestParam("abIdagli") Integer abIdagli, @RequestParam("abIdagch") Integer abIdagch, @RequestParam("abIdag") Integer abIdag, @RequestBody TlTrAgli tlTrAgli) {

        tlTrAgli.setAbIdagli(abIdagli);
        tlTrAgli.setAbIdagch(abIdagch);
        tlTrAgli.setAbIdag(abIdag);

        LOGGER.debug("TlTrAgli details with id is updated with: {}" , tlTrAgli);

        return tlTrAgliService.update(tlTrAgli);
    }

	@ApiOperation(value = "Partially updates the  TlTrAgli instance associated with the given composite-id.")
	@RequestMapping(value = "/composite-id", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlTrAgli patchTlTrAgli(@RequestParam("abIdagli") Integer abIdagli, @RequestParam("abIdagch") Integer abIdagch, @RequestParam("abIdag") Integer abIdag, @RequestBody @MapTo(TlTrAgli.class) Map<String, Object> tlTrAgliPatch) {

        TlTrAgliId tltragliId = new TlTrAgliId();
        tltragliId.setAbIdagli(abIdagli);
        tltragliId.setAbIdagch(abIdagch);
        tltragliId.setAbIdag(abIdag);
        LOGGER.debug("Partially updating TlTrAgli with id: {}" , tltragliId);

        TlTrAgli tlTrAgli = tlTrAgliService.partialUpdate(tltragliId, tlTrAgliPatch);
        LOGGER.debug("TlTrAgli details after partial update: {}" , tlTrAgli);

        return tlTrAgli;
    }


    @ApiOperation(value = "Deletes the TlTrAgli instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlTrAgli(@RequestParam("abIdagli") Integer abIdagli, @RequestParam("abIdagch") Integer abIdagch, @RequestParam("abIdag") Integer abIdag) {

        TlTrAgliId tltragliId = new TlTrAgliId();
        tltragliId.setAbIdagli(abIdagli);
        tltragliId.setAbIdagch(abIdagch);
        tltragliId.setAbIdag(abIdag);

        LOGGER.debug("Deleting TlTrAgli with id: {}" , tltragliId);
        TlTrAgli tlTrAgli = tlTrAgliService.delete(tltragliId);

        return tlTrAgli != null;
    }


    /**
     * @deprecated Use {@link #findTlTrAglis(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlTrAgli instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlTrAgli> searchTlTrAglisByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlTrAglis list by query filter:{}", (Object) queryFilters);
        return tlTrAgliService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlTrAgli instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlTrAgli> findTlTrAglis(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlTrAglis list by filter:", query);
        return tlTrAgliService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlTrAgli instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlTrAgli> filterTlTrAglis(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlTrAglis list by filter", query);
        return tlTrAgliService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlTrAglis(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlTrAgliService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlTrAglisAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlTrAgli.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlTrAgliService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlTrAgli instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlTrAglis( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlTrAglis");
		return tlTrAgliService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlTrAgliAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlTrAgliService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlTrAgliService instance
	 */
	protected void setTlTrAgliService(TlTrAgliService service) {
		this.tlTrAgliService = service;
	}

}