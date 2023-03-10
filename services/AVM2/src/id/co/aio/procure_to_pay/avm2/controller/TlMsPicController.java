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

import id.co.aio.procure_to_pay.avm2.TlMsPic;
import id.co.aio.procure_to_pay.avm2.service.TlMsPicService;


/**
 * Controller object for domain model class TlMsPic.
 * @see TlMsPic
 */
@RestController("AVM2.TlMsPicController")
@Api(value = "TlMsPicController", description = "Exposes APIs to work with TlMsPic resource.")
@RequestMapping("/AVM2/TlMsPic")
public class TlMsPicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsPicController.class);

    @Autowired
	@Qualifier("AVM2.TlMsPicService")
	private TlMsPicService tlMsPicService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsPic instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPic createTlMsPic(@RequestBody TlMsPic tlMsPic) {
		LOGGER.debug("Create TlMsPic with information: {}" , tlMsPic);

		tlMsPic = tlMsPicService.create(tlMsPic);
		LOGGER.debug("Created TlMsPic with information: {}" , tlMsPic);

	    return tlMsPic;
	}

    @ApiOperation(value = "Returns the TlMsPic instance associated with the given id.")
    @RequestMapping(value = "/{abIdPic:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPic getTlMsPic(@PathVariable("abIdPic") Integer abIdPic) {
        LOGGER.debug("Getting TlMsPic with id: {}" , abIdPic);

        TlMsPic foundTlMsPic = tlMsPicService.getById(abIdPic);
        LOGGER.debug("TlMsPic details with id: {}" , foundTlMsPic);

        return foundTlMsPic;
    }

    @ApiOperation(value = "Updates the TlMsPic instance associated with the given id.")
    @RequestMapping(value = "/{abIdPic:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPic editTlMsPic(@PathVariable("abIdPic") Integer abIdPic, @RequestBody TlMsPic tlMsPic) {
        LOGGER.debug("Editing TlMsPic with id: {}" , tlMsPic.getAbIdPic());

        tlMsPic.setAbIdPic(abIdPic);
        tlMsPic = tlMsPicService.update(tlMsPic);
        LOGGER.debug("TlMsPic details with id: {}" , tlMsPic);

        return tlMsPic;
    }
    
    @ApiOperation(value = "Partially updates the TlMsPic instance associated with the given id.")
    @RequestMapping(value = "/{abIdPic:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsPic patchTlMsPic(@PathVariable("abIdPic") Integer abIdPic, @RequestBody @MapTo(TlMsPic.class) Map<String, Object> tlMsPicPatch) {
        LOGGER.debug("Partially updating TlMsPic with id: {}" , abIdPic);

        TlMsPic tlMsPic = tlMsPicService.partialUpdate(abIdPic, tlMsPicPatch);
        LOGGER.debug("TlMsPic details after partial update: {}" , tlMsPic);

        return tlMsPic;
    }

    @ApiOperation(value = "Deletes the TlMsPic instance associated with the given id.")
    @RequestMapping(value = "/{abIdPic:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsPic(@PathVariable("abIdPic") Integer abIdPic) {
        LOGGER.debug("Deleting TlMsPic with id: {}" , abIdPic);

        TlMsPic deletedTlMsPic = tlMsPicService.delete(abIdPic);

        return deletedTlMsPic != null;
    }

    /**
     * @deprecated Use {@link #findTlMsPics(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsPic instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsPic> searchTlMsPicsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsPics list by query filter:{}", (Object) queryFilters);
        return tlMsPicService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsPic instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsPic> findTlMsPics(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsPics list by filter:", query);
        return tlMsPicService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsPic instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsPic> filterTlMsPics(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsPics list by filter", query);
        return tlMsPicService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsPics(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsPicService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsPicsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsPic.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsPicService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsPic instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsPics( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsPics");
		return tlMsPicService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsPicAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsPicService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsPicService instance
	 */
	protected void setTlMsPicService(TlMsPicService service) {
		this.tlMsPicService = service;
	}

}