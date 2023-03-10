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

import id.co.aio.procure_to_pay.avm2.TlMsEmDel;
import id.co.aio.procure_to_pay.avm2.service.TlMsEmDelService;


/**
 * Controller object for domain model class TlMsEmDel.
 * @see TlMsEmDel
 */
@RestController("AVM2.TlMsEmDelController")
@Api(value = "TlMsEmDelController", description = "Exposes APIs to work with TlMsEmDel resource.")
@RequestMapping("/AVM2/TlMsEmDel")
public class TlMsEmDelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsEmDelController.class);

    @Autowired
	@Qualifier("AVM2.TlMsEmDelService")
	private TlMsEmDelService tlMsEmDelService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsEmDel instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsEmDel createTlMsEmDel(@RequestBody TlMsEmDel tlMsEmDel) {
		LOGGER.debug("Create TlMsEmDel with information: {}" , tlMsEmDel);

		tlMsEmDel = tlMsEmDelService.create(tlMsEmDel);
		LOGGER.debug("Created TlMsEmDel with information: {}" , tlMsEmDel);

	    return tlMsEmDel;
	}

    @ApiOperation(value = "Returns the TlMsEmDel instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsEmDel getTlMsEmDel(@PathVariable("abIdil") Integer abIdil) {
        LOGGER.debug("Getting TlMsEmDel with id: {}" , abIdil);

        TlMsEmDel foundTlMsEmDel = tlMsEmDelService.getById(abIdil);
        LOGGER.debug("TlMsEmDel details with id: {}" , foundTlMsEmDel);

        return foundTlMsEmDel;
    }

    @ApiOperation(value = "Updates the TlMsEmDel instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsEmDel editTlMsEmDel(@PathVariable("abIdil") Integer abIdil, @RequestBody TlMsEmDel tlMsEmDel) {
        LOGGER.debug("Editing TlMsEmDel with id: {}" , tlMsEmDel.getAbIdil());

        tlMsEmDel.setAbIdil(abIdil);
        tlMsEmDel = tlMsEmDelService.update(tlMsEmDel);
        LOGGER.debug("TlMsEmDel details with id: {}" , tlMsEmDel);

        return tlMsEmDel;
    }
    
    @ApiOperation(value = "Partially updates the TlMsEmDel instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsEmDel patchTlMsEmDel(@PathVariable("abIdil") Integer abIdil, @RequestBody @MapTo(TlMsEmDel.class) Map<String, Object> tlMsEmDelPatch) {
        LOGGER.debug("Partially updating TlMsEmDel with id: {}" , abIdil);

        TlMsEmDel tlMsEmDel = tlMsEmDelService.partialUpdate(abIdil, tlMsEmDelPatch);
        LOGGER.debug("TlMsEmDel details after partial update: {}" , tlMsEmDel);

        return tlMsEmDel;
    }

    @ApiOperation(value = "Deletes the TlMsEmDel instance associated with the given id.")
    @RequestMapping(value = "/{abIdil:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsEmDel(@PathVariable("abIdil") Integer abIdil) {
        LOGGER.debug("Deleting TlMsEmDel with id: {}" , abIdil);

        TlMsEmDel deletedTlMsEmDel = tlMsEmDelService.delete(abIdil);

        return deletedTlMsEmDel != null;
    }

    /**
     * @deprecated Use {@link #findTlMsEmDels(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsEmDel instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsEmDel> searchTlMsEmDelsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsEmDels list by query filter:{}", (Object) queryFilters);
        return tlMsEmDelService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsEmDel instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsEmDel> findTlMsEmDels(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsEmDels list by filter:", query);
        return tlMsEmDelService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsEmDel instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsEmDel> filterTlMsEmDels(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsEmDels list by filter", query);
        return tlMsEmDelService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsEmDels(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsEmDelService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsEmDelsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsEmDel.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsEmDelService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsEmDel instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsEmDels( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsEmDels");
		return tlMsEmDelService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsEmDelAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsEmDelService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsEmDelService instance
	 */
	protected void setTlMsEmDelService(TlMsEmDelService service) {
		this.tlMsEmDelService = service;
	}

}